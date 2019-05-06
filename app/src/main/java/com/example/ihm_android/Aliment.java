package com.example.ihm_android;

import java.util.Date;

public class Aliment {
    private String nom;
    private Date expirationDate;
    private int quantite;
    private String unite;
    private int imgPath;

    Aliment(String nom, Date expiration,int quantite,String  unite,int imgPath) {
        this.nom = nom;
        this.expirationDate=expiration;
        this.quantite = quantite;
        this. unite =  unite;
        this.imgPath = imgPath;
    }
    String getNom(){ return this.nom;}
    Date getExpirationDate(){return this.expirationDate;}
    int getQuantite(){return this.quantite;}
    String getUnite(){return this.unite;}
    int getImgPath(){return this.imgPath;}
    void setQuantite(int q) {this.quantite=q;}
}
