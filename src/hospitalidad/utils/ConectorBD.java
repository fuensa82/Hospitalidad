/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad.utils;


//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;

public class ConectorBD {

    
    public static Connection getConnection() throws NamingException, SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(Config.getUsuario());
        dataSource.setPassword(Config.getContrasenia());
        dataSource.setDatabaseName(Config.getBaseDatos());
        dataSource.setPort(Config.getPuerto());
        dataSource.setServerTimezone("UTC");
        //dataSource.setServerName("");
        dataSource.setServerName(Config.getHostBaseDatos());

        Connection conexion = dataSource.getConnection();
        return conexion;
    }
}
