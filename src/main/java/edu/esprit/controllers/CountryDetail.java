package edu.esprit.controllers;

import javafx.fxml.FXML;

import java.awt.*;

import javafx.scene.control.Label;

public class CountryDetail {

    @FXML
    private Label labelCountry;

    @FXML
    private Label labelMarket;

    @FXML
    private Label labelLocale;

    @FXML
    private Label labelCurrencyTitle;

    @FXML
    private Label labelCurrency;

    @FXML
    private Label labelCurrencySymbol;

    @FXML
    private Label labelSite;

    public void setData(String country, String market, String locale, String currencyTitle, String currency, String currencySymbol, String site) {
        labelCountry.setText(country);
        labelMarket.setText(market);
        labelLocale.setText(locale);
        labelCurrencyTitle.setText(currencyTitle + " (" + currency + ")");
        labelCurrency.setText(currencySymbol);
        labelCurrencySymbol.setText(currencySymbol);
        labelSite.setText(site);

    }
}
