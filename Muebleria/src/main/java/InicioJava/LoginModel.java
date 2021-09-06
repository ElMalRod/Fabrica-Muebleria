/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InicioJava;

import Utils.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author emili
 */
public class LoginModel {
    private Connection conexion = ConexionDB.getConexionDB();

    public int devolverTipoPersona(String Nombre, String Contrasena) {
        String query = "SELECT tipo FROM Usuario WHERE nombre = ? AND password = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(query)){
            ps.setString(1, Nombre);
            ps.setString(2, Contrasena);
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }
    
    
}
