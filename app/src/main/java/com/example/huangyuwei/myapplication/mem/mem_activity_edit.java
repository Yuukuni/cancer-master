package com.example.huangyuwei.myapplication.mem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TimePicker;

import com.example.huangyuwei.myapplication.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by XiDream on 2018/3/24.
 */

public class mem_activity_edit extends Dialog {

    private Context context;
    private mem_activity instance;

    private EditText activity_name_input, activity_location_input, activity_remark_input;
    private EditText activity_from_date_input, activity_from_time_input;
    private EditText activity_to_date_input, activity_to_time_input;

    private String activity_name, activity_location, activity_remark;
    private String activity_from_date, activity_from_time;
    private String activity_to_date, activity_to_time;

    private DatePickerDialog fromDateDialog;
    private DatePickerDialog toDateDialog;
    private TimePickerDialog fromTimeDialog;
    private TimePickerDialog toTimeDialog;

    private Date currentDate;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat datedbFormatter;
    private SimpleDateFormat timeFormatter;
    private SimpleDateFormat timedbFormatter;

    private String yesStr;
    private mem_activity_edit.onYesOnclickListener yesOnclickListener;
    private Button btn_confirm;

    public mem_activity_edit(@NonNull Context context, mem_activity instance) {
        super(context);
        this.instance = instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mem_activity);
        context = getContext();
        init();
        setFromDateField();
        setToDateField();
        setFromTimeField();
        setToTimeField();
        setConfirmButton();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void init() {
        activity_name_input = (EditText) findViewById(R.id.activity_name_input);
        activity_location_input = (EditText) findViewById(R.id.activity_location_input);
        activity_remark_input = (EditText) findViewById(R.id.activity_remark_input);
        activity_from_date_input = (EditText) findViewById(R.id.activity_from_date_input);
        activity_from_time_input = (EditText) findViewById(R.id.activity_from_time_input);
        activity_to_date_input = (EditText) findViewById(R.id.activity_to_date_input);
        activity_to_time_input = (EditText) findViewById(R.id.activity_to_time_input);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.TAIWAN);
        datedbFormatter = new SimpleDateFormat("yyyyMMdd");
        timedbFormatter = new SimpleDateFormat("HHmm",Locale.TAIWAN);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFromDateField() {
        activity_from_date_input.setInputType(InputType.TYPE_NULL);
        activity_from_date_input.requestFocus();
        activity_from_date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDateDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        fromDateDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                currentDate = c.getTime();
                activity_from_date_input.setText(dateFormatter.format(currentDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        activity_from_date_input.setText(dateFormatter.format(Calendar.getInstance().getTime()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setToDateField() {
        activity_to_date_input.setInputType(InputType.TYPE_NULL);
        activity_to_date_input.requestFocus();
        activity_to_date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDateDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        toDateDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                currentDate = c.getTime();
                activity_to_date_input.setText(dateFormatter.format(currentDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        activity_to_date_input.setText(dateFormatter.format(Calendar.getInstance().getTime()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFromTimeField() {
        activity_from_time_input.setInputType(InputType.TYPE_NULL);
        activity_from_time_input.requestFocus();
        activity_from_time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromTimeDialog.show();
            }
        });

        activity_from_time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromTimeDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        fromTimeDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH),hourOfDay,minute);
                activity_from_time_input.setText(timeFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MONTH),true);

        activity_from_time_input.setText(timeFormatter.format(Calendar.getInstance().getTime()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setToTimeField() {
        activity_to_time_input.setInputType(InputType.TYPE_NULL);
        activity_to_time_input.requestFocus();
        activity_to_time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTimeDialog.show();
            }
        });

        activity_to_time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTimeDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        toTimeDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH),hourOfDay,minute);
                activity_to_time_input.setText(timeFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MONTH),true);

        activity_to_time_input.setText(timeFormatter.format(Calendar.getInstance().getTime()));
    }

    private void setConfirmButton() {
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

    public void setYesOnclickListener(String str, mem_activity_edit.onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    public interface onYesOnclickListener {
        public void onYesClick();
    }

    void getData() {
        activity_name = activity_name_input.getText().toString();
        activity_location = activity_location_input.getText().toString();
        activity_remark = activity_remark_input.getText().toString();

        activity_from_date = activity_from_date_input.getText().toString();
        activity_from_time = activity_from_time_input.getText().toString();
        activity_to_date = activity_to_date_input.getText().toString();
        activity_to_time = activity_to_time_input.getText().toString();
    }

}
