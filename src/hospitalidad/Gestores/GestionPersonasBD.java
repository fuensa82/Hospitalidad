package hospitalidad.Gestores;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import hospitalidad.beans.PersonaBean;
import hospitalidad.utils.ConectorBD;
import hospitalidad.utils.FechasUtils;
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
                    "relviajetodo.idTipoViajero=tiposviajeros.idTipoViajero AND "+
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
                    persona.setFechaNacimiento(FechasUtils.fecha(resultado.getString(5)));
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
     * Devuelve la lista de personas que no tienen asignado ningun autobus en el viaje
     * @param idViaje
     * @return 
     */
    public static ArrayList<PersonaBean> getListaPersonasSinAutobus(String idViaje) {
        ArrayList<PersonaBean> result;
        result = new ArrayList();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PersonaBean persona;
            PreparedStatement consulta = conexion.prepareStatement(
            "SELECT relviajetodo.idPersona, personas.DNI, personas.Nombre, personas.Apellidos, personas.FechaNacimiento " +
            "FROM relviajetodo, personas " +
            "WHERE relviajetodo.idPersona=personas.idPersona and " +
            "   relviajetodo.idViaje=? AND " +
            "   relviajetodo.idPersona NOT IN( " +
            "   SELECT relpersonaautobus.idPersona FROM relpersonaautobus,autobuses " +
            "   WHERE relpersonaautobus.idAutobus=autobuses.idAutobus AND " +
            "   	autobuses.idViaje=?" +
            "   	)");
            
            consulta.setString(1, idViaje);
            consulta.setString(2, idViaje);
            
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                    persona=new PersonaBean();
                    persona.setIdPersona(resultado.getString(1));
                    persona.setDNI(resultado.getString(2));
                    persona.setNombre(resultado.getString(3));
                    persona.setApellidos(resultado.getString(4));
                    persona.setFechaNacimiento(FechasUtils.fecha(resultado.getString(5)));
                    result.add(persona);
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
     * Devuelve la lista de personas que no tienen asignado ninguna habitacion en el viaje y que sí que estan en el viaje
     * @param idViaje
     * @return 
     */
    public static ArrayList<PersonaBean> getListaPersonasSinHabitacion(String idViaje) {
        ArrayList<PersonaBean> result;
        result = new ArrayList();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PersonaBean persona;
            PreparedStatement consulta = conexion.prepareStatement(
            "SELECT relviajetodo.idPersona, personas.DNI, personas.Nombre, personas.Apellidos, personas.FechaNacimiento " +
            "FROM relviajetodo, personas " +
            "WHERE relviajetodo.idPersona=personas.idPersona and " +
            "   relviajetodo.idViaje=? AND " +
            "   relviajetodo.idPersona NOT IN( " +
            "   SELECT relpersonahabitacion.idPersona FROM relpersonahabitacion,habitaciones " +
            "   WHERE relpersonahabitacion.idhabitacion=habitaciones.idhabitacion AND " +
            "   	habitaciones.idViaje=?" +
            "   	)");
            
            consulta.setString(1, idViaje);
            consulta.setString(2, idViaje);
            
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                    persona=new PersonaBean();
                    persona.setIdPersona(resultado.getString(1));
                    persona.setDNI(resultado.getString(2));
                    persona.setNombre(resultado.getString(3));
                    persona.setApellidos(resultado.getString(4));
                    persona.setFechaNacimiento(FechasUtils.fecha(resultado.getString(5)));
                    result.add(persona);
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
     * Devuelve la lista de personas que no están en este viaje
     * @param idViaje
     * @return 
     */
    public static ArrayList<PersonaBean> getListaPersonasSinViaje(String idViaje, boolean isActivo) {
        ArrayList<PersonaBean> result;
        result = new ArrayList();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PersonaBean persona;
            PreparedStatement consulta = conexion.prepareStatement(
            "SELECT idPersona, DNI, Nombre, Apellidos, FechaNacimiento, Correo, Telefono1, Telefono2, Direccion, CP, Localidad, Provincia, Observaciones, Activo " +
            "FROM personas " +
            "WHERE idPersona NOT IN (SELECT idPersona FROM relviajetodo WHERE idViaje=?) "+
            "ORDER BY Apellidos");
            consulta.setString(1, idViaje);
            
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
    
    public static PersonaBean getPersona(String idPersona){
        PersonaBean personaResult=new PersonaBean();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
            "SELECT idPersona, DNI, Nombre, Apellidos, FechaNacimiento, Correo, Telefono1, Telefono2, Direccion, CP, Localidad, Provincia, Observaciones, Activo, InformeMedico " +
            "FROM personas " +
            "WHERE idPersona=?");
            consulta.setString(1, idPersona);
            
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()){
                personaResult.setIdPersona(resultado.getString(1));
                personaResult.setDNI(resultado.getString(2));
                personaResult.setNombre(resultado.getString(3));
                personaResult.setApellidos(resultado.getString(4));
                personaResult.setFechaNacimiento(FechasUtils.fecha(resultado.getString(5)));
                personaResult.setCorreo(resultado.getString(6));
                personaResult.setTelefono1(resultado.getString(7));
                personaResult.setTelefono2(resultado.getString(8));
                personaResult.setDireccion(resultado.getString(9));
                personaResult.setCP(resultado.getString(10));
                personaResult.setLocalidad(resultado.getString(11));
                personaResult.setProvincia(resultado.getString(12));
                personaResult.setObservaciones(resultado.getString(13));
                personaResult.setInformeMedico(resultado.getString(15));
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
        return personaResult;
    }
    
    public static boolean setPersona(PersonaBean persona){
        boolean result=false;
        Connection conexion = null;

        try {
            conexion = ConectorBD.getConnection();
        
            PreparedStatement insert1 = conexion.prepareStatement("INSERT INTO `hospitalidad`.`personas` (`DNI`, `Nombre`, `Apellidos`, `FechaNacimiento`, `Correo`, `Telefono1`, `Telefono2`, `Direccion`, `CP`, `Localidad`, `Provincia`, `Observaciones`, `InformeMedico`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                            
            insert1.setString(1, persona.getDNI());
            insert1.setString(2, persona.getNombre());
            insert1.setString(3, persona.getApellidos());
            insert1.setString(4, FechasUtils.fechaParaMysql(persona.getFechaNacimiento()));
            insert1.setString(5, persona.getCorreo());
            insert1.setString(6, persona.getTelefono1());
            insert1.setString(7, persona.getTelefono2());
            insert1.setString(8, persona.getDireccion());
            insert1.setString(9, persona.getCP());
            insert1.setString(10, persona.getLocalidad());
            insert1.setString(11, persona.getProvincia());
            insert1.setString(12, persona.getObservaciones());
            insert1.setString(13, persona.getInformeMedico());
            

            insert1.executeUpdate();
            
            return true;
            
        } catch (NamingException ex) {
            Logger.getLogger(GestionAutobusesBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionAutobusesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public static boolean actualizarPersona(PersonaBean persona){
        boolean result=false;
        
        Connection conexion = null;

        try {
            conexion = ConectorBD.getConnection();
        
            PreparedStatement insert1 = conexion.prepareStatement(
                    "UPDATE personas " +
                    "	SET DNI=?, " +
                    "	 Nombre=?, " +
                    "	 Apellidos=?, " +
                    "	 FechaNacimiento=?, " +
                    "	 Correo=?, " +
                    "	 Telefono1=?, " +
                    "	 Telefono2=?, " +
                    "	 Direccion=?, " +
                    "	 CP=?, " +
                    "	 Localidad=?, " +
                    "	 Provincia=?, " +
                    "	 Observaciones=?, " +
                    "	 InformeMedico=? " +
                    "	WHERE idPersona=?");
            insert1.setString(1, persona.getDNI());
            insert1.setString(2, persona.getNombre());
            insert1.setString(3, persona.getApellidos());
            insert1.setString(4, FechasUtils.fechaParaMysql(persona.getFechaNacimiento()));
            insert1.setString(5, persona.getCorreo());
            insert1.setString(6, persona.getTelefono1());
            insert1.setString(7, persona.getTelefono2());
            insert1.setString(8, persona.getDireccion());
            insert1.setString(9, persona.getCP());
            insert1.setString(10, persona.getLocalidad());
            insert1.setString(11, persona.getProvincia());
            insert1.setString(12, persona.getObservaciones());
            insert1.setString(13, persona.getInformeMedico());
            insert1.setString(14, persona.getIdPersona());
            

            insert1.executeUpdate();
            
            return true;
            
        } catch (NamingException ex) {
            Logger.getLogger(GestionAutobusesBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionAutobusesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
   
}
