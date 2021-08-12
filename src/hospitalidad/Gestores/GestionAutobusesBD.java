/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.Gestores;

import hospitalidad.beans.AutobusBean;
import hospitalidad.beans.PersonaBean;

/**
 *
 * @author vPalomo
 */
public class GestionAutobusesBD {
    
    public static boolean guardarPasajero(AutobusBean autobus, PersonaBean persona){
        boolean result=false;
        
        return result;
    }
    /**
     * Devuelve el numero de plazas ocupadas de un autobus
     * @param idAutobus
     * @return 
     */
    public static int getPlazasOcupadas(String idAutobus){
        int plazas=0;
        
        return plazas;
    }
    
    public static int getNumPlazas(String idAutobus){
        int plazas=0;
        
        return plazas;
    }
    
    public static boolean guardaAutobus(AutobusBean autobus){
        boolean result=false;
        
        return result;
    }
    
    public static AutobusBean consultaAutobus(String idAutobus){
        AutobusBean autobus=new AutobusBean();
        
        return autobus;
    }
}
