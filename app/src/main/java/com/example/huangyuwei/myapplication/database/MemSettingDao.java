package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by XiDream on 2018/4/17.
 */

@Dao
public interface MemSettingDao {
    @Query("SELECT * FROM memsetting")
    List<MemSetting> getAllMemSetting();

    @Insert
    void insertMemSetting(MemSetting... memSettings);

    @Update
    void updateMemSetting(MemSetting... memSettings);
}
