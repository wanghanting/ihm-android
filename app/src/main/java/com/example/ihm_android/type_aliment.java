package com.example.ihm_android;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class type_aliment extends AppCompatActivity {

    Data data;
    Button bAddFoodType;
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_aliment);

        data = (Data) getApplication();
        bAddFoodType = (Button) findViewById(R.id.bAddFoodType);

        CustomGrid adapter = new CustomGrid(type_aliment.this, data.type_aliment_name, data.type_aliment_picture);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(type_aliment.this, "You Clicked at " +data.type_aliment_name.get(+ position), Toast.LENGTH_SHORT).show();

            }
        });

        bAddFoodType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = (LayoutInflater.from(type_aliment.this)).inflate(R.layout.activity_user_input_food_type, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(type_aliment.this);
                alertBuilder.setView(view);
                final EditText edUserInputFoodType = (EditText) view.findViewById(R.id.edUserInputFoodType);

                alertBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!edUserInputFoodType.getText().toString().isEmpty()){
                            data.type_aliment_name.add(edUserInputFoodType.getText().toString());
                            data.type_aliment_picture.add(R.drawable.login);
                        }
                        startActivity(new Intent(type_aliment.this, type_aliment.class));
                    }
                });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });
    }
}
