package com.example.ihm_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AjouterAliment extends AppCompatActivity {
    //private View view;
    private EditText nomAliment;
    private TextView dateExpiration;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouteraliments);
//        view =this.getLayoutInflater().inflate(R.layout.ajouteraliments,null);
//        setContentView(view);
        Button ajouterButton;
        CalendarView expiration;
        nomAliment=(EditText) findViewById(R.id.txt_nomAliment);
        ajouterButton=(Button) findViewById(R.id.ajouter);
        expiration = (CalendarView) findViewById(R.id.ca_expiration);
        dateExpiration = (TextView) findViewById(R.id.dateExpiration);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateExpiration.setText(String.valueOf(year)+"-"+ String.valueOf(year)+"-"+ String.valueOf(day));
        date = getResources().getString(R.string.date_de_expiration);



        expiration.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast toast= Toast.makeText(AjouterAliment.this,year + "年" + (month+1) + "月" + dayOfMonth + "日", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                String newdate= String.format(date,year,(month+1) ,dayOfMonth);
                dateExpiration.setText(newdate);
            }
        });




        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = nomAliment.getText().toString();
                String dateExpi = dateExpiration.getText().toString();
                Aliment newAli = new Aliment(nom,dateExpi);
                Intent intent = new Intent();
                intent.setClass(AjouterAliment.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }
}

