package com.example.ihm_android;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SendMessenger extends AppCompatActivity {
    private Button sendButton;
    private EditText telNumber;
    private EditText smsContenu;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);
        sendButton = (Button)findViewById(R.id.send_button);
        telNumber = (EditText)findViewById(R.id.tel_number);
        smsContenu = (EditText)findViewById(R.id.sms_text);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(SendMessenger.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SendMessenger.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                } else if(ContextCompat.checkSelfPermission(SendMessenger.this , Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(SendMessenger.this,new String[]{Manifest.permission.READ_PHONE_STATE},2);
                }
                else {
                    sendMsg();

                }

            }
        });
    }
    private void sendMsg() {

        String number = telNumber.getText().toString();

        String content = smsContenu.getText().toString();
        try{
            if (TextUtils.isEmpty(number)) {
                Toast.makeText(SendMessenger.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(SendMessenger.this, "请输入sms contenu", Toast.LENGTH_SHORT).show();
                return;
            }
            ArrayList<String> messages = SmsManager.getDefault().divideMessage(content);
            for (String text : messages) {
                SmsManager.getDefault().sendTextMessage(number, null, text, null, null);
            }
            Toast.makeText(SendMessenger.this, "success", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch(requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    sendMsg();
                } else {
                    Toast.makeText(this,"您没有此权限！",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    sendMsg();
                } else {
                    Toast.makeText(this,"您没有此权限！",Toast.LENGTH_SHORT).show();
                }
            default:
        }
    }


}
