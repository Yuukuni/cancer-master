package com.example.huangyuwei.myapplication.mem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.huangyuwei.myapplication.R;

public class mem_activity extends AppCompatActivity {

    private TableLayout activityTable;
    private Button addActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_activity);

        activityTable = (TableLayout) findViewById(R.id.activity_table);
        addActivity = (Button) findViewById(R.id.addActivity);
    }
}
