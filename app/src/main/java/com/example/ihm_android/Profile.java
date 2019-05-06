package com.example.ihm_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    Button bHome;
    EditText etLastName, etFirstName, etShortDescription, etLongDescription;
    User user;
    String username;
    String password;
    Data data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        data = (Data) getApplication();

        bHome = (Button) findViewById(R.id.bHome);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etShortDescription = (EditText) findViewById(R.id.etShortDescription);
        etLongDescription = (EditText) findViewById(R.id.etLongDescription);

        bHome.setOnClickListener(this);

        etFirstName.setText(data.user.getUsername());
        etShortDescription.setText(data.user.getPassword());
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bHome:
                data.user.setLastName(etLastName.getText().toString());
                data.user.setFirstName(etFirstName.getText().toString());
                data.user.setSmallDescription(etShortDescription.getText().toString());
                data.user.setLongDescription(etLongDescription.getText().toString());
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
