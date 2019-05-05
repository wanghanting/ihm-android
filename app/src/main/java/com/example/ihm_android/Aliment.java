package com.example.ihm_android;

import java.util.Date;

public class Aliment {
    private String nom;
    private Date expirationDate;
    private int quantite;
    private String unite;

    Aliment(String nom, Date expiration,int quantite,String  unite) {
        this.nom = nom;
        this.expirationDate=expiration;
        this.quantite = quantite;
        this. unite =  unite;
    }
    String getNom(){ return this.nom;}
    Date getExpirationDate(){return this.expirationDate;}
    int getQuantite(){return this.quantite;}
    String getUnite(){return this.unite;}
    void setQuantite(int q) {this.quantite=q;}
}
