package com.example.huangyuwei.myapplication.mem;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.center;
import com.example.huangyuwei.myapplication.cure.cure_main;
import com.example.huangyuwei.myapplication.database.MemActivity;

import java.util.List;

import static com.example.huangyuwei.myapplication.MainActivity.cb;

public class mem_activity extends AppCompatActivity {

    private static final int UPDATE_ACTIVITY = 0;

    private List<MemActivity> allActivities;
    private TableLayout activityTable;
    private LayoutInflater inflater;
    private Button addActivity;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_activity);
        init();
        setActivityTable();
        setAddActivityButton();
    }

    private void init() {
        allActivities = cb.memActivityDao().getAllMemActivity();
        activityTable = (TableLayout) findViewById(R.id.activity_table);
        inflater = LayoutInflater.from(this);
        addActivity = (Button) findViewById(R.id.addActivity);
    }

    private void setActivityTable() {
        for(int i = 0; i < allActivities.size(); i++) {
            MemActivity activity = allActivities.get(i);
            logActivity(activity);

            TableRow activityRow = (TableRow) inflater.inflate(R.layout.table_row_mem_activity, activityTable, false);
            TextView date = (TextView) activityRow.findViewById(R.id.MemActivityDate);
            TextView time = (TextView) activityRow.findViewById(R.id.MemActivityTime);
            TextView name = (TextView) activityRow.findViewById(R.id.MemActivityName);

            if(activity.fromDate.contentEquals(activity.toDate)) {
                date.setText(activity.fromDate);
                if(activity.fromTime.contentEquals(activity.toTime)) {
                    time.setText(activity.fromTime);
                }
                else {
                    time.setText(activity.fromTime + "\n" + activity.toTime);
                }
            }
            else {
                date.setText(activity.fromDate + "\n" + activity.toDate);
                time.setText(activity.fromTime + "\n" + activity.toTime);
            }
            name.setText(activity.name);

            setOnClickListeners(activityRow, activity);
            activityTable.addView(activityRow);
        }
    }

    private void logActivity(MemActivity activity) {
        Log.d("TAG",
                "\ncreateDate: " + activity.createDate +
                        "\ncreateDate: " + activity.createTime +
                        "\nname: " + activity.name +
                        "\nfromDate: " + activity.fromDate +
                        "\nfromTime: " + activity.fromTime +
                        "\ntoDate: " + activity.toDate +
                        "\ntoTime: " + activity.toTime +
                        "\nlocationName: " + activity.locationName +
                        "\nlocationAddress: " + activity.locationAddress +
                        "\nremark: " + activity.remark);
    }

    private void setOnClickListeners(TableRow activityRow, final MemActivity activity) {
        activityRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final mem_activity_dialog activityContent = new mem_activity_dialog(mem_activity.this, activity);
                activityContent.show();
                activityContent.btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activityContent.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("activity", activity);
                        Intent intent = new Intent();
                        intent.setClass(mem_activity.this  , mem_activity_edit.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, UPDATE_ACTIVITY);
                    }
                });
                activityContent.btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activityContent.dismiss();
                    }
                });
            }
        });

        activityRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
    }

    private void setAddActivityButton() {
        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mem_activity.this, mem_activity_edit.class);
                startActivityForResult(intent, UPDATE_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPDATE_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(mem_activity.this, mem_activity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
