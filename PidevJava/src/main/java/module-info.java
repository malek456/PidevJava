module com.example.reclamation {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.apache.opennlp.tools;
    requires org.apache.commons.text;
    requires layout;
    requires kernel;
    requires javax.mail;
    requires jwi;
    requires org.junit.jupiter.api;
    requires opencv;
    requires java.desktop;
    requires javafx.swing;
    requires java.net.http;
    requires json.simple;
    requires com.google.gson;
    requires assemblyai.java;
    requires okhttp3;
    requires okio;
    requires revai.java.sdk;


    opens com.example.reclamation to javafx.fxml;
    exports com.example.reclamation;
    exports com.example.reclamation.controllers;
    opens com.example.reclamation.controllers to javafx.fxml;
    exports com.example.reclamation.test;
    opens com.example.reclamation.test to javafx.fxml;
    exports com.example.reclamation.models;
    opens com.example.reclamation.models to javafx.fxml;
    exports com.example.reclamation.chatbot;
    opens com.example.reclamation.chatbot to javafx.fxml;
    exports opennlp.summarization.textrank;
    opens opennlp.summarization.textrank to javafx.fxml;

}
