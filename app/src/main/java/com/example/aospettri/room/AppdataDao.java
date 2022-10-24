package com.example.aospettri.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AppdataDao {

    @Query("select ck from appdata where id=1")
    String findCK();
}

