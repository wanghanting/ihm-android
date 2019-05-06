package com.example.ihm_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity implements View.OnClickListener{

    Button bRegister;
    EditText etUsername, etPassword, etConfirmationPassword;
    TextView tvLoginLink;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        data = (Data) getApplication();

        bRegister = (Button) findViewById(R.id.bRegister);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmationPassword = (EditText) findViewById(R.id.etConfirmationPassword);
        tvLoginLink = (TextView) findViewById(R.id.tvLoginLink);

        bRegister.setOnClickListener(this);
        tvLoginLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bRegister:
                if (etUsername.getText().toString().length() == 0){
                    etUsername.setError("Entrer un nom d'utilisateur");
                }
                if (etPassword.getText().toString().length() == 0){
                    etPassword.setError("Entrer un mot de passe");
                }
                if (etConfirmationPassword.getText().toString().length() == 0){
                    etConfirmationPassword.setError("Entrer un mot de passe");
                }
                else if (!etConfirmationPassword.getText().toString().equals(etPassword.getText().toString())){
                    etConfirmationPassword.setError("Mot de passe incorrect");
                }
                else{
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    User user = new User(username, password);
                    data.user = user;
                    startActivity(new Intent(this, Profile.class));
                }
                break;
            case R.id.tvLoginLink:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }
}
