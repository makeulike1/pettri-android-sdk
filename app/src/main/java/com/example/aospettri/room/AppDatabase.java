package com.example.aospettri.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Appdata.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppdataDao appdataDao();
}