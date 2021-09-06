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
public class Error {
        private int numeroLineaError;
    private String tipoError;
    private String mensajeError;

    public Error(int numeroLineaError, String tipoError, String mensajeError) {
        this.numeroLineaError = numeroLineaError;
        this.tipoError = tipoError;
        this.mensajeError = mensajeError;
    }

    public Error(int numeroLineaError, String mensajeError) {
        this.numeroLineaError = numeroLineaError;
        this.mensajeError = mensajeError;
    }

    public int getNumeroLineaError() {
        return numeroLineaError;
    }

    public void setNumeroLineaError(int numeroLineaError) {
        this.numeroLineaError = numeroLineaError;
    }

    public String getTipoError() {
        return tipoError;
    }

    public void setTipoError(String tipoError) {
        this.tipoError = tipoError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
