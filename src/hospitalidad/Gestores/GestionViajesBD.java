/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.Gestores;

import hospitalidad.beans.PersonaBean;
import hospitalidad.beans.ViajeBean;
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
public class GestionViajesBD {

    /**
     * Devuelve los datos del último viaje que se ha añadido
     *
     * @return
     */
    public static ViajeBean getLastViaje() {
        ViajeBean viaje = null;
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "SELECT idViaje, Nombre, FechaIni, FechaFin n"
                    + "from viajes "
                    + "ORDER BY idViaje DESC LIMIT 1");

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                viaje = new ViajeBean();
                viaje.setIdViaje(resultado.getString(1));
                viaje.setNombre(resultado.getString(2));
                viaje.setFechaIni(resultado.getString(3));
                viaje.setFechaFin(resultado.getString(4));
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
        return viaje;
    }

    /**
     * Devuelve el Bean con los datos completos del viaje
     *
     * @param idViaje del Viaje que se quiere consultar el resto de datos
     * @return
     */
    public static ViajeBean getViaje(String idViaje) {
        ViajeBean viaje = null;
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "SELECT idViaje, Nombre, FechaIni, FechaFin "
                    + "from viajes "
                    + "WHERE idViaje=?");
            consulta.setString(1, idViaje);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                viaje = new ViajeBean();
                viaje.setIdViaje(resultado.getString(1));
                viaje.setNombre(resultado.getString(2));
                viaje.setFechaIni(resultado.getString(3));
                viaje.setFechaFin(resultado.getString(4));
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
        return viaje;
    }

    public static javax.swing.DefaultComboBoxModel getModeloComboViajes() {
        ArrayList<ViajeBean> lista = getListaViajes();
        String[] viajes = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            viajes[i] = lista.get(i).toString();
        }

        return new javax.swing.DefaultComboBoxModel<>(viajes);

    }

    /**
     * Devuelve la lista completa de los viajes que ha habido
     *
     * @return
     */
    public static ArrayList<ViajeBean> getListaViajes() {
        ArrayList<ViajeBean> result;
        result = new ArrayList<ViajeBean>();
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                    "SELECT idViaje, Nombre, FechaIni, FechaFin FROM viajes");

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                ViajeBean viaje = new ViajeBean();
                viaje.setIdViaje(resultado.getString(1));
                viaje.setNombre(resultado.getString(2));
                viaje.setFechaIni(resultado.getString(3));
                viaje.setFechaFin(resultado.getString(4));
                result.add(viaje);
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
        return result;
    }

    /**
     *
     * @param listaPersonas
     * @param idViaje
     * @param tipoViajero
     * @return
     */
    public static boolean guardaPersonasPeregrinacion(ArrayList<PersonaBean> listaPersonas, String idViaje, String idTipoViajero) {
        boolean result = false;

        Connection conexion = null;
        try {
            if (listaPersonas.size() > 0) {
                conexion = ConectorBD.getConnection();
                String sql = "INSERT INTO relviajetodo (idViaje,idPersona,idTipoViajero) VALUES";
                boolean ejecutar = false;
                for (PersonaBean persona : listaPersonas) {
                    sql += "(" + idViaje + "," + persona.getIdPersona() + "," + idTipoViajero + "),";
                    ejecutar = true;
                }
                PreparedStatement insert1 = conexion.prepareStatement(sql.substring(0, sql.length() - 1));
                System.out.println("sql: " + insert1);
                if (ejecutar) {
                    insert1.executeUpdate();
                }
            }
            return true; //Correcto

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestionViajesBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return result;

    }

    /**
     * Guarda de uno en uno las personas que se pasan en la lista, al final devuelve un mensaje que contiene los errores si los hubiera
     * @param listaPersonas
     * @param idViaje
     * @param idTipoViajero
     * @return Devuelve un mensaje con lo que ha ocurrido en la inserción
     */
    public static String guardaPersonasPeregrinacionUnitaria(ArrayList<PersonaBean> listaPersonas, String idViaje, String idTipoViajero) {
        String result = "";
        int correcto = 0;
        int errores = 0;
        String personasConError="";
        Connection conexion = null;
        try {
            if (listaPersonas.size() > 0) {
                conexion = ConectorBD.getConnection();
                boolean ejecutar = false;
                for (PersonaBean persona : listaPersonas) {
                    PreparedStatement insert1 = conexion.prepareStatement(
                            "INSERT INTO relviajetodo (idViaje,idPersona,idTipoViajero) VALUES (?,?,?)");
                    insert1.setString(1, idViaje);
                    insert1.setString(2, persona.getIdPersona());
                    insert1.setString(3, idTipoViajero);
                    System.out.println("sql: " + insert1);
                    int filas = 0;
                    try {
                        filas = insert1.executeUpdate();
                        if (filas == 0) {
                            errores++;
                            personasConError+="  "+persona.getApellidos()+", "+persona.getNombre()+"\n ";
                        } else {
                            correcto++;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        errores++;
                        personasConError+="  "+persona.getApellidos()+", "+persona.getNombre()+"\n ";
                    }
                    
                    
                }
            }
            if(errores>0){
                result="Se han dado de alta "+correcto+" personas\n"+
                       "Han dado error "+errores+" personas\n"+
                        personasConError;
            }else{
                result="Se han dado de alta "+correcto;
            }
            return result; //Correcto

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestionViajesBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return result;
    }
    /**
     * Elimina a una persona de la peregrinacion y por tanto se le ellimina tambien del autobus y del hotel (si los tuviera ya
     * asignados)
     * @param idPersona
     * @param idViaje
     * @return 
     */
    public static boolean eliminaPersonasPeregrinacion(String idPersona, String idViaje){
        int result1=eliminaPersonasViaje(idPersona, idViaje);
        int result2=GestionAutobusesBD.eliminaPersonasAutobus(idPersona, idViaje);
        int result3=GestionHabitacionesBD.eliminaPersonasHabitacion(idPersona, idViaje);
        //Falta eliminar a la persona del hotel 
        return true;
        //int result3=
    }
    
    public static ArrayList<PersonaBean> getPersonaSinAutobus(String idViaje){
        ArrayList<PersonaBean> result;
        result = new ArrayList();
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PersonaBean persona;
            PreparedStatement consulta = conexion.prepareStatement(
            "SELECT idPersona,DNI,Nombre,Apellidos FROM relviajetodo  " +
            "WHERE idViaje=? AND  " +
            "idPersona NOT IN( " +
            "   SELECT relpersonaautobus.idPersona FROM relpersonaautobus,autobuses " +
            "   WHERE relpersonaautobus.idAutobus=autobuses.idAutobus AND " +
            "       autobuses.idViaje=? " +
            "       )");
            
            consulta.setString(1, idViaje);
            consulta.setString(2, idViaje);
            
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()){
                    persona=new PersonaBean();
                    persona.setIdPersona(resultado.getString(1));
                    persona.setDNI(resultado.getString(2));
                    persona.setNombre(resultado.getString(3));
                    persona.setApellidos(resultado.getString(4));
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
    
    private static int eliminaPersonasViaje(String idPersona, String idViaje) {
        int fila = 0;
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement insert1 = conexion.prepareStatement(
                    "DELETE FROM relviajetodo where idViaje=? and idPersona=?");
            insert1.setString(1, idViaje);
            insert1.setString(2, idPersona);
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
