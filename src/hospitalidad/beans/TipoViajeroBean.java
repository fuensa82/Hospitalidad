/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.beans;

/**
 *
 * @author vPalomo
 */
public class TipoViajeroBean {

    public TipoViajeroBean(String idTipoViajero, String NombreTipo, String Descripcion) {
        this.idTipoViajero = idTipoViajero;
        this.NombreTipo = NombreTipo;
        this.Descripcion = Descripcion;
    }

    public TipoViajeroBean() {
    }
    private String idTipoViajero;
    private String NombreTipo;
    private String Descripcion;
    
    public String toString(){
        return idTipoViajero+" - "+NombreTipo;
    }

    public String getIdTipoViajero() {
        return idTipoViajero;
    }

    public void setIdTipoViajero(String idTipoViajero) {
        this.idTipoViajero = idTipoViajero;
    }

    public String getNombreTipo() {
        return NombreTipo;
    }

    public void setNombreTipo(String NombreTipo) {
        this.NombreTipo = NombreTipo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
}
