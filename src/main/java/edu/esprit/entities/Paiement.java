package edu.esprit.entities;

import java.util.Objects;

public class Paiement {
    private int id;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> f463f8cf478b51a942471e0ee691a3f1ff8fe75d
    private String mode;
    private int num_carte;

    public Paiement(){}

    public Paiement(int id, String mode, int num_carte) {
        this.id = id;
<<<<<<< HEAD
=======
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
>>>>>>> main
=======
>>>>>>> f463f8cf478b51a942471e0ee691a3f1ff8fe75d
        this.mode = mode;
        this.num_carte = num_carte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
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

>>>>>>> main
=======
>>>>>>> f463f8cf478b51a942471e0ee691a3f1ff8fe75d
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
<<<<<<< HEAD
<<<<<<< HEAD
                "mode='" + mode + '\'' +
=======
                "id=" + id +
                ", vol=" + vol +
                ", user=" + user +
                ", mode='" + mode + '\'' +
>>>>>>> main
=======
                "mode='" + mode + '\'' +
>>>>>>> f463f8cf478b51a942471e0ee691a3f1ff8fe75d
                ", num_carte=" + num_carte +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paiement paiement = (Paiement) o;
<<<<<<< HEAD
<<<<<<< HEAD
        return id == paiement.id && num_carte == paiement.num_carte && Objects.equals(mode, paiement.mode);
=======
        return id == paiement.id && num_carte == paiement.num_carte && Objects.equals(vol, paiement.vol) && Objects.equals(user, paiement.user) && Objects.equals(mode, paiement.mode);
>>>>>>> main
=======
        return id == paiement.id && num_carte == paiement.num_carte && Objects.equals(mode, paiement.mode);
>>>>>>> f463f8cf478b51a942471e0ee691a3f1ff8fe75d
    }

    @Override
    public int hashCode() {
<<<<<<< HEAD
<<<<<<< HEAD
        return Objects.hash(id, mode, num_carte);
=======
        return Objects.hash(id, vol, user, mode, num_carte);
>>>>>>> main
=======
        return Objects.hash(id, mode, num_carte);
>>>>>>> f463f8cf478b51a942471e0ee691a3f1ff8fe75d
    }
}
