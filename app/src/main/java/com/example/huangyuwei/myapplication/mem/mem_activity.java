package com.example.huangyuwei.myapplication.mem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.center;
import com.example.huangyuwei.myapplication.cure.cure_main;
import com.example.huangyuwei.myapplication.database.CancerDatabase;
import com.example.huangyuwei.myapplication.database.MemActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.huangyuwei.myapplication.MainActivity.cb;

public class mem_activity extends AppCompatActivity {

    private static final int UPDATE_ACTIVITY = 0;
    private static final int SINGLE_PAGE_ACTIVITIES = 10;
    private static final int LIST_PAGES = 5;

    private int pageAmount;
    private int currentPage;
    private int currentPagePosition;

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
        setPaging();
    }

    private void init() {
        currentPage = 1;
        currentPagePosition = 1;
        allActivities = cb.memActivityDao().getAllMemActivity();
        activityTable = (TableLayout) findViewById(R.id.activity_table);
        inflater = LayoutInflater.from(this);
        addActivity = (Button) findViewById(R.id.addActivity);
        pageAmount = (allActivities.size() - 1) / SINGLE_PAGE_ACTIVITIES + 1;
    }

    private void setActivityTable() {
        int count = activityTable.getChildCount();
        TableRow topRow = (TableRow) findViewById(R.id.activity_top_row);
        for (int i = 0; i < count; i++) {
            View child = activityTable.getChildAt(i);
            if (child instanceof TableRow && child != topRow) {
                ((ViewGroup) child).removeAllViews();
            }
        }

        for(int i = (currentPage-1) * SINGLE_PAGE_ACTIVITIES; i < currentPage * SINGLE_PAGE_ACTIVITIES; i++) {
            if(i >= allActivities.size()) {
                break;
            }

            MemActivity activity = allActivities.get(i);
            logActivity(activity);

            TableRow activityRow = (TableRow) inflater.inflate(R.layout.table_row_mem_activity, activityTable, false);
            TextView date = (TextView) activityRow.findViewById(R.id.MemActivityDate);
            TextView time = (TextView) activityRow.findViewById(R.id.MemActivityTime);
            TextView name = (TextView) activityRow.findViewById(R.id.MemActivityName);

            name.setText(activity.name);
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
                final AlertDialog d = new AlertDialog.Builder(mem_activity.this)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteMemActivity(cb, activity);
                                refresh();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setMessage("確定要刪除「" + activity.name + "」這個活動嗎")
                        .setTitle("刪除活動「" + activity.name + "」" )
                        .create();
                d.show();
                return true;
            }
        });
    }

    private void deleteMemActivity(final CancerDatabase db, MemActivity activity) {
        db.beginTransaction();
        try {
            db.memActivityDao().delete(activity);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
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

    private void setPaging() {
        TextView topPages = (TextView) findViewById(R.id.topPages);
        TextView previousPages = (TextView) findViewById(R.id.previousPages);
        final TextView previousPage = (TextView) findViewById(R.id.previousPage);
        final List<TextView> currentPages = new ArrayList();
        currentPages.add((TextView) findViewById(R.id.page1));
        currentPages.add((TextView) findViewById(R.id.page2));
        currentPages.add((TextView) findViewById(R.id.page3));
        currentPages.add((TextView) findViewById(R.id.page4));
        currentPages.add((TextView) findViewById(R.id.page5));
        TextView nextPage = (TextView) findViewById(R.id.nextPage);
        TextView nextPages = (TextView) findViewById(R.id.nextPages);
        TextView bottomPages = (TextView) findViewById(R.id.bottomPages);

        topPages.setText("|<");
        previousPages.setText("<<");
        previousPage.setText("<");
        nextPage.setText(">");
        nextPages.setText(">>");
        bottomPages.setText(">|");

        for(int i = 1; i <= LIST_PAGES; i++) {
            if(currentPage - currentPagePosition + i > pageAmount){
                currentPages.get(i-1).setText("");
            }
            else{
                currentPages.get(i-1).setText(Integer.toString(currentPage - currentPagePosition + i));
                if(i != currentPagePosition) {
                    currentPages.get(i-1).getPaint().setFlags(0);
                    final int position = i;
                    currentPages.get(i-1).setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            currentPage += (position - currentPagePosition);
                            currentPagePosition = position;
                            setActivityTable();
                            setPaging();
                        }
                    });
                }
                else{
                    currentPages.get(i-1).getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
                }
            }
        }

        Log.d("CURRENT_PAGE", Integer.toString(currentPage));

        Log.d("CURRENT_PAGE_POSITION", Integer.toString(currentPagePosition));


        previousPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(currentPage != 1){
                    currentPage--;
                    if(currentPagePosition > 1){
                        currentPagePosition--;
                    }
                    else{
                        currentPagePosition = 5;
                    }
                    setActivityTable();
                    setPaging();
                }
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(currentPage < pageAmount){
                    currentPage++;
                    if(currentPagePosition < 5){
                        currentPagePosition++;
                    }
                    else{
                        currentPagePosition = 1;
                    }
                    setActivityTable();
                    setPaging();
                }
            }
        });

        if(pageAmount > 5) {
            previousPages.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(currentPage > 5){
                        currentPage -= (currentPagePosition + 4);
                        currentPagePosition = 1;
                    }
                    setActivityTable();
                    setPaging();
                }
            });
            nextPages.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if((pageAmount - 1)/LIST_PAGES > (currentPage - 1)/LIST_PAGES){
                        currentPage += (6 - currentPagePosition);
                        currentPagePosition = 1;
                    }
                    setActivityTable();
                    setPaging();
                }
            });
            topPages.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    currentPage = 1;
                    currentPagePosition = 1;
                    setActivityTable();
                    setPaging();
                }
            });
            bottomPages.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int bottomPosition = pageAmount % LIST_PAGES;
                    if(bottomPosition == 0){
                        currentPage = pageAmount - 4;
                    }
                    else{
                        currentPage = pageAmount - bottomPosition + 1;
                    }
                    currentPagePosition = 1;
                    setActivityTable();
                    setPaging();
                }
            });
        }

    }

    private void setPageOnClickListener(final int i, TextView page) {
        page.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                currentPage += (i - currentPagePosition);
                currentPagePosition = i;
                setActivityTable();
                setPaging();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPDATE_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                refresh();
            }
        }
    }

    private void refresh() {
        Intent intent = new Intent(mem_activity.this, mem_activity.class);
        startActivity(intent);
        finish();
    }

}
