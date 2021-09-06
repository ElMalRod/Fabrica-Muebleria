/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectores;


import Lectores.DatosLinea;
import Muebleria.EnsambleMueble;
import Muebleria.EnsamblePieza;
import Utils.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author emili
 */
class LecturaEnsambleMueble {
    private Connection conexion = ConexionDB.getConexionDB();
    private ArrayList<DatosLinea> datosEnsambleMuebles;
    private ArrayList<Error> listaErrores;

    public LecturaEnsambleMueble(ArrayList<DatosLinea> datosEnsambleMuebles, ArrayList<Error> listaErrores) {
        this.datosEnsambleMuebles = datosEnsambleMuebles;
        this.listaErrores = listaErrores;
    }

    public void analizarEnsambleMueble() throws SQLException {
        for (DatosLinea datosEnsambleMueble : datosEnsambleMuebles) {
            if (datosEnsambleMueble.getDatos().length == 3) {

                String nombreMueble = datosEnsambleMueble.getDatos()[0];
                String nombreUsuario = datosEnsambleMueble.getDatos()[1];
                String fechaEnsamble = datosEnsambleMueble.getDatos()[2]; //dd/MM/yyyy
                LocalDate fechaEnsamblado;
                Double precioEnsamble = 0.0;
                try {
                    fechaEnsamblado = cambioFormato(fechaEnsamble); //yyyy/MM/dd
                    try {
                        //Calcular el precio de ensamblaje
                        boolean success = true;
                        ArrayList<EnsamblePieza> recetaEnsamble = recetaPorMueble(nombreMueble); //Consulta Receta por nombre mueble

                        if (recetaEnsamble.isEmpty()) {
                            listaErrores.add(new Error(datosEnsambleMueble.getNumLinea(), "Logico", "No hay receta disponible para el mueble"));
                        } else {
                            conexion.setAutoCommit(false);
                            for (EnsamblePieza ensamblePieza : recetaEnsamble) {
                                if (ensamblePieza.getCantidadPieza() > disponibilidadPieza(ensamblePieza.getNombrePieza())) {
                                    success = false;
                                    listaErrores.add(new Error(datosEnsambleMueble.getNumLinea(), "Logico", "No hay piezas disponibles de " + ensamblePieza.getNombrePieza()));
                                    conexion.rollback();
                                    break;
                                } else {
                                    precioEnsamble += costoEnsamblePieza(ensamblePieza.getNombrePieza(), ensamblePieza.getCantidadPieza());
                                }
                            }
                            if (success) {
                                EnsambleMueble nuevoEnsambleMueble = new EnsambleMueble(fechaEnsamblado, precioEnsamble, nombreUsuario, nombreMueble);
                                agregarEnsambleMueble(nuevoEnsambleMueble, datosEnsambleMueble.getNumLinea());
                                conexion.commit();
                            }
                        }
                    } catch (SQLException e) {
                        conexion.rollback();
                    } finally {
                        conexion.setAutoCommit(true);
                    }
                } catch (IllegalArgumentException | DateTimeException e) {
                    listaErrores.add(new Error(datosEnsambleMueble.getNumLinea(), "Formato", "La fecha no venia con un formato correcto"));
                }

            } else {
                listaErrores.add(new Error(datosEnsambleMueble.getNumLinea(), "Formato", "No vienen el numero de datos correctos"));
            }

        }
    }

    private void agregarEnsambleMueble(EnsambleMueble ensambleMueble, int numeroLinea) {
        String query = "INSERT INTO Ensamble_Mueble (fecha_ensamble,precio_ensamble,nombre_usuario,nombre_mueble) VALUES (?,?,?,?)";

        try ( PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setDate(1, java.sql.Date.valueOf(ensambleMueble.getFechaEnsamble()));
            ps.setDouble(2, ensambleMueble.getPrecioEnsamble());
            ps.setString(3, ensambleMueble.getNombreUsuario());
            ps.setString(4, ensambleMueble.getNombreMueble());

            ps.execute();
        } catch (SQLException e) {
            if (e.getErrorCode()==1452) {
                listaErrores.add(new Error(numeroLinea, "Logico", "El usuario "+ensambleMueble.getNombreUsuario()+" no existe en el sistema"));
            } else {
                listaErrores.add(new Error(numeroLinea, "Logico", "No se ha podido ingresar el ensamble"));
            } 
        }
    }

    private LocalDate cambioFormato(String fecha) {
        return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private ArrayList<EnsamblePieza> recetaPorMueble(String nombreMueble) {
        String query = "SELECT tipo_pieza, cantidad_pieza FROM Ensamble_Pieza WHERE nombre_mueble=?";
        ArrayList<EnsamblePieza> recetaPiezas = new ArrayList<>();

        try ( PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nombreMueble);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recetaPiezas.add(new EnsamblePieza(
                            nombreMueble,
                            rs.getString("tipo_pieza"),
                            rs.getInt("cantidad_pieza")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return recetaPiezas;
    }

    private int disponibilidadPieza(String tipoPieza) {
        String query = "SELECT count(*) FROM Asignacion_Precio where tipo_pieza = ? AND utilizada = 0";

        try ( PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, tipoPieza);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    private Double costoEnsamblePieza(String tipoPieza, int cantidadPiezas) {
        String query = "select id, precio FROM Asignacion_Precio where tipo_pieza = ? AND utilizada = 0 LIMIT ?";
        Double costoEnsamblado = 0.0;

        try ( PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, tipoPieza);
            ps.setInt(2, cantidadPiezas);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    costoEnsamblado += rs.getDouble("precio");
                    cambiarEstado(rs.getInt("id"));
                    restarExistencia(tipoPieza);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return costoEnsamblado;
    }

    private void cambiarEstado(int id) {
        String query = "UPDATE Asignacion_Precio SET utilizada = 1 WHERE id = ?";

        try ( PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
    
    private void restarExistencia(String tipoPieza){
        String query = "UPDATE Pieza SET cantidad_stock = cantidad_stock - 1 WHERE tipo = ?";
        
        try ( PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, tipoPieza);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
