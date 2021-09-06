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
public class Pieza {
     private String tipo;
    private int cantidadStock;

    public Pieza(String tipo, int cantidadStock) {
        this.tipo = tipo;
        this.cantidadStock = cantidadStock;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }
    
    
}
