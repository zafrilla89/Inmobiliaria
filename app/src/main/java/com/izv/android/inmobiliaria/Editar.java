package com.izv.android.inmobiliaria;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class Editar extends Activity {

    private ArrayList<Inmueble> lista;
    private int index;
    private EditText etlocalidad, etcalle, ettipo, etprecio;
    private Inmueble i;
    private Gestorinmueble gi;

    /***********************************************************************/
    /*                                                                     */
    /*                              METODOS ON                             */
    /*                                                                     */
    /***********************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);
        gi=new Gestorinmueble(this);
        gi.open();
        Cursor c= gi.getCursor();
        Bundle b = getIntent().getExtras();
        if(b !=null ) {
            lista = b.getParcelableArrayList("datos");
            index=b.getInt("index");
        }
        etlocalidad = (EditText) findViewById(R.id.localidad);
        etcalle = (EditText) findViewById(R.id.calle);
        etprecio=(EditText)findViewById(R.id.precio);
        ettipo=(EditText)findViewById(R.id.tipo);
        i=lista.get(index);
        etlocalidad.setText(i.getLocalidad());
        etcalle.setText(i.getCalle());
        ettipo.setText(i.getTipo());
        etprecio.setText(i.getPrecio());
    }

    /***********************************************************************/
    /*                                                                     */
    /*                        METODOS AUXILIARES                           */
    /*                                                                     */
    /***********************************************************************/

    public boolean comprueba(Inmueble i2, Inmueble i){
        Inmueble i3;
        if(i.equals(i2)){
            return true;
        }else {
            for (int c = 0; c < lista.size(); c++) {
                i = lista.get(c);
                if (i2.equals(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void tostada (String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /***********************************************************************/
    /*                                                                     */
    /*                           METODOS ONCLICK                           */
    /*                                                                     */
    /***********************************************************************/

    public void editar(View v){
        boolean semaforo;
        String lo=etlocalidad.getText().toString().trim();
        String ca=etcalle.getText().toString().trim();
        String ti=ettipo.getText().toString().trim();
        String pr=etprecio.getText().toString().trim();
        if (lo.length() > 0 && ca.length() > 0 && ti.length() > 0 && pr.length() > 0 ) {
            Inmueble i2=new Inmueble(lo,ca,ti,pr);
            boolean comprobar=true;
            comprobar=comprueba(i2,i);
            if(comprobar==true){
                i2.setId(i.getId());
                gi.update(i2);
                int id=i.getId();
                Intent i = new Intent(this,Hacerfotos.class);
                Bundle b = new Bundle();
                b.putLong("id", id);
                i.putExtras(b);
                startActivity(i);
                finish();
                tostada("INMUEBLE EDITADO");
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
}
