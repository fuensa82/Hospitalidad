/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.Gestores;

import hospitalidad.beans.InformePDFBean;
import hospitalidad.utils.ConectorBD;
import hospitalidad.utils.FechasUtils;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author vPalomo
 */
public class GestionInformesBD {

    public static int EscribirArchivoBD(File archivo, String idPersona, String observaciones){
        FileInputStream archivoIS;
        try {
            archivoIS = new FileInputStream(archivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionInformesBD.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        Connection conexion = null;
        try {
            conexion=ConectorBD.getConnection();
            PreparedStatement consulta = conexion.prepareStatement(
                "INSERT INTO informespdf(idPersona, fechaAlta, fichero, Observaciones, extension)"
                + "VALUES(?, ?, ?, ?, ?)");
            //conexion.setAutoCommit(false);
            consulta.setString(1, idPersona);
            consulta.setString(4, observaciones);
            consulta.setString(5, archivo.getAbsolutePath().substring(archivo.getAbsolutePath().lastIndexOf('.')+1));
            consulta.setString(2, FechasUtils.fechaHoyParaMysql());
            consulta.setBinaryStream(3, archivoIS, (int) archivo.length());
            consulta.executeUpdate();
            //conexion.commit();
            try {
                archivoIS.close();
            } catch (IOException ex) {
                Logger.getLogger(GestionInformesBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(GestionInformesBD.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
        }

        
        return 1;
    }
    
    public static ArrayList<InformePDFBean> consultaInformes(String idPersona) {
        ArrayList<InformePDFBean> listaInformes = new ArrayList<InformePDFBean>();

        Connection conexion = null;
        try {
            conexion = ConectorBD.getConnection();
            InformePDFBean informe;
            PreparedStatement consulta = conexion.prepareStatement(
                    "SELECT idInformePDF, idPersona, Observaciones, fechaAlta, extension " +
                    "FROM informespdf " +
                    "WHERE informespdf.idPersona=?");
            consulta.setString(1, idPersona);

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                informe = new InformePDFBean();
                informe.setIdInformesPDF(resultado.getString(1));
                informe.setIdPersona(resultado.getString(2));
                informe.setObservaciones(resultado.getString(3));
                informe.setFechaAlta(FechasUtils.fecha(resultado.getString(4)));
                informe.setExtension(resultado.getString(5));
                listaInformes.add(informe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException ex) {

        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
            }
        }
        return listaInformes;
    }
    public static void abrirInformeArchivo(String idInformePDF) throws IOException{
        FileOutputStream output = null;
        try {
            
            Connection conexion = null;
            try {
                conexion=ConectorBD.getConnection();
                PreparedStatement consulta = conexion.prepareStatement(
                        "SELECT informespdf.fichero, extension " +
                        "FROM informespdf " +
                        "WHERE informespdf.idInformePDF=? LIMIT 1");
                consulta.setString(1, idInformePDF);
                ResultSet resultado = consulta.executeQuery();
                if(resultado.next()){
                    InputStream input = null;
                    int random=(int) (1000*Math.random());
                    String nombreFichero="temporal\\fichero"+random+"."+resultado.getString(2);
                    System.out.println("Nombre fichero: "+nombreFichero);
                    File archivo=new File(nombreFichero);
                    output=new FileOutputStream(archivo);
                    input = resultado.getBinaryStream("fichero");
                    System.out.println("Leyendo archivo desde la base de datos...");
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        output.write(buffer);
                    }
                    //System.out.println("Archivo guardado en : " + archivo.getAbsolutePath());
                    Desktop.getDesktop().open(archivo);
                }
                
            } catch (NamingException | SQLException ex) {
                Logger.getLogger(GestionInformesBD.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    conexion.close();
                } catch (SQLException ex) {
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionInformesBD.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(GestionInformesBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
