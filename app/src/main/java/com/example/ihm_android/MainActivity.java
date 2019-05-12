package com.example.ihm_android;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context mContext;
    private TextView mTextMessage, tvDeconnexionLink, tvListeAlimentLink;
    private Button ajouterButton;
    private ListView listViewAliment;
    private Spinner listType;
    private String path= "Environment.getExternalStorageDirectory()";

    private class MySimpleAdapter extends SimpleAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

//            final NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
//
//            mBuilder.setContentTitle("Suppression")
//                    .setContentText("Suppression avec succès")
//                    .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
//                    .setTicker("Here comes the news!")
//                    .setWhen(System.currentTimeMillis())
//                    .setPriority(Notification.PRIORITY_MAX)
//                    .setOngoing(false)
//                    .setDefaults(Notification.DEFAULT_VIBRATE)
//                    .setSmallIcon(R.drawable.ic_notifications_black_24dp);

            Button supprimerButton=(Button) v.findViewById(R.id.supprimer);
            supprimerButton.setTag(position);
            supprimerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Data data= (Data)getApplication();
                    data.getFood_list().remove((int)v.getTag());
                    notifyDataSetChanged();
//                    mNotificationManager.notify(2, mBuilder.build());
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
                    //int i = (int) v.getTag();
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
        setContentView(R.layout.page_d_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTextMessage = (TextView) findViewById(R.id.message);
        Data data= (Data)getApplication();
        listViewAliment = (ListView)findViewById(R.id.list_food) ;

        final NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle("Création")
                .setContentText("Rempli le tableau dessous")
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                .setTicker("Here comes the news!")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp);

        final MySimpleAdapter adapter = new MySimpleAdapter(this,data.getFood_list_by_type(),R.layout.list_item2,new String[] {"image", "aliment","num","unite","supprimer"}, new int[] {R.id.imaAliment,R.id.aliInfo,R.id.numCurrent,R.id.unite,R.id.supprimer});
        listViewAliment.setAdapter(adapter);
        listViewAliment.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"you click" + position + "st item",Toast.LENGTH_SHORT).show();
            }
        });

        listType = (Spinner)findViewById(R.id.spinner);
        final ArrayList<String> array = data.getAllType();
        ArrayAdapter<String> adapter_type=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,array);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listType.setAdapter(adapter_type);
        listType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Data data = (Data)getApplication();
                String type = listType.getSelectedItem().toString();
                data.setType(type);
                data.initalFoodListByType();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "点击了" + array.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ajouterButton=(Button) findViewById(R.id.ajoutermain);
        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AjouterAliment.class);
                startActivity(intent);
                mNotificationManager.notify(1, mBuilder.build());
            }
        });

        tvDeconnexionLink = (TextView) findViewById(R.id.tvDeconnexionLink);
        tvDeconnexionLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        tvListeAlimentLink = (TextView) findViewById(R.id.tvListeAlimentLink);
        tvListeAlimentLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, type_aliment.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Action comming soon!", Snackbar.LENGTH_LONG)
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

    public PendingIntent getDefalutIntent(int flags){
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }


    @SuppressLint("path")
    public static String saveMyBitmap(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/" + bitName + ".png");

        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("在保存图片时出错：" + e.toString());
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        } catch (Exception e) {
            return "create_bitmap_error";
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "/sdcard/" + bitName + ".png";
    }

    public static String saveBitmapToSDCard(Bitmap bitmap, String imagename) {
        String path = "/sdcard/" + "img-" + imagename + ".jpg";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.close();
            }

            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
