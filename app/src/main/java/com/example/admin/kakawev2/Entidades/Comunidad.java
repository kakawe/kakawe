package com.example.admin.kakawev2.Entidades;

/**
 * Created by admin on 02/02/2018.
 */

public class Comunidad {
    String nombre;
    String localidad;
    String direccion;

    public Comunidad(String nombre, String localidad, String direccion) {
        this.nombre = nombre;
        this.localidad = localidad;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
