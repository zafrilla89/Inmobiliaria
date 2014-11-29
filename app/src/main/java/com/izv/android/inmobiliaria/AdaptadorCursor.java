package com.izv.android.inmobiliaria;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by ZAFRA on 27/11/2014.
 */
public class AdaptadorCursor extends CursorAdapter {
    private Cursor c;
    public AdaptadorCursor(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup vg) {
        LayoutInflater i = LayoutInflater.from(vg.getContext());
        View v = i.inflate(R.layout.item, vg, false);
        return v;
    }

    @Override
    public void bindView(View v, Context co, Cursor c) {
        Inmueble in=new Inmueble();
        in= Gestorinmueble.getRow(c);
        TextView tvlocalidad, tvcalle, tvtipo, tvprecio;
        tvlocalidad = (TextView) v.findViewById(R.id.tvlocalidad);
        tvcalle = (TextView) v.findViewById(R.id.tvcalle);
        tvtipo =(TextView)v.findViewById(R.id.tvtipo);
        tvprecio=(TextView)v.findViewById(R.id.tvprecio);
        tvlocalidad.setText(in.getLocalidad());
        tvcalle.setText(in.getCalle());
        tvtipo.setText(in.getTipo());
        tvprecio.setText(in.getPrecio()+"€");
    }
}

