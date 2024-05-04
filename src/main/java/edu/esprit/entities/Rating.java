package edu.esprit.entities;

public class Rating {
    private int stars;

    public Rating() {
    }

    public Rating(int stars) {
        this.stars = stars;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
