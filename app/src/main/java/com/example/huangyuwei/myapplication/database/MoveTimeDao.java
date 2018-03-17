package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by XiDream on 2018/3/17.
 */

@Dao
public interface MoveTimeDao {

    @Query("SELECT * FROM movetime")
    List<MoveTime> getAllMoveTime();

    @Insert
    void insertMoveTime(MoveTime... moveTimes);

    @Update
    void updateMoveTime(MoveTime... moveTimes);

    @Delete
    void delete(MoveTime... moveTimes);

}
