package com.example.pprzy.eZdrowie.model;

/**
 * Created by pprzy on 07.01.2018.
 */

public class Basic {

    private int id;
    private String name;
    private String age;
    private String gender;
    private String height;
    private String weight_basic;


    public Basic(){}

    public Basic(String name, String age, String gender, String height, String weight_basic) {
        super();
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight_basic = weight_basic;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String age) {
        this.age = age;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getWeightBasic() {
        return weight_basic;
    }
    public void setWeightBasic(String weight_basic) {
        this.weight_basic = weight_basic;
    }


    public int getId2(String date) {
        return id;
    }

    @Override
    public String toString() {
        return "Basic [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", height=" + height + ", weight_basic=" + weight_basic
                + "]";
    }
}