/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectores;


import Lectores.DatosLinea;
import Muebleria.EnsamblePieza;
import Utils.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author emili
 */
public class LecturaEnsamblePieza {
     private Connection conexion = ConexionDB.getConexionDB();
    private ArrayList<DatosLinea> datosEnsamblePiezas;
    private ArrayList<Error> listaErrores;

    public LecturaEnsamblePieza(ArrayList<DatosLinea> datosEnsamblePiezas, ArrayList<Error> listaErrores) {
        this.datosEnsamblePiezas = datosEnsamblePiezas;
        this.listaErrores = listaErrores;
    }

    public void analizarEnsamblePieza() {
        for (DatosLinea datosEnsamblePieza : datosEnsamblePiezas) {
            if (datosEnsamblePieza.getDatos().length == 3) {
                try {
                    String nombreMueble = datosEnsamblePieza.getDatos()[0];
                    String nombrePieza = datosEnsamblePieza.getDatos()[1];
                    int cantidadPieza = Integer.parseInt(datosEnsamblePieza.getDatos()[2]);
                    EnsamblePieza nuevoEnsamblePieza = new EnsamblePieza(nombreMueble, nombrePieza, cantidadPieza);
                    agregarEnsamblePieza(nuevoEnsamblePieza, datosEnsamblePieza.getNumLinea());
                } catch (NumberFormatException e) {
                    listaErrores.add(new Error(datosEnsamblePieza.getNumLinea(), "Formato", "No hay un formato apropiado de la cantidad"));
                } catch (NullPointerException nullP){
                    listaErrores.add(new Error(datosEnsamblePieza.getNumLinea(), "Formato", "Alguno de los datos venian vacios"));
                }
            } else {
                listaErrores.add(new Error(datosEnsamblePieza.getNumLinea(), "Formato", "No vienen el numero de datos correctos"));
            }
        }
    }

    private void agregarEnsamblePieza(EnsamblePieza nuevoEnsamblePieza, int numeroLinea) {
        String query = "INSERT INTO Ensamble_Pieza VALUES (?,?,?)";

        try ( PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nuevoEnsamblePieza.getNombreMueble());
            ps.setString(2, nuevoEnsamblePieza.getNombrePieza());
            ps.setInt(3, nuevoEnsamblePieza.getCantidadPieza());

            ps.execute();
        } catch (SQLException e) {
            if (e.getErrorCode()==1452) {
                listaErrores.add(new Error(numeroLinea, "Logico", "La llave foranea no tiene un apunte correcto"));
            } else {
                listaErrores.add(new Error(numeroLinea, "Logico", "No se ha podido ingresar el ensamble"));
            }    
        }
    }

    
}
