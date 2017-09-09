package com.kuuhaku.project.noticiasanime;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by victor on 08-06-17.
 */

public class ContenidoAdapter extends ArrayAdapter<Contenido> {
    Context context;
    int layoutResourceID;
    Contenido datos[];

    public ContenidoAdapter(Context context, int layoutResourceID, Contenido[] datos){
        super(context, layoutResourceID, datos);
        this.context = context;
        this.layoutResourceID = layoutResourceID;
        this.datos = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        ContenidoHolder holder = null;

        if(row == null){
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            row = layoutInflater.inflate(layoutResourceID, parent, false);

            holder = new ContenidoHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.imgList);
            holder.textView1 = (TextView) row.findViewById(R.id.tvListTitulo);
            holder.textView2 = (TextView) row.findViewById(R.id.tvListDescripcion);

            row.setTag(holder);
        }else{
            holder = (ContenidoHolder) row.getTag();
        }

        Contenido contenido = datos[position];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.textView1.setText(Html.fromHtml(contenido.titulo, Html.FROM_HTML_MODE_COMPACT));
            holder.textView2.setText(Html.fromHtml(contenido.detalle, Html.FROM_HTML_MODE_COMPACT));
        }else{
            holder.textView1.setText(Html.fromHtml(contenido.titulo));
            holder.textView2.setText(Html.fromHtml(contenido.detalle));
        }

        holder.imageView.setImageDrawable(contenido.img);

        return  row;
    }

     class ContenidoHolder{
        ImageView imageView;
        TextView textView1;
        TextView textView2;
    }
}
