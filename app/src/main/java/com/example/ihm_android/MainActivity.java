package com.example.ihm_android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context mContext;
    private TextView mTextMessage, tvDeconnexionLink, tvListeAlimentLink;
    private Button ajouterButton;
    private Button bAddFoodType;
    private Button addCalender;
    private ListView listViewAliment;
    private Spinner listType;
    private Spinner listStatus;
    private String path= "Environment.getExternalStorageDirectory()";
    private static String calanderURL = "content://com.android.calendar/calendars";
    private static String calanderEventURL = "content://com.android.calendar/events";
    private static String calanderRemiderURL = "content://com.android.calendar/reminders";
//    Data data= (Data)getApplication();

    private class MySimpleAdapter extends SimpleAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            int color=Color.rgb(242,239,230);
            v.setBackgroundColor(color);

            Button supprimerButton=(Button) v.findViewById(R.id.supprimer);
            supprimerButton.setTag(position);
            supprimerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder altdial = new AlertDialog.Builder(MainActivity.this);
                    altdial.setMessage("Voulez-vous vraiment supprimer cet aliment ?").setCancelable(false)
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Data data= (Data)getApplication();
//                                  data.getFood_list().remove((int)v.getTag());
                                    data.deleteFood((int)v.getTag());
                                    data.initalFoodListByType();
                                    notifyDataSetChanged();
//                                  mNotificationManager.notify(2, mBuilder.build());
                                }
                            })
                            .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert = altdial.create();
                    alert.show();
                }
            });
            TextView info = (TextView)v.findViewById(R.id.aliInfo);
            info.setTag(position);
            info.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Data data= (Data)getApplication();
                    data.setFlagnum(Integer.parseInt(v.getTag().toString()));
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ChangerAliment.class);
                    startActivity(intent);
                }
            });
            int colorWhite=Color.rgb(73,90,128);
            info.setTextColor(colorWhite);

            TextView unite = (TextView)v.findViewById(R.id.unite);
            unite.setTag(position);
            unite.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Data data= (Data)getApplication();
                    data.setFlagnum(Integer.parseInt(v.getTag().toString()));
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ChangerAliment.class);
                    startActivity(intent);
                }
            });
            unite.setTextColor(colorWhite);
            unite.setTextSize(15);

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
                            Data data= (Data)getApplication();
                            data.getAliment_list().get((int) v.getTag()).setQuantite(Integer.parseInt(num.getText().toString()));
                            data.initialFoodList();
                        }
                    });

                }
            });
            num.setTextColor(colorWhite);
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
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"you click" + position + "st item",Toast.LENGTH_SHORT).show();
            }
        });

        listType = (Spinner)findViewById(R.id.spinner);
        final ArrayAdapter<String> adapter_type=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data.getAllType());
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listType.setAdapter(adapter_type);
        listType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Data data= (Data)getApplication();
                String type = listType.getSelectedItem().toString();
                data.setType(type);
                data.initalFoodListByType();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "you click" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listStatus = (Spinner)findViewById(R.id.spinner2);
        final String[] arr={"Tout","OK","périmé","presque périmé"};
        final ArrayAdapter<String> adapter_status=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arr);
        adapter_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listStatus.setAdapter(adapter_status);
        listStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Data data= (Data)getApplication();
                String status = listStatus.getSelectedItem().toString();
                data.setStatus(status);
                data.initalFoodListByType();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "you click" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bAddFoodType = (Button) findViewById(R.id.ajouter_type);
        bAddFoodType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = (LayoutInflater.from(MainActivity.this)).inflate(R.layout.activity_user_input_food_type, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setView(view);
                final EditText edUserInputFoodType = (EditText) view.findViewById(R.id.edUserInputFoodType);

                alertBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!edUserInputFoodType.getText().toString().isEmpty()){
                            Data data= (Data)getApplication();
                            data.addType(new Type(edUserInputFoodType.getText().toString()));
                        }
                        adapter_type.notifyDataSetChanged();
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                    }
                });
                Dialog dialog = alertBuilder.create();
                dialog.show();
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

        addCalender = (Button) findViewById(R.id.add_calender);
        addCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCalendars();
                Data data= (Data)getApplication();
                checkPermission();
                String calId = "";
                Cursor userCursor = getContentResolver().query(Uri.parse(calanderURL), null, null, null, null);
                if (userCursor.getCount() > 0) {
                    userCursor.moveToLast();  //注意：是向最后一个账户添加，开发者可以根据需要改变添加事件 的账户
                    calId = userCursor.getString(userCursor.getColumnIndex("_id"));
                } else {
                    Toast.makeText(MainActivity.this, "没有账户，请先添加账户", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Aliment> aliments = data.getAliment_list();
                for(Aliment ali :aliments) {
                    ContentValues event = new ContentValues();
                    event.put("title", ali.getNom() + " sera périmé");
                    event.put("description", ali.getNom() + " sera périmé");
                    // 插入账户
                    event.put("calendar_id", calId);
                    System.out.println("calId: " + calId);
                    event.put("eventLocation", "france");

                    java.util.Calendar mCalendar = java.util.Calendar.getInstance();
                    long start = ali.getExpirationDate().getTime();
                    mCalendar.set(Calendar.HOUR_OF_DAY, 12);
                    long end = ali.getExpirationDate().getTime();

                    event.put("dtstart", start);
                    event.put("dtend", end);
                    event.put("hasAlarm", 1);

                    event.put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/France");  //这个是时区，必须有，
                    //添加事件
                    Uri newEvent = getContentResolver().insert(Uri.parse(calanderEventURL), event);
                    //事件提醒的设定
                    long id = Long.parseLong(newEvent.getLastPathSegment());
                    ContentValues values = new ContentValues();
                    values.put("event_id", id);
                    // 提前10分钟有提醒
                    values.put("minutes", 10);
                    getContentResolver().insert(Uri.parse(calanderRemiderURL), values);
                }
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, Test.class);
//                startActivity(intent);

                Toast.makeText(MainActivity.this, "插入事件成功!!!", Toast.LENGTH_LONG).show();
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
            System.out.println("Error: " + e.toString());
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

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR}; // 选择你需要申请的权限
            for (int i = 0; i < permissions.length; i++) {
                int state = ContextCompat.checkSelfPermission(this, permissions[i]);
                if (state != PackageManager.PERMISSION_GRANTED) { // 判断权限的状态
                    ActivityCompat.requestPermissions(this, permissions, 200); // 申请权限
                    return;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requestCode == 200) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) { // 用户点的拒绝，仍未拥有权限
                    Toast.makeText(this, "请在设置中打开摄像头或存储权限", Toast.LENGTH_SHORT).show();
                    // 可以选择添加如下代码在系统设置中打开该应用的设置页面
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    return;
                }
            }
        }
    }

    private void initCalendars() {

        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, "yy");

        value.put(CalendarContract.Calendars.ACCOUNT_NAME, "mygmailaddress@gmail.com");
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, "com.android.exchange");
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "mytt");
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, -9206951);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, "mygmailaddress@gmail.com");
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = CalendarContract.Calendars.CONTENT_URI;
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "chenss961214@gmail.com")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, "com.android.exchange")
                .build();

        getContentResolver().insert(calendarUri, value);
    }

}
