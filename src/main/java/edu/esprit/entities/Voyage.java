package edu.esprit.entities;

public class Voyage {
    private int id;
    private String Destination;
    private String picture;

    private String offer;

    @Override
    public String toString() {
        return "Voyage{" +
                "id=" + id +
                ", Destination='" + Destination + '\'' +
                ", picture='" + picture + '\'' +
                ", offer='" + offer + '\'' +
                '}';
    }

    public Voyage(String destination, String picture, String offer) {
        this.Destination = destination;
        this.picture = picture;
        this.offer = offer;
    }

    public Voyage(int id, String destination, String picture, String offer) {
        this.id = id;
        this.Destination = destination;
        this.picture = picture;
        this.offer = offer;
    }

    public static void add(Hebergement he) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        this.Destination = destination;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
