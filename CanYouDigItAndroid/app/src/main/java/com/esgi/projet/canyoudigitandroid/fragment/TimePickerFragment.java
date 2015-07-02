package com.esgi.projet.canyoudigitandroid.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.esgi.projet.canyoudigitandroid.MainActivity;
import com.esgi.projet.canyoudigitandroid.R;
import com.esgi.projet.canyoudigitandroid.receiver.AlarmReceiver;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Jkn1092 on 24/06/2015.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public int year;
    public int month;
    public int day;

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

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hourOfDay, minute);
        String titre = ((EditText)getActivity().findViewById(R.id.titreNote)).getText().toString();

        Intent myIntent = new Intent(getActivity(),AlarmReceiver.class);
        myIntent.putExtra("rappel", "Rappel !");
        myIntent.putExtra("titre", titre);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }





}