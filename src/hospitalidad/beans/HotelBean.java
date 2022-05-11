/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospitalidad.beans;

/**
 *
 * @author vPalomo
 */
public class HotelBean {
    private String idHotel;
    private String nombreHotel;

    public String getIdHotel() {
        return idHotel;
    }
    public String toString(){
        return idHotel+" - "+nombreHotel;
    }
    
    public void setIdHotel(String idHotel) {
        this.idHotel = idHotel;
    }

    public String getNombreHotel() {
        return nombreHotel;
    }

    public void setNombreHotel(String nombreHotel) {
        this.nombreHotel = nombreHotel;
    }
    
}
