package com.kuuhaku.project.noticiasanime;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static java.security.AccessController.getContext;

/**
 * Created by victor on 08-07-17.
 */

public class MainPagina1Contenido extends AppCompatActivity {

    TextView tvTituloNoticia;
    ImageView ivImageNoticia;
    Conexion c;
    LinearLayout layout;
    MainPagina1Contenido activity;


    public void onCreate(Bundle b){
        super.onCreate(b);

        setContentView(R.layout.antivity_pagina1_contenido);

        Bundle bundle = getIntent().getExtras();

        c = new Conexion();
        tvTituloNoticia = (TextView) findViewById(R.id.web);
        ivImageNoticia = (ImageView) findViewById(R.id.imagenContenido);
        layout = (LinearLayout) findViewById(R.id.linearLayoutContenido);

        activity = this;

        CargarInterfaz cargar = new CargarInterfaz();
        cargar.execute(bundle.getString("URL"));

    }

    private class CargarInterfaz extends AsyncTask<String, Object, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvTituloNoticia.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {

            Object[] object = new Object[3];

            BusquedaBlogIsWarContenido b = new BusquedaBlogIsWarContenido(params[0]);

            object[0] = b.get();
            object[1] = b.getContenido();

            publishProgress(object);

            return null;
        }


        @Override
        protected void onProgressUpdate(Object... values) {
            boolean seguirTexto = true;
            TextView texto = new TextView(activity);
            Object[][] objetos = (Object[][]) values[1];

            //Titulo del la Noticia
            tvTituloNoticia.setText(Html.fromHtml((String)values[0]));
            //Imagen de la Noticia
            ivImageNoticia.setImageDrawable((Drawable) objetos[0][0]);
            //textoContenido.setText(Html.fromHtml((String) objetos[1][0]));

            for(int i = 1; i < objetos.length; i++){
                if(((Integer) objetos[i][1]) == 1){
                    ImageView imagen = new ImageView(activity);
                    imagen.setScaleType(ImageView.ScaleType.FIT_XY);
                    imagen.setAdjustViewBounds(true);
                    imagen.setPadding(dp(10),dp(10),dp(10),dp(10));
                    imagen.setImageDrawable((Drawable) objetos[i][0]);
                    layout.addView(imagen);
                    seguirTexto = true;
                }else if(((Integer)objetos[i][1]) == 0){
                    if(seguirTexto){
                        texto = new TextView(activity);
                        texto.setTextSize(dp(15));
                        texto.setPadding(0,0,0,0);
                        layout.addView(texto);
                        seguirTexto = false;
                    }
                    texto.append(Html.fromHtml((String)objetos[i][0]));
                }
            }

            //layout.addView(textoContenido);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tvTituloNoticia.setVisibility(View.VISIBLE);
        }


        private int dp(int num){
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getResources().getDisplayMetrics());
            return px;
        }

    }

}
