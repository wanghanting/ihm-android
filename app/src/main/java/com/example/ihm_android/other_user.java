package com.example.ihm_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class other_user extends AppCompatActivity {

    TextView tvUserNameFollower;
    TextView etLastNameFollower;
    TextView etFirstNameFollower;
    TextView etShortDescriptionFollower;
    TextView etLongDescriptionFollower;
    TextView tvNumeroTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String nom = intent.getStringExtra("nom");
        String prenom = intent.getStringExtra("prenom");
        String smalldescription = intent.getStringExtra("smalldescription");
        String longDescription = intent.getStringExtra("longDescription");
        String numeroTel = intent.getStringExtra("numeroTel");

        tvUserNameFollower = (TextView) findViewById(R.id.tvUserNameFollower);
        etLastNameFollower = (TextView) findViewById(R.id.etLastNameFollower);
        etFirstNameFollower = (TextView) findViewById(R.id.etFirstNameFollower);
        etShortDescriptionFollower = (TextView) findViewById(R.id.etShortDescriptionFollower);
        etLongDescriptionFollower = (TextView) findViewById(R.id.etLongDescriptionFollower);
        tvNumeroTel = (TextView) findViewById(R.id.tvNumeroTel);


        tvUserNameFollower.setText(username);
        etLastNameFollower.setText(nom);
        etFirstNameFollower.setText(prenom);
        etShortDescriptionFollower.setText(smalldescription);
        etLongDescriptionFollower.setText(longDescription);
        tvNumeroTel.setText(numeroTel);
    }
}
