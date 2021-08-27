/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.beans;

import hospitalidad.Gestores.GestionInformesBD;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vPalomo
 */
public class InformePDFBean {
    private String idInformesPDF;
    private String idPersona;
    private String fechaAlta;
    private String extension;
    private String Observaciones;

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public void abrirInforme(){
        try {
            GestionInformesBD.abrirInformeArchivo(idInformesPDF);
        } catch (IOException ex) {
            Logger.getLogger(InformePDFBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String getIdInformesPDF() {
        return idInformesPDF;
    }

    public void setIdInformesPDF(String idInformesPDF) {
        this.idInformesPDF = idInformesPDF;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
