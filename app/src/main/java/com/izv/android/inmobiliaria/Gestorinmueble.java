package com.izv.android.inmobiliaria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZAFRA on 27/11/2014.
 */
public class Gestorinmueble {
    private Ayudante abd;
    private SQLiteDatabase bd;

    public Gestorinmueble(Context c) {
        abd = new Ayudante(c);
    }

    public void open() {
        bd = abd.getWritableDatabase();
    }

    public void openRead() {
        bd = abd.getReadableDatabase();
    }

    public void close() {
        abd.close();
    }

    public long insert(Inmueble in) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaInmueble.LOCALIDAD, in.getLocalidad());
        valores.put(Contrato.TablaInmueble.CALLE, in.getCalle());
        valores.put(Contrato.TablaInmueble.TIPO, in.getTipo() );
        valores.put(Contrato.TablaInmueble.PRECIO, in.getPrecio());
        long id = bd.insert(Contrato.TablaInmueble.TABLA, null, valores);
        //id -> codigo autonumerico
        return id;
    }

    public int delete(Inmueble in) {
        String condicion = Contrato.TablaInmueble._ID + " = ?";
        String[] argumentos = { in.getId() + "" };
        int cuenta = bd.delete(
                Contrato.TablaInmueble.TABLA, condicion,argumentos);
        return cuenta;
    }


    public int update(Inmueble in) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaInmueble.LOCALIDAD, in.getLocalidad());
        valores.put(Contrato.TablaInmueble.CALLE,in.getCalle());
        valores.put(Contrato.TablaInmueble.TIPO, in.getTipo());
        valores.put(Contrato.TablaInmueble.PRECIO, in.getPrecio());
        String condicion = Contrato.TablaInmueble._ID + " = ?";
        String[] argumentos = { in.getId() + "" };
        int cuenta = bd.update(Contrato.TablaInmueble.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public List<Inmueble> select() {
        return select(null,null,null);
    }

    public List<Inmueble> select(String condicion, String[] parametros, String orderby) {
        List<Inmueble> alj = new ArrayList<Inmueble>();
            Cursor cursor = bd.query(Contrato.TablaInmueble.TABLA, null,
                    condicion, parametros, null, null, orderby);
            cursor.moveToFirst();
            Inmueble in;
            while (!cursor.isAfterLast()) {
                in = getRow(cursor);
                alj.add(in);
                cursor.moveToNext();
            }
            cursor.close();
            return alj;
    }

    public static Inmueble getRow(Cursor c) {
        Inmueble in = new Inmueble();
        in.setId(c.getInt(0));
        in.setLocalidad(c.getString(1));
        in.setCalle(c.getString(2));
        in.setTipo(c.getString(3));
        in.setPrecio(c.getString(4));
        return in;
    }

    public Inmueble getRow(long id){
        List<Inmueble> alj= select(Contrato.TablaInmueble._ID + " = ?",new String[]{ id+"" },null );
        if (alj.isEmpty()) {
            return alj.get(0);
        }
        return null;
    }

    public Cursor getCursor(String condicion, String[] parametros, String orderby) {
        Cursor cursor = bd.query(
                Contrato.TablaInmueble.TABLA, null, condicion, parametros, null, null,
                orderby);
        return cursor;
    }

    public Cursor getCursor() {
        Cursor cursor = bd.query(
                Contrato.TablaInmueble.TABLA, null, null, null, null, null,
                null);
        return cursor;
    }

}
