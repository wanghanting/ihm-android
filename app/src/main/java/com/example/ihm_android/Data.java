package com.example.ihm_android;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Data {
    ArrayList<Aliment> aliment_list = new ArrayList<>();
    ArrayList<Map<String, Object>> food_list = new ArrayList<Map<String,Object>>();
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public Data() throws ParseException {
        initalAlimentList();
        initialFoodList();
    }

    void initalAlimentList () throws ParseException {

        Date date1 = df.parse("2019-05-20");
        aliment_list.add(new Aliment("apple",date1));
        aliment_list.add(new Aliment("pear",date1));
        aliment_list.add(new Aliment("orange",date1));
    }

    ArrayList<Map<String,Object>> initialFoodList (){

        for (int i = 0; i < this.aliment_list.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", R.drawable.add);
            map.put("aliment", aliment_list.get(i).getNom()+" "+df.format(aliment_list.get(i).getExpirationDate()));
            map.put("suprimer", R.drawable.cross);
            food_list.add(map);
        }
        return this.food_list;
    }


    void addFood(Aliment aliment){
        aliment_list.add(aliment);

    }
}
