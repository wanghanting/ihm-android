package com.example.ihm_android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
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

public class AjouterAliment extends AppCompatActivity {
    //private View view;
    private Button ajouterButton;
    private CalendarView expiration;
    private EditText nomAliment;
    private EditText quantite;
    private Spinner spinnerUnite;
    private Spinner spinnerType;
    private TextView dateExpiration;
    private String date;
    private String type;
    private Data data;
//    public static final String CHANNEL_ID = "channelFred";
    public static final int NOTIFICATION_SUCCESS_ID = 88888;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouteraliments);
        this.data= (Data)getApplication();
//        view =this.getLayoutInflater().inflate(R.layout.ajouteraliments,null);
//        setContentView(view);

        nomAliment=(EditText) findViewById(R.id.txt_nomAliment);
        quantite=(EditText) findViewById(R.id.quantite);
        spinnerUnite = (Spinner) findViewById(R.id.spinner1);
        spinnerType = (Spinner) findViewById(R.id.ali_type);
        expiration = (CalendarView) findViewById(R.id.ca_expiration);
        dateExpiration = (TextView) findViewById(R.id.dateExpiration);
        ajouterButton=(Button) findViewById(R.id.ajouter);

        final NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        String aliment = nomAliment.getText().toString();
        mBuilder.setContentTitle("Ajouté")
                .setContentText("Ajouté avec succès!")
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                .setTicker("Here comes the news!")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp);


        final String[] arr={"g","kg","ml","L"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnite.setAdapter(adapter);
        spinnerUnite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(AjouterAliment.this, "点击了" + arr[position], Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AjouterAliment.this, "Clique sur " + array.get(position), Toast.LENGTH_SHORT).show();
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
                //Toast toast= Toast.makeText(AjouterAliment.this,year + "年" + (month+1) + "月" + dayOfMonth + "日", Toast.LENGTH_LONG);
                //toast.setGravity(Gravity.CENTER, 0, 0);
                //toast.show();
                String newdate= String.format(date,year,(month+1) ,dayOfMonth);
                dateExpiration.setText(newdate);
            }
        });



        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    data.addFood(newAli);
                    data.setType("Tout");
                    data.initalFoodListByType();
                    Intent intent = new Intent();
                    intent.setClass(AjouterAliment.this,MainActivity.class);
                    startActivity(intent);
                    mNotificationManager.notify(2, mBuilder.build());
                }

            }
        });
    }

    public PendingIntent getDefalutIntent(int flags){
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }
}

