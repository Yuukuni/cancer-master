package com.example.huangyuwei.myapplication.mem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.database.CancerDatabase;
import com.example.huangyuwei.myapplication.database.MemActivity;
import com.example.huangyuwei.myapplication.database.MemSetting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.huangyuwei.myapplication.MainActivity.cb;

public class mem_setting extends AppCompatActivity {

    private boolean foodTimeUsed_1;
    private boolean foodTimeUsed_2;
    private boolean foodTimeUsed_3;
    private boolean moveTimeUsed_1;
    private boolean moveTimeUsed_2;

    private EditText physicalTimeInput;
    private Spinner physicalFrequencyInput;
    private int physicalTimeHour;
    private int physicalTimeMinute;
    private int physicalFrequencySelect;

    private CheckBox foodTimeCheckbox_1, foodTimeCheckbox_2, foodTimeCheckbox_3;
    private EditText foodTimeInput_1, foodTimeInput_2, foodTimeInput_3;
    private Spinner foodFrequencyInput;
    private int foodTimeHour_1, foodTimeHour_2, foodTimeHour_3;
    private int foodTimeMinute_1, foodTimeMinute_2, foodTimeMinute_3;
    private int foodFrequencySelect;

    private CheckBox moveTimeCheckbox_1, moveTimeCheckbox_2;
    private EditText moveTimeInput_1, moveTimeInput_2;
    private Spinner moveFrequencyInput;
    private int moveTimeHour_1, moveTimeHour_2;
    private int moveTimeMinute_1, moveTimeMinute_2;
    private int moveFrequencySelect;

    private EditText moodTimeInput;
    private Spinner moodFrequencyInput;
    private int moodTimeHour;
    private int moodTimeMinute;
    private int moodFrequencySelect;

    private SimpleDateFormat timeFormatter;
    private TimePickerDialog physicalTimePicker;
    private TimePickerDialog foodTimePicker_1;
    private TimePickerDialog foodTimePicker_2;
    private TimePickerDialog foodTimePicker_3;
    private TimePickerDialog moveTimePicker_1;
    private TimePickerDialog moveTimePicker_2;
    private TimePickerDialog moodTimePicker;

    private MemSetting memSetting;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_setting);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {
        setMemSetting();
        setData();
        setViews();
        setTimePickers();
        setListeners();
    }

    private void setMemSetting() {
        List<MemSetting> memSettings = cb.memSettingDao().getAllMemSetting();
        if(memSettings.size() == 0) {
            MemSetting memSetting = new MemSetting();
            initMemSetting(memSetting);
            addMemSetting(cb, memSetting);
        }
        memSetting = memSettings.get(0);
    }

    private void initMemSetting(MemSetting memSetting) {
        memSetting.primaryKey = 1;

        memSetting.foodTimeUsed_1 = true;
        memSetting.foodTimeUsed_2 = true;
        memSetting.foodTimeUsed_3 = true;
        memSetting.moveTimeUsed_1 = true;
        memSetting.moveTimeUsed_2 = true;

        memSetting.physicalTimeHour = 0;
        memSetting.foodTimeHour_1 = 0;
        memSetting.foodTimeHour_2 = 0;
        memSetting.foodTimeHour_3 = 0;
        memSetting.moveTimeHour_1 = 0;
        memSetting.moveTimeHour_2 = 0;
        memSetting.moodTimeHour = 0;

        memSetting.physicalTimeMinute = 0;
        memSetting.foodTimeMinute_1 = 0;
        memSetting.foodTimeMinute_2 = 0;
        memSetting.foodTimeMinute_3 = 0;
        memSetting.moveTimeMinute_1 = 0;
        memSetting.moveTimeMinute_2 = 0;
        memSetting.moodTimeMinute = 0;

        memSetting.physicalFrequencySelect = 0;
        memSetting.foodFrequencySelect = 0;
        memSetting.moveFrequencySelect = 0;
        memSetting.moodFrequencySelect = 0;
    }

    private void setData() {
        foodTimeUsed_1 = memSetting.foodTimeUsed_1;
        foodTimeUsed_2 = memSetting.foodTimeUsed_2;
        foodTimeUsed_3 = memSetting.foodTimeUsed_3;
        moveTimeUsed_1 = memSetting.moveTimeUsed_1;
        moveTimeUsed_2 = memSetting.moveTimeUsed_2;

        physicalTimeHour = memSetting.physicalTimeHour;
        foodTimeHour_1 = memSetting.foodTimeHour_1;
        foodTimeHour_2 = memSetting.foodTimeHour_2;
        foodTimeHour_3 = memSetting.foodTimeHour_3;
        moveTimeHour_1 = memSetting.moveTimeHour_1;
        moveTimeHour_2 = memSetting.moveTimeHour_2;
        moodTimeHour = memSetting.moodTimeHour;

        physicalTimeMinute = memSetting.physicalTimeMinute;
        foodTimeMinute_1 = memSetting.foodTimeMinute_1;
        foodTimeMinute_2 = memSetting.foodTimeMinute_2;
        foodTimeMinute_3 = memSetting.foodTimeMinute_3;
        moveTimeMinute_1 = memSetting.moveTimeMinute_1;
        moveTimeMinute_2 = memSetting.moveTimeMinute_2;
        moodTimeMinute = memSetting.moodTimeMinute;

        physicalFrequencySelect = memSetting.physicalFrequencySelect;
        foodFrequencySelect = memSetting.foodFrequencySelect;
        moveFrequencySelect = memSetting.moveFrequencySelect;
        moodFrequencySelect = memSetting.moodFrequencySelect;
    }

    private void setViews() {
        physicalTimeInput = (EditText) findViewById(R.id.physicalTimeInput);
        foodTimeInput_1 = (EditText) findViewById(R.id.foodTimeInput_1);
        foodTimeInput_2 = (EditText) findViewById(R.id.foodTimeInput_2);
        foodTimeInput_3 = (EditText) findViewById(R.id.foodTimeInput_3);
        moveTimeInput_1 = (EditText) findViewById(R.id.moveTimeInput_1);
        moveTimeInput_2 = (EditText) findViewById(R.id.moveTimeInput_2);
        moodTimeInput = (EditText) findViewById(R.id.moodTimeInput);

        foodTimeCheckbox_1 = (CheckBox) findViewById(R.id.foodTimeCheckbox_1);
        foodTimeCheckbox_2 = (CheckBox) findViewById(R.id.foodTimeCheckbox_2);
        foodTimeCheckbox_3 = (CheckBox) findViewById(R.id.foodTimeCheckbox_3);
        moveTimeCheckbox_1 = (CheckBox) findViewById(R.id.moveTimeCheckbox_1);
        moveTimeCheckbox_2 = (CheckBox) findViewById(R.id.moveTimeCheckbox_2);

        foodTimeCheckbox_1.setChecked(foodTimeUsed_1);
        foodTimeCheckbox_2.setChecked(foodTimeUsed_2);
        foodTimeCheckbox_3.setChecked(foodTimeUsed_3);
        moveTimeCheckbox_1.setChecked(moveTimeUsed_1);
        moveTimeCheckbox_2.setChecked(moveTimeUsed_2);

        physicalFrequencyInput = (Spinner) findViewById(R.id.physicalFrequencyInput);
        foodFrequencyInput = (Spinner) findViewById(R.id.foodFrequencyInput);
        moveFrequencyInput = (Spinner) findViewById(R.id.moveFrequencyInput);
        moodFrequencyInput = (Spinner) findViewById(R.id.moodFrequencyInput);

        physicalFrequencyInput.setSelection(physicalFrequencySelect);
        foodFrequencyInput.setSelection(foodFrequencySelect);
        moveFrequencyInput.setSelection(moveFrequencySelect);
        moodFrequencyInput.setSelection(moodFrequencySelect);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setTimePickers() {
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.TAIWAN);
        setPhysicalTimePicker();
        setFoodTimePicker_1();
        setFoodTimePicker_2();
        setFoodTimePicker_3();
        setMoveTimePicker_1();
        setMoveTimePicker_2();
        setMoodTimePicker();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setPhysicalTimePicker() {
        final Calendar setTime = Calendar.getInstance();
        setTime.set(setTime.get(Calendar.YEAR), setTime.get(Calendar.MONTH), setTime.get(Calendar.DAY_OF_MONTH), physicalTimeHour , physicalTimeMinute);

        physicalTimePicker = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                physicalTimeInput.setText(timeFormatter.format(setTime.getTime()));
            }
        }, physicalTimeHour, physicalTimeMinute, true);

        physicalTimeInput.setInputType(InputType.TYPE_NULL);
        physicalTimeInput.setText(timeFormatter.format(setTime.getTime()));
        physicalTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                physicalTimePicker.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFoodTimePicker_1() {
        final Calendar setTime = Calendar.getInstance();
        setTime.set(setTime.get(Calendar.YEAR), setTime.get(Calendar.MONTH), setTime.get(Calendar.DAY_OF_MONTH), foodTimeHour_1 , foodTimeMinute_1);

        foodTimePicker_1 = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                foodTimeInput_1.setText(timeFormatter.format(setTime.getTime()));
            }
        }, foodTimeHour_1, foodTimeMinute_1, true);

        foodTimeInput_1.setInputType(InputType.TYPE_NULL);
        foodTimeInput_1.setText(timeFormatter.format(setTime.getTime()));
        foodTimeInput_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodTimePicker_1.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFoodTimePicker_2() {
        final Calendar setTime = Calendar.getInstance();
        setTime.set(setTime.get(Calendar.YEAR), setTime.get(Calendar.MONTH), setTime.get(Calendar.DAY_OF_MONTH), foodTimeHour_2 , foodTimeMinute_2);

        foodTimePicker_2 = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                foodTimeInput_2.setText(timeFormatter.format(setTime.getTime()));
            }
        }, foodTimeHour_2, foodTimeMinute_2, true);

        foodTimeInput_2.setInputType(InputType.TYPE_NULL);
        foodTimeInput_2.setText(timeFormatter.format(setTime.getTime()));
        foodTimeInput_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodTimePicker_2.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFoodTimePicker_3() {
        final Calendar setTime = Calendar.getInstance();
        setTime.set(setTime.get(Calendar.YEAR), setTime.get(Calendar.MONTH), setTime.get(Calendar.DAY_OF_MONTH), foodTimeHour_3 , foodTimeMinute_3);

        foodTimePicker_3 = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                foodTimeInput_3.setText(timeFormatter.format(setTime.getTime()));
            }
        }, foodTimeHour_3, foodTimeMinute_3, true);

        foodTimeInput_3.setInputType(InputType.TYPE_NULL);
        foodTimeInput_3.setText(timeFormatter.format(setTime.getTime()));
        foodTimeInput_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodTimePicker_3.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setMoveTimePicker_1() {
        final Calendar setTime = Calendar.getInstance();
        setTime.set(setTime.get(Calendar.YEAR), setTime.get(Calendar.MONTH), setTime.get(Calendar.DAY_OF_MONTH), moveTimeHour_1 , moveTimeMinute_1);

        moveTimePicker_1 = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                moveTimeInput_1.setText(timeFormatter.format(setTime.getTime()));
            }
        }, moveTimeHour_1, moveTimeMinute_1, true);

        moveTimeInput_1.setInputType(InputType.TYPE_NULL);
        moveTimeInput_1.setText(timeFormatter.format(setTime.getTime()));
        moveTimeInput_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTimePicker_1.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setMoveTimePicker_2() {
        final Calendar setTime = Calendar.getInstance();
        setTime.set(setTime.get(Calendar.YEAR), setTime.get(Calendar.MONTH), setTime.get(Calendar.DAY_OF_MONTH), moveTimeHour_2 , moveTimeMinute_2);

        moveTimePicker_2 = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                moveTimeInput_2.setText(timeFormatter.format(setTime.getTime()));
            }
        }, moveTimeHour_2, moveTimeMinute_2, true);

        moveTimeInput_2.setInputType(InputType.TYPE_NULL);
        moveTimeInput_2.setText(timeFormatter.format(setTime.getTime()));
        moveTimeInput_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTimePicker_2.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setMoodTimePicker() {
        final Calendar setTime = Calendar.getInstance();
        setTime.set(setTime.get(Calendar.YEAR), setTime.get(Calendar.MONTH), setTime.get(Calendar.DAY_OF_MONTH), moodTimeHour , moodTimeMinute);

        moodTimePicker = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                moodTimeInput.setText(timeFormatter.format(setTime.getTime()));
            }
        }, moodTimeHour, moodTimeMinute, true);

        moodTimeInput.setInputType(InputType.TYPE_NULL);
        moodTimeInput.setText(timeFormatter.format(setTime.getTime()));
        moodTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moodTimePicker.show();
            }
        });
    }

    private void setListeners() {
        physicalFrequencyInput.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                physicalFrequencySelect = position;
            }
            public void onNothingSelected(AdapterView arg0) {
            }
        });
        foodFrequencyInput.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                foodFrequencySelect = position;
            }
            public void onNothingSelected(AdapterView arg0) {
            }
        });
        moveFrequencyInput.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                moveFrequencySelect = position;
            }
            public void onNothingSelected(AdapterView arg0) {
            }
        });
        moodFrequencyInput.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                moodFrequencySelect = position;
            }
            public void onNothingSelected(AdapterView arg0) {
            }
        });
    }

    private void update() {

    }

    private void addMemSetting(final CancerDatabase db, MemSetting setting) {
        db.beginTransaction();
        try {
            db.memSettingDao().insertMemSetting(setting);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void updateMemActivity(final CancerDatabase db, MemSetting setting) {
        db.beginTransaction();
        try {
            db.memSettingDao().updateMemSetting(setting);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
