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
public class AsignacionPrecio {
  private int id;
    private Double precio;
    private boolean utilizada;
    private String tipoPieza;

    public AsignacionPrecio(Double precio, boolean utilizada, String tipoPieza) {
        this.precio = precio;
        this.utilizada = utilizada;
        this.tipoPieza = tipoPieza;
    }

    public AsignacionPrecio(int id, Double precio, boolean utilizada, String tipoPieza) {
        this.id = id;
        this.precio = precio;
        this.utilizada = utilizada;
        this.tipoPieza = tipoPieza;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getTipoPieza() {
        return tipoPieza;
    }

    public void setTipoPieza(String tipoPieza) {
        this.tipoPieza = tipoPieza;
    }

    public boolean isUtilizada() {
        return utilizada;
    }

    public void setUtilizada(boolean utilizada) {
        this.utilizada = utilizada;
    }
    
    
    
    
}

