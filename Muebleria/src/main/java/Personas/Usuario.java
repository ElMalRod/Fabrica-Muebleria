/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personas;

/**
 *
 * @author emili
 */
public class Usuario {
    private String nombre;
    private int tipoUsuario;
    private String password;

    public Usuario(String nombre, int tipoUsuario, String password) {
        this.nombre = nombre;
        this.tipoUsuario = tipoUsuario;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setPassword(String password) {
        this.password = password;
    } 
}
