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
    
    public static ArrayList<PersonaBean> consultaPasajeros(String idAutobus){
        ArrayList<PersonaBean> plazas=new ArrayList<PersonaBean>();
        
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PersonaBean persona;
            PreparedStatement consulta = conexion.prepareStatement(
            "SELECT personas.idPersona, DNI, Nombre, Apellidos, FechaNacimiento, "+
		"Correo, Telefono1, Telefono2, Direccion, CP, Localidad, "+
		"Provincia, Observaciones, activo  "+
            "FROM personas "+
            "ORDER BY Apellidos");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                if("true".equals(resultado.getString(14).trim())==isActivo){
                    persona=new PersonaBean();
                    persona.setIdPersona(resultado.getString(1));
                    persona.setDNI(resultado.getString(2));
                    persona.setNombre(resultado.getString(3));
                    persona.setApellidos(resultado.getString(4));
                    plazas.add(persona);
                }
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
}
