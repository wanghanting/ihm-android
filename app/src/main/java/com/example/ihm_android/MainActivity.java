package com.example.ihm_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private TextView mTextMessage;
    private Button ajouterButton;
    private Button supprimerButton;
    private ListView listView;
    Data data= new Data();

    public MainActivity() throws ParseException {
    }
    private class MySimpleAdapter extends SimpleAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            Button btn=(Button) v.findViewById(R.id.supprimer);
            btn.setTag(position);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    data.food_list.remove((int)v.getTag());
                    notifyDataSetChanged();
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
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);

        listView = (ListView)findViewById(R.id.list_food) ;

        MySimpleAdapter adapter = new MySimpleAdapter(this,this.data.food_list,R.layout.list_item,new String[] {"image", "aliment","suprimer"}, new int[] {R.id.imageView1,R.id.textView1,R.id.supprimer});


        listView.setAdapter(adapter);
        int numId;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            //list点击事件
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"你点击了第" + position + "项",Toast.LENGTH_SHORT).show();
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

    }



}
