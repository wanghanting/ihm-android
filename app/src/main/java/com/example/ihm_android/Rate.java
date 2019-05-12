package com.example.ihm_android;

public class Rate {
    private String nom;
    private String sum;

    Rate(String nom,String sum){
        this.nom = nom;
        this.sum = sum;
    }

    public String getNom() {
        return nom;
    }

    public String getSum() {
        return sum;
    }
}
