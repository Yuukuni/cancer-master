package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

/**
 * Created by XiDream on 2018/3/27.
 */
@Entity(tableName = "memActivity", primaryKeys = {"id"})
public class MemActivity {
    public int id;
    public String name;
    public int fromDate;
    public int fromTime;
    public int toDate;
    public int toTime;
    public String locationName;
    public String locationAddress;
    public String remark;
}
