package com.example.huangyuwei.myapplication.mem;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.huangyuwei.myapplication.R;

import java.util.Locale;

public class mem_activity extends AppCompatActivity {

    private static final int ADD_ACTIVITY = 0;

    private Context context;
    private static mem_activity instance;

    private Button addActivity;
    private mem_activity_edit editActivity;

    private TableLayout activityTable;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_activity);

        context = this;
        instance = this;

        dateFormatter = new SimpleDateFormat("yyyyMMdd");
        timeFormatter = new SimpleDateFormat("HHmm", Locale.TAIWAN);

        activityTable = (TableLayout) findViewById(R.id.activity_table);
        //activityTable= CancerDatabase.getInMemoryDatabase(getContext()).foodTimeDao().getAllFoodTime();
        //initial of List<MemActivity>

        addActivity = (Button) findViewById(R.id.addActivity);
        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mem_activity.this, mem_activity_edit.class);
                startActivityForResult(intent, ADD_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                // back to here
            }
        }
    }
}
