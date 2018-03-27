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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.huangyuwei.myapplication.R;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.huangyuwei.myapplication.MainActivity.getContext;

public class mem_activity_edit extends AppCompatActivity {

    private final int REQUEST_CODE_PLACEPICKER = 1;

    private Context context;
    private mem_activity_edit instance;

    private EditText activity_name_input;
    private EditText activity_from_date_input, activity_from_time_input;
    private EditText activity_to_date_input, activity_to_time_input;
    private TextView activity_location_name_input, activity_location_address_input;
    private EditText activity_remark_input;
    private Button btn_select_location;
    private Button btn_confirm;

    private String activity_name;
    private String activity_from_date, activity_from_time;
    private String activity_to_date, activity_to_time;
    private String acitvity_location_name, activity_location_address;
    private String activity_remark;

    private DatePickerDialog fromDateDialog;
    private DatePickerDialog toDateDialog;
    private TimePickerDialog fromTimeDialog;
    private TimePickerDialog toTimeDialog;

    private Date currentDate;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_activity_edit);
        context = getContext();
        init();
        setFromDateField();
        setToDateField();
        setFromTimeField();
        setToTimeField();
        setSelectLocationButton();
        setConfirmButton();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void init() {
        activity_name_input = (EditText) findViewById(R.id.activity_name_input);
        activity_from_date_input = (EditText) findViewById(R.id.activity_from_date_input);
        activity_from_time_input = (EditText) findViewById(R.id.activity_from_time_input);
        activity_to_date_input = (EditText) findViewById(R.id.activity_to_date_input);
        activity_to_time_input = (EditText) findViewById(R.id.activity_to_time_input);
        activity_location_name_input = (TextView) findViewById(R.id.activity_location_name_input);
        activity_location_address_input = (TextView) findViewById(R.id.activity_location_address_input);
        activity_remark_input = (EditText) findViewById(R.id.activity_remark_input);
        btn_select_location = (Button) findViewById(R.id.btn_select_location);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);

        activity_name = new String();
        activity_from_date = new String();
        activity_from_time = new String();
        activity_to_date = new String();
        activity_to_time = new String();
        acitvity_location_name = new String();
        activity_location_address = new String();
        activity_remark = new String();

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.TAIWAN);
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

    private void setConfirmButton() {
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    void getData() {
        activity_name = activity_name_input.getText().toString();
        activity_from_date = activity_from_date_input.getText().toString();
        activity_from_time = activity_from_time_input.getText().toString();
        activity_to_date = activity_to_date_input.getText().toString();
        activity_to_time = activity_to_time_input.getText().toString();
        acitvity_location_name = activity_location_name_input.getText().toString();
        activity_location_address = activity_location_address_input.getText().toString();
        activity_remark = activity_remark_input.getText().toString();
    }
}
