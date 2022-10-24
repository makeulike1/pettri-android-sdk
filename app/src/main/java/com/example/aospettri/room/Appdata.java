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

}
