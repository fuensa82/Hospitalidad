/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.Gestores;

import hospitalidad.beans.HabitacionBean;
import hospitalidad.beans.HotelBean;
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
        ArrayList<HabitacionBean> lista = getListaHabitaciones(idViaje,""+0);
        String[] habitaciones = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            habitaciones[i] = lista.get(i).toString();
        }
        return new javax.swing.DefaultComboBoxModel<>(habitaciones);

    }
    
    public static javax.swing.DefaultComboBoxModel getModeloComboHotelesConOpcionTodos() {
        ArrayList<HotelBean> lista = getListaHoteles();
        String[] habitaciones = new String[lista.size()+1];
        habitaciones[0]="0 - Todos los hoteles";
        for (int i = 1; i < (lista.size()+1); i++) {
            habitaciones[i] = lista.get(i-1).toString();
        }
        return new javax.swing.DefaultComboBoxModel<>(habitaciones);
    }
    
    public static javax.swing.DefaultComboBoxModel getModeloComboHoteles() {
        ArrayList<HotelBean> lista = getListaHoteles();
        String[] habitaciones = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            habitaciones[i] = lista.get(i).toString();
        }
        return new javax.swing.DefaultComboBoxModel<>(habitaciones);

    }
    
    public static ArrayList<HabitacionBean> getListaHabitaciones(String idViaje, String idHotel) {
        ArrayList<HabitacionBean> lista = new ArrayList();
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            HabitacionBean habitacion;
            String sql;
            if("0".equals(idHotel)){
                sql="select idHabitacion, descripcion1, descripcion2, camas, Observaciones, idViaje, nombreHotel "
                    + "FROM habitaciones, hoteles "
                    + "WHERE idViaje=? and "
                    + " habitaciones.idHotel=hoteles.IdHotel"
                        + " ORDER BY nombreHotel, descripcion1";
            }else{
                sql="select idHabitacion, descripcion1, descripcion2, camas, Observaciones, idViaje, nombreHotel "
                    + "FROM habitaciones, hoteles "
                    + "WHERE idViaje=? and habitaciones.idHotel=? and "
                    + " habitaciones.idHotel=hoteles.IdHotel "
                        + " ORDER BY nombreHotel, descripcion1";
            }
            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, idViaje);
            if(!"0".equals(idHotel)){
                consulta.setString(2, idHotel);
            }

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                habitacion = new HabitacionBean();
                habitacion.setIdHabitacion(resultado.getString(1));
                habitacion.setDescripcion1(resultado.getString(2));
                habitacion.setDescripcion2(resultado.getString(3));
                habitacion.setCamasTotales(resultado.getInt(4));
                habitacion.setObservaciones(resultado.getString(5));
                habitacion.setIdViaje(resultado.getString(6));
                habitacion.setNombreHotel(resultado.getString(7));
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
                    "select idHabitacion, descripcion1, descripcion2, camas, Observaciones, idViaje, idHotel "
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
                habitacion.setIdHotel(resultado.getString(7));
                
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
                    "SELECT habitaciones.idHabitacion, descripcion1, descripcion2, camas, Observaciones, idViaje, NombreHotel " +
                    "FROM habitaciones, relpersonahabitacion, hoteles " +
                    "WHERE habitaciones.idHabitacion=relpersonahabitacion.idHabitacion AND " +
                            "hoteles.IdHotel=habitaciones.idHotel AND "+
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
                habitacion.setNombreHotel(resultado.getString(7));
                
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
    public static boolean setHabitacionMtto(HabitacionBean habitacion){
        boolean result=false;
        
        Connection conexion = null;

        try {
            conexion = ConectorBD.getConnection();
        
            PreparedStatement insert1 = conexion.prepareStatement(
                    "UPDATE habitaciones " +
                    "	SET camas=?, " +
                    "	 descripcion1=?, " +
                    "	 descripcion2=?, " +
                    "	 observaciones=?  " +
                    "	WHERE idHabitacion=?");
            insert1.setString(1, ""+habitacion.getCamasTotales());
            insert1.setString(2, habitacion.getDescripcion1());
            insert1.setString(3, habitacion.getDescripcion2());
            insert1.setString(4, habitacion.getObservaciones());
            insert1.setString(5, habitacion.getIdHabitacion());
            insert1.executeUpdate();
            
            return true;
            
        } catch (NamingException ex) {
            Logger.getLogger(GestionAutobusesBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionAutobusesBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    public static int setHabitacion(HabitacionBean habitacion){
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            // INSERT INTO `hospitalidad`.`habitaciones` (`Camas`, `Descripcion1`, `Descripcion2`, `Observaciones`, `idViaje`, `idHotel`) VALUES ('2', 'Hab', '3', 'sin', '2', '4');
            PreparedStatement insert1 = conexion.prepareStatement("INSERT INTO hospitalidad.habitaciones(Camas, Descripcion1, Descripcion2, Observaciones, idViaje, idHotel) VALUES (?,?,?,?,?,?);");
            insert1.setString(1, ""+habitacion.getCamasTotales());
            insert1.setString(2, habitacion.getDescripcion1());
            insert1.setString(3, habitacion.getDescripcion2());
            insert1.setString(4, habitacion.getObservaciones());
            insert1.setString(5, habitacion.getIdViaje());
            insert1.setString(6, habitacion.getIdHotel());
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
    
    public static int crearHotel(String nombre){
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            PreparedStatement insert1 = conexion.prepareStatement("INSERT INTO hospitalidad.hoteles(nombreHotel) VALUES (?);");
            insert1.setString(1, nombre);
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

    private static ArrayList<HotelBean> getListaHoteles() {
        ArrayList<HotelBean> lista = new ArrayList();
        HotelBean hotel;
        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            HabitacionBean habitacion;
            PreparedStatement consulta = conexion.prepareStatement(
                    "select idHotel, nombreHotel "
                    + "FROM hoteles ");

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                hotel = new HotelBean();
                hotel.setIdHotel(resultado.getString(1));
                hotel.setNombreHotel(resultado.getString(2));
                lista.add(hotel);
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
}
