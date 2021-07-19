/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.Gestores;

import hospitalidad.beans.PersonaBean;
import hospitalidad.beans.TipoViajeroBean;
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
 * 
 */
public class GestionTiposViajeroBD {
    /**
     * Dado el id de un viaje y una persona nos devuelve el tipo de viajero que es
     * @param idPersona ID de la persona que se quiere consultar el tipo de viajero que era
     * @param idViaje ID del viaje en el que se quiere consultar el tipoo de viajero que es la persona enviada
     * @return Devuelve un Bean con los datos del tipo de Viajero (id, nombreCorto y descripcion)
     */
    public static TipoViajeroBean getTipoViajero(String idPersona, String idViaje){
        TipoViajeroBean tipoViajero = null;
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
            "SELECT NombreCortoTipo, Descripcion, relviajetodo.idTipoViajero " +
                "FROM tiposviajeros, relviajetodo " +
                "WHERE idPersona=? and idViaje=? and " +
                "	tiposviajeros.idTipoViajero=relviajetodo.idTipoViajero");
            consulta.setString(1, idPersona);
            consulta.setString(2, ""+idViaje);
            
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                tipoViajero=new TipoViajeroBean();
                tipoViajero.setNombreTipo(resultado.getString(1));
                tipoViajero.setDescripcion(resultado.getString(2));
                tipoViajero.setIdTipoViajero(resultado.getString(3));
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
        return tipoViajero;
    }
    /**
     * Devuelve la lista completa de tipo diferentes de viajeros que hay

     * @return 
     */
    public static ArrayList<TipoViajeroBean> getListaTipoViajero(){
        ArrayList<TipoViajeroBean> result;
        result = new ArrayList<TipoViajeroBean>();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
            "SELECT NombreCortoTipo, Descripcion, relviajetodo.idTipoViajero " +
                "FROM tiposviajeros");

            
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                TipoViajeroBean tipoViajero=new TipoViajeroBean();
                tipoViajero.setNombreTipo(resultado.getString(1));
                tipoViajero.setDescripcion(resultado.getString(2));
                tipoViajero.setIdTipoViajero(resultado.getString(3));
                result.add(tipoViajero);
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
