/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Muebleria;

/**
 *
 * @author emili
 */
public class EnsamblePieza {
     private String nombreMueble;
    private String nombrePieza;
    private int cantidadPieza;

    public EnsamblePieza(String nombreMueble, String nombrePieza, int cantidadPieza) {
        this.nombreMueble = nombreMueble;
        this.nombrePieza = nombrePieza;
        this.cantidadPieza = cantidadPieza;
    }

    public String getNombreMueble() {
        return nombreMueble;
    }

    public void setNombreMueble(String nombreMueble) {
        this.nombreMueble = nombreMueble;
    }

    public String getNombrePieza() {
        return nombrePieza;
    }

    public void setNombrePieza(String nombrePieza) {
        this.nombrePieza = nombrePieza;
    }

    public int getCantidadPieza() {
        return cantidadPieza;
    }

    public void setCantidadPieza(int cantidadPieza) {
        this.cantidadPieza = cantidadPieza;
    }
    
    
}
