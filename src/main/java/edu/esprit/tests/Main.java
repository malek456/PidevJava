package edu.esprit.tests;

import edu.esprit.services.ServiceVol;

public class Main {
    public static void main(String[] args) {
        ServiceVol sp = new ServiceVol();
        System.out.println(sp.getAll());
    }

}
