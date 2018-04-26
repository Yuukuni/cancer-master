package com.example.huangyuwei.myapplication.database;

import android.arch.persistence.room.Entity;

/**
 * Created by XiDream on 2018/4/17.
 */
@Entity(tableName = "memsetting", primaryKeys = {"primaryKey"})
public class MemSetting {
    public int primaryKey;

    public boolean physicalUsed;
    public boolean foodUsed;
    public boolean moveUsed;
    public boolean moodUsed;

    public boolean foodTimeUsed_1;
    public boolean foodTimeUsed_2;
    public boolean foodTimeUsed_3;
    public boolean moveTimeUsed_1;
    public boolean moveTimeUsed_2;

    public int physicalTimeHour;
    public int foodTimeHour_1;
    public int foodTimeHour_2;
    public int foodTimeHour_3;
    public int moveTimeHour_1;
    public int moveTimeHour_2;
    public int moodTimeHour;

    public int physicalTimeMinute;
    public int foodTimeMinute_1;
    public int foodTimeMinute_2;
    public int foodTimeMinute_3;
    public int moveTimeMinute_1;
    public int moveTimeMinute_2;
    public int moodTimeMinute;

    public int physicalFrequencySelect;
    public int foodFrequencySelect;
    public int moveFrequencySelect;
    public int moodFrequencySelect;
}
