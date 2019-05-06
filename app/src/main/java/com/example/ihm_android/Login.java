package com.example.ihm_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.sql.DriverManager.println;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button bLogin;
    EditText etUsername, etPassword;
    TextView tvRegisterLink, tvLoginError;
    User user;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        data = (Data) getApplication();
        user = data.user;

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        tvLoginError = (TextView) findViewById(R.id.tvLoginError);


        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bLogin:
                if (etUsername.getText().toString().length() == 0){
                    etUsername.setError("Entrer un nom d'utilisateur");
                }
                if (etPassword.getText().toString().length() == 0){
                    etPassword.setError("Entrer un mot de passe");
                }
                else if (user == null){
                    tvLoginError.requestFocus();
                    tvLoginError.setText("Si vous n'avez pas de compte, veuillez vous en créer un en cliquant sur \"S'enregistrer\"");
                }
                else if (!user.getUsername().toString().equals(etUsername.getText().toString()) || !user.getPassword().toString().equals(etPassword.getText().toString())){
                    tvLoginError.requestFocus();
                    tvLoginError.setText("Mauvaise combinaison de nom d'utilisateur et de mot de passe. Veuillez ré-essayer !");
                }
                else startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tvRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}
