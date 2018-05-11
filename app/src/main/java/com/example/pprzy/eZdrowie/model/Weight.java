package com.example.pprzy.eZdrowie.model;

/**
 * Created by pprzy on 07.01.2018.
 */

public class Weight {

    private int id;
    private String date;
    private String weight;


    public Weight(String date, String weight) {
        super();
        this.date = date;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    public String getWeightByDate() {
        return date;
    }

    public int getId2(String date) {
        return id;
    }

    @Override
    public String toString() {
        return "Waga [id=" + id + ", date=" + date + ", weight=" + weight
                + "]";
    }
}
