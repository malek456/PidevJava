package edu.esprit.entities;

import java.util.Objects;

public class Paiement {
    private int id;
    private Vol vol;
    private User user;
    private String mode;
    private int num_carte;

    public Paiement(int id, Vol vol, User user, String mode, int num_carte) {
        this.id = id;
        this.vol = vol;
        this.user = user;
        this.mode = mode;
        this.num_carte = num_carte;
    }

    public Paiement(Vol vol, User user, String mode, int num_carte) {
        this.vol = vol;
        this.user = user;
        this.mode = mode;
        this.num_carte = num_carte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                "id=" + id +
                ", vol=" + vol +
                ", user=" + user +
                ", mode='" + mode + '\'' +
                ", num_carte=" + num_carte +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paiement paiement = (Paiement) o;
        return id == paiement.id && num_carte == paiement.num_carte && Objects.equals(vol, paiement.vol) && Objects.equals(user, paiement.user) && Objects.equals(mode, paiement.mode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vol, user, mode, num_carte);
    }
}
