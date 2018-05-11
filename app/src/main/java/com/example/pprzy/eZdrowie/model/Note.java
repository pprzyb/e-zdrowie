//kolumna DATE to notatka, a NOTE to data. Pomyłka w początkowym stadium tworzenia bazy, obecnie zbyt duży problem z refaktoryzacją
package com.example.pprzy.eZdrowie.model;

public class Note {

    private int id;
    private String date;
    private String note;

    //construcor
    public Note(String date, String note) {
        super();
        this.date = date;
        this.note = note;
    }

    //methods
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
    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "Notatka [id=" + id + ", date=" + date + ", note=" + note
                + "]";
    }
}