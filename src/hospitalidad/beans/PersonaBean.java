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
public class PersonaBean {

    private String idPersona;
    private String DNI;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private String correo;
    private String telefono1;
    private String telefono2;
    private String direccion;
    private String CP;
    private String localidad;
    private String provincia;
    private String observaciones;
    private String informeMedico;

    public TipoViajeroBean getTipo(String idViaje) {
        System.out.println("idViaje: "+idViaje);
        return GestionTiposViajeroBD.getTipoViajero(idViaje, idViaje, idPersona);
    }

    

    public String getInformeMedico() {
        return informeMedico;
    }

    public void setInformeMedico(String InformeMedico) {
        this.informeMedico = InformeMedico;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String Telefono1) {
        this.telefono1 = Telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
