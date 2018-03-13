package com.kakawe.admin.kakawev2.Entidades;

/**
 * Created by jose on 12/02/2018.
 */

public class Usuario {
    private String nombre,correo,contrasena,foto;

    public Usuario(String nombre, String correo, String contrasena, String foto) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.foto = foto;
    }

    public Usuario() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
