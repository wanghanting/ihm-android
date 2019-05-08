package com.example.ihm_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class type_aliment extends AppCompatActivity {

    Data data;

    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_aliment);

        data = (Data) getApplication();

        CustomGrid adapter = new CustomGrid(type_aliment.this, data.type_aliment_name, data.type_aliment_picture);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(type_aliment.this, "You Clicked at " +data.type_aliment_name.get(+ position), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
