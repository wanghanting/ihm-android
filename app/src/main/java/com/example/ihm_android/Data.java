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
    User user;
    ArrayList<Aliment> aliment_list = new ArrayList<>();
    ArrayList<Type> type_list = new ArrayList<>();
    ArrayList<Map<String, Object>> food_list = new ArrayList<Map<String,Object>>();
    ArrayList<Map<String, Object>> button_list = new ArrayList<>();
    ArrayList<Map<String, Object>> food_list_by_type = new ArrayList<>();
    ArrayList<String> type_aliment_name = new ArrayList<>();
    ArrayList<Integer> type_aliment_picture = new ArrayList<>();
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    int flagnum;
    String type = "Tout";

    @Override
    public void onCreate() {

        try {
            initialAlimentList();
            initialFoodList();
            initialTypeList();
            initialButtonList();
            initalFoodListByType();
            init_type_aliment_name();
            init_type_aliment_picture();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        super.onCreate();
    }

    void init_type_aliment_name(){
        this.type_aliment_name.add("Fruit");
        this.type_aliment_name.add("Légume");
        this.type_aliment_name.add("Laitage");
        this.type_aliment_name.add("Féculent");
        this.type_aliment_name.add("Viande");
        this.type_aliment_name.add("Boisson");
        this.type_aliment_name.add("Poisson");
    }

    void init_type_aliment_picture(){
        this.type_aliment_picture.add(R.drawable.fruit);
        this.type_aliment_picture.add(R.drawable.legume);
        this.type_aliment_picture.add(R.drawable.laitage);
        this.type_aliment_picture.add(R.drawable.feculent);
        this.type_aliment_picture.add(R.drawable.viande);
        this.type_aliment_picture.add(R.drawable.boisson);
        this.type_aliment_picture.add(R.drawable.poisson);
    }

    void initialAlimentList () throws ParseException {

        Date date1 = df.parse("2019-05-20");
        this.aliment_list.add(new Aliment("apple",date1,3,"kg", R.drawable.apple, "fruit"));
        this.aliment_list.add(new Aliment("pear",date1,5,"kg",R.drawable.pear,"fruit"));
        this.aliment_list.add(new Aliment("orange",date1,8,"kg",R.drawable.orange,"fruit"));
    }

    void initialFoodList (){
        this.getFood_list().clear();
        for (int i = 0; i < this.aliment_list.size(); i++) {
            Aliment food = this.aliment_list.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", food.getImgPath());
            map.put("aliment", food.getNom()+"  \n"+df.format(food.getExpirationDate()));
            map.put("num",food.getQuantite());
            map.put("unite",food.getUnite());
            map.put("supprimer",R.drawable.cross);
            this.food_list.add(map);
        }
    }

    void initialTypeList () throws ParseException {
        this.type_list.add(new Type("Tout"));
        this.type_list.add(new Type("fruit"));
        this.type_list.add(new Type("légume"));
        this.type_list.add(new Type("boisson"));
        this.type_list.add(new Type("+"));
    }


    void initialButtonList (){
        this.getButton_list().clear();
        for (int i = 0; i < this.type_list.size(); i++) {
            Type type = this.type_list.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nom", type.getNom());
            this.button_list.add(map);
        }
    }



    ArrayList<Map<String,Object>> getFood_list(){ return this.food_list;}
    ArrayList<Aliment> getAliment_list(){ return this.aliment_list;}
    ArrayList<Map<String,Object>> getButton_list(){return this.button_list;}
    ArrayList<Map<String,Object>> getFood_list_by_type(){return this.food_list_by_type;}
    ArrayList<Type> getType_list(){return this.type_list;}

    ArrayList<String> getTypes(){
        ArrayList<String> types = new ArrayList<>();
        for(int i = 1;i < this.type_list.size()-1;i++){
            types.add(this.type_list.get(i).getNom());
        }
        return types;
    }

    ArrayList<String> getAllType(){
        ArrayList<String> types = new ArrayList<>();
        for(int i = 0;i < this.type_list.size()-1;i++){
            types.add(this.type_list.get(i).getNom());
        }
        return types;
    }

    void setType (String type){this.type = type;}

    void initalFoodListByType(){
        this.getFood_list_by_type().clear();
        if(this.type.equals("Tout")){
            for (int i = 0; i < this.food_list.size(); i++) {
                this.food_list_by_type.add(food_list.get(i));
            }
        }
        else {
            for (int i = 0; i < this.aliment_list.size(); i++) {
                Aliment food = this.aliment_list.get(i);
                if (food.getType().equals(this.type)) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("image", food.getImgPath());
                    map.put("aliment", food.getNom() + "  \n" + df.format(food.getExpirationDate()));
                    map.put("num", food.getQuantite());
                    map.put("unite", food.getUnite());
                    map.put("supprimer", R.drawable.cross);
                    this.food_list_by_type.add(map);
                }
            }
        }
        System.out.print(food_list_by_type);
    }

    User getUser(){return user;}

    void addFood(Aliment aliment){
        this.aliment_list.add(aliment);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("image", aliment.getImgPath());
        map.put("aliment", aliment.getNom()+"  \n"+df.format(aliment.getExpirationDate()) );
        map.put("num",aliment.getQuantite());
        map.put("unite",aliment.getUnite());
        map.put("supprimer", R.drawable.cross);
        this.food_list.add(map);
    }
    void setFlagnum(int flag){this.flagnum=flag;}
}
