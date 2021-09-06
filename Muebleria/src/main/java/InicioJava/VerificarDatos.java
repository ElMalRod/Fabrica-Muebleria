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

/**
 *
 * @author emili
 */
public class VerificarDatos {
     Connection conexion = ConexionDB.getConexionDB();
    
    public int comprobarDatos(){
        int contador = 0;
       String query = "SELECT COUNT(*) FROM Cliente UNION ALL "
                + "SELECT COUNT(*) FROM Asignacion_Precio UNION ALL "
                + "SELECT COUNT(*) FROM Usuario UNION ALL "
                + "SELECT COUNT(*) FROM Ensamble_Mueble UNION ALL "
                + "SELECT COUNT(*) FROM Pieza UNION ALL "
                + "SELECT COUNT(*) FROM Mueble UNION ALL "
                + "SELECT COUNT(*) FROM Ensamble_Pieza";
        
        try (PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){
            
            while (rs.next()) {
                if(rs.getInt(1)>0){
                    contador++;
                }
            }
            
            if (contador==7) {
                return 1;
            } else {
                return 0;
            }
            
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }
}
