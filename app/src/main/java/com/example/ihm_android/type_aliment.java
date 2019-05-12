package com.example.ihm_android;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class type_aliment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Data data;
    Button bAddFoodType;
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_type_aliment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

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
            case R.id.nav_type:
                startActivity(new Intent(this,type_aliment.class));
                break;
            case R.id.nav_page:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.nav_consommation:
                startActivity(new Intent(this,Consommation.class));
                break;
            case R.id.nav_share:
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
