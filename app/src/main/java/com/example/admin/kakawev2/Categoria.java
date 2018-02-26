package com.example.admin.kakawev2;

/**
 * Created by jose on 26/02/2018.
 */

public class Categoria {
    private String categoria;
    private int fotoCategoria;

    public Categoria(String categoria, int fotoCategoria) {
        this.categoria = categoria;
        this.fotoCategoria = fotoCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getFotoCategoria() {
        return fotoCategoria;
    }

    public void setFotoCategoria(int fotoCategoria) {
        this.fotoCategoria = fotoCategoria;
    }
}
