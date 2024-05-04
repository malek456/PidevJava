package edu.esprit.tests;

import edu.esprit.services.ServiceVoyage;

public class Main {
    public static void main(String[] args) {
        ServiceVoyage sp = new ServiceVoyage();
        System.out.println(sp.getAll());
    }

}
