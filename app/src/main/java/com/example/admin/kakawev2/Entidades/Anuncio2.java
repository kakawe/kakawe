package com.example.admin.kakawev2.Entidades;

/**
 * Created by admin on 19/02/2018.
 */

public class Anuncio2 {

    /*
    String key;
    String fotoCategoria;

     */
    String key;
    String correoAnunciante;
    String titulo;
    String tipo;
    String categoria;
    String foto;
    String descripcion;
    String fechaCaducidad;
    String horaCaducidad;

    public Anuncio2() {
    }

    public Anuncio2(String key, String correoAnunciante, String titulo, String tipo, String categoria, String descripcion, String fechaCaducidad, String horaCaducidad) {
        this.key = key;
        this.correoAnunciante = correoAnunciante;
        this.titulo = titulo;
        this.tipo = tipo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fechaCaducidad = fechaCaducidad;
        this.horaCaducidad = horaCaducidad;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCorreoAnunciante() {
        return correoAnunciante;
    }

    public void setCorreoAnunciante(String correoAnunciante) {
        this.correoAnunciante = correoAnunciante;
    }

    public String getHoraCaducidad() {
        return horaCaducidad;
    }

    public void setHoraCaducidad(String horaCaducidad) {
        this.horaCaducidad = horaCaducidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }
}
