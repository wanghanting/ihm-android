package com.example.ihm_android;

import android.Manifest;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);
        sendButton = (Button) findViewById(R.id.send_button);
        telNumber = (EditText) findViewById(R.id.tel_number);
        smsContenu = (EditText) findViewById(R.id.sms_text);
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String phone_number = telNumber.getText().toString().trim();
                String sms_content = smsContenu.getText().toString().trim();
                if (phone_number.equals("")) {
                    Toast.makeText(SendMessenger.this, "input number", Toast.LENGTH_LONG).show();
                } else {
                    SmsManager smsManager = SmsManager.getDefault();
                    if (sms_content.length() > 70) {
                        List<String> contents = smsManager.divideMessage(sms_content);
                        for (String sms : contents) {
                            smsManager.sendTextMessage(phone_number, null, sms, null, null);
                        }
                    } else {
                        smsManager.sendTextMessage(phone_number, null, sms_content, null, null);
                    }
                    Toast.makeText(SendMessenger.this, "success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(SendMessenger.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
