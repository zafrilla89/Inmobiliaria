package com.izv.android.inmobiliaria;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;


public class Principal extends Activity {

    private ArrayList<Inmueble> lista;
    private AdaptadorCursor a;
    private Gestorinmueble gi;
    private ListView lv;
    private Cursor c;

    /***********************************************************************/
    /*                                                                     */
    /*                              METODOS ON                             */
    /*                                                                     */
    /***********************************************************************/

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id=item.getItemId();
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int index=info.position;
        if (id == R.id.m_editar) {
            editar(index);
        }else {
            if (id == R.id.m_borrar) {
                lista= (ArrayList<Inmueble>) gi.select();
                Inmueble in =lista.get(index);
                gi.delete(in);
                borrarfoto(in);
                a.changeCursor(gi.getCursor());
                lista= (ArrayList<Inmueble>) gi.select();

            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        iniciarcomponentes();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menucontextual,menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.anadir) {
            return anadir();
        }
        return super.onOptionsItemSelected(item);
    }

    /***********************************************************************/
    /*                                                                     */
    /*                        METODOS AUXILIARES                           */
    /*                                                                     */
    /***********************************************************************/

    public boolean anadir(){
        Intent i = new Intent(this,Alta.class);
        startActivity(i);
        finish();
        return true;
    }

    public void borrarfoto(Inmueble in){
        File archivo = new File(String.valueOf(getExternalFilesDir(null)));
        String id=""+in.getId();
        String[] listaDirectorio = archivo.list();
        for (int x=0;x<listaDirectorio.length;x++){
            String archi=listaDirectorio[x];
            if (archi.indexOf("inmobiliaria_"+id) != -1){
            File f= new File(getExternalFilesDir(null),archi);
                f.delete();
            }
        }
    }

    public boolean editar(int index){
        Intent i = new Intent(this,Editar.class);
        i.putParcelableArrayListExtra("datos", lista);
        i.putExtra("index",index);
        startActivity(i);
        finish();
        return true;
    }

    public void iniciarcomponentes(){
        lista = new ArrayList<Inmueble>();
        gi=new Gestorinmueble(this);
        gi.open();
        lista= (ArrayList<Inmueble>) gi.select();
        c= gi.getCursor();
        lv=(ListView)findViewById(R.id.listView);
        a=new AdaptadorCursor(this, c);
        lv.setAdapter(a);
        final Fragmento2 f2 = (Fragmento2) getFragmentManager().findFragmentById(R.id.fragment3);
        final boolean horizontal = f2 != null && f2.isInLayout();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (horizontal) {
                    File archivo = new File(String.valueOf(getExternalFilesDir(null)));
                    ArrayList<Bitmap> lis=new ArrayList<Bitmap>();
                    Adaptador ad = new Adaptador(Principal.this,R.layout.itemfoto,lis);
                    ListView lv= (ListView)findViewById(R.id.lvfotos);
                    lv.setAdapter(ad);
                    f2.ponerimagen(ad,archivo,lista, i,lis);
                } else {
                    Intent in = new Intent(Principal.this, Secundaria.class);
                    in.putExtra("index", i);
                    in.putParcelableArrayListExtra("datos", lista);
                    startActivity(in);
                }
            }
        });
        registerForContextMenu(lv);
    }
}
