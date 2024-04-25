/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.Gestores;

import hospitalidad.beans.AutobusBean;
import hospitalidad.beans.PersonaBean;
import hospitalidad.utils.ConectorBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author vPalomo
 */
public class GestionAutobusesBD {
    /**
     * Guarda (si el autobus tiene sitio libre) la persona en el autobus. Si no hay sitio devuelve false
     * y no se asigna la persona al autobus
     * @param autobus
     * @param persona
     * @return 
     */
    public static boolean guardarPasajero(AutobusBean autobus, PersonaBean persona) {
        if(getPlazasLibres(autobus.getIdAutobus(), "1".equals(persona.getIdTipoViajero()))<=0) return false;
        return setPasajeroAutobus(autobus.getIdAutobus(), persona.getIdPersona());
    }

    public static int setAutobus(AutobusBean autobus){
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            // INSERT INTO `hospitalidad`.`autobuses` (`Descripcion`, `PlazasNoEnfermos`, `PlazasEnfermos`, `Observaciones`, `idViaje`) VALUES ('Nº 2', '10', '9', 'Ninguna', '1');
            
            PreparedStatement insert1 = conexion.prepareStatement("INSERT INTO hospitalidad.autobuses(Descripcion, PlazasNoEnfermos, PlazasEnfermos, Observaciones, idViaje) VALUES (?,?,?,?,?);");
            insert1.setString(1, ""+autobus.getDescripcion());
            insert1.setString(2, ""+autobus.getPlazas(false));
            insert1.setString(3, ""+autobus.getPlazas(true));
            insert1.setString(4, autobus.getObservaciones());
            insert1.setString(5, autobus.getIdViaje());
            System.out.println(insert1);
            int fila = insert1.executeUpdate();
            return fila; //Correcto

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestionViajesBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
    /**
     * Devuelve el numero de plazas ocupadas de un autobus según el tipo de pasajero
     *
     * @param idAutobus
     * @return
     */
    public static int getPlazasOcupadas(String idAutobus, boolean enfermo) {
        int plazas = 0;
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta;
            if(enfermo)
                consulta= conexion.prepareStatement(
                    "SELECT COUNT(*) AS plazas FROM personas, relpersonaautobus WHERE idAutobus=? and "
                            + " personas.idPersona=relpersonaautobus.idPersona and"
                            + " personas.ActualTipoViajero=1 ");
            else
                consulta= conexion.prepareStatement(
                    "SELECT COUNT(*) AS plazas FROM personas, relpersonaautobus WHERE idAutobus=? and "
                            + " personas.idPersona=relpersonaautobus.idPersona and"
                            + " personas.ActualTipoViajero!=1 ");
            
            consulta.setString(1, idAutobus);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
               plazas=resultado.getInt(1);
                System.out.println("Plazas libres enfermos "+enfermo+": "+plazas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {

        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
        }
        return plazas;
    }

    /**
     * Devuelve el numero de plazas para enfermo o para no enfermos
     * @param idAutobus
     * @param enfermo
     * @return 
     */
    public static int getPlazasLibres(String idAutobus, boolean enfermo) {
        int plazasO = getPlazasOcupadas(idAutobus, enfermo);
        int plazasT = getNumPlazas(idAutobus, enfermo);
        return plazasT - plazasO;
    }

    /**
     * 
     * @param idAutobus
     * @param enfermo
     * @return 
     */
    public static int getNumPlazas(String idAutobus, boolean enfermo) {
        int plazas = 0;
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta;
            if(!enfermo)
            consulta = conexion.prepareStatement(
                    "select PlazasNoEnfermos "
                    + "FROM autobuses "
                    + "WHERE idAutobus=?");
            else
                consulta = conexion.prepareStatement(
                    "select PlazasEnfermos "
                    + "FROM autobuses "
                    + "WHERE idAutobus=?");

            consulta.setString(1, idAutobus);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
               plazas=resultado.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {

        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
        }
        return plazas;
    }
    
/**
 * Añade una lista de personas a un autobus, Hay que indicar si son enfermos o no
 * @param listaPasajeros
 * @param idAutobus
 * @param enfermo
 * @return 
 */
    public static String añadirPasajerosAutobus(ArrayList<PersonaBean> listaPasajeros, String idAutobus){
        //boolean enfermo=true;
        String result="";
        int plazasLibresEnfermos=getPlazasLibres(idAutobus, true);
        int plazasLibresNoEnfermos=getPlazasLibres(idAutobus, false);
        int plazasSolicitadasEnfernos=0;
        int plazasSolicitadasNoEnfernos=0;
        for (PersonaBean pasajero : listaPasajeros) {
            if("1".equals(pasajero.getIdTipoViajero())){
                plazasSolicitadasEnfernos++;
            }else{
                plazasSolicitadasNoEnfernos++;
            }
        }
        
        if(plazasLibresEnfermos<plazasSolicitadasEnfernos){
            return "No hay sufucuentes plazas libres para enfremos\n"+
                    "Plazas libres: "+plazasLibresEnfermos+
                    "\nPlazas solicitadas: "+plazasSolicitadasEnfernos;
        }
        if(plazasLibresNoEnfermos<plazasSolicitadasNoEnfernos){
            return "No hay sufucuentes plazas libres para hospitalarios\n"+
                    "Plazas libres: "+plazasLibresNoEnfermos+
                    "\nPlazas solicitadas: "+plazasSolicitadasNoEnfernos;
        }
        int correctos=0;
        int errores=0;
        String nombresErrores="";
        for (PersonaBean persona: listaPasajeros){
            if(setPasajeroAutobus(idAutobus, persona.getIdPersona())){
                correctos++;
            }else{
                errores++;
                nombresErrores+="  "+persona.getApellidos()+", "+persona.getNombre()+"\n";
            }
        }
        if(errores==0){
            return "Asignación realizada correctamente";
        }else{
            return "Se han realizado "+correctos+" asignaciones correctamente\n"+
                    "Han dado error "+errores+" asignaciones\n"+
                    nombresErrores;
        }
        
    }
    /**
     * Añade los passajeros al autobus, aunque ya no comprueba que tenga plazas libres suficientes, eso ya debe venir comprobado
     * @param idAutobus
     * @param idPersona
     * @return 
     */
    public static boolean setPasajeroAutobus(String idAutobus, String idPersona) {
        //if(getPlazasLibres(idAutobus, enfermo)<=0) return false;
        boolean result = false;
        Connection conexion = null;

        try {
            conexion = ConectorBD.getConnection();
        
            PreparedStatement insert1 = conexion.prepareStatement(
                    "INSERT INTO relpersonaautobus VALUES (?,?)");
            insert1.setString(1, idAutobus);
            insert1.setString(2, idPersona);

            insert1.executeUpdate();
            
            return true;
            
        } catch (NamingException ex) {
            Logger.getLogger(GestionAutobusesBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionAutobusesBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
/*
    public static boolean guardaAutobus(AutobusBean autobus) {
        boolean result = false;

        return result;
    }

    public static AutobusBean consultaAutobus(String idAutobus) {
        AutobusBean autobus = new AutobusBean();

        return autobus;
    }
*/
    /**
     *
     * @param idAutobus
     * @return devuelve el arrayList de PersonaBean con los datos de los
     * pasajeros
     */
    public static ArrayList<PersonaBean> consultaPasajeros(String idAutobus) {
        ArrayList<PersonaBean> plazas = new ArrayList<PersonaBean>();

        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PersonaBean persona;
            PreparedStatement consulta = conexion.prepareStatement(
                    "SELECT personas.idPersona, DNI, Nombre, Apellidos "
                    + "FROM personas, relpersonaautobus "
                    + "WHERE personas.idPersona=relpersonaautobus.idPersona AND "
                    + "		relpersonaautobus.idAutobus=? "
                    + "ORDER BY Apellidos");
            consulta.setString(1, idAutobus);

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                persona = new PersonaBean();
                persona.setIdPersona(resultado.getString(1));
                persona.setDNI(resultado.getString(2));
                persona.setNombre(resultado.getString(3));
                persona.setApellidos(resultado.getString(4));
                plazas.add(persona);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {

        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
        }
        return plazas;
    }

    public static javax.swing.DefaultComboBoxModel getModeloComboAutobuses(String idViaje) {
        ArrayList<AutobusBean> lista = getListaAutobuses(idViaje);
        String[] autobuses = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            autobuses[i] = lista.get(i).toString();
        }
        return new javax.swing.DefaultComboBoxModel<>(autobuses);

    }
    
    public static javax.swing.DefaultComboBoxModel getModeloComboAutobusesConTodos(String idViaje) {
        ArrayList<AutobusBean> lista = getListaAutobuses(idViaje);
        String[] autobuses = new String[lista.size()+1];
        autobuses[0]="0 - Todos los autobuses";
        for (int i = 1; i < (lista.size()+1); i++) {
            autobuses[i] = lista.get(i-1).toString();
        }
        return new javax.swing.DefaultComboBoxModel<>(autobuses);

    }
    
    public static ArrayList<AutobusBean> getListaAutobuses(String idViaje) {
        ArrayList<AutobusBean> lista = new ArrayList();
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            AutobusBean autobus;
            PreparedStatement consulta = conexion.prepareStatement(
                    "select idAutobus, Descripcion, PlazasNoEnfermos, PlazasEnfermos, Observaciones, idViaje "
                    + "FROM autobuses "
                    + "WHERE idViaje=?");

            consulta.setString(1, idViaje);

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                autobus = new AutobusBean();
                autobus.setIdAutobus(resultado.getString(1));
                autobus.setDescripcion(resultado.getString(2));
                autobus.setPlazasNoEnfermos(resultado.getInt(3));
                autobus.setPlazasEnfermos(resultado.getInt(4));
                autobus.setObservaciones(resultado.getString(5));
                autobus.setIdViaje(resultado.getString(6));
                lista.add(autobus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {

        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
        }
        return lista;

    }

    /**
     * Devuelve un AutobusBean con los datos del autobus pasado como id
     *
     * @param idAutobus
     * @return
     */
    public static AutobusBean getDatosAutobus(String idAutobus) {
        AutobusBean autobus = new AutobusBean();
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "select idAutobus, Descripcion, PlazasEnfermos, PlazasNoEnfermos, Observaciones, idViaje "
                    + "FROM autobuses "
                    + "WHERE idAutobus=?");

            consulta.setString(1, idAutobus);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                autobus = new AutobusBean();
                autobus.setIdAutobus(resultado.getString(1));
                autobus.setDescripcion(resultado.getString(2));
                autobus.setPlazasEnfermos(resultado.getInt(3));
                autobus.setPlazasNoEnfermos(resultado.getInt(4));
                autobus.setObservaciones(resultado.getString(5));
                autobus.setIdViaje(resultado.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {

        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
        }
        return autobus;
    }
    /**
     * Devuelve los datos del autobus asignado a una persona en un viaje.
     * Si devuelve null es que la persona no tiene asignado aun ningún autobus
     * @param idPersona
     * @param idViaje
     * @return Devuelve un objeto AutobusBean con los datos del autobus
     */
    public static AutobusBean getDatosAutobus(String idPersona, String idViaje) {
        AutobusBean autobus = new AutobusBean();
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "SELECT autobuses.idAutobus, Descripcion, PlazasEnfermos, PlazasNoEnfermos, Observaciones, idViaje " +
                    "FROM autobuses, relpersonaautobus " +
                    "WHERE autobuses.idAutobus=relpersonaautobus.idAutobus AND " +
                    "autobuses.idViaje=? AND " +
                    "relpersonaautobus.idPersona=?");
            consulta.setString(1, idViaje);
            consulta.setString(2, idPersona);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                autobus = new AutobusBean();
                autobus.setIdAutobus(resultado.getString(1));
                autobus.setDescripcion(resultado.getString(2));
                autobus.setPlazasEnfermos(resultado.getInt(3));
                autobus.setPlazasNoEnfermos(resultado.getInt(4));
                autobus.setObservaciones(resultado.getString(5));
                autobus.setIdViaje(resultado.getString(6));
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {

        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
        }
        return autobus;
    }
    
    /**
     * Para eliminar bien a una persona de una peregrinacion hay que borrarle de
     * la peregrinacion, pero tambien del autobus y del hotel/habitación. Este
     * método borra a la persona del autobus donde esté asignado en esa
     * peregrinacion
     *
     * @param idPersona
     * @param idViaje
     * @return
     */
    public static int eliminaPersonasAutobus(String idPersona, String idViaje) {
        int fila = 0;
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement insert1 = conexion.prepareStatement(
                    "DELETE FROM relpersonaautobus "
                    + "WHERE idPersona=? AND idAutobus=( "
                    + "SELECT autobuses.idAutobus "
                    + "FROM relpersonaautobus,autobuses "
                    + "WHERE relpersonaautobus.idAutobus=autobuses.idAutobus AND "
                    + "		autobuses.idViaje=? and "
                    + "		idPersona=?)");
            insert1.setString(1, idPersona);
            insert1.setString(2, idViaje);
            insert1.setString(3, idPersona);
            System.out.println("sql: " + insert1);
            fila = insert1.executeUpdate();
            return fila; //Correcto

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestionViajesBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fila;
    }

    public static boolean updateAutobus(AutobusBean autobus) {
        boolean result=false;
        
        Connection conexion = null;

        try {
            conexion = ConectorBD.getConnection();
        
            PreparedStatement insert1 = conexion.prepareStatement(
                    "UPDATE autobuses " +
                    "	SET Descripcion=?, " +
                    "	 PlazasNoEnfermos=?, " +
                    "	 PlazasEnfermos=?, " +
                    "	 Observaciones=?,  " +
                    "	 idViaje=?  " +
                    "	WHERE idAutobus=?");
            insert1.setString(1, autobus.getDescripcion() );
            insert1.setString(2, ""+autobus.getPlazasNoEnfermos());
            insert1.setString(3, ""+autobus.getPlazasEnfermos());
            insert1.setString(4, autobus.getObservaciones());
            insert1.setString(5, autobus.getIdViaje());
            insert1.setString(6, autobus.getIdAutobus());
            insert1.executeUpdate();
            
            return true;
            
        } catch (NamingException ex) {
            Logger.getLogger(GestionAutobusesBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionAutobusesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }

    public static void copiarAutobusesAnoAnterior() {
        String idViajeNuevo=GestionViajesBD.getLastViaje().getIdViaje();
        String idViajeAnterior=GestionViajesBD.getPenuntimoViaje();
        ArrayList<AutobusBean> lista=getListaAutobuses(idViajeAnterior);
        for(AutobusBean bus:lista){
            bus.setIdViaje(idViajeNuevo);
            GestionAutobusesBD.setAutobus(bus);
        }
    }
}
