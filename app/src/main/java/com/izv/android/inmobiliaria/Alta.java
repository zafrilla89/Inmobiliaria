package com.izv.android.inmobiliaria;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;


public class Alta extends Activity {

    private EditText etlocalidad, etcalle, ettipo, etprecio;
    private String localidad, calle, tipo, precio;
    private boolean semaforo=false;
    private ArrayList<Inmueble> datos;
    private Inmueble in;
    private boolean comprobar;
    Gestorinmueble gi;

    /***********************************************************************/
    /*                                                                     */
    /*                              METODOS ON                             */
    /*                                                                     */
    /***********************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta);
        gi=new Gestorinmueble(this);
        gi.open();
        Cursor c= gi.getCursor();
        datos= (ArrayList<Inmueble>) gi.select();

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
                long idinmueble=gi.insert(in);
                Intent i = new Intent(this,Hacerfotos.class);
                Bundle b = new Bundle();
                b.putLong("id", idinmueble);
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
            tostada("NO RELLENASTE TODOS LOS CAMPOS O NO HAS HECHO FOTO");
        }

    }

    public void volver(View v){
        Intent i =new Intent(this,Principal.class);
        startActivity(i);
        finish();
    }
}
