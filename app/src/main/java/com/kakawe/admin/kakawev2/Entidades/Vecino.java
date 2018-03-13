package com.kakawe.admin.kakawev2.Entidades;

/**
 * Created by admin on 02/02/2018.
 */

public class Vecino {
    String correo;
    String mail;
    String piso;
    String puerta;

    public Vecino(String correo, String mail, String piso, String puerta) {
        this.correo = correo;
        this.mail = mail;
        this.piso = piso;
        this.puerta = puerta;
    }

    public Vecino() {
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    @Override
    public String toString() {
        return "Vecino{" +
                "correo='" + correo + '\'' +
                ", mail='" + mail + '\'' +
                ", piso='" + piso + '\'' +
                ", puerta='" + puerta + '\'' +
                '}';
    }
}