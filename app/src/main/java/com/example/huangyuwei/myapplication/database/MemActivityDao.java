package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by XiDream on 2018/3/27.
 */
@Dao
public interface MemActivityDao {
    @Query("SELECT * FROM memActivity")
    List<MemActivity> getAllMemActivity();

    @Insert
    void insertMemActivity(MemActivity... memActivities);

    @Update
    void updateMemActivity(MemActivity... memActivities);

    @Delete
    void delete(MemActivity... memActivities);
}
