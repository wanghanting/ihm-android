package com.example.ihm_android;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Aliment {
    private String nom;
    private Date expirationDate;

    Aliment(String nom, Date expiration) {
        this.nom = nom;
        this.expirationDate=expiration;
    }
    String getNom(){ return this.nom;}
    Date getExpirationDate(){return this.expirationDate;}
}
