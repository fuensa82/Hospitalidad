/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vPalomo
 */
public class Config {
    private static String baseDatos;
    private static String usuario;
    private static String contrasenia;
    private static String hostBaseDatos;
    private static String puerto="3306";
    
    private static void cargarDatos(){
        System.out.println("Cargando datos del fichero de config");
        FileReader fr = null;
        try {
            String sistema=System.getProperty("os.name");
            System.out.println(sistema);
            String nombreFichero;
            if(sistema.contains("Windows")){
                nombreFichero="config.txt";
            }else{
                nombreFichero="/home/pi/Fichajes/config.txt";
            }
            File archivo = new File (nombreFichero);
            fr = new FileReader (archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            
            while((linea=br.readLine())!=null){
                String[] valores=linea.split("=");
                if(valores[0].equalsIgnoreCase("baseDatos")){
                    baseDatos=valores[1];
                }else if(valores[0].equalsIgnoreCase("usuario")){
                    usuario=valores[1];
                }else if(valores[0].equalsIgnoreCase("contrasenia")){
                    contrasenia=valores[1];
                }else if(valores[0].equalsIgnoreCase("host")){
                    hostBaseDatos=valores[1];
                }else if(valores[0].equalsIgnoreCase("puerto")){
                    puerto=valores[1];
                }
            }
        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    public static int getPuerto() {
        return Integer.parseInt(puerto);
    }

    public static String getBaseDatos() {
        if(baseDatos==null)cargarDatos();
        return baseDatos;
    }

    public static String getUsuario() {
        if(usuario==null)cargarDatos();
        return usuario;
    }

    public static String getContrasenia() {
        if(contrasenia==null)cargarDatos();
        return contrasenia;
    }

    public static String getHostBaseDatos() {
        if(hostBaseDatos==null)cargarDatos();
        return hostBaseDatos;
    }
}
