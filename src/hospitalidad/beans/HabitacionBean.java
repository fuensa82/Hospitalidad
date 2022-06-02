/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.beans;

import hospitalidad.Gestores.GestionAutobusesBD;
import hospitalidad.Gestores.GestionHabitacionesBD;
import java.util.ArrayList;

/**
 *
 * @author vPalomo
 */
public class HabitacionBean {
    private ArrayList<PersonaBean> huespedes;
    private String idHabitacion;
    private int camasTotales;
    private String observaciones;
    private String descripcion1;
    private String descripcion2;
    private String idViaje;
    private String idHotel;
    private String nombreHotel;

    public String getNombreHotel() {
        return nombreHotel;
    }

    public void setNombreHotel(String nombreHotel) {
        this.nombreHotel = nombreHotel;
    }

    public String getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(String idHotel) {
        this.idHotel = idHotel;
    }

    public String getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(String idViaje) {
        this.idViaje = idViaje;
    }
    

    public HabitacionBean(String idHabitacion) {
        this.idHabitacion = idHabitacion;
        this.cargaDatos();
    }
    public HabitacionBean() {

    }
    
    public String toString(){
        return idHabitacion+" - "+descripcion1+" - "+nombreHotel+" - Camas libres: "+GestionHabitacionesBD.getCamasLibres(idHabitacion);
    }
    
    public String getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(String idHabitacion) {
        this.idHabitacion = idHabitacion;
    }
    
    public String getDescripcion1() {
        return descripcion1;
    }
    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }
    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }
    

    /**
     * Devuelve true si se inserta correctamente en la base de datos, false si da error o la habitacion ya está completa
     * @param persona
     * @return 
     */
    /*public boolean añadirHuesped(PersonaBean persona){
        int camasOcupadas=GestionHabitacionesBD.getCamasOcupadas(idHabitacion);
        if(camasOcupadas!=huespedes.size()){
            throw new RuntimeException("Inconsistencia de datos grave. No cuadran las plazas de la habitacion con id "+idHabitacion+"\n"+
                    "Camas de la habitacion: "+camasTotales+"\n"+
                    "Camas ocupadas en java: "+huespedes.size()+"\n"+
                    "Camas en base de datos: "+camasOcupadas);
        }
        if(huespedes.size()>=this.getCamasLibres())return false;
        return GestionAutobusesBD.setPasajeroAutobus(idHabitacion, persona.getIdPersona());

    }*/

   

    public int getCamasLibres() {
        int camasOcupadas=GestionHabitacionesBD.getCamasOcupadas(idHabitacion);
        System.out.println("Camas ocupadas: ");
        return camasTotales-camasOcupadas;
    }

    public ArrayList<PersonaBean> getHuespedes() {
        if(huespedes==null){
            huespedes=GestionHabitacionesBD.consultaHuespedes(idHabitacion);
        }
        return huespedes;
    }

    public void setHuespedes(ArrayList<PersonaBean> huespedes) {
        this.huespedes = huespedes;
    }

    public int getCamasTotales() {
        return camasTotales;
    }

    public void setCamasTotales(int camasTotales) {
        this.camasTotales = camasTotales;
    }
    

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public boolean cargaDatos(){
        boolean result=false;
        if(idHabitacion==null || "".equalsIgnoreCase(idHabitacion))return result;
        
        HabitacionBean habitacion=GestionHabitacionesBD.getDatosHabitacion(idHabitacion);
        this.descripcion1=habitacion.getDescripcion1();
        this.descripcion2=habitacion.getDescripcion2();
        this.idHabitacion=habitacion.getIdHabitacion();
        this.idViaje=habitacion.getIdViaje();
        this.observaciones=habitacion.getObservaciones();
        this.camasTotales=habitacion.getCamasTotales();
        
        return result;
    }
    
}
