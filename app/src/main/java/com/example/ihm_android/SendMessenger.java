package com.example.ihm_android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SendMessenger extends AppCompatActivity {
    private Button sendButton;
    private EditText telNumber;
    private EditText smsContenu;
    private List<InformationContact> informationContacts;
    private ListView listContact;
    private String[] permissions;
    List<String> mPermissionList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);
         permissions = new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE
        };
        check();
        initViews();


    }
    private void check() {
        //判断是否有权限
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(SendMessenger.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
            if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
                Toast.makeText(SendMessenger.this,"已经授权",Toast.LENGTH_LONG).show();
            } else {//请求权限方法
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(SendMessenger.this, permissions, 296);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 296) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SendMessenger.this, "已经授权", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SendMessenger.this, "已经拒绝授权", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initViews() {
        PhoneUtil phoneUtil = new PhoneUtil(this);
        informationContacts = phoneUtil.getPhone();
        listContact = (ListView) findViewById(R.id.listContact);
        MyAdapter myAdapter = new MyAdapter();
        listContact.setAdapter(myAdapter);
        //给listview增加点击事件
        listContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                telNumber.setText(informationContacts.get(position).getTelPhone());
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
                            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                            smsIntent.setDataAndType(Uri.parse("smsto:" + phone_number), "vnd.android-dir/mms-sms");

                            smsIntent.putExtra("address", phone_number);
                            smsIntent.putExtra("sms_body", sms_content);
                            try {
                                startActivity(smsIntent);
                                Toast.makeText(SendMessenger.this, "success", Toast.LENGTH_SHORT).show();
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(SendMessenger.this,
                                        "SMS faild, please try again later." + ex, Toast.LENGTH_SHORT).show();
                            }
                        }

                        Intent intent = new Intent();
                        intent.setClass(SendMessenger.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

        private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return informationContacts.size();
        }

        @Override
        public Object getItem(int position) {
            return informationContacts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            InformationContact phoneDto = informationContacts.get(position);
            LinearLayout linearLayout = new LinearLayout(SendMessenger.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            TextView tv_name = new TextView(SendMessenger.this);
            tv_name.setId(View.generateViewId());
            tv_name.setLayoutParams(layoutParams);
            tv_name.setText(phoneDto.getName());
            TextView tv_num = new TextView(SendMessenger.this);
            tv_num.setId(View.generateViewId());
            tv_num.setLayoutParams(layoutParams);
            tv_num.setText(phoneDto.getTelPhone());
            linearLayout.addView(tv_name);
            linearLayout.addView(tv_num);
            return linearLayout;
        }
    }


}
