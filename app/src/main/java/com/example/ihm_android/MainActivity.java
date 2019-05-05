package com.example.ihm_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
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
                        public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                        @Override
                        public void afterTextChanged(Editable edit) {
                            Data data = (Data) getApplication();
                            data.getAliment_list().get((int)v.getTag()).setQuantite(Integer.parseInt(num.getText().toString()));
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
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        Data data= (Data)getApplication();
        listView = (ListView)findViewById(R.id.list_food) ;

        MySimpleAdapter adapter = new MySimpleAdapter(this,data.getFood_list(),R.layout.list_item,new String[] {"image", "aliment","num","unite","supprimer"}, new int[] {R.id.imageView1,R.id.textView1,R.id.numCurrent,R.id.unite,R.id.supprimer});
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
    }



}
