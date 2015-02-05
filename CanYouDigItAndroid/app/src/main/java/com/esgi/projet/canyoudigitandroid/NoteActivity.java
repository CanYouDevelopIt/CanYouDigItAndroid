package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class NoteActivity extends Activity implements AdapterView.OnItemSelectedListener {

    EditText nomTitre;
    EditText contenu;
    TextView date;
    Spinner importance;
    String laDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        nomTitre = (EditText) findViewById(R.id.titreNote);
        contenu = (EditText) findViewById(R.id.contenuNote);
        date = (TextView) findViewById(R.id.dateNote);
        importance = (Spinner) findViewById(R.id.spinner);
        Date theDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        laDate = sdf.format(theDate);
        date.setText(laDate);

        String[] niveauImportance = new String[] {"Très important","Important","Normal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, niveauImportance);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        importance.setAdapter(adapter);


    }



    public void onBackPressed() {
        Note nouvelleNote = new Note(nomTitre.getText().toString(), contenu.getText().toString(), importance.getSelectedItemPosition(), laDate,"");
        Log.v("ta mere la chienne", nouvelleNote.getDateModif());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("nouvelleNote", nouvelleNote);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.v("onItemSelected", "POSITION"+position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
