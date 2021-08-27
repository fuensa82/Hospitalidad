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
        if(getPlazasLibres(autobus.getIdAutobus())<=0) return false;
        return setPasajeroAutobus(autobus.getIdAutobus(), persona.getIdPersona());
    }

    /**
     * Devuelve el numero de plazas ocupadas de un autobus
     *
     * @param idAutobus
     * @return
     */
    public static int getPlazasOcupadas(String idAutobus) {
        int plazas = 0;
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "SELECT COUNT(*) AS plazas FROM relpersonaautobus WHERE idAutobus=?");
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

    public static int getPlazasLibres(String idAutobus) {
        int plazasO = getPlazasOcupadas(idAutobus);
        int plazasT = getNumPlazas(idAutobus);
        return plazasT - plazasO;
    }

    public static int getNumPlazas(String idAutobus) {
        int plazas = 0;
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "select Plazas "
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

    public static String añadirPasajerosAutobus(ArrayList<PersonaBean> listaPasajeros, String idAutobus){
        String result="";
        if(getPlazasLibres(idAutobus)<listaPasajeros.size()){
            return "No hay sufucuentes plazas libres\n"+
                    "Plazas libres: "+getPlazasLibres(idAutobus)+
                    "\nPlazas solicitadas: "+listaPasajeros.size();
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
    public static boolean setPasajeroAutobus(String idAutobus, String idPersona) {
        if(getPlazasLibres(idAutobus)<=0) return false;
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

    public static boolean guardaAutobus(AutobusBean autobus) {
        boolean result = false;

        return result;
    }

    public static AutobusBean consultaAutobus(String idAutobus) {
        AutobusBean autobus = new AutobusBean();

        return autobus;
    }

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
    public static ArrayList<AutobusBean> getListaAutobuses(String idViaje) {
        ArrayList<AutobusBean> lista = new ArrayList();
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            AutobusBean autobus;
            PreparedStatement consulta = conexion.prepareStatement(
                    "select idAutobus, Descripcion, Plazas, Observaciones, idViaje "
                    + "FROM autobuses "
                    + "WHERE idViaje=?");

            consulta.setString(1, idViaje);

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                autobus = new AutobusBean();
                autobus.setIdAutobus(resultado.getString(1));
                autobus.setDescripcion(resultado.getString(2));
                autobus.setPlazasTotales(resultado.getInt(3));
                autobus.setObservaciones(resultado.getString(4));
                autobus.setIdViaje(resultado.getString(5));
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
                    "select idAutobus, Descripcion, Plazas, Observaciones, idViaje "
                    + "FROM autobuses "
                    + "WHERE idAutobus=?");

            consulta.setString(1, idAutobus);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                autobus = new AutobusBean();
                autobus.setIdAutobus(resultado.getString(1));
                autobus.setDescripcion(resultado.getString(2));
                autobus.setPlazasTotales(resultado.getInt(3));
                autobus.setObservaciones(resultado.getString(4));
                autobus.setIdViaje(resultado.getString(5));
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
                    "SELECT autobuses.idAutobus, Descripcion, Plazas, Observaciones, idViaje " +
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
                autobus.setPlazasTotales(resultado.getInt(3));
                autobus.setObservaciones(resultado.getString(4));
                autobus.setIdViaje(resultado.getString(5));
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
}
