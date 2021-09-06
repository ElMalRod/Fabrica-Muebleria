package Lectores;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Lectores.DatosLinea;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 *
 * @author emili
 */
public class Lector {
private ArrayList<Error> listaErrores;

    public void leerTXT(InputStream inputStream) {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

            String linea;
            int lineaAnalizada = 0; //Apoyo para indicar la linea del error
            ArrayList<DatosLinea> usuarios = new ArrayList<>();
            ArrayList<DatosLinea> piezas = new ArrayList<>();
            ArrayList<DatosLinea> muebles = new ArrayList<>();
            ArrayList<DatosLinea> ensamblePiezas = new ArrayList<>();
            ArrayList<DatosLinea> ensamblarMueble = new ArrayList<>();
            ArrayList<DatosLinea> clientes = new ArrayList<>();
            listaErrores = new ArrayList<>();

            while ((linea = br.readLine()) != null) {
                lineaAnalizada++;
                int primerParentesis = 0;
                int ultimoParentesis = 0;
                primerParentesis = posicionPrimerParentesis(linea);
                ultimoParentesis = posicionUltimoParentesis(linea);

                if (linea.startsWith("USUARIO")) {
                    if (comprobacionParentesis(linea, 7, lineaAnalizada)) {
                        String[] contUsuario = extraerContenido(primerParentesis, ultimoParentesis, linea);
                        if (contUsuario==null) {
                            listaErrores.add(new Error(lineaAnalizada, "Formato", "Uno de los datos viene vacio"));
                        } else {
                        usuarios.add(new DatosLinea(contUsuario, lineaAnalizada));
                        }
                    }
                } else if (linea.startsWith("PIEZA")) {
                    if (comprobacionParentesis(linea, 5, lineaAnalizada)) {
                        String[] contPieza = extraerContenido(primerParentesis, ultimoParentesis, linea);
                        if (contPieza==null) {
                            listaErrores.add(new Error(lineaAnalizada, "Formato", "Uno de los datos viene vacio"));
                        } else {
                        piezas.add(new DatosLinea(contPieza, lineaAnalizada));
                        }
                    }
                } else if (linea.startsWith("MUEBLE")) {
                    if (comprobacionParentesis(linea, 6, lineaAnalizada)) {
                        String[] contMueble = extraerContenido(primerParentesis, ultimoParentesis, linea);
                        if (contMueble==null) {
                            listaErrores.add(new Error(lineaAnalizada, "Formato", "Uno de los datos viene vacio"));
                        } else {
                        muebles.add(new DatosLinea(contMueble, lineaAnalizada));
                        }
                    }
                } else if (linea.startsWith("ENSAMBLE_PIEZAS")) {
                    if (comprobacionParentesis(linea, 15, lineaAnalizada)) {
                        String[] contEnsamPieza = extraerContenido(primerParentesis, ultimoParentesis, linea);
                        if (contEnsamPieza==null) {
                            listaErrores.add(new Error(lineaAnalizada, "Formato", "Uno de los datos viene vacio"));
                        } else {
                        ensamblePiezas.add(new DatosLinea(contEnsamPieza, lineaAnalizada));
                        }
                    }
                } else if (linea.startsWith("ENSAMBLAR_MUEBLE")) {
                    if (comprobacionParentesis(linea, 16, lineaAnalizada)) {
                        String[] contEnsamMueble = extraerContenido(primerParentesis, ultimoParentesis, linea);
                        if (contEnsamMueble==null) {
                            listaErrores.add(new Error(lineaAnalizada, "Formato", "Uno de los datos viene vacio"));
                        } else {
                        ensamblarMueble.add(new DatosLinea(contEnsamMueble, lineaAnalizada));
                        }
                    }
                } else if (linea.startsWith("CLIENTE")) {
                    if (comprobacionParentesis(linea, 7, lineaAnalizada)) {
                        String[] contCliente = extraerContenido(primerParentesis, ultimoParentesis, linea);
                        if (contCliente==null) {
                            listaErrores.add(new Error(lineaAnalizada, "Formato", "Uno de los datos viene vacio"));
                        } else {
                        clientes.add(new DatosLinea(contCliente, lineaAnalizada));
                        }
                    }
                } else {
                    listaErrores.add(new Error(lineaAnalizada, "Formato", "La linea no inicia correctamente"));
                }
            }

            LecturaUsuario lecturaUsuario = new LecturaUsuario(usuarios, listaErrores);
            lecturaUsuario.analizarUsuario();

            LecturaCliente lecturaCliente = new LecturaCliente(clientes, listaErrores);
            lecturaCliente.analizarCliente();

            LecturaPieza lecturaPieza = new LecturaPieza(piezas, listaErrores);
            lecturaPieza.analizarPieza();

            LecturaMueble lecturaMueble = new LecturaMueble(muebles, listaErrores);
            lecturaMueble.analizarMueble();

            LecturaEnsamblePieza lecturaEnsamblePieza = new LecturaEnsamblePieza(ensamblePiezas, listaErrores);
            lecturaEnsamblePieza.analizarEnsamblePieza();

            LecturaEnsambleMueble lecturaEnsambleMueble = new LecturaEnsambleMueble(ensamblarMueble, listaErrores);
            lecturaEnsambleMueble.analizarEnsambleMueble();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Comprobar si vienen ambos parentesis, para luego extraer solo el
     * contenido
     *
     * @param linea
     * @param primerParentesis Varia segun la palabra inicial
     * @return
     */
    private boolean comprobacionParentesis(String linea, int primerParentesis, int lineaAnalizada) {
        if (empiezaParentesis(linea, primerParentesis)) {
            if (terminaParentesis(linea)) {
                return true;
            } else {
                //No contiene parentesis cierre
                listaErrores.add(new Error(lineaAnalizada, "Formato", "No existe parentesis de cierre"));
                return false;
            }
        }
        //No contiene parentesis de apertura
        listaErrores.add(new Error(lineaAnalizada, "Formato", "No existe parentesis de apertura"));
        return false;
    }

    private int posicionPrimerParentesis(String linea) {
        return linea.indexOf("(") + 1;
    }

    private int posicionUltimoParentesis(String linea) {
        return linea.length() - 1;
    }

    private boolean empiezaParentesis(String linea, int posicion) {
        return linea.substring(posicion, posicion + 1).equals("(");
    }

    private boolean terminaParentesis(String linea) {
        return linea.endsWith(")");
    }

    private String[] extraerContenido(int primerParentesis, int ultimoParentesis, String linea) {
        String contenido = linea.substring(primerParentesis, ultimoParentesis); //Obtenemos los datos de la entidad
        String[] contenidoExtraido = contenido.split(",");
        
        for (int i = 0; i < contenidoExtraido.length; i++) {
            if (!contenidoExtraido[i].equals("\"")) {
               if (contenidoExtraido[i].startsWith("\"") && contenidoExtraido[i].endsWith("\"")) {
                contenidoExtraido[i] = contenidoExtraido[i].substring(1, contenidoExtraido[i].length() - 1);
                } 
            }
        }
        
        for (String content : contenidoExtraido) {
            if (content.length()==0) {
                contenidoExtraido=null;
                return contenidoExtraido;
            }
        }
        
        return contenidoExtraido;
    }

    public ArrayList<Error> getListaErrores() {
        return listaErrores;
    }

}
