/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectores;


import Lectores.DatosLinea;
import Muebleria.Mueble;
import Utils.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author emili
 */
public class LecturaMueble {
     private Connection conexion = ConexionDB.getConexionDB();
    private ArrayList<DatosLinea> datosMuebles;
    private ArrayList<Error> listaErrores;

    public LecturaMueble(ArrayList<DatosLinea> datosMuebles, ArrayList<Error> listaErrores) {
        this.datosMuebles = datosMuebles;
        this.listaErrores = listaErrores;
    }

    public void analizarMueble() {
        for (DatosLinea datosMueble : datosMuebles) {
            if (datosMueble.getDatos().length == 2) {
                String nombre = datosMueble.getDatos()[0];
                Double precio;
                try {
                    precio = Double.parseDouble(datosMueble.getDatos()[1]);
                    Mueble nuevoMueble = new Mueble(nombre, precio);
                    agregarMueble(nuevoMueble, datosMueble.getNumLinea());
                } catch (NumberFormatException e) {
                    listaErrores.add(new Error(datosMueble.getNumLinea(), "Formato", "No hay un formato apropiado del precio"));
                }
            } else {
                listaErrores.add(new Error(datosMueble.getNumLinea(), "Formato", "No vienen el numero de datos correctos"));
            }
        }
    }

    private void agregarMueble(Mueble nuevoMueble, int numeroLinea) {
        String query = "INSERT INTO Mueble VALUES (?,?)";

        try ( PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nuevoMueble.getNombre());
            ps.setDouble(2, nuevoMueble.getPrecio());

            ps.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                //Llave Primaria Duplicada
                listaErrores.add(new Error(numeroLinea, "Logico", "Se duplica la llave primaria del mueble"));
            }
            else{
                listaErrores.add(new Error(numeroLinea, "Logico", "No se ha podido ingresar el mueble correctamente"));
            }
        }
    }

    
}
