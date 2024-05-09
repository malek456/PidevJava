package edu.esprit.entities;

import java.util.Objects;

public class Hebergement {

    private int id;
    private String Name;
    private String picture;
    private String Location;

    private String Description;
    private String SelectedType; // Nouveau champ

    private String Activities;

    private Float Price;

    private double Latitude;
    private double Longitude;

    private Voyage voyage;


    public Hebergement(String name, String picture, String location, String description, String selectedType , String activities, Float price, Double latitude , Double longitude , Voyage voyage) {
        Name = name;
        this.picture = picture;
        Location = location;
        Description = description;
        SelectedType = selectedType;
        Activities = activities;
        Price = price;
        this.Latitude = latitude ;
        this.Longitude = longitude ;
        this.voyage = voyage;
    }

    public Hebergement(int id, String name, String picture, String location, String description, String selectedType , String activities, Float price , Double latitude , Double longitude, Voyage voyage) {
        this.id = id;
        Name = name;
        this.picture = picture;
        Location = location;
        Description = description;
        SelectedType = selectedType;
        Activities = activities;
        Price = price;
        this.Latitude = latitude ;
        this.Longitude = longitude ;
        this.voyage = voyage;


    }

    public String getSelectedType() {
        return SelectedType;
    }

    public void setSelectedType(String selectedType) {
        SelectedType = selectedType;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    public String getActivities() {
        return Activities;
    }

    public void setActivities(String activities) {
        Activities = activities;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        this.Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        this.Longitude = longitude;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }



    @Override
    public String toString() {
        return "Hebergement{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", picture='" + picture + '\'' +
                ", Location='" + Location + '\'' +
                ", Description='" + Description + '\'' +
                ", SelectedType='" + SelectedType + '\'' +                ", Activities='" + Activities + '\'' +
                ", Price=" + Price +
                ", latitude=" + Latitude +
                ", longitude=" + Longitude +
                ", voyage=" + voyage +

                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hebergement that = (Hebergement) o;
        return id == that.id && Objects.equals(Name, that.Name) && Objects.equals(picture, that.picture) && Objects.equals(Location, that.Location) && Objects.equals(Description, that.Description) && Objects.equals(SelectedType, that.SelectedType) && Objects.equals(Activities, that.Activities) && Objects.equals(Price, that.Price) && Objects.equals(Latitude, that.Latitude) && Objects.equals(Longitude, that.Longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Name, picture, Location, Description, SelectedType, Activities, Price , Latitude , Longitude, voyage);
    }
}
