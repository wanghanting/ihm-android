package com.example.ihm_android;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class other_user extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView tvUserNameFollower;
    TextView etLastNameFollower;
    TextView etFirstNameFollower;
    TextView etShortDescriptionFollower;
    TextView etLongDescriptionFollower;
    TextView tvNumeroTel;
    Button bSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_other_user_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String nom = intent.getStringExtra("nom");
        String prenom = intent.getStringExtra("prenom");
        String smalldescription = intent.getStringExtra("smalldescription");
        String longDescription = intent.getStringExtra("longDescription");
        final String numeroTel = intent.getStringExtra("numeroTel");

        tvUserNameFollower = (TextView) findViewById(R.id.tvUserNameFollower);
        etLastNameFollower = (TextView) findViewById(R.id.etLastNameFollower);
        etFirstNameFollower = (TextView) findViewById(R.id.etFirstNameFollower);
        etShortDescriptionFollower = (TextView) findViewById(R.id.etShortDescriptionFollower);
        etLongDescriptionFollower = (TextView) findViewById(R.id.etLongDescriptionFollower);
        tvNumeroTel = (TextView) findViewById(R.id.tvNumeroTel);
        bSendMessage = (Button) findViewById(R.id.bSendMessage);


        tvUserNameFollower.setText(username);
        etLastNameFollower.setText(nom);
        etFirstNameFollower.setText(prenom);
        etShortDescriptionFollower.setText(smalldescription);
        etLongDescriptionFollower.setText(longDescription);
        tvNumeroTel.setText(numeroTel);

        bSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("numeroTel", numeroTel);
                intent.setClass(other_user.this, SendMessenger.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_profil:
                startActivity(new Intent(this,Profile.class));
                break;
            case R.id.nav_page:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.nav_consommation:
                startActivity(new Intent(this,Consommation.class));
                break;
            case  R.id.nav_send:
                startActivity(new Intent(this,SendMessenger.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
