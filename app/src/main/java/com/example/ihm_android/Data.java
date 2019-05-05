package com.example.ihm_android;

import android.app.Application;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Data extends Application {
    ArrayList<Aliment> aliment_list = new ArrayList<>();
    ArrayList<Map<String, Object>> food_list = new ArrayList<Map<String,Object>>();
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void onCreate() {

        try {
            initialAlimentList();
            initialFoodList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        super.onCreate();
    }


    void initialAlimentList () throws ParseException {

        Date date1 = df.parse("2019-05-20");
        this.aliment_list.add(new Aliment("apple",date1,3,"un"));
        this.aliment_list.add(new Aliment("pear",date1,5,"un"));
        this.aliment_list.add(new Aliment("orange",date1,8,"un"));
    }

    void initialFoodList (){
        this.getFood_list().clear();
        for (int i = 0; i < this.aliment_list.size(); i++) {
            Aliment food = this.aliment_list.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", R.drawable.add);
            map.put("aliment", food.getNom()+"  "+df.format(food.getExpirationDate()));
            map.put("num",food.getQuantite());
            map.put("unite",food.getUnite());
            map.put("supprimer", R.drawable.cross);
            this.food_list.add(map);
        }
    }
    ArrayList<Map<String,Object>> getFood_list(){ return this.food_list;}
    ArrayList<Aliment> getAliment_list(){ return this.aliment_list;}


    void addFood(Aliment aliment){
        this.aliment_list.add(aliment);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("image", R.drawable.add);
        map.put("aliment", aliment.getNom()+"  "+df.format(aliment.getExpirationDate()) );
        map.put("num",aliment.getQuantite());
        map.put("unite",aliment.getUnite());
        map.put("suprimer", R.drawable.cross);
        this.food_list.add(map);


    }
}
