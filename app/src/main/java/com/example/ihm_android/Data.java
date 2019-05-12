package com.example.ihm_android;

import android.app.Application;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Data extends Application {
    User user;
    ArrayList<Aliment> aliment_list = new ArrayList<>();
    ArrayList<Aliment> aliment_list_by_type = new ArrayList<>();
    ArrayList<Type> type_list = new ArrayList<>();
    ArrayList<Map<String, Object>> food_list = new ArrayList<Map<String,Object>>();
    ArrayList<Map<String, Object>> button_list = new ArrayList<>();
    ArrayList<Map<String, Object>> food_list_by_type = new ArrayList<>();
    ArrayList<String> type_aliment_name = new ArrayList<>();
    ArrayList<Integer> type_aliment_picture = new ArrayList<>();
    ArrayList<Sum> sum_aliment = new ArrayList<>();
    ArrayList<String> sum_food = new ArrayList<>();
    ArrayList<Sum> sum_expired_aliment = new ArrayList<>();
    ArrayList<Rate> rate_aliment = new ArrayList<>();
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    int flagnum;
    String type = "Tout";

    @Override
    public void onCreate() {

        try {
            initialAlimentList();
            initialFoodList();
            initialTypeList();
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
    }


    ArrayList<Map<String,Object>> getFood_list(){ return this.food_list;}
    ArrayList<Aliment> getAliment_list(){ return this.aliment_list;}
    ArrayList<Map<String,Object>> getButton_list(){return this.button_list;}
    ArrayList<Map<String,Object>> getFood_list_by_type(){return this.food_list_by_type;}
    ArrayList<Type> getType_list(){return this.type_list;}

    ArrayList<String> getTypes(){
        ArrayList<String> types = new ArrayList<>();
        for(int i = 1;i < this.type_list.size();i++){
            types.add(this.type_list.get(i).getNom());
        }
        return types;
    }

    ArrayList<String> getAllType(){
        ArrayList<String> types = new ArrayList<>();
        for(int i = 0;i < this.type_list.size();i++){
            types.add(this.type_list.get(i).getNom());
        }
        return types;
    }

    void setType (String type){this.type = type;}

    void initalFoodListByType(){
        this.getFood_list_by_type().clear();
        this.aliment_list_by_type.clear();
        if(this.type.equals("Tout")){
            for (int i = 0; i < this.food_list.size(); i++) {
                this.food_list_by_type.add(food_list.get(i));
            }
            for(int i = 0; i < this.aliment_list.size(); i++){
                this.aliment_list_by_type.add(aliment_list.get(i));
            }
        }
        else {
            for (int i = 0; i < this.aliment_list.size(); i++) {
                Aliment food = this.aliment_list.get(i);
                if (food.getType().equals(this.type)) {
                    this.aliment_list_by_type.add(food);
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
    }

    void setSum_food(){
        sum_aliment.clear();
        double sum_solid = 0;
        double sum_liquid = 0;
        for(int cpt = 0;cpt < food_list_by_type.size();cpt++){
            if(aliment_list_by_type.get(cpt).getUnite().equals("kg")){
                sum_solid += aliment_list_by_type.get(cpt).getQuantite();
            }else if(aliment_list_by_type.get(cpt).getUnite().equals("g")){
                sum_solid += (double)aliment_list_by_type.get(cpt).getQuantite()/1000;
            }else if(aliment_list_by_type.get(cpt).getUnite().equals("L")){
                sum_liquid += aliment_list_by_type.get(cpt).getQuantite();
            }else if(aliment_list_by_type.get(cpt).getUnite().equals("ml")){
                sum_liquid += (double)aliment_list_by_type.get(cpt).getQuantite()/1000;
            }
        }
        if(type.equals("Tout")){
            this.sum_aliment.add(new Sum("Aliments",""));
        }else {
            this.sum_aliment.add(new Sum(this.type,""));
        }
        DecimalFormat fmt = new DecimalFormat("##0.00");
        this.sum_aliment.add(new Sum("Solide",fmt.format(sum_solid)));
        this.sum_aliment.add(new Sum("Liquide",fmt.format(sum_liquid)));
    }

    ArrayList<String> getSum_food(){
        sum_food.clear();
        setSum_food();
        this.sum_food.add(this.sum_aliment.get(0).getNom());
        this.sum_food.add(this.sum_aliment.get(1).getNom()+"    "+this.sum_aliment.get(1).getSum()+"kg");
        this.sum_food.add(this.sum_aliment.get(2).getNom()+"   "+this.sum_aliment.get(2).getSum()+"L");
        return this.sum_food;
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

    void addType(Type type){
        this.type_list.add(type);
    }
    void setFlagnum(int flag){this.flagnum=flag;}
}
