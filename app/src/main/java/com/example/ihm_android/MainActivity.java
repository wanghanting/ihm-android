package com.example.ihm_android;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button ajouterButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        ajouterButton=(Button) findViewById(R.id.ajoutermain);
        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AjouterAliment.class);
                startActivity(intent);

            }
        });
//        listView = (ListView)findViewById(R.id.list_food) ;
//        ArrayList<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
//        for (int i = 0; i < 5; i++) {
//            Map<String, Object> map = new HashMap<String,Object>();
//            map.put("image", R.drawable.add);
//            map.put("username", "wangxiangjun_" + i);
//            list.add(map);
//        }
//        SimpleAdapter adapter = new SimpleAdapter(this,list,R.id.item,new String[] {"image", "username"}, new int[] {R.id.imageView1,R.id.textView1});
//        listView.setAdapter(adapter);
    }

}
