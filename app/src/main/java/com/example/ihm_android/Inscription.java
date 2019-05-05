package com.example.ihm_android;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.ParseException;

public class Inscription extends AppCompatActivity {
    private TextView Txt_password_con;
    private TextView Txt_password;
    private TextView Txt_username;

    public Inscription() throws ParseException {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);
//        view =this.getLayoutInflater().inflate(R.layout.ajouteraliments,null);
//        setContentView(view);

        Txt_username = (TextView) findViewById(R.id.Txt_username);
        Txt_password = (TextView) findViewById(R.id.Txt_password);
        Txt_password_con= (TextView) findViewById(R.id.Txt_password_confirmed);
    }

}
