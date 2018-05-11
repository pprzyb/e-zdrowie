package com.example.pprzy.eZdrowie.model;

/**
 * Created by pprzy on 07.01.2018.
 */

public class Phys {

    private int id;
    private String date;
    private String scale_phys;
    private String walk;
    private String breaks;
    private String adtActivity;


    public Phys(String date, String scale_phys, String walk, String breaks, String adtActivity) {
        super();
        this.date = date;
        this.scale_phys = scale_phys;
        this.walk = walk;
        this.breaks = breaks;
        this.adtActivity = adtActivity;
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
    public String getScalePhys() {
        return scale_phys;
    }
    public void setScalePhys(String scale_phys) {
        this.scale_phys = scale_phys;
    }
    public String getWalk() {
        return walk;
    }
    public void setWalk(String walk) {
        this.walk = walk;
    }
    public String getBreaks() {
        return breaks;
    }
    public void setBreaks(String breaks) {
        this.breaks = breaks;
    }
    public String getAdtActivity() {
        return adtActivity;
    }
    public void setNumberOfAwakes(String adtActivity) {
        this.adtActivity = adtActivity;
    }
    public String getPhysByDate() {
        return date;
    }

    public int getId2(String date) {
        return id;
    }

    @Override
    public String toString() {
        return "Sleep [id=" + id + ", date=" + date + ", scale_phys=" + scale_phys + ", walk=" + walk + ", breaks=" + breaks + ", adtActivity=" + adtActivity
                + "]";
    }
}