package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Commentaire {
    private int id ;
    private Date date ;

    private Event event;
    private String contenu;

    public Commentaire(int id, Event event, Date date, String contenu) {
        this.id = id;
        this.date = date;
        this.event = event;
        this.contenu = contenu;
    }

    public Commentaire( Event event, Date date, String contenu) {
        this.date = date;
        this.event = event;
        this.contenu = contenu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Date getDate() {
        return (java.sql.Date) date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", date=" + date +
                ", event=" + event +
                ", contenu='" + contenu + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commentaire that = (Commentaire) o;
        return id == that.id && Objects.equals(date, that.date) && Objects.equals(event, that.event) && Objects.equals(contenu, that.contenu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, event, contenu);
    }
}
