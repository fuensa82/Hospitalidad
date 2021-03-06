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
    private int plazasEnfermos;

    public int getPlazasEnfermos() {
        return plazasEnfermos;
    }

    public int getPlazasNoEnfermos() {
        return plazasNoEnfermos;
    }
    private int plazasNoEnfermos;
    private String observaciones;
    private String descripcion;

    public AutobusBean(String idAutobus) {
        this.idAutobus = idAutobus;
        this.cargaDatos();
    }
    public AutobusBean() {

    }
    
    public String toString(){
        return idAutobus+" - "+descripcion+" - "+observaciones+" - Enf.: "+GestionAutobusesBD.getPlazasLibres(idAutobus,true)+" - No Enf.: "+GestionAutobusesBD.getPlazasLibres(idAutobus,false);
    }
    
    public String getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(String idViaje) {
        this.idViaje = idViaje;
    }
    
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
    /*public boolean añadirPasajero(PersonaBean persona){
        int plazasOcupadas=GestionAutobusesBD.getPlazasOcupadas(idAutobus);
        if(plazasOcupadas!=pasajeros.size()){
            throw new RuntimeException("Inconsistencia de datos grave. No cuadran las plazas del autobus con id "+idAutobus+"\n"+
                    "Plazas del autobús: "+plazasTotales+"\n"+
                    "Plazas ocupadas en java: "+pasajeros.size()+"\n"+
                    "Plazas en base de datos: "+plazasOcupadas);
        }
        if(pasajeros.size()>=this.getPlazasLibres())return false;
        return GestionAutobusesBD.setPasajeroAutobus(idAutobus, persona.getIdPersona());
    }*/
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

    public int getPlazasLibres(boolean enfermo) {
        int plazasOcupadas=GestionAutobusesBD.getPlazasOcupadas(idAutobus, enfermo);
        if(enfermo)
            return plazasEnfermos-plazasOcupadas;
        else
            return plazasNoEnfermos-plazasOcupadas;
    }
    public int getPlazas(boolean enfermo) {
        if(enfermo)
            return plazasEnfermos;
        else
            return plazasNoEnfermos;
    }

    public void setPlazas(int plazas, boolean enfermo) {
        if(enfermo)
            plazasEnfermos=plazas;
        else
            plazasNoEnfermos=plazas;
    }
    
    public void setPlazasEnfermos(int plazas) {
        plazasEnfermos=plazas;
    }
    
    public void setPlazasNoEnfermos(int plazas) {
        plazasNoEnfermos=plazas;
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
        this.plazasEnfermos=autobus.getPlazas(true);
        this.plazasNoEnfermos=autobus.getPlazas(false);
        
        return result;
    }
    
}
