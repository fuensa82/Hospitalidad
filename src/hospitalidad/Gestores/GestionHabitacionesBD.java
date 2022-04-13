/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.Gestores;

import hospitalidad.beans.AutobusBean;
import hospitalidad.beans.HabitacionBean;
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
public class GestionHabitacionesBD {
    /**
     * Guarda (si el autobus tiene sitio libre) la persona en el autobus. Si no hay sitio devuelve false
     * y no se asigna la persona al autobus
     * @param autobus
     * @param persona
     * @return 
     */
    public static boolean guardarHuesped(HabitacionBean habitacion, PersonaBean persona) {
        if(getCamasLibres(habitacion.getIdHabitacion())<=0) return false;
        return setHuespedHabitacion(habitacion.getIdHabitacion(), persona.getIdPersona());
    }

    /**
     * Devuelve el numero de camas ocupadas de una habitacion
     *
     * @param idAutobus
     * @return
     */
    public static int getCamasOcupadas(String idHabitacion) {
        int camas = 0;
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "SELECT COUNT(*) AS camas FROM relpersonahabitacion WHERE idHabitacion=?");
            consulta.setString(1, idHabitacion);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
               camas=resultado.getInt(1);
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
        return camas;
    }

    public static int getCamasLibres(String idHabitacion) {
        int camasO = getCamasOcupadas(idHabitacion);
        int camasT = getNumCamas(idHabitacion);
        return camasT - camasO;
    }

    public static int getNumCamas(String idHabitacion) {
        int camas = 0;
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "select camas "
                    + "FROM habitaciones "
                    + "WHERE idHabitacion=?");

            consulta.setString(1, idHabitacion);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
               camas=resultado.getInt(1);
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
        return camas;
    }

    public static String añadirHuespedHabitacion(ArrayList<PersonaBean> listaHuespedes, String idHabitacion){
        System.out.println("añadirHuespedHabitacion idHabitacion: "+idHabitacion);
        String result="";
        if(getCamasLibres(idHabitacion)<listaHuespedes.size()){
            return "No hay sufucuentes plazas libres\n"+
                    "Plazas libres: "+getCamasLibres(idHabitacion)+
                    "\nPlazas solicitadas: "+listaHuespedes.size();
        }
        int correctos=0;
        int errores=0;
        String nombresErrores="";
        for (PersonaBean persona: listaHuespedes){
            if(setHuespedHabitacion(idHabitacion, persona.getIdPersona())){
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
    public static boolean setHuespedHabitacion(String idHabitacion, String idPersona) {
        if(getCamasLibres(idHabitacion)<=0) return false;
        boolean result = false;
        Connection conexion = null;

        try {
            conexion = ConectorBD.getConnection();
        
            PreparedStatement insert1 = conexion.prepareStatement(
                    "INSERT INTO relpersonahabitacion VALUES (?,?)");
            insert1.setString(1, idHabitacion);
            insert1.setString(2, idPersona);

            insert1.executeUpdate();
            
            return true;
            
        } catch (NamingException ex) {
            Logger.getLogger(GestionHabitacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionHabitacionesBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
    /*
    public static boolean guardaHabitacion(HabitacionBean autobus) {
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
     * @param idHabitacion
     * @return devuelve el arrayList de PersonaBean con los datos de los
     * huespedes de la habitacion
     */
    public static ArrayList<PersonaBean> consultaHuespedes(String idHabitacion) {
        ArrayList<PersonaBean> camas = new ArrayList<>();

        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PersonaBean persona;
            PreparedStatement consulta = conexion.prepareStatement(
                    "SELECT personas.idPersona, DNI, Nombre, Apellidos "
                    + "FROM personas, relpersonahabitacion "
                    + "WHERE personas.idPersona=relpersonahabitacion.idPersona AND "
                    + "		relpersonahabitacion.idHabitacion=? "
                    + "ORDER BY Apellidos");
            consulta.setString(1, idHabitacion);

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                persona = new PersonaBean();
                persona.setIdPersona(resultado.getString(1));
                persona.setDNI(resultado.getString(2));
                persona.setNombre(resultado.getString(3));
                persona.setApellidos(resultado.getString(4));
                camas.add(persona);
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
        return camas;
    }

    public static javax.swing.DefaultComboBoxModel getModeloComboHabitaciones(String idViaje) {
        ArrayList<HabitacionBean> lista = getListaHabitaciones(idViaje);
        String[] habitaciones = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            habitaciones[i] = lista.get(i).toString();
        }
        return new javax.swing.DefaultComboBoxModel<>(habitaciones);

    }
    
    public static ArrayList<HabitacionBean> getListaHabitaciones(String idViaje) {
        ArrayList<HabitacionBean> lista = new ArrayList();
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            HabitacionBean habitacion;
            PreparedStatement consulta = conexion.prepareStatement(
                    "select idHabitacion, descripcion1, descripcion2, camas, Observaciones, idViaje "
                    + "FROM habitaciones "
                    + "WHERE idViaje=?");

            consulta.setString(1, idViaje);

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                habitacion = new HabitacionBean();
                habitacion.setIdHabitacion(resultado.getString(1));
                habitacion.setDescripcion1(resultado.getString(2));
                habitacion.setDescripcion2(resultado.getString(3));
                habitacion.setCamasTotales(resultado.getInt(4));
                habitacion.setObservaciones(resultado.getString(5));
                habitacion.setIdViaje(resultado.getString(6));
                lista.add(habitacion);
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
     * Devuelve una HabitacionBean con los datos de la habitacion pasado como id
     *
     * @param idHabitacion
     * @return
     */
    public static HabitacionBean getDatosHabitacion(String idHabitacion) {
        HabitacionBean habitacion = new HabitacionBean();
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "select idHabitacion, descripcion1, descripcion2, camas, Observaciones, idViaje "
                    + "FROM habitaciones "
                    + "WHERE idHabitacion=?");

            consulta.setString(1, idHabitacion);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                habitacion = new HabitacionBean();
                habitacion.setIdHabitacion(resultado.getString(1));
                habitacion.setDescripcion1(resultado.getString(2));
                habitacion.setDescripcion2(resultado.getString(3));
                habitacion.setCamasTotales(resultado.getInt(4));
                habitacion.setObservaciones(resultado.getString(5));
                habitacion.setIdViaje(resultado.getString(6));
                
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
        return habitacion;
    }
    
    /**
     * Devuelve los datos de la habitacion asignada a una persona en un viaje.
     * Si devuelve null es que la persona no tiene asignado aun ninguna habitacion
     * @param idPersona
     * @param idViaje
     * @return Devuelve un objeto HabitaionBean con los datos de la habitacion
     */
    public static HabitacionBean getDatosHabitacion(String idPersona, String idViaje) {
        HabitacionBean habitacion = new HabitacionBean();
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "SELECT habitaciones.idHabitacion, descripcion1, descripcion2, camas, Observaciones, idViaje " +
                    "FROM habitaciones, relpersonahabitacion " +
                    "WHERE habitaciones.idHabitacion=relpersonahabitacion.idHabitacion AND " +
                    "habitaciones.idViaje=? AND " +
                    "relpersonahabitacion.idPersona=?");
            consulta.setString(1, idViaje);
            consulta.setString(2, idPersona);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                habitacion = new HabitacionBean();
                habitacion.setIdHabitacion(resultado.getString(1));
                habitacion.setDescripcion1(resultado.getString(2));
                habitacion.setDescripcion2(resultado.getString(3));
                habitacion.setCamasTotales(resultado.getInt(4));
                habitacion.setObservaciones(resultado.getString(5));
                habitacion.setIdViaje(resultado.getString(6));
                
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
        return habitacion;
    }
    
    /**
     * Para eliminar bien a una persona de una peregrinacion hay que borrarle de
     * la peregrinacion, pero tambien del autobus y del hotel/habitación. Este
     * método borra a la persona de la habitacion donde esté asignado en esa
     * peregrinacion
     *
     * @param idPersona
     * @param idViaje
     * @return
     */
    public static int eliminaPersonasHabitacion(String idPersona, String idViaje) {
        int fila = 0;
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement insert1 = conexion.prepareStatement(
                    "DELETE FROM relpersonahabitacion "
                    + "WHERE idPersona=? AND idHabitacion=( "
                    + "SELECT habitaciones.idHabitacion "
                    + "FROM relpersonahabitacion,habitaciones "
                    + "WHERE relpersonahabitacion.idHabitacion=habitaciones.idHabitacion AND "
                    + "		habitaciones.idViaje=? and "
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
    
    public static int setHabitacion(HabitacionBean habitacion){
        return 1;
    }
}
