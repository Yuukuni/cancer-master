package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

/**
 * Created by XiDream on 2018/3/17.
 */
@Entity(tableName = "movetime", primaryKeys = {"date_id", "time"})
public class MoveTime {
    public int date_id;
    public int time;
    public String MoveName;
    public Double duration;
    public Double calories;
}
