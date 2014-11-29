package com.izv.android.inmobiliaria;

import android.provider.BaseColumns;

/**
 * Created by ZAFRA on 27/11/2014.
 */
public class Contrato {

    private Contrato(){

    }

    public static abstract class TablaInmueble implements BaseColumns {
        public static final String TABLA = "inmueble";
        public static final String LOCALIDAD = "localidad";
        public static final String CALLE = "calle";
        public static final String TIPO = "tipo";
        public static final String PRECIO = "precio";
    }
}
