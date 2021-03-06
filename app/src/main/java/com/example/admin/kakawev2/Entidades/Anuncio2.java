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
    String nombreAnunciante;
    String titulo;
    String tipo;
    String categoria;
    String foto;
    String descripcion;
    String fechaCaducidad;
    String horaCaducidad;
    String piso;
    String puerta;

    public Anuncio2() {
    }

    public Anuncio2(String key, String correoAnunciante, String nombreAnunciante, String titulo, String tipo, String categoria, String descripcion, String fechaCaducidad, String horaCaducidad, String piso, String puerta) {
        this.key = key;
        this.correoAnunciante = correoAnunciante;
        this.nombreAnunciante = nombreAnunciante;
        this.titulo = titulo;
        this.tipo = tipo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fechaCaducidad = fechaCaducidad;
        this.horaCaducidad = horaCaducidad;
        this.piso = piso;
        this.puerta = puerta;
    }

    public String getNombreAnunciante() {
        return nombreAnunciante;
    }

    public void setNombreAnunciante(String nombreAnunciante) {
        this.nombreAnunciante = nombreAnunciante;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
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

    @Override
    public String toString() {
        return "Anuncio2{" +
                "key='" + key + '\'' +
                ", correoAnunciante='" + correoAnunciante + '\'' +
                ", nombreAnunciante='" + nombreAnunciante + '\'' +
                ", titulo='" + titulo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCaducidad='" + fechaCaducidad + '\'' +
                ", horaCaducidad='" + horaCaducidad + '\'' +
                ", piso='" + piso + '\'' +
                ", puerta='" + puerta + '\'' +
                '}';
    }
}
