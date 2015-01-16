package com.izv.android.inmobiliaria;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 2dam on 27/11/2014.
 */
public class Inmueble implements Comparable<Inmueble>, Parcelable{

    private String localidad;
    private String calle;
    private String tipo;
    private String precio;
    private String subido;
    private int id;

    public Inmueble() {
    }

    public Inmueble(String localidad, String calle, String tipo, String precio, int id) {
        this.localidad = localidad;
        this.calle = calle;
        this.tipo = tipo;
        this.precio = precio;
        this.id=id;
    }

    public Inmueble(String localidad, String calle, String tipo, String precio) {
        this.localidad = localidad;
        this.calle = calle;
        this.tipo = tipo;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getSubido() {
        return subido;
    }

    public void setSubido(String subido) {
        this.subido = subido;
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "localidad='" + localidad + '\'' +
                ", calle='" + calle + '\'' +
                ", tipo='" + tipo + '\'' +
                ", precio=" + precio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        Inmueble i=(Inmueble)o;
        if (localidad.equalsIgnoreCase(i.getLocalidad()) && calle.equalsIgnoreCase(i.getCalle())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = localidad != null ? localidad.hashCode() : 0;
        result = 31 * result + (calle != null ? calle.hashCode() : 0);
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        result = 31 * result + (precio != null ? precio.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Inmueble i) {
        if (this.getLocalidad().compareToIgnoreCase(i.getLocalidad()) == 0) {
            if (this.getCalle().compareToIgnoreCase(i.getCalle()) != 0) {
                return this.getCalle().compareToIgnoreCase(i.getCalle());
            }
        } else {
            return this.getLocalidad().compareToIgnoreCase(i.getLocalidad());
        }
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.localidad);
        parcel.writeString(this.calle);
        parcel.writeString(this.tipo);
        parcel.writeString(this.precio);
        parcel.writeInt(this.id);
    }

    public static final Parcelable.Creator<Inmueble> CREATOR = new Parcelable.Creator<Inmueble>() {

        @Override
        public Inmueble createFromParcel(Parcel p) {
            String localidad = p.readString();
            String calle = p.readString();
            String tipo = p.readString();
            String precio = p.readString();
            int id=p.readInt();
            return new Inmueble(localidad, calle, tipo, precio, id);
        }

        @Override
        public Inmueble[] newArray(int i) {
            return new Inmueble[i];
        }
    };
}

