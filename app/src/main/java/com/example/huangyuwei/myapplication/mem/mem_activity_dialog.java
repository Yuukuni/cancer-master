package com.example.huangyuwei.myapplication.mem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.database.MemActivity;

/**
 * Created by XiDream on 2018/3/29.
 */

public class mem_activity_dialog extends Dialog {

    private MemActivity activity;

    private TextView activity_name;
    private TextView activity_from_date, activity_from_time;
    private TextView activity_to_date, activity_to_time;
    private TextView activity_location_name, activity_location_address;
    private TextView activity_remark;

    Button btn_edit;
    Button btn_cancel;

    public mem_activity_dialog(@NonNull Context context, MemActivity activity) {
        super(context);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mem_activity);
        init();
        setContent();
    }

    private void init() {
        activity_name = (TextView) findViewById(R.id.activity_name);
        activity_from_date = (TextView) findViewById(R.id.activity_from_date);
        activity_from_time = (TextView) findViewById(R.id.activity_from_time);
        activity_to_date = (TextView) findViewById(R.id.activity_to_date);
        activity_to_time = (TextView) findViewById(R.id.activity_to_time);
        activity_location_name = (TextView) findViewById(R.id.activity_location_name);
        activity_location_address = (TextView) findViewById(R.id.activity_location_address);
        activity_remark = (TextView) findViewById(R.id.activity_remark);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
    }

    private void setContent() {
        activity_name.setText(activity.name);
        activity_from_date.setText(activity.fromDate);
        activity_from_time.setText(activity.fromTime);
        activity_to_date.setText(activity.toDate);
        activity_to_time.setText(activity.toTime);
        activity_location_name.setText(activity.locationName);
        activity_location_address.setText(activity.locationAddress);
        activity_remark.setText(activity.remark);
    }
}
