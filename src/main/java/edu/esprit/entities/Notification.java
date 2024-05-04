package edu.esprit.entities;

import java.sql.Timestamp;

public class Notification {
    private int id;
    private Reclamation reclamation;
    private Reponse reponse;
    private User client;
    private String type;
    private int active;
    private Timestamp date;

    public Notification() {
    }

    public Notification(int id, Reclamation reclamation, Reponse reponse, User client, String type, int active, Timestamp date) {
        this.id = id;
        this.reclamation = reclamation;
        this.reponse = reponse;
        this.client = client;
        this.type = type;
        this.active = active;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    public Reponse getReponse() {
        return reponse;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
