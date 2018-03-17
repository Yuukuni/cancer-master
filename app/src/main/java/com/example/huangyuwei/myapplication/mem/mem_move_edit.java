package com.example.huangyuwei.myapplication.mem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.huangyuwei.myapplication.R;
import com.example.huangyuwei.myapplication.database.CancerDatabase;
import com.example.huangyuwei.myapplication.database.FoodTime;
import com.example.huangyuwei.myapplication.database.MoveTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.huangyuwei.myapplication.MainActivity.cb;
import static com.example.huangyuwei.myapplication.MainActivity.getContext;

public class mem_move_edit extends Fragment{
    private EditText fromDateEtxt;
    private EditText fromTimeEtxt;
    private TextView tablelabel;
    private TableLayout movetable;
    private DatePickerDialog fromDatePickerDialog;
    private TimePickerDialog fromTimePickerDialog;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat datedbFormatter;
    private SimpleDateFormat timeFormatter;
    private SimpleDateFormat timedbFormatter;
    //private Button saveFood;
    private Button addMove;
    private Date currentDateView;
    private List<MoveTime> movedays;
    public mem_move_edit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mem_move_edit, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.TAIWAN);
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.TAIWAN);
        datedbFormatter = new SimpleDateFormat("yyyyMMdd");
        timedbFormatter = new SimpleDateFormat("HHmm",Locale.TAIWAN);
        findViewsById();

        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        setDateField();
        fromDateEtxt.setText(dateFormatter.format(Calendar.getInstance().getTime()));

        tablelabel.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        movedays=CancerDatabase.getInMemoryDatabase(getContext()).moveTimeDao().getAllMoveTime();
        currentDateView=Calendar.getInstance().getTime();
        for (int i = 0; i <movedays.size(); i++) {
            if(Integer.parseInt(datedbFormatter.format(currentDateView)) == movedays.get(i).date_id) {
                Log.d("TAG", movedays.get(i).time + " " + movedays.get(i).MoveName + " " + movedays.get(i).duration + " " + movedays.get(i).calories);
                addTableRow(movetable, movedays.get(i));
            }
        }




        addMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.moveday_dialog,null);
//layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.

                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                fromTimeEtxt = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
                fromTimeEtxt.setInputType(InputType.TYPE_NULL);
                fromTimeEtxt.requestFocus();
                setTimeField();
                fromTimeEtxt.setText(timeFormatter.format(Calendar.getInstance().getTime()));
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText datetext = (EditText) getView().findViewById(R.id.EditTextDate);
                        String date = datetext.getText().toString();
                        Date datedb = Calendar.getInstance().getTime(); //initialize
                        try {
                            datedb = dateFormatter.parse(date);
                        } catch (ParseException e){

                        }

                        String dateindb=datedbFormatter.format(datedb);

                        final EditText timetext = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
                        String time = timetext.getText().toString();
                        Date timedb = Calendar.getInstance().getTime(); //initialize
                        try {
                            timedb = timeFormatter.parse(time);
                        } catch (ParseException e){ }


                        String timeindb=timedbFormatter.format(timedb);


                        final EditText movetext = (EditText) dialoglayout.findViewById(R.id.EditTextMove);
                        String move = movetext.getText().toString();

                        final EditText moveDuration = (EditText) dialoglayout.findViewById(R.id.EditTextDuration);
                        String duration = moveDuration.getText().toString();

                        final EditText moveCalories = (EditText) dialoglayout.findViewById(R.id.EditTextMoveCalories);
                        String calories = moveCalories.getText().toString();

                        MoveTime mtime = new MoveTime();

                        mtime.date_id=Integer.parseInt(dateindb);
                        mtime.time=Integer.parseInt(timeindb);
                        mtime.MoveName=move;
                        mtime.duration=Double.parseDouble(duration);
                        mtime.calories=Double.parseDouble(calories);
                        Log.d("TAG",dateindb+" "+timeindb+ " "+move+" "+duration+" "+calories);
                        //addFoodDay(CancerDatabase.getInMemoryDatabase(getContext()),day);
                        boolean unique=true;
                        for(int i=0;i<movedays.size();i++){
                            if(movedays.get(i).time==Integer.parseInt(timeindb) && movedays.get(i).date_id==Integer.parseInt(dateindb))
                                unique=false;
                        }
                        if(unique) {
                            addMoveTime(cb, mtime);
                            refreshTable();
                            movetext.setText("");
                            moveDuration.setText("");
                            moveCalories.setText("");
                            dialog.dismiss();
                        }
                        else{
                            final AlertDialog d = new AlertDialog.Builder(getContext())
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setMessage( "同一個時間不能一直輸入運動哦" )
                                    .setTitle("運動太多了吧")
                                    .create();
                            d.show();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.setView(dialoglayout).create();
                dialog.show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addTableRow(TableLayout tl, final MoveTime movedata){
        int time = movedata.time;
        String move = movedata.MoveName;
        Double duration = movedata.duration;
        Double calories = movedata.calories;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        TableRow tr = (TableRow)inflater.inflate(R.layout.table_row_mem_move, tl, false);

        // Add First Column
        TextView textViewTime = (TextView)tr.findViewById(R.id.TextViewTime);
        Calendar dcal= Calendar.getInstance();
        dcal.set(dcal.get(Calendar.YEAR), dcal.get(Calendar.MONTH), dcal.get(Calendar.DAY_OF_MONTH),time/100,time%100);
        textViewTime.setText(timeFormatter.format(dcal.getTime()));

        // Add the 3rd Column
        TextView textViewMove = (TextView)tr.findViewById(R.id.TextViewMove);
        textViewMove.setText(move);

        TextView textViewDuration = (TextView)tr.findViewById(R.id.TextViewDuration);
        textViewDuration.setText(duration.toString());

        TextView textViewCalories = (TextView)tr.findViewById(R.id.TextViewCalories);
        textViewCalories.setText(calories.toString());


        final int dtime= time;
        final String s = "確定刪除"+dtime/100+":"+ dtime%100+"的"+move+"嗎？";
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialoglayout = inflater.inflate(R.layout.moveday_dialog,null);
//layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.

                //Building dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                fromTimeEtxt = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
                fromTimeEtxt.setInputType(InputType.TYPE_NULL);
                fromTimeEtxt.requestFocus();
                setTimeField();
                Calendar cl=Calendar.getInstance();
                cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH),movedata.time/100,movedata.time%100);
                fromTimeEtxt.setText(timeFormatter.format(cl.getTime()));
                EditText oldmovetext = (EditText) dialoglayout.findViewById(R.id.EditTextMove);
                EditText oldmoveduration = (EditText) dialoglayout.findViewById(R.id.EditTextDuration);
                EditText oldmoveCalories = (EditText) dialoglayout.findViewById(R.id.EditTextMoveCalories);
                oldmovetext.setText(movedata.MoveName);
                oldmoveduration.setText(movedata.duration.toString());
                oldmoveCalories.setText(movedata.calories.toString());

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText datetext = (EditText) getView().findViewById(R.id.EditTextDate);
                        String date = datetext.getText().toString();
                        Date datedb = Calendar.getInstance().getTime(); //initialize
                        try {
                            datedb = dateFormatter.parse(date);
                        } catch (ParseException e){

                        }

                        String dateindb=datedbFormatter.format(datedb);

                        final EditText timetext = (EditText) dialoglayout.findViewById(R.id.EditTextTime);
                        String time = timetext.getText().toString();
                        Date timedb = Calendar.getInstance().getTime(); //initialize
                        //Log.d("TAG",time.toString());
                        Log.d("TAG",timedb.toString());
                        try {
                            timedb = timeFormatter.parse(time);
                        } catch (ParseException e){

                        }

                        Log.d("TAG",timedb.toString());

                        String timeindb=timedbFormatter.format(timedb);


                        final EditText movetext = (EditText) dialoglayout.findViewById(R.id.EditTextMove);
                        String move = movetext.getText().toString();

                        final EditText moveDuration = (EditText) dialoglayout.findViewById(R.id.EditTextDuration);
                        String duration = moveDuration.getText().toString();

                        final EditText moveCalories = (EditText) dialoglayout.findViewById(R.id.EditTextMoveCalories);;
                        String calories = moveCalories.getText().toString();

                        MoveTime mtime = new MoveTime();

                        mtime.date_id=Integer.parseInt(dateindb);
                        mtime.time=Integer.parseInt(timeindb);
                        mtime.MoveName=move;
                        mtime.duration=Double.parseDouble(duration);
                        mtime.calories=Double.parseDouble(calories);
                        Log.d("TAG",dateindb+" "+timeindb+ " "+move+" "+duration+" "+calories);
                        //addFoodDay(CancerDatabase.getInMemoryDatabase(getContext()),day);
                        boolean unique=true;
                        for(int i=0;i<movedays.size();i++){
                            if(movedays.get(i) != movedata) {
                                if (movedays.get(i).date_id == mtime.date_id && movedays.get(i).time == mtime.time)
                                    unique = false;
                            }
                        }
                        if(unique) {
                            updateMoveTime(cb, mtime);
                            refreshTable();
                            movetext.setText("");
                            moveDuration.setText("");
                            moveCalories.setText("");
                        }
                        else{
                            final AlertDialog d = new AlertDialog.Builder(getContext())
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setMessage( "你這時間已經有紀錄過東西哦！" )
                                    .setTitle("重複了哦！")
                                    .create();
                            d.show();
                        }
                        dialog.dismiss();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog d = builder.setView(dialoglayout).create();
                d.show();

            }

        });

        tr.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog d = new AlertDialog.Builder(getContext())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteMoveTime(cb, movedata);
                                refreshTable();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setMessage( s )
                        .setTitle("刪除" + dtime/100+":"+ dtime%100 )
                        .create();
                d.show();
                return false;
            }
        });


        tl.addView(tr);
    }



    private MoveTime addMoveTime(final CancerDatabase db, MoveTime time) {
        db.beginTransaction();
        try {
            db.moveTimeDao().insertMoveTime(time);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return time;
    }

    private MoveTime deleteMoveTime(final CancerDatabase db, MoveTime time) {
        db.beginTransaction();
        try {
            db.moveTimeDao().delete(time);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return time;
    }

    private MoveTime updateMoveTime(final CancerDatabase db, MoveTime time) {
        db.beginTransaction();
        try {
            db.moveTimeDao().updateMoveTime(time);
            db.setTransactionSuccessful();
            Log.d("TAG","success123");
        } finally {
            db.endTransaction();
        }
        return time;
    }

    private void findViewsById() {
        tablelabel = (TextView) getActivity().findViewById(R.id.move_day_label);
        movetable = (TableLayout) getActivity().findViewById(R.id.move_daytable);

        addMove=(Button)getView().findViewById(R.id.addMoveDay);
        fromDateEtxt = (EditText) getView().findViewById(R.id.EditTextDate);
    }

    private void setTimeField() {

        fromTimeEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromTimePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();

        fromTimePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH),hourOfDay,minute);
                fromTimeEtxt.setText(timeFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MONTH),true);

    }

    private void setDateField() {
        fromDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newCl = Calendar.getInstance();
                newCl.set(year, monthOfYear, dayOfMonth);
                currentDateView=newCl.getTime();
                fromDateEtxt.setText(dateFormatter.format(currentDateView.getTime()));
                tablelabel.setText(dateFormatter.format(currentDateView.getTime()));
                refreshTable();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshTable(){
        int count = movetable.getChildCount();
        for (int i = 2; i < count; i++) {
            View child = movetable.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        movedays.clear();
        movedays=CancerDatabase.getInMemoryDatabase(getContext()).moveTimeDao().getAllMoveTime();
        for (int i = 0; i <movedays.size(); i++) {
            if(Integer.parseInt(datedbFormatter.format(currentDateView)) == movedays.get(i).date_id) {
                Log.d("TAG", movedays.get(i).time + " " + movedays.get(i).MoveName + " " + movedays.get(i).duration + " " + movedays.get(i).calories);
                addTableRow(movetable, movedays.get(i));
            }
        }
    }

}