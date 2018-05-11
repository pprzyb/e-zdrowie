package com.example.pprzy.eZdrowie.model;

/**
 * Created by pprzy on 07.01.2018.
 */

public class Sleep {

    private int id;
    private String date;
    private String scale;
    private String timeToGetSleep;
    private String timeOfSleep;
    private String numberOfAwakes;



    public Sleep(String date, String scale, String timeToGetSleep, String timeOfSleep, String numberOfAwakes) {
        super();
        this.date = date;
        this.scale = scale;
        this.timeToGetSleep = timeToGetSleep;
        this.timeOfSleep = timeOfSleep;
        this.numberOfAwakes = numberOfAwakes;
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
    public String getScale() {
        return scale;
    }
    public void setScale(String scale) {
        this.scale = scale;
    }
    public String getTimeToGetSleep() {
        return timeToGetSleep;
    }
    public void setTimeToGetSleep(String timeToGetSleep) {
        this.timeToGetSleep = timeToGetSleep;
    }
    public String getTimeOfSleep() {
        return timeOfSleep;
    }
    public void setTimeOfSleep(String timeOfSleep) {
        this.timeOfSleep = timeOfSleep;
    }
    public String getNumberOfAwakes() {
        return numberOfAwakes;
    }
    public void setNumberOfAwakes(String numberOfAwakes) {
        this.numberOfAwakes = numberOfAwakes;
    }
    public String getSleepByDate() {
        return date;
    }

    public int getId2(String date) {
        return id;
    }

    @Override
    public String toString() {
        return "Sleep [id=" + id + ", date=" + date + ", scale=" + scale + ", timeToGetSleep=" + timeToGetSleep + ", timeOfSleep=" + timeOfSleep + ", numberOfAwakes=" + numberOfAwakes
                + "]";
    }
}