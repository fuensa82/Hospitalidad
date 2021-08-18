/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.beans;

import hospitalidad.Gestores.GestionAutobusesBD;
import java.util.ArrayList;

/**
 *
 * @author vPalomo
 */
public class AutobusBean {
    private ArrayList<PersonaBean> pasajeros;
    private String idAutobus;
    private String idViaje;

    public String getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(String idViaje) {
        this.idViaje = idViaje;
    }
    private int plazas;
    private String observaciones;
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve true si se inserta correctamente en la base de datos, false si da error o el autobus ya está completo
     * @param persona
     * @return 
     */
    public boolean añadirPasajero(PersonaBean persona){
        int plazasOcupadas=GestionAutobusesBD.getPlazasOcupadas(idAutobus);
        if(plazasOcupadas!=pasajeros.size()){
            throw new RuntimeException("Inconsistencia de datos grave. No cuadran las plazas del autobus con id "+idAutobus+"\n"+
                    "Plazas del autobús: "+plazas+"\n"+
                    "Plazas ocupadas en java: "+pasajeros.size()+"\n"+
                    "Plazas en base de datos: "+plazasOcupadas);
        }
        
        boolean result=false;
        if(pasajeros.size()>=plazas)return false;
        return result;
    }
    public ArrayList<PersonaBean> getPasajeros() {
        if(pasajeros==null){
            pasajeros=GestionAutobusesBD.consultaPasajeros(idAutobus);
        }
        return pasajeros;
    }

    public void setPasajeros(ArrayList<PersonaBean> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public String getIdAutobus() {
        return idAutobus;
    }

    public void setIdAutobus(String idAutobus) {
        this.idAutobus = idAutobus;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public boolean cargaDatos(){
        boolean result=false;
        if(idAutobus==null || "".equalsIgnoreCase(idAutobus))return result;
        
        AutobusBean autobus=GestionAutobusesBD.getDatosAutobus(idAutobus);
        this.descripcion=autobus.getDescripcion();
        this.idAutobus=autobus.getIdAutobus();
        this.idViaje=autobus.getIdViaje();
        this.observaciones=autobus.getObservaciones();
        this.plazas=autobus.getPlazas();
        
        return result;
    }
    
}
