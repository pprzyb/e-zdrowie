package com.example.pprzy.eZdrowie.model;

/**
 * Created by pprzy on 07.01.2018.
 */

public class Puls {

    private int id;
    private String date;
    private String puls;
    private String cis_skurcz;
    private String cis_rozkurcz;

    public Puls(String date, String puls, String cis_skurcz, String cis_rozkurcz) {
        super();
        this.date = date;
        this.puls = puls;
        this.cis_skurcz = cis_skurcz;
        this.cis_rozkurcz = cis_rozkurcz;
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
    public String getPuls() {
        return puls;
    }
    public void setPuls(String puls) {
        this.puls = puls;
    }
    public String getCisSkurcz() {
        return cis_skurcz;
    }
    public void setCis_skurcz(String puls) {
        this.cis_skurcz = cis_skurcz;
    }
    public String getCisRozkurcz() {
        return cis_rozkurcz;
    }
    public void setCis_rozkurcz(String puls) {
        this.cis_rozkurcz = cis_rozkurcz;
    }
    public String getPulsByDate() {
        return date;
    }

    public int getId2(String date) {
        return id;
    }

    @Override
    public String toString() {
        return "Puls [id=" + id + ", date=" + date + ", puls=" + puls + ", cis_skurcz=" + cis_skurcz + ", cis_rozkurcz=" + cis_rozkurcz
                + "]";
    }
}