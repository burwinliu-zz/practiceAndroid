package com.example.databaseform;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;
import java.util.Locale;

public class TimePickerFragment
        extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface OnInputListener{
        void sendInput(String date, String time_start, String time_end);
        void sendDates(int d, int m, int y);
        void sendTime(int h_start, int h_end, int m_start, int m_end);
    }

    private OnInputListener onInputListener;

    private int dayInt, monthInt, yearInt, h_start, h_end, m_start, m_end;

    private CalendarView calendarView;
    private TimePicker timePicker;
    private Button confirm, deny;
    private String date, time_start, time_end;
    private ToggleButton start, end;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        // Init variables
        View view = inflater.inflate(R.layout.time_dialog, container, false);
        onInputListener = (OnInputListener) getActivity();

        calendarView = view.findViewById(R.id.calendar);
        timePicker = view.findViewById(R.id.time);
        confirm = view.findViewById(R.id.confirm);
        deny = view.findViewById(R.id.cancel);

        start = view.findViewById(R.id.start);
        end = view.findViewById(R.id.end);

        final MainActivity mainActivity = ((MainActivity) this.getActivity());


        // set values
        // calendar set
        // EXPLAINED TO HERE
        try {
            dayInt = mainActivity.getDay();
            monthInt = mainActivity.getMonth();
            yearInt = mainActivity.getYear();

            if(dayInt != 0 || monthInt != 0 || yearInt != 0) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, yearInt);
                calendar.set(Calendar.MONTH, monthInt);
                calendar.set(Calendar.DAY_OF_MONTH, dayInt);

                long milliTime = calendar.getTimeInMillis();
                calendarView.setDate(milliTime, true, true);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd, yyyy");
            date = sdf.format(new Date(calendarView.getDate()));
        }
        catch (Exception NullPointerException) {Log.e("onCreateView", "Null Calendar");}
        //time set
        try {
            String AM_PMStart, AM_PMEnd;
            h_start = mainActivity.getHourStart();
            h_end = mainActivity.getHourEnd();
            m_start = mainActivity.getMinuteStart();
            m_end = mainActivity.getMinuteEnd();

            if(h_start < 12) {
                AM_PMStart = "AM";
            } else {
                AM_PMStart = "PM";
            }
            if(h_end < 12) {
                AM_PMEnd = "AM";
            } else {
                AM_PMEnd = "PM";
            }

            time_start = String.format(Locale.getDefault(),"%02d:%02d %s",
                    h_start, m_start, AM_PMStart);
            time_end = String.format(Locale.getDefault(),"%02d:%02d %s",
                    h_end, h_end, AM_PMEnd);

            setTime(h_start, m_start);
        }
        catch (Exception NullPointerException){Log.e("onCreateView", "Null Time");}
        //button set
        start.setChecked(true);
        end.setChecked(false);

        //Set listeners
        //start/end
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setChecked(true);
                end.setChecked(false);
                setTime(h_start, m_start);
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end.setChecked(true);
                start.setChecked(false);
                setTime(h_end, m_end);
            }
        });
        //calendar
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int monthOfYear, int dayOfMonth) {
                date = String.format(Locale.getDefault(),"%s %02d, %04d",
                        correspondingMonth(monthOfYear), dayOfMonth, year);
                dayInt = dayOfMonth;
                monthInt = monthOfYear;
                yearInt = year;
            }
        });
        //time
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "AM";
                } else {
                    AM_PM = "PM";
                }
                // TODO double check the edge case conditional
                if(start.isChecked()){
                    if(hourOfDay > h_end || (hourOfDay == h_end && minute > m_end)){
                        h_end = hourOfDay+1;
                        if(hourOfDay == 11){
                            time_end = String.format(Locale.getDefault(),"%02d:%02d %s",
                                    h_end, m_end, "PM");
                        }
                        else if (hourOfDay == 23){
                            time_end = String.format(Locale.getDefault(),"%02d:%02d %s",
                                    0, m_end, "AM");
                            h_end = 0;
                        }
                        else{
                            time_end = String.format(Locale.getDefault(), "%02d:%02d %s",
                                    h_end, m_end, AM_PM);
                        }
                    }
                    time_start = String.format(Locale.getDefault(),"%02d:%02d %s",
                            hourOfDay, minute, AM_PM);
                    h_start = hourOfDay;
                    m_start = minute;
                }
                else{
                    if(hourOfDay < h_start || (hourOfDay == h_start && minute < m_start)){
                        h_start = hourOfDay-1;
                        if(hourOfDay == 0){
                            time_end = String.format(Locale.getDefault(),"%02d:%02d %s",
                                    23, m_end, "PM");
                            h_start = 23;
                        }
                        else if(hourOfDay == 12){
                            time_end = String.format(Locale.getDefault(),"%02d:%02d %s",
                                    11, m_end, "AM");
                        }
                        else {
                            time_end = String.format(Locale.getDefault(), "%02d:%02d %s",
                                    hourOfDay - 1, m_end, AM_PM);
                        }
                    }
                    time_end = String.format(Locale.getDefault(),"%02d:%02d %s",
                            hourOfDay, minute, AM_PM);
                    h_end = hourOfDay;
                    m_end = minute;
                }
            }
        });

        //deny/confirm
        deny.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(getDialog() != null && getDialog().isShowing()) {
                    getDialog().dismiss();
                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onInputListener.sendInput(date, time_start, time_end);
                onInputListener.sendDates(dayInt, monthInt, yearInt);
                onInputListener.sendTime(h_start, h_end, m_start, m_end);
                if(getDialog() != null && getDialog().isShowing()) {
                    getDialog().dismiss();
                }
            }
        });
        return view;
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd){
        date = String.format(Locale.getDefault(),"%02d %02d, %04d", dd, mm, yy);
        Log.w("On Data Set", date);
    }

    private String correspondingMonth(int month) {
        String monthString;
        switch (month+1) {
            case 1:  monthString = "January";
                break;
            case 2:  monthString = "February";
                break;
            case 3:  monthString = "March";
                break;
            case 4:  monthString = "April";
                break;
            case 5:  monthString = "May";
                break;
            case 6:  monthString = "June";
                break;
            case 7:  monthString = "July";
                break;
            case 8:  monthString = "August";
                break;
            case 9:  monthString = "September";
                break;
            case 10: monthString = "October";
                break;
            case 11: monthString = "November";
                break;
            case 12: monthString = "December";
                break;
            default: monthString = "Invalid month";
                break;
        }
        return monthString;
    }

    private void setTime(int hour, int minute){
        timePicker.setHour(hour);
        timePicker.setMinute(minute);
    }
}