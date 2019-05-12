package com.example.ihm_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Consommation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Data data;
    private ListView listViewSum;
    private ArrayAdapter adapter;
    private Spinner listType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_conso);
        this.data= (Data)getApplication();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        listViewSum = (ListView)findViewById(R.id.sum_food) ;
        final ArrayList<String> array = data.getSum_food();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
//为listView的容器添加适配器
        listViewSum.setAdapter(adapter);
        //设置点击事件mlv
        listViewSum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Consommation.this, "你点击的是" + position, Toast.LENGTH_SHORT).show();
            }
        });
//实现长按监听
        listViewSum.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            /*
             * 点击事件的参数
             * 1、parent指定的是适配器AdqpterView绑定的视图容器,也就是Listview;
             * 2、View:Item的适配器对象的view
             * 3、position:Item在数据数组的对应下标所以
             * id:Item所在的行号
             * */
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                array.remove(position);
                //监听数据源的改变
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        listType = (Spinner)findViewById(R.id.spinner);
        final ArrayList<String> arrayType = data.getAllType();
        ArrayAdapter<String> adapter_type=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayType);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listType.setAdapter(adapter_type);
        listType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Data data = (Data)getApplication();
                String type = listType.getSelectedItem().toString();
                data.setType(type);
                data.initalFoodListByType();
                data.getSum_food();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

}
