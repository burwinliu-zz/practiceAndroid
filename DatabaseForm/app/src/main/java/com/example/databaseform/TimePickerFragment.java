package com.example.databaseform;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment
        extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Spinner hour, minute, ampm;
    private Button confirm, deny;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.time_dialog, container, false);
        hour = view.findViewById(R.id.hour);
        minute = view.findViewById(R.id.minute);
        ampm = view.findViewById(R.id.ampm);

        confirm = view.findViewById(R.id.confirm);
        deny = view.findViewById(R.id.cancel);

        deny.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
             getDialog().dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).dateSelected.setText("TEXT GOT");
                getDialog().dismiss();
            }
        });
        return view;
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {}
}