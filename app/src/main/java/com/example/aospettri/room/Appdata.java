package com.example.aospettri.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Appdata {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "ck")
    public String ck;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCk() {
        return ck;
    }

    public void setCk(String ck) {
        this.ck = ck;
    }

    public Appdata(Integer id, String ck){
        this.id = id;
        this.ck = ck;
    }

    @Override
    public String toString() {
        return "Appdata{" +
                "id=" + id +
                ", ck='" + ck + '\'' +
                '}';
    }
}
