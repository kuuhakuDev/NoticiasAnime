package com.kuuhaku.project.noticiasanime;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by victor on 08-03-17.
 */

public class Conexion {
    private List<String> codigo = new ArrayList<String>();

    public String[] codigoFuente(String pagina) {
        URL url;
        HttpURLConnection httpcon;
        String linea;
        try {
            // Se abre la conexión
            url = new URL(pagina);

            httpcon = (HttpURLConnection) url.openConnection();
            httpcon.setConnectTimeout(1000);
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");

            InputStreamReader isr = new InputStreamReader(httpcon.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            // Lectura
            while((linea = br.readLine()) != null){
                codigo.add(linea);
            }

            br.close();
            isr.close();
            httpcon.disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String[] codigoFuente = new String[codigo.size()];
        codigoFuente = codigo.toArray(codigoFuente);

        return codigoFuente;
    }

    public Drawable getDrawable(String url) {
        try {
            URL link = new URL(url);
            HttpURLConnection http = (HttpURLConnection) link.openConnection();
            http.setConnectTimeout(1000);
            InputStream is = http.getInputStream();

            Drawable d = Drawable.createFromStream(is, "src name");

            is.close();
            http.disconnect();
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
