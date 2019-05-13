package com.example.ihm_android;

import java.util.Date;

public class Aliment {
    private String nom;
    private Date expirationDate;
    private int quantite;
    private String unite;
    private int imgPath;
    private String type;
    private int status;
    Date currentTime = new Date();

    Aliment(String nom, Date expiration,int quantite,String  unite,int imgPath, String type) {
        this.nom = nom;
        this.expirationDate=expiration;
        this.quantite = quantite;
        this. unite =  unite;
        this.imgPath = imgPath;
        this.type = type;
        this.status = (int) ((this.expirationDate.getTime() - currentTime.getTime()) / (1000 * 60 * 60 * 24));
    }
    String getNom(){ return this.nom;}
    Date getExpirationDate(){return this.expirationDate;}
    int getQuantite(){return this.quantite;}
    String getUnite(){return this.unite;}
    int getImgPath(){return this.imgPath;}
    void setQuantite(int q) {this.quantite=q;}
    String getType(){return this.type;}
    int getStatus(){return this.status;}
}
