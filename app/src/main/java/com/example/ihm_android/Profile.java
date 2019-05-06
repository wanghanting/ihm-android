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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bHome = (Button) findViewById(R.id.bHome);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etShortDescription = (EditText) findViewById(R.id.etShortDescription);
        etLongDescription = (EditText) findViewById(R.id.etLongDescription);

        bHome.setOnClickListener(this);


//        bHome.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View source)
//            {
//                //获取启动前Activity的上一个Intent
//                Intent intent = new Intent(Profile.this,MainActivity.class);
//                //启动intent对应的Activity
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bHome:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
