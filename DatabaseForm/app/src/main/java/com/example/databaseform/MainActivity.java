package com.example.databaseform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerFragment.OnInputListener {
    TextView dateSelected, hourStartSelected, hourEndSelected;
    Button input, view;
    Calendar calendar = Calendar.getInstance();
    ConstraintLayout textViews;

    int day = 0;
    int month = 0;
    int year = 0;
    int hour_start = calendar.get(Calendar.HOUR_OF_DAY);
    int minute_start = calendar.get(Calendar.MINUTE);
    int hour_end = hour_start+1;
    int minute_end = minute_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateSelected = findViewById(R.id.date_selected);
        hourStartSelected = findViewById(R.id.time_startSelected);
        hourEndSelected = findViewById(R.id.time_endSelected);

        textViews = findViewById(R.id.textViews);

        textViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment dialog = new TimePickerFragment();
                dialog.show(getSupportFragmentManager(), "TimeFragmentManager");

            }
        });
    }

    protected int getDay(){
        return day;
    }

    protected int getMonth(){
        return month;
    }

    protected int getYear(){
        return year;
    }

    protected int getHourStart(){
        return hour_start;
    }

    protected int getHourEnd(){
        return hour_end;
    }

    protected int getMinuteStart(){
        return minute_start;
    }

    protected int getMinuteEnd(){
        return minute_end;
    }

    //Implemented Interface
    @Override
    public void sendTime(int h_start, int h_end, int m_start, int m_end){
        hour_start = h_start;
        minute_start = m_start;
        hour_end = h_end;
        minute_end = m_end;
    }

    @Override
    public void sendInput(String date, String time_start, String time_end) {
        dateSelected.setText(date);
        hourStartSelected.setText(time_start);
        hourEndSelected.setText(time_end);
    }

    @Override
    public void sendDates(int d, int m, int y) {
        day = d;
        month = m;
        year = y;
    }
}
