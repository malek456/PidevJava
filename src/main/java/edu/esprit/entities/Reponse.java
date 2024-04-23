package com.example.reclamation.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Reponse {
    private int id;
    private Timestamp date;
    private String contenu;
    private String etat;

    private Reclamation reclamation;

    public Reponse() {
    }

    public Reponse(Timestamp date, String contenu, String etat, Reclamation reclamation) {
        this.date = date;
        this.contenu = contenu;
        this.etat = etat;
        this.reclamation = reclamation;
    }

    public Reponse(int id, Timestamp date, String contenu, String etat, Reclamation reclamation) {
        this.id = id;
        this.date = date;
        this.contenu = contenu;
        this.etat = etat;
        this.reclamation = reclamation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }


    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", date=" + date +
                ", contenu='" + contenu + '\'' +
                ", etat='" + etat +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reponse reponse = (Reponse) o;
        return Objects.equals(contenu, reponse.contenu) && Objects.equals(etat, reponse.etat) && Objects.equals(reclamation, reponse.reclamation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, contenu, etat, reclamation);
    }
}
