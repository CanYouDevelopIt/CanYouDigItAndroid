package com.esgi.projet.canyoudigitandroid.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;

import com.esgi.projet.canyoudigitandroid.R;

import java.util.Calendar;

/**
 * Created by Jkn1092 on 24/06/2015.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String dth = (String) ((Button) getActivity().findViewById(R.id.dthRappel)).getText();
        String hourChaine;
        String minuteChaine;

        if(hourOfDay > 9)
            hourChaine = Integer.toString(hourOfDay);
        else
            hourChaine = "0" + Integer.toString(hourOfDay);

        if(minute > 9)
            minuteChaine = Integer.toString(minute);
        else
            minuteChaine = "0" + Integer.toString(minute);

        dth += " " + hourChaine + ":" + minuteChaine;
        ((Button) getActivity().findViewById(R.id.dthRappel)).setText(dth);
    }



}