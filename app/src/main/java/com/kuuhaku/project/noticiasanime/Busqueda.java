package com.kuuhaku.project.noticiasanime;


import android.graphics.drawable.Drawable;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by victor on 08-07-17.
 */


public class Busqueda {
    private Conexion c;
    int indexTitulo;

    public Busqueda(){
        c = new Conexion();

    }


    public String[] buscarLinea(String[] codigo, String texto){
        List<String> lista = new ArrayList<String>();

        int i = 0;
        while(i < codigo.length){
            if(codigo[i].indexOf(texto) != -1){
                lista.add(codigo[i]);
                indexTitulo = i;
            }
            i++;
        }

        String[] str = listAString(lista);

        return str;
    }

    public String[] comillas(String[] param, String ref, int index){
        String[] lista = new String[param.length];

        for(int i = 0; i < param.length; i++){
            int x = 0;
            lista[i] = "";
            for(int j = param[i].indexOf(ref, index); j < param[i].length() && x !=2; j++){
                if(param[i].charAt(j) == '\"'){
                    x++;
                }
                else if(x == 1){
                    lista[i] += param[i].charAt(j);
                }
            }
        }
        return lista;
    }

    public String[] etiquetas(String[] param, String ref, int index){
        String[] lista = new String[param.length];

        for(int i = 0; i < param.length; i++){
            int x = 0;
            lista[i] = "";
            try {
                for (int j = param[i].indexOf(ref, index); j < param[i].length() && x != 2; j++) {
                    System.out.print(param[i].charAt(j));
                    if (param[i].charAt(j) == '<' || param[i].charAt(j) == '>') {
                        x++;
                    } else if (x == 1) {
                        lista[i] += param[i].charAt(j);
                    }
                }
            }catch(StringIndexOutOfBoundsException e){
                lista[i] = "<<Ocurrio un Error>>";
            }
        }
        return lista;
    }

    //Convierte un List a String[]
    private String[] listAString(List<String> lista){
        String[] str = new String[lista.size()];
        str = lista.toArray(str);
        return str;
    }

    public Drawable[] getImagenes(String[] lineas){

        String[] contenido = comillas(lineas, "src", 0);
        Drawable[] imagenes = new Drawable[contenido.length];

        for(int i = 0; i < contenido.length; i++){

            imagenes[i] = c.getDrawable(contenido[i]);

        }

        return imagenes;

    }

}