package edu.esprit.projecthope.entities;

public class SessionUtilisateur {
    private static User utilisateurActuel;
    public static void demarrerSession(User user) {
        utilisateurActuel = user;
    }

    public static void arreterSession() {
        utilisateurActuel = null;
    }

    public static User getUtilisateurActuel() {
        return utilisateurActuel;
    }

    public static boolean estSessionActive() {
        return utilisateurActuel != null;
    }



}
