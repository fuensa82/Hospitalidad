/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FechasUtils {
    
    /**
     * Dada una fecha en formato MySQL (aaaa-mm-dd) genera la fecha estandar
     * española (dd-mm-aaaa)
     *
     * @param fecha
     * @return
     */
    public static String fecha(String fecha) {
        if ((fecha == null) || (fecha.equalsIgnoreCase("")) || (fecha.equals("0000-00-00"))) {
            return "";
        }
        return fecha.substring(8, 10) + "-" + fecha.substring(5, 7) + "-" + fecha.substring(0, 4);
    }
    /**
     * Dada una fecha de mysql (aaaa-mm-dd hh:mm:ss) genera la fecha estandar con el separador que se le pasa como parámetro
     * @param fecha
     * @param separador
     * @return 
     */
    public static String fechaYHora(String fecha, String separador) {
        if ((fecha == null) || (fecha.equalsIgnoreCase("")) || (fecha.equals("0000-00-00"))) {
            return "";
        }
        return fecha.substring(8, 10) + separador + fecha.substring(5, 7) + separador + fecha.substring(0, 4) + " "+fecha.substring(11) ;
    }
    
    /**
     * Dada una fecha de mysql (aaaa-mm-dd hh:mm:ss) genera la fecha estandar con el separador /
     * Se puede usar la funcion fechaYHora(String fecha, String separador) si necesitamos otro separador
     * @param fecha
     * @return 
     */
    public static String fechaYHora(String fecha) {
        return fechaYHora(fecha,"/");
    }
    /**
     * Dada una fecha en formato MySQL (aaaa-mm-dd) genera la fecha estandar
     * española (dd-mm-aaaa) pero con el delimitador que se le pase, que
     * puede ser /, un espacio, nada, ...
     *
     * @param fecha
     * @return
     */
    public static String fecha(String fecha, String separador) {
        if ((fecha == null) || (fecha.equalsIgnoreCase("")) || (fecha.equals("0000-00-00"))) {
            return "";
        }
        return fecha.substring(8, 10) + separador + fecha.substring(5, 7) + separador + fecha.substring(0, 4);
    }

    
    /**
     * Prepara una fecha para poder ser utilizada en MySQL
     *
     * @param fecha Fecha en formado dd-mm-aaaa
     * @return Devuelve la fecha para poder ser usada en MySQL, es decir, en
     * formado aaaa-mm-dd
     */
    public static String fechaParaMysql(String fecha) {
        if ((fecha == null) || (fecha.equalsIgnoreCase(""))) {
            return null;
        }else if(fecha.length()!=10){
            return null;
        }
        return fecha.substring(6, 10) + "-" + fecha.substring(3, 5) + "-" + fecha.substring(0, 2);
    }

    /**
     * Devuelve la fecha de hoy pero en formado para mysql, es decir, la fecha
     * de hoy en formato aaaa-mm-dd
     *
     * @return
     */
    public static String fechaHoyParaMysql() {
        return FechasUtils.fechaParaMysql(FechasUtils.fechaActualString());
    }

    /**
     * Devuelve la fecha actual en formato hh:mm:ss
     *
     * @return
     */
    public static String horaAhora() {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("HH:mm:ss");
        return formateador.format(LocalDateTime.now());
    }

    /**
     * Combierte una fecha en formado AAAA-MM-DD a entero para poder ser
     * ordenada o comparada
     *
     * @param fecha Fecha en formado AAAA-MM-DD
     * @return
     */
    public static int numeroFechaAAAAMMDD(String fecha) {
        if ((fecha == null) || (fecha.equalsIgnoreCase(""))) {
            return -1;
        }
        String aux = fecha.substring(0, 4) + fecha.substring(5, 7) + fecha.substring(8, 10);
        int num = Integer.parseInt(aux);
        return num;
    }

    /**
     * Combierte una fecha en formado DD-MM-AAAA a entero para poder ser
     * ordenada o comparada
     *
     * @param fecha fecha en formado DD-MM-AAAA
     * @return
     */
    public static int numeroFechaDDMMAAAA(String fecha) {
        if ((fecha == null) || (fecha.equalsIgnoreCase(""))) {
            return -1;
        }

        String aux = fecha.substring(6, 10) + fecha.substring(3, 5) + fecha.substring(0, 2);
        int num = Integer.parseInt(aux);
        return num;
    }

    public static String dameAnoFechaActual() {
        String hoy = FechasUtils.fechaActualString();
        return hoy.substring(hoy.lastIndexOf("-") + 1);
    }

    public static String dameMesFechaActual() {
        String hoy = FechasUtils.fechaActualString();
        return hoy.substring(hoy.indexOf("-") + 1, hoy.indexOf("-") + 3);
    }

    /**
     * Devuelve la fecha de hoy en formado dd-mm-aaaa
     *
     * @return
     */
    public static String fechaActualString() {
        return fechaActualString("-");
    }
    
    /**
     *  Devuelve la fecha de hoy en formado dd-mm-aaaa pero con el separador que le pasemos como parámetro.
     * @param separador
     * @return 
     */
    public static String fechaActualString(String separador) {
        Calendar c = Calendar.getInstance();
        String anio = Integer.toString(c.get(1));

        int m = c.get(2) + 1;
        String mes;
        if (m < 10) {
            mes = "0" + m;
        } else {
            mes = "" + m;
        }

        int d = c.get(5);
        String dia;
        if (d < 10) {
            dia = "0" + d;
        } else {
            dia = "" + d;
        }
        return dia + separador + mes + separador + anio;
    }
/**
 * Dado el mes nos dice su numero (1-> Enero, ...) y si el mes es Todos nos devuelve 0.
 * No es case sensitive.
 * @param mes
 * @return
 * @throws Exception 
 */
    public static String getNumMes(String mes) throws Exception {
        if ("Enero".equalsIgnoreCase(mes)) {
            return "01";
        } else if ("Febrero".equalsIgnoreCase(mes)) {
            return "02";
        } else if ("Marzo".equalsIgnoreCase(mes)) {
            return "03";
        } else if ("Abril".equalsIgnoreCase(mes)) {
            return "04";
        } else if ("Mayo".equalsIgnoreCase(mes)) {
            return "05";
        } else if ("Junio".equalsIgnoreCase(mes)) {
            return "06";
        } else if ("Julio".equalsIgnoreCase(mes)) {
            return "07";
        } else if ("Agosto".equalsIgnoreCase(mes)) {
            return "08";
        } else if ("Septiembre".equalsIgnoreCase(mes)) {
            return "09";
        } else if ("Octubre".equalsIgnoreCase(mes)) {
            return "10";
        } else if ("Noviembre".equalsIgnoreCase(mes)) {
            return "11";
        } else if ("Diciembre".equalsIgnoreCase(mes)) {
            return "12";
        } else if("Todos".equalsIgnoreCase(mes)){
            return "0";
        } else {
            throw new Exception("Error al convertir el mes " + mes + " a número");
        }

    }
    /**
     * Devuelve el número del mes en formato string. Enero es el 01, febrero 02, ...
     * @return
     * @throws Exception 
     */
    public static String getNumMesActualString(){
        Calendar c = Calendar.getInstance();
        int m = c.get(2) + 1;
        String mes;
        if (m < 10) {
            mes = "0" + m;
        } else {
            mes = "" + m;
        }
        return mes;
    }
    public static int getNumMesActualInteger() {
        String mes=FechasUtils.getNumMesActualString();
        return Integer.parseInt(mes);
    }
    
    
/**
 * Dado el mes nos devuelve el literal del mes. Si le mandamos un 1 nos devuelve Enero. El caso del 0 devolverá
 * el literal "todos los meses"
 * @param mes
 * @return
 * @throws Exception 
 */
    public static String getMesNum(int mes){
        if(mes==0){
            return "todos los meses";
        }else if (mes==1) {
            return "Enero";
        } else if (mes==2) {
            return "Febrero";
        } else if (mes==3) {
            return "Marzo";
        } else if (mes==4) {
            return "Abril";
        } else if (mes==5) {
            return "Mayo";
        } else if (mes==6) {
            return "Junio";
        } else if (mes==7) {
            return "Julio";
        } else if (mes==8) {
            return "Agosto";
        } else if (mes==9) {
            return "Septiembre";
        } else if (mes==10) {
            return "Octubre";
        } else if (mes==11) {
            return "Noviembre";
        } else if (mes==12) {
            return "Diciembre";
        } else {
            return "todos los meses";
        }

    }
    /**
     * Rellena por la izq el texto que le pasemos rellenando con el relleno que le pasemos
     * @param texto cadena a rellenar por la izquierda
     * @param relleno Texto que utilizaremos para rellenar
     * @param longitud logitud que deberá tener la cadena resultante.
     * @return 
     */
    public static String rellenaIzquierda(String texto, String relleno, int longitud) {
        String aux = "";
        for (int i = 0; i < longitud; i++) {
            aux += relleno;
        }
        aux += texto;
        return aux.substring(aux.length() - longitud);

    }

    public static String getFechaString(GregorianCalendar c) {
        return c.get(GregorianCalendar.DAY_OF_MONTH) + "/"
                + FechasUtils.rellenaIzquierda("" + (1 + c.get(GregorianCalendar.MONTH)), "0", 2) + "/"
                + c.get(GregorianCalendar.YEAR) + " "
                + FechasUtils.rellenaIzquierda("" + c.get(GregorianCalendar.HOUR_OF_DAY), "0", 2) + ":"
                + FechasUtils.rellenaIzquierda("" + c.get(GregorianCalendar.MINUTE), "0", 2) + ":"
                + FechasUtils.rellenaIzquierda("" + c.get(GregorianCalendar.SECOND), "0", 2);
        //+ c.get(GregorianCalendar.MINUTE) + ":"
        //+ c.get(GregorianCalendar.SECOND);
    }
}
