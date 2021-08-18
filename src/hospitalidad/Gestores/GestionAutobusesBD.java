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
    
    public static boolean guardarPasajero(AutobusBean autobus, PersonaBean persona){
        boolean result=false;
        
        return result;
    }
    /**
     * Devuelve el numero de plazas ocupadas de un autobus
     * @param idAutobus
     * @return 
     */
    public static int getPlazasOcupadas(String idAutobus){
        int plazas=0;
        
        return plazas;
    }
    
    public static int getNumPlazas(String idAutobus){
        int plazas=0;
        
        return plazas;
    }
    
    public static boolean guardaAutobus(AutobusBean autobus){
        boolean result=false;
        
        return result;
    }
    
    public static AutobusBean consultaAutobus(String idAutobus){
        AutobusBean autobus=new AutobusBean();
        
        return autobus;
    }
    /**
     * 
     * @param idAutobus
     * @return devuelve el arrayList de PersonaBean con los datos de los pasajeros
     */
    public static ArrayList<PersonaBean> consultaPasajeros(String idAutobus){
        ArrayList<PersonaBean> plazas=new ArrayList<PersonaBean>();
        
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PersonaBean persona;
            PreparedStatement consulta = conexion.prepareStatement(
            "SELECT personas.idPersona, DNI, Nombre, Apellidos " +
            "FROM personas, relpersonaautobus " +
            "WHERE personas.idPersona=relpersonaautobus.idPersona AND " +
            "		relpersonaautobus.idAutobus=? "+
            "ORDER BY Apellidos");
            consulta.setString(1, idAutobus);
            
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                persona=new PersonaBean();
                persona.setIdPersona(resultado.getString(1));
                persona.setDNI(resultado.getString(2));
                persona.setNombre(resultado.getString(3));
                persona.setApellidos(resultado.getString(4));
                plazas.add(persona);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {
            
        }finally{
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
        }
        
        return plazas;
    }
    
    public static ArrayList<AutobusBean> getListaAutobuses(String idViaje){
        ArrayList<AutobusBean> lista= new ArrayList();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            AutobusBean autobus;
            PreparedStatement consulta = conexion.prepareStatement(
            "select idAutobus, Descripcion, Plazas, Observaciones, idViaje " +
            "FROM autobuses " +
            "WHERE idViaje=?");
            
            consulta.setString(1, idViaje);
            
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                autobus=new AutobusBean();
                autobus.setIdAutobus(resultado.getString(1));
                autobus.setDescripcion(resultado.getString(2));
                autobus.setPlazas(resultado.getInt(3));
                autobus.setObservaciones(resultado.getString(4));
                autobus.setIdViaje(resultado.getString(5));
                lista.add(autobus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {
            
        }finally{
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
        }
        return lista;
        
    }
    /**
     * Devuelve un AutobusBean con los datos del autobus pasado como id
     * @param idAutobus
     * @return 
     */
    public static AutobusBean getDatosAutobus(String idAutobus){
        AutobusBean autobus=new AutobusBean();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
            "select idAutobus, Descripcion, Plazas, Observaciones, idViaje " +
            "FROM autobuses " +
            "WHERE idAutobus=?");
            
            consulta.setString(1, idAutobus);
            
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()){
                autobus=new AutobusBean();
                autobus.setIdAutobus(resultado.getString(1));
                autobus.setDescripcion(resultado.getString(2));
                autobus.setPlazas(resultado.getInt(3));
                autobus.setObservaciones(resultado.getString(4));
                autobus.setIdViaje(resultado.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {
            
        }finally{
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
        }
        return autobus;
    }
    /**
     * Para eliminar bien a una persona de una peregrinacion hay que borrarle de la peregrinacion, pero 
     * tambien del autobus y del hotel/habitación.
     * Este método borra a la persona del autobus donde esté asignado en esa peregrinacion
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
                    "DELETE FROM relpersonaautobus " +
                    "WHERE idPersona=? AND idAutobus=( " +
                        "SELECT autobuses.idAutobus " +
                        "FROM relpersonaautobus,autobuses " +
                        "WHERE relpersonaautobus.idAutobus=autobuses.idAutobus AND " +
                        "		autobuses.idViaje=? and " +
                        "		idPersona=?)");
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
