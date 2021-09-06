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
public class Cliente {
     
    private String NIT;
    private String nombre;
    private String direccion;
    private String municipio;
    private String departamento;

    public Cliente(String NIT, String nombre, String direccion) {
        this.NIT = NIT;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Cliente(String NIT, String nombre, String direccion, String municipio, String departamento) {
        this.NIT = NIT;
        this.nombre = nombre;
        this.direccion = direccion;
        this.municipio = municipio;
        this.departamento = departamento;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
}
