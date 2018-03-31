package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

import java.io.Serializable;

/**
 * Created by XiDream on 2018/3/27.
 */
@Entity(tableName = "memActivity", primaryKeys = {"createDate", "createTime"})
public class MemActivity implements Serializable{
    public int createDate;
    public int createTime;
    public String name;
    public String fromDate;
    public String fromTime;
    public String toDate;
    public String toTime;
    public String locationName;
    public String locationAddress;
    public String remark;
}
