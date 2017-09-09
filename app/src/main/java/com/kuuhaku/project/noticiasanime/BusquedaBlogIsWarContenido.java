package com.kuuhaku.project.noticiasanime;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 08-07-17.
 */

public class BusquedaBlogIsWarContenido {

    private Conexion c;
    private Busqueda b;
    private String titulo = "class=\"s-post-header\"";
    private String imagen = "<img";
    //private String contenido = "";
    private String[] codigo;
    private String url;
    private int indexTitulo;
    private Integer[] numImagenes;
    private Object[][] contenido;

    public BusquedaBlogIsWarContenido(String url){
        c = new Conexion();
        b = new Busqueda();
        this.url = url;
        codigo = c.codigoFuente(url);
    }

    public String get(){


        String[] linea = b.buscarLinea(codigo, this.titulo);
        indexTitulo = b.indexTitulo;
        String[] titulo = b.etiquetas(linea, "h1", 0);

        return titulo[0];
    }

    public Object[][] getContenido(){

        return buscarLinea(codigo, this.imagen, indexTitulo);
    }

    private Object[][] buscarLinea(String[] codigo, String texto, int index){
        boolean entrar = false;

        List<String> listaString = new ArrayList<String>();
        List<Integer> listaInteger = new ArrayList<Integer>();


        for(int i = index; i < codigo.length && codigo[i].indexOf("end article content") == -1; i++){
            if(codigo[i].indexOf(texto) != -1){
                listaString.add(codigo[i]);
                listaInteger.add(1);
                entrar = true;
            }else if(entrar){
                listaString.add(codigo[i]);
                listaInteger.add(0);
            }

        }

        contenido = new Object[listaInteger.size()][2];
        String[] x = new String[1];
        for(int i = 0; i < listaInteger.size(); i++){
            if(listaInteger.get(i) == 1){
                x[0] = listaString.get(i);
                contenido[i][0] = b.getImagenes(x)[0];
            }else {
                contenido[i][0] = listaString.get(i);
            }
            contenido[i][1] = listaInteger.get(i);
        }
        return contenido;
    }

    public StringBuilder getTexto(String[] codigo, Integer[] integers, int index){

        StringBuilder textolista = new StringBuilder();

        boolean entrar = true;

        for(int i = index; i < codigo.length && codigo[i].indexOf("end article content") == -1; i++){

            for(int j = 0; j < integers.length; j++){
                if(integers[j] == i){
                    entrar = false;
                }
            }
            if(entrar){
                textolista.append(codigo[i]);
            }
            entrar = true;

        }


        return textolista;
    }

}
