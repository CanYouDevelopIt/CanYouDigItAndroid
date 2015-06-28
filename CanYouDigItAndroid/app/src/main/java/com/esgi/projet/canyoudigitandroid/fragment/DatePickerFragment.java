package com.esgi.projet.canyoudigitandroid.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import com.esgi.projet.canyoudigitandroid.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        String dayChaine;
        String monthChaine;

        if(day > 9)
            dayChaine = Integer.toString(day);
        else
            dayChaine = "0" + Integer.toString(day);

        if(month > 9)
            monthChaine = Integer.toString(month);
        else
            monthChaine = "0" + Integer.toString(month);

        String dth = dayChaine + "/" + monthChaine + "/" + year;

        DialogFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getFragmentManager(), "timePicker");
        ((Button) getActivity().findViewById(R.id.dthRappel)).setText(dth);
    }

}