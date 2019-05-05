package com.example.ihm_android;

import android.support.v7.app.AppCompatActivity;

import java.util.Date;

public class User {
    private String username;
    private String password;
    private String nom;
    private String prenom;

    User(String username,String password){
        this.username = username;
        this.password = password;
    }

    String getNom(){ return this.nom;}
    String getPrenom(){return this.prenom;}
    String getUsername(){return this.username;}
}
