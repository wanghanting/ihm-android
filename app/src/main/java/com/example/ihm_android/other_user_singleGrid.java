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
import android.widget.AdapterView;
import android.widget.GridView;

public class other_user_singleGrid extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Data data;
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_type_aliment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        data = (Data) getApplication();

        Other_user_customGrid adapter = new Other_user_customGrid(other_user_singleGrid.this, data.other_user_username, data.other_user_picture);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                User user = findUser(data.other_user_username.get(+ position));
                Intent intent = new Intent(other_user_singleGrid.this, other_user.class);
                intent.putExtra("username", user.getUsername());
                intent.putExtra("nom", user.getLastName());
                intent.putExtra("prenom", user.getFirstName());
                intent.putExtra("smalldescription", user.getSmallDescription());
                intent.putExtra("longDescription", user.getLongDescription());
                intent.putExtra("numeroTel", user.getNumeroTel());
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

    public User findUser(String username){
        User user = null;
        for (int i=0; i<data.users_list.size(); i++){
            if (data.users_list.get(i).getUsername().equals(username)){
                user = data.users_list.get(i);
            }
        }
        return user;
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
            case R.id.nav_follower:
                startActivity(new Intent(this,other_user_singleGrid.class));
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
