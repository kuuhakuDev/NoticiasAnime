package com.kuuhaku.project.noticiasanime;

import android.graphics.drawable.Drawable;

/**
 * Created by victor on 08-06-17.
 */

public class Contenido {
    String titulo;
    String detalle;
    String url;
    Drawable img;

    public Contenido(){

    }

    public Contenido(String titulo, String detalle, String url, Drawable img){
        this.titulo = titulo;
        this.detalle = detalle;
        this.url = url;
        this.img = img;
    }

}
