package hospitalidad.Gestores;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
public class GestionPersonasBD {
    /**
     * Devuelve la lista de todas las personas de la bade de datos filtrando por activo o no, es decir, 
     * las personas que están dadas de baja (por el motivo que sea) no aparecerán en esta lista.
     * 
     * //AUN FALTA DESARROLLAR PARTE
     * 
     * @param isActivo true si queremos la lista de las parsonas activas, false si queremos la lista de personas
     * que hemos desactivado por algún motivo.
     * @return 
     */
    public static ArrayList<PersonaBean> getListaPersonas(boolean isActivo){
        ArrayList<PersonaBean> result;
        result = new ArrayList();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PersonaBean persona;
            PreparedStatement consulta = conexion.prepareStatement(
            "SELECT personas.idPersona, DNI, Nombre, Apellidos, FechaNacimiento, "+
		"Correo, Telefono1, Telefono2, Direccion, CP, Localidad, "+
		"Provincia, Observaciones, activo,nombreCortoTipo  "+
            "FROM personas, tiposviajeros, relviajetodo "+
            "WHERE personas.idPersona=relviajetodo.idPersona AND "+
                    "relviajetodo.idTipoViajero=tiposviajeros.idTipoViajero and "+
                    "relviajetodo.idViaje=1 "+
            "ORDER BY Apellidos");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                if("true".equals(resultado.getString(14).trim())==isActivo){
                    persona=new PersonaBean();
                    persona.setIdPersona(resultado.getString(1));
                    persona.setDNI(resultado.getString(2));
                    persona.setNombre(resultado.getString(3));
                    persona.setApellidos(resultado.getString(4));
                    result.add(persona);
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
        return result;
    }
    /**
     * 
     * @param isActivo
     * @param idViaje
     * @param idTipoViajero (Puede ser 0 lo que implica todos los tipos de viajeros
     * @return 
     */
    public static ArrayList<PersonaBean> getListaPersonas(boolean isActivo, String idViaje, String idTipoViajero){
        ArrayList<PersonaBean> result;
        result = new ArrayList();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PersonaBean persona;
            PreparedStatement consulta = conexion.prepareStatement(
            "SELECT personas.idPersona, DNI, Nombre, Apellidos, FechaNacimiento, "+
		"Correo, Telefono1, Telefono2, Direccion, CP, Localidad, "+
		"Provincia, Observaciones, activo, nombreCortoTipo  "+
            "FROM personas, tiposviajeros, relviajetodo "+
            "WHERE personas.idPersona=relviajetodo.idPersona AND "+
                    "tiposviajeros.idTipoViajero=? AND "+
                    "relviajetodo.idViaje=?"+
            "ORDER BY Apellidos ");
            
            consulta.setString(1, idTipoViajero);
            consulta.setString(2, idViaje);
            
            if("0".equalsIgnoreCase(idTipoViajero)){
                consulta = conexion.prepareStatement(
                    "SELECT personas.idPersona, DNI, Nombre, Apellidos, FechaNacimiento, "+
                        "Correo, Telefono1, Telefono2, Direccion, CP, Localidad, "+
                        "Provincia, Observaciones, activo  "+
                    "FROM personas, relviajetodo "+
                    "WHERE personas.idPersona=relviajetodo.idPersona AND "+
                            "relviajetodo.idViaje=?"+
                    "ORDER BY Apellidos ");

                consulta.setString(1, idViaje);
            }
            
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                if("true".equals(resultado.getString(14).trim())==isActivo){
                    persona=new PersonaBean();
                    persona.setIdPersona(resultado.getString(1));
                    persona.setDNI(resultado.getString(2));
                    persona.setNombre(resultado.getString(3));
                    persona.setApellidos(resultado.getString(4));
                    result.add(persona);
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
        return result;
    }
   
}
