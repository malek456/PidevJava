package edu.esprit.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Reservation {
    private int id;
    private int number_of_persons;
    private Timestamp date_from;
    public Reservation(){}

    public Reservation(int id, int number_of_persons, Timestamp date_from) {
        this.id = id;
        this.number_of_persons = number_of_persons;
        this.date_from = date_from;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber_of_persons() {
        return number_of_persons;
    }

    public void setNumber_of_persons(int number_of_persons) {
        this.number_of_persons = number_of_persons;
    }

    public Timestamp getDate_from() {
        return date_from;
    }

    public void setDate_from(Timestamp date_from) {
        this.date_from = date_from;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "number_of_persons=" + number_of_persons +
                ", date_from=" + date_from +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && number_of_persons == that.number_of_persons && Objects.equals(date_from, that.date_from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number_of_persons, date_from);
    }
}
