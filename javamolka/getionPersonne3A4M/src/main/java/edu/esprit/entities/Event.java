package edu.esprit.entities;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Event {
    private int id;

    private String Type;
    private Date Datedebut;



    private Date Datefin;

    private String lieux;
    private Float Prix;
    private String Image;

    public Event(int id, String type, Date datedebut, Date datefin, Float prix, String lieux, String image) {
        this.id = id;
        Type = type;
        Datedebut = datedebut;
        Datefin = datefin;
        this.lieux = lieux;
        Prix = prix;
        Image = image;
    }




    public Event(String type, Date datedebut, Date datefin, String lieux, Float prix, String image) {
        Type = type;
        Datedebut = datedebut;
        Datefin = datefin;
        this.lieux = lieux;
        Prix = prix;
        Image = image;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public java.sql.Date getDatedebut() {
        return (java.sql.Date) Datedebut;
    }

    public void setDatedebut(Date datedebut) {
        Datedebut = datedebut;
    }

    public java.sql.Date getDatefin() {
        return (java.sql.Date) Datefin;
    }

    public void setDatefin(Date datefin) {
        Datefin = datefin;
    }

    public String getLieux() {
        return lieux;
    }

    public void setLieux(String lieux) {
        this.lieux = lieux;
    }

    public Float getPrix() {
        return Prix;
    }

    public String getImage() {
        return Image;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", Type='" + Type + '\'' +
                ", Datedebut=" + Datedebut +
                ", Datefin=" + Datefin +
                ", lieux='" + lieux + '\'' +
                ", Prix=" + Prix +
                ", Image='" + Image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id && Objects.equals(Type, event.Type) && Objects.equals(Datedebut, event.Datedebut) && Objects.equals(Datefin, event.Datefin) && Objects.equals(lieux, event.lieux) && Objects.equals(Prix, event.Prix) && Objects.equals(Image, event.Image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Type, Datedebut, Datefin, lieux, Prix, Image);
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setPrix(Float prix) {
        Prix = prix;
    }

    public void setDatedebut(Timestamp timestamp) {
    }
}

