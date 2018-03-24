package com.example.huangyuwei.myapplication.mem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.database.CancerDatabase;

import java.util.Calendar;

import static com.example.huangyuwei.myapplication.MainActivity.getContext;

public class mem_activity extends AppCompatActivity {

    private Context context;
    private static mem_activity instance;

    private Button addActivity;
    private mem_activity_edit editActivity;

    private TableLayout activityTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_activity);

        context = this;
        instance = this;

        activityTable = (TableLayout) findViewById(R.id.activity_table);
        //activityTable= CancerDatabase.getInMemoryDatabase(getContext()).foodTimeDao().getAllFoodTime();
        //initial of List<MemActivity>

        addActivity = (Button) findViewById(R.id.addActivity);

        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editActivity = new mem_activity_edit(context, instance);
                editActivity.setTitle("新增");
                editActivity.setYesOnclickListener("確定", new mem_activity_edit.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        editActivity.dismiss();
                    }
                });
                editActivity.show();
            }
        });

    }
}
