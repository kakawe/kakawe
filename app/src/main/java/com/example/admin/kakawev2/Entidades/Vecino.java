package com.example.admin.kakawev2.Entidades;

/**
 * Created by admin on 02/02/2018.
 */

public class Vecino {
    String correo;
    String mail;
    String piso;
    String puerta;

    public Vecino() {
    }

    public Vecino(String correo,String mail, String piso, String puerta) {
        this.correo = correo;
        this.mail = mail;
        this.piso = piso;
        this.puerta = puerta;
    }

    public String getMail() {
        return mail;
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
