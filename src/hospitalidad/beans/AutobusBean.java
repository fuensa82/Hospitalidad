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
    private int plazas;
    private String observaciones;

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
    
}
