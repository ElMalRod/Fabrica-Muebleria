/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectores;

import Lectores.DatosLinea;
import Clientes.Usuario;
import Utils.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author emili
 */
public class LecturaUsuario {
    private Connection conexion = ConexionDB.getConexionDB();
    private ArrayList<DatosLinea> datosUsuario;
    private ArrayList<Error> listaErrores;

    public LecturaUsuario(ArrayList<DatosLinea> datosUsuario, ArrayList<Error> listaErrores) {
        this.datosUsuario = datosUsuario;
        this.listaErrores = listaErrores;
    }

    public void analizarUsuario() {
        for (DatosLinea datosUser : datosUsuario) {
            if (datosUser.getDatos().length == 3) {
                String nombre = datosUser.getDatos()[0];
                String password = datosUser.getDatos()[1];
                int tipoUsuario = 0;
                if (password.length() < 6) {
                    listaErrores.add(new Error(datosUser.getNumLinea(), "Formato", "La contraseña no tiene 6 caracteres como minimo"));
                } else {
                    try {
                        tipoUsuario = Integer.valueOf(datosUser.getDatos()[2]);
                        Usuario nuevoUsuario = new Usuario(nombre, tipoUsuario, password);
                        agregarUsuario(nuevoUsuario,datosUser.getNumLinea());
                    } catch (NumberFormatException e) {
                        listaErrores.add(new Error(datosUser.getNumLinea(), "Formato", "No existe un numero entero"));
                    }
                }
            } else {
                listaErrores.add(new Error(datosUser.getNumLinea(), "Formato", "No vienen el numero de datos correctos"));
            }
        }
    }

    private void agregarUsuario(Usuario nuevoUsuario, int numeroLinea) {
        String query = "INSERT INTO Usuario VALUES (?,?,?)";

        try ( PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nuevoUsuario.getNombre());
            ps.setInt(2, nuevoUsuario.getTipoUsuario());
            ps.setString(3, nuevoUsuario.getPassword());

            ps.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                //Llave Primaria Duplicada
                listaErrores.add(new Error(numeroLinea, "Logico", "Se duplica la llave primaria de usuario"));
            }
            else{
                listaErrores.add(new Error(numeroLinea, "Logico", "No se ha podido ingresar el usuario correctamente"));
            }
        }
    }
    
}
