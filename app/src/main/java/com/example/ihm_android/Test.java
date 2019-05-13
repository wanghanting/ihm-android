package com.example.ihm_android;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class Test extends AppCompatActivity {


        //Android2.2版本以后的URL，之前的就不写了
        private static String calanderURL = "content://com.android.calendar/calendars";
        private static String calanderEventURL = "content://com.android.calendar/events";
        private static String calanderRemiderURL = "content://com.android.calendar/reminders";


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.test);
            checkPermission();
        }

        public void onClick(View v) {
            if (v.getId() == R.id.readUserButton) {  //读取系统日历账户，如果为0的话先添加
                Cursor userCursor = getContentResolver().query(Uri.parse(calanderURL), null, null, null, null);

                System.out.println("Count: " + userCursor.getCount());
                Toast.makeText(this, "Count: " + userCursor.getCount(), Toast.LENGTH_LONG).show();

                for (userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()) {
                    System.out.println("name: " + userCursor.getString(userCursor.getColumnIndex("ACCOUNT_NAME")));


                    String userName1 = userCursor.getString(userCursor.getColumnIndex("name"));
                    String userName0 = userCursor.getString(userCursor.getColumnIndex("ACCOUNT_NAME"));
                    Toast.makeText(this, "NAME: " + userName1 + " -- ACCOUNT_NAME: " + userName0, Toast.LENGTH_LONG).show();
                }
            }
            else if (v.getId() == R.id.inputaccount) {        //添加日历账户
                initCalendars();

            }

            else if (v.getId() == R.id.readEventButton) {  //读取事件
                Cursor eventCursor = getContentResolver().query(Uri.parse(calanderEventURL), null, null, null, null);
                if (eventCursor.getCount() > 0) {
                    eventCursor.moveToLast();             //注意：这里与添加事件时的账户相对应，都是向最后一个账户添加
                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    Toast.makeText(Test.this, eventTitle, Toast.LENGTH_LONG).show();
                }
            }
            else if (v.getId() == R.id.writeEventButton) {
                // 获取要出入的gmail账户的id
                String calId = "";
                Cursor userCursor = getContentResolver().query(Uri.parse(calanderURL), null, null, null, null);
                if (userCursor.getCount() > 0) {
                    userCursor.moveToLast();  //注意：是向最后一个账户添加，开发者可以根据需要改变添加事件 的账户
                    calId = userCursor.getString(userCursor.getColumnIndex("_id"));
                }
                else {
                    Toast.makeText(Test.this, "没有账户，请先添加账户", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues event = new ContentValues();
                event.put("title", "Test");
                event.put("description", "Test");
                // 插入账户
                event.put("calendar_id", calId);
                System.out.println("calId: " + calId);
                event.put("eventLocation", "地球-华夏");

                Calendar mCalendar = Calendar.getInstance();
                mCalendar.set(Calendar.HOUR_OF_DAY, 11);
                mCalendar.set(Calendar.MINUTE, 45);
                long start = mCalendar.getTime().getTime();
                mCalendar.set(Calendar.HOUR_OF_DAY, 12);
                long end = mCalendar.getTime().getTime();

                event.put("dtstart", start);
                event.put("dtend", end);
                event.put("hasAlarm", 1);

                event.put(Events.EVENT_TIMEZONE, "Asia/Shanghai");  //这个是时区，必须有，
                //添加事件
                Uri newEvent = getContentResolver().insert(Uri.parse(calanderEventURL), event);
                //事件提醒的设定
                long id = Long.parseLong(newEvent.getLastPathSegment());
                ContentValues values = new ContentValues();
                values.put("event_id", id);
                // 提前10分钟有提醒
                values.put("minutes", 10);
                getContentResolver().insert(Uri.parse(calanderRemiderURL), values);

                Toast.makeText(Test.this, "插入事件成功!!!", Toast.LENGTH_LONG).show();
            }
        }


        //添加账户
        private void initCalendars() {

            TimeZone timeZone = TimeZone.getDefault();
            ContentValues value = new ContentValues();
            value.put(Calendars.NAME, "yy");

            value.put(Calendars.ACCOUNT_NAME, "mygmailaddress@gmail.com");
            value.put(Calendars.ACCOUNT_TYPE, "com.android.exchange");
            value.put(Calendars.CALENDAR_DISPLAY_NAME, "mytt");
            value.put(Calendars.VISIBLE, 1);
            value.put(Calendars.CALENDAR_COLOR, -9206951);
            value.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_OWNER);
            value.put(Calendars.SYNC_EVENTS, 1);
            value.put(Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
            value.put(Calendars.OWNER_ACCOUNT, "mygmailaddress@gmail.com");
            value.put(Calendars.CAN_ORGANIZER_RESPOND, 0);

            Uri calendarUri = Calendars.CONTENT_URI;
            calendarUri = calendarUri.buildUpon()
                    .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                    .appendQueryParameter(Calendars.ACCOUNT_NAME, "chenss961214@gmail.com")
                    .appendQueryParameter(Calendars.ACCOUNT_TYPE, "com.android.exchange")
                    .build();

            getContentResolver().insert(calendarUri, value);
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

    }

