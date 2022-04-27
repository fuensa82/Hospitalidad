/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.beans;

import hospitalidad.Gestores.GestionTiposViajeroBD;

/**
 *
 * @author vPalomo
 */
public class TipoViajeroBean {

    public TipoViajeroBean(String idTipoViajero, String nombreTipo, String descripcion) {
        this.idTipoViajero = idTipoViajero;
        this.nombreTipo = nombreTipo;
        this.descripcion = descripcion;
    }

    public TipoViajeroBean() {
    }
    private String idTipoViajero;
    private String nombreTipo;
    private String descripcion;
    
    public String toString(){
        return idTipoViajero+" - "+nombreTipo;
    }

    public String getIdTipoViajero() {
        return idTipoViajero;
    }

    public void setIdTipoViajero(String idTipoViajero) {
        this.idTipoViajero = idTipoViajero;
    }

    public String getNombreTipo() {
        if("".equals(nombreTipo) && !"".equals(idTipoViajero)){
            nombreTipo=GestionTiposViajeroBD.getNombreTipoViajero(idTipoViajero);
        }
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
