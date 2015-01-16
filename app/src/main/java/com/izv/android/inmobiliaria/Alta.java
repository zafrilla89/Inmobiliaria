package com.izv.android.inmobiliaria;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class Alta extends Activity {

    private EditText etlocalidad, etcalle, ettipo, etprecio;
    private String localidad, calle, tipo, precio;
    private boolean semaforo=false;
    private ArrayList<Inmueble> datos;
    private Inmueble in;
    private boolean comprobar;
    private Cursor c;

    /***********************************************************************/
    /*                                                                     */
    /*                              METODOS ON                             */
    /*                                                                     */
    /***********************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta);
        selectdatos();
        datos=sacarlista();

    }

    /***********************************************************************/
    /*                                                                     */
    /*                        METODOS AUXILIARES                           */
    /*                                                                     */
    /***********************************************************************/

    public boolean comprueba(Inmueble i2){
        Inmueble i;
        if (datos==null){
            return false;
        }else {
            for (int c = 0; c < datos.size(); c++) {
                i = datos.get(c);
                if (i.equals(i2) == true) {
                    return false;
                }
            }
            return true;
        }
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

    public ArrayList<Inmueble> sacarlista(){
        ArrayList<Inmueble> lista=new ArrayList<Inmueble>();
        c.moveToFirst();
        Inmueble in;
        while (!c.isAfterLast()) {
            in = getRow(c);
            lista.add(in);
            c.moveToNext();
        }
        return lista;
    }

    public void tostada (String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /***********************************************************************/
    /*                                                                     */
    /*                           METODOS ONCLICK                           */
    /*                                                                     */
    /***********************************************************************/

    public void guardar(View view){
        etlocalidad = (EditText) findViewById(R.id.etlocalidad);
        etcalle = (EditText) findViewById(R.id.etcalle);
        etprecio=(EditText)findViewById(R.id.etprecio);
        ettipo=(EditText)findViewById(R.id.ettipo);
        localidad=etlocalidad.getText().toString().trim();
        calle=etcalle.getText().toString().trim();
        precio=etprecio.getText().toString();
        tipo=ettipo.getText().toString();
        if (localidad.length() > 0 && calle.length() > 0 && precio.length() > 0 && tipo.length() > 0 ) {
            comprobar=true;
            in = new Inmueble(localidad, calle, tipo, precio);
            comprobar=comprueba(in);
            if(comprobar==true){
                insertar(in);
                selectdatos();
                datos=sacarlista();
                in=datos.get(datos.size()-1);
                Intent i = new Intent(this,Hacerfotos.class);
                Bundle b = new Bundle();
                b.putLong("id", in.getId());
                i.putExtras(b);
                startActivity(i);
                finish();
                tostada("INMUEBLE REGISTRADO");
            }else{
                tostada("INMUEBLE REPETIDO");
            }
            semaforo=true;

        }else{
            semaforo=false;
        }
        if(semaforo==false) {
            tostada("NO RELLENASTE TODOS LOS CAMPOS");
        }
    }

    public void volver(View v){
        Intent i =new Intent(this,Principal.class);
        startActivity(i);
        finish();
    }

    /***********************************************************************/
    /*                                                                     */
    /*                    METODOS CONTENT PROVIDER                         */
    /*                                                                     */
    /***********************************************************************/

    public void insertar (Inmueble in){
        Uri uri=Contrato.TablaInmueble.CONTENT_URI;
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaInmueble.CALLE, in.getCalle());
        valores.put(Contrato.TablaInmueble.LOCALIDAD, in.getLocalidad());
        valores.put(Contrato.TablaInmueble.TIPO, in.getTipo());
        valores.put(Contrato.TablaInmueble.PRECIO, in.getPrecio());
        valores.put(Contrato.TablaInmueble.SUBIDO, "0");
        Uri urielemento= getContentResolver().insert(uri,valores);
        Cursor cursor=getContentResolver().query(
                urielemento,null,null,null,null);
    }

    public void selectdatos() {
        Uri uri = Contrato.TablaInmueble.CONTENT_URI;
        String[] proyeccion = null;
        String condicion = null;
        String[] parametros = null;
        String orden = null;
        c= getContentResolver().query(
                uri,
                proyeccion,
                condicion,
                parametros,
                orden);
    }

}
