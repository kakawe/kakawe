package com.example.admin.kakawev2.Entidades;

/**
 * Created by admin on 03/02/2018.
 */

public class Anuncio {
    private String titulo;
    private String mensaje;
    private int foto;
    private String tipo;

    public Anuncio() {
    }

    public Anuncio(String titulo, String mensaje, int foto, String tipo) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.foto = foto;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

}
