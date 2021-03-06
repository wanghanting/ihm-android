package com.example.ihm_android;

import android.support.v7.app.AppCompatActivity;

import java.util.Date;

public class User {
    private String username;
    private String password;
    private String lastName;
    private String firstName;
    private String smallDescription;
    private String longDescription;
    private String numeroTel;

    User(String username,String password){
        this.username = username;
        this.password = password;
        this.lastName = "";
        this.firstName = "";
        this.smallDescription = "";
        this.longDescription = "";
        this.numeroTel="";
    }

    User(String username, String lastName, String firstName, String smallDescription, String longDescription, String numeroTel){
        this.username = username;
        this.password = "";
        this.lastName = lastName;
        this.firstName = firstName;
        this.smallDescription = smallDescription;
        this.longDescription = longDescription;
        this.numeroTel = numeroTel;
    }

    public void setLastName(String lastName){this.lastName = lastName;}
    public void setFirstName(String firstName){this.firstName = firstName;}
    public void setSmallDescription(String smallDescription){this.smallDescription = smallDescription;}
    public void setLongDescription(String longDescription){this.longDescription = longDescription;}
    public void setNumeroTel(String numeroTel){this.numeroTel =numeroTel;}


    public String getUsername() {return username;}
    public String getPassword(){return password;}
    public String getLastName(){return lastName;}
    public String getFirstName(){return firstName;}
    public String getSmallDescription(){return smallDescription;}
    public String getLongDescription(){return longDescription;}
    public String getNumeroTel() {return numeroTel;}
}
