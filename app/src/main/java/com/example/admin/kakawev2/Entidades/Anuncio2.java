package com.example.admin.kakawev2.Entidades;

/**
 * Created by admin on 19/02/2018.
 */

public class Anuncio2 {

    /*
    String key;

     */
    String correoAnunciante;
    String titulo;
    String tipo;
    String categoria;
    String foto;
    String descripcion;
    String fechaPublicacion;
    String fechaCaducidad;
    String horaCaducidad;
    String horaPublicacion;

    public Anuncio2() {
    }


    public Anuncio2(String correoAnunciante, String titulo, String tipo, String categoria, String foto, String descripcion, String fechaPublicacion, String fechaCaducidad) {
        this.correoAnunciante = correoAnunciante;
        this.titulo = titulo;
        this.tipo = tipo;
        this.categoria = categoria;
        this.foto = foto;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaCaducidad = fechaCaducidad;
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

    public String getHoraPublicacion() {
        return horaPublicacion;
    }

    public void setHoraPublicacion(String horaPublicacion) {
        this.horaPublicacion = horaPublicacion;
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

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }
}
