package com.example.ihm_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context mContext;
    private TextView mTextMessage, tvConnexionLink;
    private Button ajouterButton;
    private ListView listView;




    public MainActivity() throws ParseException {
    }
    private class MySimpleAdapter extends SimpleAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            Button supprimerButton=(Button) v.findViewById(R.id.supprimer);
            supprimerButton.setTag(position);
            supprimerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Data data= (Data)getApplication();
                    data.getFood_list().remove((int)v.getTag());
                    notifyDataSetChanged();
                }
            });
            TextView info = (TextView)v.findViewById(R.id.aliInfo);
            info.setTag(position);
            info.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Data data = (Data)getApplication();
                    data.setFlagnum(Integer.parseInt(v.getTag().toString()));
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ChangerAliment.class);
                    startActivity(intent);
                }
            });
            final EditText num = (EditText) v.findViewById(R.id.numCurrent);
            num.setTag(position);
            num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    num.setFocusable(true);
                    num.setFocusableInTouchMode(true);
                    num.requestFocus();
                    num.findFocus();
                    int i = (int) v.getTag();
                    num.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        public void afterTextChanged(Editable edit) {
                            Data data = (Data) getApplication();
                            data.getAliment_list().get((int) v.getTag()).setQuantite(Integer.parseInt(num.getText().toString()));
                            data.initialFoodList();
                        }
                    });

                }
            });
            return v;
        }

        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.page_d_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTextMessage = (TextView) findViewById(R.id.message);
        Data data= (Data)getApplication();
        listView = (ListView)findViewById(R.id.list_food) ;

        MySimpleAdapter adapter = new MySimpleAdapter(this,data.getFood_list(),R.layout.list_item,new String[] {"image", "aliment","num","unite","supprimer"}, new int[] {R.id.imageView1,R.id.aliInfo,R.id.numCurrent,R.id.unite,R.id.supprimer});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"you click" + position + "st item",Toast.LENGTH_SHORT).show();
            }
        });

        ajouterButton=(Button) findViewById(R.id.ajoutermain);
        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AjouterAliment.class);
                startActivity(intent);

            }
        });

        tvConnexionLink = (TextView) findViewById(R.id.tvConnexionLink);
        tvConnexionLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_profil:
                startActivity(new Intent(this,Profile.class));
                break;
            case R.id.nav_type:
                break;
            case R.id.nav_follower:
                break;
            case R.id.nav_consommation:
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
