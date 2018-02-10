package com.example.admin.kakawev2.Entidades;

/**
 * Created by jose on 10/02/2018.
 */

public class Usuario {
    private String nombre,correo,contrasena,fotoPerfil;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, String contrasena, String fotoPerfil) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fotoPerfil = fotoPerfil;
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

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
