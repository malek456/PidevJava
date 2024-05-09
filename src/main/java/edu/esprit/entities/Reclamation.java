<<<<<<< HEAD
package com.example.reclamation.models;
=======
package edu.esprit.entities;
>>>>>>> ba038a7 (metiers+api)

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Reclamation {
    private int id;
    private String type;
    private Date date;
    private String description;
    private String statut;
    private Timestamp date_envoi;
    private User id_client;
    private Reservation id_reservation;

    private Paiement id_paiement;

    public Reclamation() {
    }

    public Reclamation(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public Reclamation(int id, String type, Date date, String description, String statut, Timestamp date_envoi, User id_client, Reservation id_reservation, Paiement id_paiement) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.description = description;
        this.statut = statut;
        this.date_envoi = date_envoi;
        this.id_client = id_client;
        this.id_reservation = id_reservation;
        this.id_paiement = id_paiement;
    }

    public Reclamation(String type, Date date, String description, String statut, Timestamp date_envoi,Paiement id_paiement, Reservation id_reservation) {
        this.type = type;
        this.date = date;
        this.description = description;
        this.statut = statut;
        this.date_envoi = date_envoi;
        this.id_paiement = id_paiement;
        this.id_reservation = id_reservation;
    }

    public Reclamation(String type, Date date, String description, String statut, Timestamp date_envoi, User id_client) {
        this.type = type;
        this.date = date;
        this.description = description;
        this.statut = statut;
        this.date_envoi = date_envoi;
        this.id_client = id_client;
    }

<<<<<<< HEAD
=======
    public Reclamation(int id, String type, String description, String statut, Timestamp date_envoi, User id_client) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.statut = statut;
        this.date_envoi = date_envoi;
        this.id_client = id_client;
    }

>>>>>>> ba038a7 (metiers+api)
    public Reclamation(int id, String type, Date date, String description, String statut, Timestamp date_envoi, User id_client) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.description = description;
        this.statut = statut;
        this.date_envoi = date_envoi;
        this.id_client = id_client;
    }

<<<<<<< HEAD
=======
    public Reclamation(String type, String description, String statut, Timestamp date_envoi, User id_client) {
        this.type = type;
        this.description = description;
        this.statut = statut;
        this.date_envoi = date_envoi;
        this.id_client = id_client;
    }

>>>>>>> ba038a7 (metiers+api)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Timestamp getDate_envoi() {
        return date_envoi;
    }

    public void setDate_envoi(Timestamp date_envoi) {
        this.date_envoi = date_envoi;
    }

    public User getId_client() {
        return id_client;
    }

    public void setId_client(User id_client) {
        this.id_client = id_client;
    }

    public Reservation getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(Reservation id_reservation) {
        this.id_reservation = id_reservation;
    }

    public Paiement getId_paiement() {
        return id_paiement;
    }

    public void setId_paiement(Paiement id_paiement) {
        this.id_paiement = id_paiement;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", statut='" + statut + '\'' +
                ", date_envoi=" + date_envoi +
                ", id_client=" + id_client +
                ", id_reservation=" + id_reservation +
                ", id_paiement=" + id_paiement +
                "} \n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return Objects.equals(type, that.type) && Objects.equals(date, that.date) && Objects.equals(description, that.description) &&  Objects.equals(id_client, that.id_client) && Objects.equals(id_reservation, that.id_reservation) && Objects.equals(id_paiement, that.id_paiement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, date, description, statut, date_envoi, id_client, id_reservation, id_paiement);
    }
}
