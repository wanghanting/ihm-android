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
    ArrayList<Aliment> aliment_list_not_delete = new ArrayList<>();
    ArrayList<Aliment> aliment_list_not_delete_by_type = new ArrayList<>();
    ArrayList<Type> type_list = new ArrayList<>();
    ArrayList<Map<String, Object>> food_list = new ArrayList<Map<String, Object>>();
    ArrayList<Map<String, Object>> button_list = new ArrayList<>();
    ArrayList<Map<String, Object>> food_list_by_type = new ArrayList<>();
    ArrayList<String> type_aliment_name = new ArrayList<>();
    ArrayList<Integer> type_aliment_picture = new ArrayList<>();
    ArrayList<Sum> sum_aliment = new ArrayList<>();
    ArrayList<String> sum_food = new ArrayList<>();
    ArrayList<Sum> sum_expired_aliment = new ArrayList<>();
    ArrayList<String> sum_expired_food = new ArrayList<>();
    ArrayList<Rate> rate_aliment = new ArrayList<>();
    ArrayList<String> rate_food = new ArrayList<>();
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    int flagnum;
    String type = "Tout";
    String status = "Tout";
    ArrayList<User> users_list = new ArrayList<>();

    @Override
    public void onCreate() {

        try {
            initialAlimentList();
            initialFoodList();
            initialTypeList();
            initalFoodListByType();
            init_users_list();
            init_type_aliment_name();
            init_type_aliment_picture();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        super.onCreate();
    }

    void init_type_aliment_name() {
        for (int i=0; i<users_list.size(); i++) {
            this.type_aliment_name.add(users_list.get(i).getUsername());
        }
    }


    void init_users_list(){
        this.users_list.add(new User("John Doe", "Doe", "John", "J'aime l'écologie", "Je déteste gaspiller", "0621888340"));
        this.users_list.add(new User("Jane Doe", "Doe", "Jane", "J'aime l'écologie", "Je déteste gaspiller", "0621888340"));
        this.users_list.add(new User("John Smith", "Smith", "John", "J'aime l'écologie", "Je déteste gaspiller", "0621888340"));
        this.users_list.add(new User("Jane Smith", "Smith", "Jane", "J'aime l'écologie", "Je déteste gaspiller", "0621888340"));
        this.users_list.add(new User("Jane John", "John", "Jane", "J'aime l'écologie", "Je déteste gaspiller", "0621888340"));
    }

    void init_type_aliment_picture() {
        for (int i=0; i<users_list.size(); i++){
            this.type_aliment_picture.add(R.drawable.anonymous);
        }
    }

    void initialAlimentList() throws ParseException {

        Date date1 = df.parse("2019-05-20");
        Date date2 = df.parse("2019-05-14");
        Date date3 = df.parse("2019-05-12");
        this.aliment_list.add(new Aliment("pomme", date1, 3, "kg", R.drawable.apple, "fruit"));
        this.aliment_list.add(new Aliment("poire", date1, 5, "kg", R.drawable.pear, "fruit"));
        this.aliment_list.add(new Aliment("orange", date1, 8, "kg", R.drawable.orange, "fruit"));
        this.aliment_list.add(new Aliment("lait", date2, 1, "L", R.drawable.laitage, "laitage"));
        this.aliment_list.add(new Aliment("viande", date2, 500, "g", R.drawable.viande, "viande"));
        this.aliment_list.add(new Aliment("poisson", date3, 450, "g", R.drawable.poisson, "poisson"));
        this.aliment_list_not_delete.add(new Aliment("pomme", date1, 3, "kg", R.drawable.apple, "fruit"));
        this.aliment_list_not_delete.add(new Aliment("poire", date1, 5, "kg", R.drawable.pear, "fruit"));
        this.aliment_list_not_delete.add(new Aliment("orange", date1, 8, "kg", R.drawable.orange, "fruit"));
        this.aliment_list_not_delete.add(new Aliment("lait", date2, 1, "L", R.drawable.laitage, "laitage"));
        this.aliment_list_not_delete.add(new Aliment("viande", date2, 500, "g", R.drawable.viande, "viande"));
        this.aliment_list_not_delete.add(new Aliment("poisson", date3, 450, "g", R.drawable.poisson, "poisson"));
    }

    void initialFoodList() {
        this.getFood_list().clear();
        for (int i = 0; i < this.aliment_list.size(); i++) {
            Aliment food = this.aliment_list.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", food.getImgPath());
            map.put("aliment", food.getNom() + "  \n" + df.format(food.getExpirationDate()));
            map.put("num", food.getQuantite());
            map.put("unite", food.getUnite());
            map.put("supprimer", R.drawable.cross);
            this.food_list.add(map);
        }
    }

    void initialTypeList() throws ParseException {
        this.type_list.add(new Type("Tout"));
        this.type_list.add(new Type("fruit"));
        this.type_list.add(new Type("légume"));
        this.type_list.add(new Type("boisson"));
        this.type_list.add(new Type("laitage"));
        this.type_list.add(new Type("viande"));
        this.type_list.add(new Type("poisson"));
    }


    ArrayList<Map<String, Object>> getFood_list() {
        return this.food_list;
    }

    ArrayList<Aliment> getAliment_list() {
        return this.aliment_list;
    }

    ArrayList<Map<String, Object>> getButton_list() {
        return this.button_list;
    }

    ArrayList<Map<String, Object>> getFood_list_by_type() {
        return this.food_list_by_type;
    }

    ArrayList<Type> getType_list() {
        return this.type_list;
    }

    ArrayList<String> getTypes() {
        ArrayList<String> types = new ArrayList<>();
        for (int i = 1; i < this.type_list.size(); i++) {
            types.add(this.type_list.get(i).getNom());
        }
        return types;
    }

    ArrayList<String> getAllType() {
        ArrayList<String> types = new ArrayList<>();
        for (int i = 0; i < this.type_list.size(); i++) {
            types.add(this.type_list.get(i).getNom());
        }
        return types;
    }

    void setType(String type) {
        this.type = type;
    }

    void setStatus(String status) {
        this.status = status;
    }

    void initalFoodListByType() {
        this.getFood_list_by_type().clear();
        this.aliment_list_by_type.clear();
        if (this.type.equals("Tout") && this.status.equals("Tout")) {
            for (int i = 0; i < this.food_list.size(); i++) {
                this.food_list_by_type.add(food_list.get(i));
            }
            for (int i = 0; i < this.aliment_list.size(); i++) {
                this.aliment_list_by_type.add(aliment_list.get(i));
            }
        } else if (this.type.compareTo("Tout") != 0 && this.status.equals("Tout")) {
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
        } else if (this.type.equals("Tout") && this.status.compareTo("Tout") != 0) {
            for (int i = 0; i < this.aliment_list.size(); i++) {
                Aliment food = this.aliment_list.get(i);
                if (getStatus(food).equals(status)) {
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
        } else if(this.type.compareTo("Tout") != 0 && this.status.compareTo("Tout") != 0){
            for (int i = 0; i < this.aliment_list.size(); i++) {
                Aliment food = this.aliment_list.get(i);
                if (getStatus(food).equals(status) && food.getType().equals(this.type)) {
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
        initalAlimentNotDelete();
    }

    void initalAlimentNotDelete(){
        this.aliment_list_not_delete_by_type.clear();
        if (this.type.equals("Tout")) {
            for (int i = 0; i < this.aliment_list_not_delete.size(); i++) {
                this.aliment_list_not_delete_by_type.add(aliment_list_not_delete.get(i));
            }
        }else {
            for (int i = 0; i < this.aliment_list_not_delete.size(); i++) {
                Aliment food = this.aliment_list_not_delete.get(i);
                if (food.getType().equals(this.type)) {
                    this.aliment_list_not_delete_by_type.add(food);
                }
            }
        }
    }

    String getStatus(Aliment aliment){
        if(aliment.getStatus() < 0){
            return "périmé";
        }else if(aliment.getStatus() < 2){
            return "presque périmé";
        }else {
            return "OK";
        }
    }

    void setSum_food() {
        sum_aliment.clear();
        sum_expired_aliment.clear();
        rate_aliment.clear();
        double sum_all_solid = 0;
        double sum_all_liquid = 0;
        double sum_gaspillage_solid = 0;
        double sum_gaspillage_liquid = 0;
        for (int cpt = 0; cpt < aliment_list_not_delete_by_type.size(); cpt++) {
            if (aliment_list_not_delete_by_type.get(cpt).getUnite().equals("kg")) {
                sum_all_solid += aliment_list_not_delete_by_type.get(cpt).getQuantite();
                if(getStatus(aliment_list_not_delete_by_type.get(cpt)).equals("périmé")){
                    sum_gaspillage_solid += aliment_list_not_delete_by_type.get(cpt).getQuantite();
                }
            } else if (aliment_list_not_delete_by_type.get(cpt).getUnite().equals("g")) {
                sum_all_solid += (double) aliment_list_not_delete_by_type.get(cpt).getQuantite() / 1000;
                if(getStatus(aliment_list_not_delete_by_type.get(cpt)).equals("périmé")){
                    sum_gaspillage_solid += (double)aliment_list_not_delete_by_type.get(cpt).getQuantite() / 1000;
                }
            } else if (aliment_list_not_delete_by_type.get(cpt).getUnite().equals("L")) {
                sum_all_liquid += aliment_list_not_delete_by_type.get(cpt).getQuantite();
                if(getStatus(aliment_list_not_delete_by_type.get(cpt)).equals("périmé")){
                    sum_gaspillage_liquid += aliment_list_not_delete_by_type.get(cpt).getQuantite();
                }
            } else if (aliment_list_not_delete_by_type.get(cpt).getUnite().equals("ml")) {
                sum_all_liquid += (double) aliment_list_not_delete_by_type.get(cpt).getQuantite() / 1000;
                if(getStatus(aliment_list_not_delete_by_type.get(cpt)).equals("périmé")){
                    sum_gaspillage_liquid += (double) aliment_list_not_delete_by_type.get(cpt).getQuantite() / 1000;
                }
            }
        }
        if (type.equals("Tout")) {
            this.sum_aliment.add(new Sum("Aliments", ""));
            this.sum_expired_aliment.add(new Sum("Aliments Gaspillage",""));
            this.rate_aliment.add(new Rate("Taux",""));
        } else {
            this.sum_aliment.add(new Sum(this.type, ""));
            this.sum_expired_aliment.add(new Sum(this.type + " Gaspillage",""));
            this.rate_aliment.add(new Rate("Taux",""));
        }
        DecimalFormat fmt = new DecimalFormat("##0.00");
        this.sum_aliment.add(new Sum("Solide", fmt.format(sum_all_solid)));
        this.sum_aliment.add(new Sum("Liquide", fmt.format(sum_all_liquid)));
        this.sum_expired_aliment.add(new Sum("Solide", fmt.format(sum_gaspillage_solid)));
        this.sum_expired_aliment.add(new Sum("Liquide", fmt.format(sum_gaspillage_liquid)));
        if(sum_gaspillage_solid == 0){
            this.rate_aliment.add(new Rate("Solide","0.00%"));
        }else {
            this.rate_aliment.add(new Rate("Solide", fmt.format(sum_gaspillage_solid / sum_all_solid * 100) + "%"));
        }
        if(sum_gaspillage_liquid == 0){
            this.rate_aliment.add(new Rate("Liquide","0.00%"));
        }else {
            this.rate_aliment.add(new Rate("Liquide", fmt.format(sum_gaspillage_liquid / sum_all_liquid * 100) + "%"));
        }
    }


    ArrayList<String> getSum_food() {
        sum_food.clear();
        setSum_food();
        this.sum_food.add(this.sum_aliment.get(0).getNom());
        this.sum_food.add(this.sum_aliment.get(1).getNom() + "    " + this.sum_aliment.get(1).getSum() + "kg");
        this.sum_food.add(this.sum_aliment.get(2).getNom() + "   " + this.sum_aliment.get(2).getSum() + "L");
        return this.sum_food;
    }

    ArrayList<String> getSum_food_gas(){
        sum_expired_food.clear();
        setSum_food();
        this.sum_expired_food.add(this.sum_expired_aliment.get(0).getNom());
        this.sum_expired_food.add(this.sum_expired_aliment.get(1).getSum() + "kg");
        this.sum_expired_food.add(this.sum_expired_aliment.get(2).getSum() + "L");
        return this.sum_expired_food;
    }

    ArrayList<String> getRate_food(){
        rate_food.clear();
        setSum_food();
        this.rate_food.add(this.rate_aliment.get(0).getNom());
        this.rate_food.add(this.rate_aliment.get(1).getSum());
        this.rate_food.add(this.rate_aliment.get(2).getSum());
        return this.rate_food;
    }

    User getUser() {
        return user;
    }

    ArrayList<User> getUsers_list() {return this.users_list;}

    void addFood(Aliment aliment) {
        this.aliment_list.add(aliment);
        this.aliment_list_not_delete.add(aliment);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("image", aliment.getImgPath());
        map.put("aliment", aliment.getNom() + "  \n" + df.format(aliment.getExpirationDate()));
        map.put("num", aliment.getQuantite());
        map.put("unite", aliment.getUnite());
        map.put("supprimer", R.drawable.cross);
        this.food_list.add(map);
    }

    void deleteFood(int foodNumber) {
        this.aliment_list.remove(foodNumber);
        this.food_list.remove(foodNumber);
    }

    void addType(Type type) {
        this.type_list.add(type);
    }

    void setFlagnum(int flag) {
        this.flagnum = flag;
    }
}
