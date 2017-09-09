package com.kuuhaku.project.noticiasanime;


import android.graphics.drawable.Drawable;

/**
 * Created by victor on 08-07-17.
 */

public class BusquedaBlogIsWar {

    private Conexion c;
    private Busqueda b;
    private String[] codigo;
    private String pagina = "http://www.blogiswar.net";
    private String contenido = "class=\"views\"";
    private String detalles = "class=\"excerpt\"";
    private String imagen = "attachment-gloria";

    public BusquedaBlogIsWar(){
        c = new Conexion();
        b = new Busqueda();
    }

    public Contenido[] buscarTodo(){
        codigo = c.codigoFuente(pagina);
        String[] nombres = getTitulo();
        String[] detalles = getDetalles();
        String[] urls = getURL();
        Drawable[] imagenes = getImagenes();
        Contenido[] contenidos = new Contenido[nombres.length];

        for(int i = 0; i < nombres.length; i++){
            contenidos[i] = new Contenido(nombres[i], detalles[i], urls[i], imagenes[i]);
        }

        return contenidos;
    }

    private String[] getTitulo(){

        String[] lineas = b.buscarLinea(codigo, this.contenido);//buscarLinea(this.contenido);
        String[] contenido = b.etiquetas(lineas, "href", 100);

        return contenido;
    }

    private String[] getURL(){

        String[] lineas = b.buscarLinea(codigo, this.contenido);
        String[] contenido = b.comillas(lineas, "href", 100);

        return contenido;
    }

    private String[] getDetalles(){

        String[] lineas = b.buscarLinea(codigo, this.detalles);
        String[] contenido = b.etiquetas(lineas, "\"excerpt\"", 0);

        return contenido;
    }

    private Drawable[] getImagenes(){
        String[] lineas = b.buscarLinea(codigo, imagen);
        Drawable[] imagenes = b.getImagenes(lineas);
        return imagenes;
    }

}
