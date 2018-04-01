package com.example.huangyuwei.myapplication.mem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.database.CancerDatabase;
import com.example.huangyuwei.myapplication.database.MemActivity;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.huangyuwei.myapplication.MainActivity.cb;
import static com.example.huangyuwei.myapplication.MainActivity.getContext;

public class mem_activity_edit extends AppCompatActivity {

    private final int REQUEST_CODE_PLACEPICKER = 1;

    private boolean newActivity;
    private MemActivity activity;

    private EditText activity_name_input;
    private EditText activity_from_date_input, activity_from_time_input;
    private EditText activity_to_date_input, activity_to_time_input;
    private TextView activity_location_name_input, activity_location_address_input;
    private EditText activity_remark_input;
    private Button btn_select_location;
    private Button btn_save;
    private Button btn_cancel;

    private int createDate, createTime;
    private String activity_name;
    private String activity_from_date, activity_from_time, activity_to_date, activity_to_time;
    private String activity_location_name, activity_location_address;
    private String activity_remark;

    private DatePickerDialog fromDateDialog;
    private DatePickerDialog toDateDialog;
    private TimePickerDialog fromTimeDialog;
    private TimePickerDialog toTimeDialog;

    private Date currentDate;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;
    private SimpleDateFormat dateIntFormatter;
    private SimpleDateFormat timeIntFormatter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_activity_edit);
        init();
        setFromDateField();
        setToDateField();
        setFromTimeField();
        setToTimeField();
        setSelectLocationButton();
        setSaveButton();
        setCancelButton();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {
        activity_name_input = (EditText) findViewById(R.id.activity_name_input);
        activity_from_date_input = (EditText) findViewById(R.id.activity_from_date_input);
        activity_from_time_input = (EditText) findViewById(R.id.activity_from_time_input);
        activity_to_date_input = (EditText) findViewById(R.id.activity_to_date_input);
        activity_to_time_input = (EditText) findViewById(R.id.activity_to_time_input);
        activity_location_name_input = (TextView) findViewById(R.id.activity_location_name_input);
        activity_location_address_input = (TextView) findViewById(R.id.activity_location_address_input);
        activity_remark_input = (EditText) findViewById(R.id.activity_remark_input);
        btn_select_location = (Button) findViewById(R.id.btn_select_location);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        activity = (MemActivity) getIntent().getSerializableExtra("activity");
        if(activity == null) {
            //logActivity(activity);
            newActivity = true;
        }
        else {
            newActivity = false;
            activity_name_input.setText(activity.name);
            activity_from_date_input.setText(activity.fromDate);
            activity_from_time_input.setText(activity.fromTime);
            activity_to_date_input.setText(activity.toDate);
            activity_to_time_input.setText(activity.toTime);
            activity_location_name_input.setText(activity.locationName);
            activity_location_address_input.setText(activity.locationAddress);
            activity_remark_input.setText(activity.remark);
        }

        activity_name = new String();
        activity_from_date = new String();
        activity_from_time = new String();
        activity_to_date = new String();
        activity_to_time = new String();
        activity_location_name = new String();
        activity_location_address = new String();
        activity_remark = new String();

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.TAIWAN);
        dateIntFormatter = new SimpleDateFormat("yyyyMMdd", Locale.TAIWAN);
        timeIntFormatter = new SimpleDateFormat("HHmmss", Locale.TAIWAN);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFromDateField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDateDialog = new DatePickerDialog(mem_activity_edit.this, new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                currentDate = c.getTime();
                activity_from_date_input.setText(dateFormatter.format(currentDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        activity_from_date_input.setInputType(InputType.TYPE_NULL);
        activity_from_date_input.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        activity_from_date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDateDialog.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setToDateField() {
        Calendar newCalendar = Calendar.getInstance();
        toDateDialog = new DatePickerDialog(mem_activity_edit.this, new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                currentDate = c.getTime();
                activity_to_date_input.setText(dateFormatter.format(currentDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        activity_to_date_input.setInputType(InputType.TYPE_NULL);
        activity_to_date_input.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        activity_to_date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDateDialog.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFromTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromTimeDialog = new TimePickerDialog(mem_activity_edit.this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH),hourOfDay,minute);
                activity_from_time_input.setText(timeFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MONTH),true);

        activity_from_time_input.setInputType(InputType.TYPE_NULL);
        activity_from_time_input.setText(timeFormatter.format(Calendar.getInstance().getTime()));
        activity_from_time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromTimeDialog.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setToTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        toTimeDialog = new TimePickerDialog(mem_activity_edit.this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH),hourOfDay,minute);
                activity_to_time_input.setText(timeFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MONTH),true);

        activity_to_time_input.setInputType(InputType.TYPE_NULL);
        activity_to_time_input.setText(timeFormatter.format(Calendar.getInstance().getTime()));
        activity_to_time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTimeDialog.show();
            }
        });
    }

    private void setSelectLocationButton() {
        btn_select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlacePickerActivity();
            }
        });
    }

    private void startPlacePickerActivity() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == RESULT_OK) {
            displaySelectedPlaceFromPlacePicker(data);
        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(this, data);
        activity_location_name_input.setText(placeSelected.getName().toString());
        activity_location_address_input.setText(placeSelected.getAddress().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setSaveButton() {
        if(newActivity){
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData();
                    addActivity();
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }
        else{
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData();
                    updateActivity();
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getData() {
        currentDate = Calendar.getInstance().getTime();
        createDate = Integer.parseInt(dateIntFormatter.format(currentDate));
        createTime = Integer.parseInt(timeIntFormatter.format(currentDate));
        activity_name = activity_name_input.getText().toString();
        activity_from_date = activity_from_date_input.getText().toString();
        activity_from_time = activity_from_time_input.getText().toString();
        activity_to_date = activity_to_date_input.getText().toString();
        activity_to_time = activity_to_time_input.getText().toString();
        activity_location_name = activity_location_name_input.getText().toString();
        activity_location_address = activity_location_address_input.getText().toString();
        activity_remark = activity_remark_input.getText().toString();
    }

    private void addActivity() {
        MemActivity activity = new MemActivity();
        activity.createDate = createDate;
        activity.createTime = createTime;
        activity.name = activity_name;
        activity.fromDate = activity_from_date;
        activity.fromTime = activity_from_time;
        activity.toDate = activity_to_date;
        activity.toTime = activity_to_time;
        activity.locationName = activity_location_name;
        activity.locationAddress = activity_location_address;
        activity.remark = activity_remark;
        Log.d("ADD", "add_activity");
        logActivity(activity);
        addMemActivity(cb, activity);
    }

    private void updateActivity() {
        activity.name = activity_name;
        activity.fromDate = activity_from_date;
        activity.fromTime = activity_from_time;
        activity.toDate = activity_to_date;
        activity.toTime = activity_to_time;
        activity.locationName = activity_location_name;
        activity.locationAddress = activity_location_address;
        activity.remark = activity_remark;
        Log.d("UPDATE", "update_activity");
        logActivity(activity);
        updateMemActivity(cb, activity);
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

    private void setCancelButton() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void addMemActivity(final CancerDatabase db, MemActivity activity) {
        db.beginTransaction();
        try {
            db.memActivityDao().insertMemActivity(activity);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void updateMemActivity(final CancerDatabase db, MemActivity activity) {
        db.beginTransaction();
        try {
            db.memActivityDao().updateMemActivity(activity);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
