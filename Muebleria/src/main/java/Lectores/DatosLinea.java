/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectores;

/**
 *
 * @author emili
 */
public class DatosLinea {
    private String[] datos;
    private int numeroLinea;

    public DatosLinea(String[] datos, int numeroLinea) {
        this.datos = datos;
        this.numeroLinea = numeroLinea;
    }

    public String[] getDatos() {
        return datos;
    }

    public void setDatos(String[] datos) {
        this.datos = datos;
    }

    public int getNumLinea() {
        return numeroLinea;
    }

    public void setNumLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }
    
}
