package edu.esprit.entities;

import java.util.Objects;

public class Paiement {
    private int id;
    private String mode;
    private int num_carte;

    public Paiement(){}

    public Paiement(int id, String mode, int num_carte) {
        this.id = id;
        this.mode = mode;
        this.num_carte = num_carte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getNum_carte() {
        return num_carte;
    }

    public void setNum_carte(int num_carte) {
        this.num_carte = num_carte;
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "mode='" + mode + '\'' +
                ", num_carte=" + num_carte +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paiement paiement = (Paiement) o;
        return id == paiement.id && num_carte == paiement.num_carte && Objects.equals(mode, paiement.mode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mode, num_carte);
    }
}
