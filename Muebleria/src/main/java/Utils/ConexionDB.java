/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emili
 */
public class ConexionDB {
   private static Connection conexion = null;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/fabricamuebles";
     
    
     /**
     * Funcion que crea la conexion con la DB
     * @return
     
     */
       public static Connection getConexionDB(){
        if (conexion==null) {
            new ConexionDB();
        }
        return conexion;
    }
    
     private ConexionDB() {
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion establecida");
            
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("No se ha podido generar la conexion");
        }
    }
}
