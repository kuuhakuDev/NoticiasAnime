package com.kuuhaku.project.noticiasanime;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * Created by victor on 08-04-17.
 */

public class MainPagina1 extends AppCompatActivity{

    ListView listView;
    ProgressBar barraCarga;
    Conexion con;
    BusquedaBlogIsWar busquedaBlogIsWar;
    Intent intent;
    Contenido[] contenido;
    ContenidoAdapter adapter;

    public void onCreate(Bundle b){
        super.onCreate(b);

        setContentView(R.layout.activity_pagina1_main);
        intent = new Intent(this, MainPagina1Contenido.class);

        barraCarga = (ProgressBar) findViewById(R.id.progressBar);
        barraCarga.setVisibility(ProgressBar.INVISIBLE);
        con = new Conexion();
        busquedaBlogIsWar = new BusquedaBlogIsWar();

        listView = (ListView) findViewById(R.id.ListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Contenido item = adapter.datos[position];
                bundle.putString("URL", item.url);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        CargarInterfaz carga = new CargarInterfaz();
        carga.execute(this);

    }



    private class CargarInterfaz extends AsyncTask<MainPagina1, MainPagina1, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Aqui puedo colocar la barra de carga :3
            barraCarga.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(MainPagina1... params) {
            contenido = busquedaBlogIsWar.buscarTodo();

            publishProgress(params[0]);

            return null;
        }

        @Override
        protected void onProgressUpdate(MainPagina1... values) {
            super.onProgressUpdate(values);
            adapter = new ContenidoAdapter(values[0], R.layout.listview_items, contenido);

            listView.setAdapter(adapter);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            barraCarga.setVisibility(ProgressBar.INVISIBLE);
        }
    }
}
