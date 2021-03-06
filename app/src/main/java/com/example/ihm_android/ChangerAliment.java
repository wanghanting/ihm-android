package com.example.ihm_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChangerAliment extends AppCompatActivity {
    private Button ajouterButton;
    private CalendarView expiration;
    private EditText nomAliment;
    private EditText quantite;
    private Spinner spinnerUnite;
    private Spinner spinnerType;
    private TextView dateExpiration;
    private String date;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouteraliments);

        Data data = (Data)getApplication();
        nomAliment=(EditText) findViewById(R.id.txt_nomAliment);
        quantite=(EditText) findViewById(R.id.quantite);
        spinnerUnite = (Spinner) findViewById(R.id.spinner1);
        spinnerType = (Spinner) findViewById(R.id.ali_type);
        expiration = (CalendarView) findViewById(R.id.ca_expiration);
        dateExpiration = (TextView) findViewById(R.id.dateExpiration);
        ajouterButton=(Button) findViewById(R.id.ajouter);

        Aliment foodForChange = data.getAliment_list().get(data.flagnum);
        nomAliment.setText(foodForChange.getNom());
        quantite.setText(String.valueOf(foodForChange.getQuantite()));
        try {
            dateExpiration.setText(Data.df.parse(foodForChange.getExpirationDate().toString()).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String[] arr={"g","kg","ml","L"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnite.setAdapter(adapter);
        spinnerUnite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ChangerAliment.this, "点击了" + arr[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayList<String> array = data.getTypes();
        ArrayAdapter<String> adapter_type=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,array);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter_type);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ChangerAliment.this, "点击了" + array.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateExpiration.setText(String.valueOf(year)+"-"+ String.valueOf(month)+"-"+ String.valueOf(day));
        date = getResources().getString(R.string.date_de_expiration);



        expiration.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast toast= Toast.makeText(ChangerAliment.this,"you change date"+year + "-" + (month+1) + "-" + dayOfMonth , Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                String newdate= String.format(date,year,(month+1) ,dayOfMonth);
                dateExpiration.setText(newdate);
            }
        });




        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data data= (Data)getApplication();
                String nom = nomAliment.getText().toString();
                Date dateExpi = null;
                try {
                    dateExpi = Data.df.parse(dateExpiration.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (nom.length() == 0){
                    nomAliment.setError("Entrer un nom d'aliment");
                }
                if (quantite.getText().toString().length() == 0){
                    quantite.setError("Entrer une quantité");
                }
                else{
                    int number = Integer.parseInt(quantite.getText().toString());
                    String uni = spinnerUnite.getSelectedItem().toString();
                    String type = spinnerType.getSelectedItem().toString();
                    Aliment newAli = new Aliment(nom,dateExpi,number,uni,R.drawable.add,type);
                    data.getAliment_list().remove(data.flagnum);
                    data.getAliment_list().add(data.flagnum, newAli);
                    data.initialFoodList();
                    Intent intent = new Intent();
                    intent.setClass(ChangerAliment.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
