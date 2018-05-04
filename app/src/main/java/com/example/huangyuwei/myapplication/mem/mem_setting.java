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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.database.CancerDatabase;
import com.example.huangyuwei.myapplication.database.MemActivity;
import com.example.huangyuwei.myapplication.database.MemSetting;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.huangyuwei.myapplication.MainActivity.cb;

public class mem_setting extends AppCompatActivity {
    private static final String USED_STATE_ON = "ON";
    private static final String USED_STATE_OFF = "OFF";

    private static final ArrayList<String> FREQUENCY = new ArrayList<String>() {{
        add("每天");
        add("每週一");
        add("每週二");
        add("每週三");
        add("每週四");
        add("每週五");
        add("每週六");
        add("每週日");
    }};

    private Switch physicalSwitch;
    private TextView physicalSwitchState;
    private EditText physicalTimeInput;
    private Spinner physicalFrequencyInput;
    private boolean physicalUsed;
    private String physicalUsedState;
    private int physicalTimeHour;
    private int physicalTimeMinute;
    private int physicalFrequencySelect;

    private Switch foodSwitch;
    private TextView foodSwitchState;
    private CheckBox foodTimeCheckbox_1, foodTimeCheckbox_2, foodTimeCheckbox_3;
    private EditText foodTimeInput_1, foodTimeInput_2, foodTimeInput_3;
    private Spinner foodFrequencyInput;
    private boolean foodUsed;
    private String foodUsedState;
    private boolean foodTimeUsed_1, foodTimeUsed_2, foodTimeUsed_3;
    private int foodTimeHour_1, foodTimeHour_2, foodTimeHour_3;
    private int foodTimeMinute_1, foodTimeMinute_2, foodTimeMinute_3;
    private int foodFrequencySelect;

    private Switch moveSwitch;
    private TextView moveSwitchState;
    private CheckBox moveTimeCheckbox_1, moveTimeCheckbox_2;
    private EditText moveTimeInput_1, moveTimeInput_2;
    private Spinner moveFrequencyInput;
    private boolean moveUsed;
    private String moveUsedState;
    private boolean moveTimeUsed_1, moveTimeUsed_2;
    private int moveTimeHour_1, moveTimeHour_2;
    private int moveTimeMinute_1, moveTimeMinute_2;
    private int moveFrequencySelect;

    private Switch moodSwitch;
    private TextView moodSwitchState;
    private EditText moodTimeInput;
    private Spinner moodFrequencyInput;
    private boolean moodUsed;
    private String moodUsedState;
    private int moodTimeHour;
    private int moodTimeMinute;
    private int moodFrequencySelect;

    private Button btn_save;
    private Button btn_cancel;

    private SimpleDateFormat timeFormatter;
    private TimePickerDialog physicalTimePicker;
    private TimePickerDialog foodTimePicker_1, foodTimePicker_2, foodTimePicker_3;
    private TimePickerDialog moveTimePicker_1, moveTimePicker_2;
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

        memSetting.physicalUsed = false;
        memSetting.foodUsed = false;
        memSetting.moveUsed = false;
        memSetting.moodUsed = false;

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
        physicalUsed = memSetting.physicalUsed;
        foodUsed = memSetting.foodUsed;
        moveUsed = memSetting.moveUsed;
        moodUsed = memSetting.moodUsed;

        physicalUsedState = physicalUsed ? USED_STATE_ON : USED_STATE_OFF;
        foodUsedState = foodUsed ? USED_STATE_ON : USED_STATE_OFF;
        moveUsedState = moveUsed ? USED_STATE_ON : USED_STATE_OFF;
        moodUsedState = moodUsed ? USED_STATE_ON : USED_STATE_OFF;

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
        physicalSwitch = (Switch) findViewById(R.id.physicalSwitch);
        foodSwitch = (Switch) findViewById(R.id.foodSwitch);
        moveSwitch = (Switch) findViewById(R.id.moveSwitch);
        moodSwitch = (Switch) findViewById(R.id.moodSwitch);

        physicalSwitch.setChecked(physicalUsed);
        foodSwitch.setChecked(foodUsed);
        moveSwitch.setChecked(moveUsed);
        moodSwitch.setChecked(moodUsed);

        physicalSwitchState = (TextView) findViewById(R.id.physicalSwitchState);
        foodSwitchState = (TextView) findViewById(R.id.foodSwitchState);
        moveSwitchState = (TextView) findViewById(R.id.moveSwitchState);
        moodSwitchState = (TextView) findViewById(R.id.moodSwitchState);

        physicalSwitchState.setText(physicalUsedState);
        foodSwitchState.setText(foodUsedState);
        moveSwitchState.setText(moveUsedState);
        moodSwitchState.setText(moodUsedState);

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FREQUENCY);

        physicalFrequencyInput.setAdapter(adapter);
        foodFrequencyInput.setAdapter(adapter);
        moveFrequencyInput.setAdapter(adapter);
        moodFrequencyInput.setAdapter(adapter);

        physicalFrequencyInput.setSelection(physicalFrequencySelect);
        foodFrequencyInput.setSelection(foodFrequencySelect);
        moveFrequencyInput.setSelection(moveFrequencySelect);
        moodFrequencyInput.setSelection(moodFrequencySelect);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
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
        final Calendar physicalTime = Calendar.getInstance();
        physicalTime.set(physicalTime.get(Calendar.YEAR), physicalTime.get(Calendar.MONTH), physicalTime.get(Calendar.DAY_OF_MONTH), physicalTimeHour, physicalTimeMinute);

        physicalTimePicker = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newPhysicalTime = Calendar.getInstance();
                newPhysicalTime.set(newPhysicalTime.get(Calendar.YEAR), newPhysicalTime.get(Calendar.MONTH), newPhysicalTime.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                physicalTimeInput.setText(timeFormatter.format(newPhysicalTime.getTime()));
                physicalTimeHour = hourOfDay;
                physicalTimeMinute = minute;
            }
        }, physicalTimeHour, physicalTimeMinute, true);

        physicalTimeInput.setInputType(InputType.TYPE_NULL);
        physicalTimeInput.setText(timeFormatter.format(physicalTime.getTime()));
        physicalTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                physicalTimePicker.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFoodTimePicker_1() {
        final Calendar foodTime_1 = Calendar.getInstance();
        foodTime_1.set(foodTime_1.get(Calendar.YEAR), foodTime_1.get(Calendar.MONTH), foodTime_1.get(Calendar.DAY_OF_MONTH), foodTimeHour_1, foodTimeMinute_1);

        foodTimePicker_1 = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newFoodTime_1 = Calendar.getInstance();
                newFoodTime_1.set(newFoodTime_1.get(Calendar.YEAR), newFoodTime_1.get(Calendar.MONTH), newFoodTime_1.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                foodTimeInput_1.setText(timeFormatter.format(newFoodTime_1.getTime()));
                foodTimeHour_1 = hourOfDay;
                foodTimeMinute_1 = minute;
            }
        }, foodTimeHour_1, foodTimeMinute_1, true);

        foodTimeInput_1.setInputType(InputType.TYPE_NULL);
        foodTimeInput_1.setText(timeFormatter.format(foodTime_1.getTime()));
        foodTimeInput_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodTimePicker_1.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFoodTimePicker_2() {
        final Calendar foodTime_2 = Calendar.getInstance();
        foodTime_2.set(foodTime_2.get(Calendar.YEAR), foodTime_2.get(Calendar.MONTH), foodTime_2.get(Calendar.DAY_OF_MONTH), foodTimeHour_2 , foodTimeMinute_2);

        foodTimePicker_2 = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newFoodTime_2 = Calendar.getInstance();
                newFoodTime_2.set(newFoodTime_2.get(Calendar.YEAR), newFoodTime_2.get(Calendar.MONTH), newFoodTime_2.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                foodTimeInput_2.setText(timeFormatter.format(newFoodTime_2.getTime()));
                foodTimeHour_2 = hourOfDay;
                foodTimeMinute_2 = minute;
            }
        }, foodTimeHour_2, foodTimeMinute_2, true);

        foodTimeInput_2.setInputType(InputType.TYPE_NULL);
        foodTimeInput_2.setText(timeFormatter.format(foodTime_2.getTime()));
        foodTimeInput_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodTimePicker_2.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFoodTimePicker_3() {
        final Calendar foodTime_3 = Calendar.getInstance();
        foodTime_3.set(foodTime_3.get(Calendar.YEAR), foodTime_3.get(Calendar.MONTH), foodTime_3.get(Calendar.DAY_OF_MONTH), foodTimeHour_3 , foodTimeMinute_3);

        foodTimePicker_3 = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newFoodTime_3 = Calendar.getInstance();
                newFoodTime_3.set(newFoodTime_3.get(Calendar.YEAR), newFoodTime_3.get(Calendar.MONTH), newFoodTime_3.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                foodTimeInput_3.setText(timeFormatter.format(newFoodTime_3.getTime()));
                foodTimeHour_3 = hourOfDay;
                foodTimeMinute_3 = minute;
            }
        }, foodTimeHour_3, foodTimeMinute_3, true);

        foodTimeInput_3.setInputType(InputType.TYPE_NULL);
        foodTimeInput_3.setText(timeFormatter.format(foodTime_3.getTime()));
        foodTimeInput_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodTimePicker_3.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setMoveTimePicker_1() {
        final Calendar moveTime_1 = Calendar.getInstance();
        moveTime_1.set(moveTime_1.get(Calendar.YEAR), moveTime_1.get(Calendar.MONTH), moveTime_1.get(Calendar.DAY_OF_MONTH), moveTimeHour_1 , moveTimeMinute_1);

        moveTimePicker_1 = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newMoveTime_1 = Calendar.getInstance();
                newMoveTime_1.set(newMoveTime_1.get(Calendar.YEAR), newMoveTime_1.get(Calendar.MONTH), newMoveTime_1.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                moveTimeInput_1.setText(timeFormatter.format(newMoveTime_1.getTime()));
                moveTimeHour_1 = hourOfDay;
                moveTimeMinute_1 = minute;
            }
        }, moveTimeHour_1, moveTimeMinute_1, true);

        moveTimeInput_1.setInputType(InputType.TYPE_NULL);
        moveTimeInput_1.setText(timeFormatter.format(moveTime_1.getTime()));
        moveTimeInput_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTimePicker_1.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setMoveTimePicker_2() {
        final Calendar moveTime_2 = Calendar.getInstance();
        moveTime_2.set(moveTime_2.get(Calendar.YEAR), moveTime_2.get(Calendar.MONTH), moveTime_2.get(Calendar.DAY_OF_MONTH), moveTimeHour_2 , moveTimeMinute_2);

        moveTimePicker_2 = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newMoveTime_2 = Calendar.getInstance();
                newMoveTime_2.set(newMoveTime_2.get(Calendar.YEAR), newMoveTime_2.get(Calendar.MONTH), newMoveTime_2.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                moveTimeInput_2.setText(timeFormatter.format(newMoveTime_2.getTime()));
                moveTimeHour_2 = hourOfDay;
                moveTimeMinute_2 = minute;
            }
        }, moveTimeHour_2, moveTimeMinute_2, true);

        moveTimeInput_2.setInputType(InputType.TYPE_NULL);
        moveTimeInput_2.setText(timeFormatter.format(moveTime_2.getTime()));
        moveTimeInput_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTimePicker_2.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setMoodTimePicker() {
        final Calendar moodTime = Calendar.getInstance();
        moodTime.set(moodTime.get(Calendar.YEAR), moodTime.get(Calendar.MONTH), moodTime.get(Calendar.DAY_OF_MONTH), moodTimeHour , moodTimeMinute);

        moodTimePicker = new TimePickerDialog(mem_setting.this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newMoodTime = Calendar.getInstance();
                newMoodTime.set(newMoodTime.get(Calendar.YEAR), newMoodTime.get(Calendar.MONTH), newMoodTime.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                moodTimeInput.setText(timeFormatter.format(newMoodTime.getTime()));
                moodTimeHour = hourOfDay;
                moodTimeMinute = minute;
            }
        }, moodTimeHour, moodTimeMinute, true);

        moodTimeInput.setInputType(InputType.TYPE_NULL);
        moodTimeInput.setText(timeFormatter.format(moodTime.getTime()));
        moodTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moodTimePicker.show();
            }
        });
    }

    private void setListeners() {
        physicalSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                physicalUsed = isChecked;
                if(isChecked) {
                    physicalSwitchState.setText(USED_STATE_ON);
                }
                else {
                    physicalSwitchState.setText(USED_STATE_OFF);
                }
            }
        });
        foodSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                foodUsed = isChecked;
                if(isChecked) {
                    foodSwitchState.setText(USED_STATE_ON);
                }
                else {
                    foodSwitchState.setText(USED_STATE_OFF);
                }
            }
        });
        moveSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                moveUsed = isChecked;
                if(isChecked) {
                    moveSwitchState.setText(USED_STATE_ON);
                }
                else {
                    moveSwitchState.setText(USED_STATE_OFF);
                }
            }
        });
        moodSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                moodUsed = isChecked;
                if(isChecked) {
                    moodSwitchState.setText(USED_STATE_ON);
                }
                else {
                    moodSwitchState.setText(USED_STATE_OFF);
                }
            }
        });

        foodTimeCheckbox_1.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                foodTimeUsed_1 = isChecked;
            }
        });
        foodTimeCheckbox_2.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                foodTimeUsed_2 = isChecked;
            }
        });
        foodTimeCheckbox_3.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                foodTimeUsed_3 = isChecked;
            }
        });
        moveTimeCheckbox_1.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                moveTimeUsed_1 = isChecked;
            }
        });
        moveTimeCheckbox_2.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                moveTimeUsed_2 = isChecked;
            }
        });

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

        btn_save.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSetting();
                finish();
            }
        });

        btn_cancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveSetting() {
        memSetting.physicalUsed = physicalUsed;
        memSetting.foodUsed = foodUsed;
        memSetting.moveUsed = moveUsed;
        memSetting.moodUsed = moodUsed;

        memSetting.foodTimeUsed_1 = foodTimeUsed_1;
        memSetting.foodTimeUsed_2 = foodTimeUsed_2;
        memSetting.foodTimeUsed_3 = foodTimeUsed_3;
        memSetting.moveTimeUsed_1 = moveTimeUsed_1;
        memSetting.moveTimeUsed_2 = moveTimeUsed_2;

        memSetting.physicalTimeHour = physicalTimeHour;
        memSetting.foodTimeHour_1 = foodTimeHour_1;
        memSetting.foodTimeHour_2 = foodTimeHour_2;
        memSetting.foodTimeHour_3 = foodTimeHour_3;
        memSetting.moveTimeHour_1 = moveTimeHour_1;
        memSetting.moveTimeHour_2 = moveTimeHour_2;
        memSetting.moodTimeHour = moodTimeHour;

        memSetting.physicalTimeMinute = physicalTimeMinute;
        memSetting.foodTimeMinute_1 = foodTimeMinute_1;
        memSetting.foodTimeMinute_2 = foodTimeMinute_2;
        memSetting.foodTimeMinute_3 = foodTimeMinute_3;
        memSetting.moveTimeMinute_1 = moveTimeMinute_1;
        memSetting.moveTimeMinute_2 = moveTimeMinute_2;
        memSetting.moodTimeMinute = moodTimeMinute;

        memSetting.physicalFrequencySelect = physicalFrequencySelect;
        memSetting.foodFrequencySelect = foodFrequencySelect;
        memSetting.moveFrequencySelect = moveFrequencySelect;
        memSetting.moodFrequencySelect = moodFrequencySelect;

        updateMemSetting(cb, memSetting);
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

    private void updateMemSetting(final CancerDatabase db, MemSetting setting) {
        db.beginTransaction();
        try {
            db.memSettingDao().updateMemSetting(setting);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
