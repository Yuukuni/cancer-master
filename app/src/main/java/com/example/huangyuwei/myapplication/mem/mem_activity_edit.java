package com.example.huangyuwei.myapplication.mem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.huangyuwei.myapplication.R;

/**
 * Created by XiDream on 2018/3/24.
 */

public class mem_activity_edit extends Dialog {

    private Context context;
    private mem_activity instance;

    private EditText activity_name_input, activity_date_input, activity_location_input, activity_remark_input;
    private String activity_name, activity_date, activity_location, activity_remark;
    private Button btn_confirm;

    private String yesStr;
    private mem_activity_edit.onYesOnclickListener yesOnclickListener;

    public mem_activity_edit(@NonNull Context context, mem_activity instance) {
        super(context);
        this.instance = instance;
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mem_activity);

        context = getContext();
        init();

        btn_confirm.setText(yesStr);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();

                    getData();

                    Intent refresh = new Intent(context, mem_activity.class);
                    context.startActivity(refresh);
                    instance.finish();
                }
            }
        });
    }

    void init() {
        activity_name_input = (EditText) findViewById(R.id.activity_name_input);
        activity_date_input = (EditText) findViewById(R.id.activity_date_input);
        activity_location_input = (EditText) findViewById(R.id.activity_location_input);
        activity_remark_input = (EditText) findViewById(R.id.activity_remark_input);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
    }

    void getData() {
        activity_name = activity_name_input.getText().toString();
        activity_date = activity_date_input.getText().toString();
        activity_location = activity_location_input.getText().toString();
        activity_remark = activity_remark_input.getText().toString();
    }

    public void setYesOnclickListener(String str, mem_activity_edit.onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    public interface onYesOnclickListener {
        public void onYesClick();
    }

}
