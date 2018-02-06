package com.example.admin.kakawev2.Entidades;

/**
 * Created by admin on 02/02/2018.
 */

public class Usuario {
    String correo;
    String piso;
    String puerta;

    public Usuario() {
    }

    public Usuario(String correo, String piso, String puerta) {
        this.correo = correo;
        this.piso = piso;
        this.puerta = puerta;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        correo = correo;
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
}
