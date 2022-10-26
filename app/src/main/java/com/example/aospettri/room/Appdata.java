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

    @ColumnInfo(name = "trkId")
    public String trkId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Appdata(Integer id, String ck, String trkId){
        this.id = id;
        this.ck = ck;
        this.trkId = trkId;
    }

    @Override
    public String toString() {
        return "Appdata{" +
                "id=" + id +
                ", ck='" + ck + '\'' +
                '}';
    }
}
