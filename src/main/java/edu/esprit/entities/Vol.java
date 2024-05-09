package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;
import java.sql.Timestamp;


public class Vol {

    private int id;

    private String aeroport_depart;

    private String getAeroport_arrive;

    private Timestamp date_depart;

    private Timestamp getDate_arrive;

    private  Float prix;

    private  int code;

    private int nombre_personnes;

    private String image;

    public Vol(int id, String aeroport_depart, String getAeroport_arrive, Timestamp date_depart, Timestamp getDate_arrive, Float prix, int code, int nombre_personnes, String image) {
        this.id = id;
        this.aeroport_depart = aeroport_depart;
        this.getAeroport_arrive = getAeroport_arrive;
        this.date_depart = date_depart;
        this.getDate_arrive = getDate_arrive;
        this.prix = prix;
        this.code = code;
        this.nombre_personnes = nombre_personnes;
        this.image = image;
    }

    public Vol( String aeroport_depart, String getAeroport_arrive, Timestamp date_depart, Timestamp getDate_arrive, Float prix, int code, int nombre_personnes, String image) {
        this.aeroport_depart = aeroport_depart;
        this.getAeroport_arrive = getAeroport_arrive;
        this.date_depart = date_depart;
        this.getDate_arrive = getDate_arrive;
        this.prix = prix;
        this.code = code;
        this.nombre_personnes = nombre_personnes;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAeroport_depart() {
        return aeroport_depart;
    }

    public void setAeroport_depart(String aeroport_depart) {
        this.aeroport_depart = aeroport_depart;
    }

    public String getGetAeroport_arrive() {
        return getAeroport_arrive;
    }

    public void setGetAeroport_arrive(String getAeroport_arrive) {
        this.getAeroport_arrive = getAeroport_arrive;
    }

    public java.sql.Timestamp getDate_depart() {
        return (java.sql.Timestamp) date_depart;
    }

    public void setDate_depart(Timestamp date_depart) {
        this.date_depart = date_depart;
    }

    public Timestamp getGetDate_arrive() {
        return getDate_arrive;
    }

    public void setGetDate_arrive(Timestamp getDate_arrive) {
        this.getDate_arrive = getDate_arrive;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getNombre_personnes() {
        return nombre_personnes;
    }

    public void setNombre_personnes(int nombre_personnes) {
        this.nombre_personnes = nombre_personnes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Vol{" +
                "id=" + id +
                ", aeroport_depart='" + aeroport_depart + '\'' +
                ", getAeroport_arrive='" + getAeroport_arrive + '\'' +
                ", date_depart=" + date_depart +
                ", getDate_arrive=" + getDate_arrive +
                ", prix=" + prix +
                ", code=" + code +
                ", nombre_personnes=" + nombre_personnes +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vol vol = (Vol) o;
        return id == vol.id && code == vol.code && nombre_personnes == vol.nombre_personnes && Objects.equals(aeroport_depart, vol.aeroport_depart) && Objects.equals(getAeroport_arrive, vol.getAeroport_arrive) && Objects.equals(date_depart, vol.date_depart) && Objects.equals(getDate_arrive, vol.getDate_arrive) && Objects.equals(prix, vol.prix) && Objects.equals(image, vol.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, aeroport_depart, getAeroport_arrive, date_depart, getDate_arrive, prix, code, nombre_personnes, image);
    }
}
