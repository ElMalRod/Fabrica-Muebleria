/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectores;
import Lectores.DatosLinea;
import Clientes.Cliente;
import Utils.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author emili
 */
public class LecturaCliente {
  private Connection conexion = ConexionDB.getConexionDB();
    private ArrayList<DatosLinea> datosClientes;
    private ArrayList<Error> listaErrores;

    public LecturaCliente(ArrayList<DatosLinea> datosClientes, ArrayList<Error> listaErrores) {
        this.datosClientes = datosClientes;
        this.listaErrores = listaErrores;
    }

    public void analizarCliente() {
        for (DatosLinea datosCliente : datosClientes) {
            if (datosCliente.getDatos().length == 3 || datosCliente.getDatos().length == 5) {
                String nombre = datosCliente.getDatos()[0];
                String NIT = datosCliente.getDatos()[1];
                String direccion = datosCliente.getDatos()[2];

                if (NIT.contains("-")) {
                    listaErrores.add(new Error(datosCliente.getNumLinea(), "Formato", "El NIT contiene guiones"));
                } else{
                    if (datosCliente.getDatos().length == 5) {
                        String municipio = datosCliente.getDatos()[3];
                        String departamento = datosCliente.getDatos()[4];

                        Cliente clienteEspecifico = new Cliente(NIT, nombre, direccion, municipio, departamento);
                        agregarClienteEspecifico(clienteEspecifico,datosCliente.getNumLinea());
                    } else {
                        Cliente cliente = new Cliente(NIT, nombre, direccion);
                        agregarCliente(cliente,datosCliente.getNumLinea());
                    }
                }

            } else {
                listaErrores.add(new Error(datosCliente.getNumLinea(), "Formato", "No vienen el numero de datos correctos"));
            }
        }
    }

    private void agregarCliente(Cliente nuevoCliente, int numeroLinea) {
        String query = "INSERT INTO Cliente (NIT,nombre,direccion) VALUES (?,?,?)";

        try ( PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nuevoCliente.getNIT());
            ps.setString(2, nuevoCliente.getNombre());
            ps.setString(3, nuevoCliente.getDireccion());

            ps.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                //Llave Primaria Duplicada
                listaErrores.add(new Error(numeroLinea, "Logico", "Se duplica la llave primaria de cliente"));
            }
            else{
                listaErrores.add(new Error(numeroLinea, "Logico", "No se ha podido ingresar el usuario correctamente"));
            }
            e.printStackTrace(System.out);
        }
    }

    private void agregarClienteEspecifico(Cliente nuevoCliente, int numeroLinea) {
        String query = "INSERT INTO Cliente VALUES (?,?,?,?,?)";

        try ( PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nuevoCliente.getNIT());
            ps.setString(2, nuevoCliente.getNombre());
            ps.setString(3, nuevoCliente.getDireccion());
            ps.setString(4, nuevoCliente.getMunicipio());
            ps.setString(5, nuevoCliente.getDepartamento());

            ps.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                //Llave Primaria Duplicada
                listaErrores.add(new Error(numeroLinea, "Logico", "Se duplica la llave primaria de cliente"));
            }
            else{
                listaErrores.add(new Error(numeroLinea, "Logico", "No se ha podido ingresar el usuario correctamente"));
            }
            e.printStackTrace(System.out);
        }
    }
}
