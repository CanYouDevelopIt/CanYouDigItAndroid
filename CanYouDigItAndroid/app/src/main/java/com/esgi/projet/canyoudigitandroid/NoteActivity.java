package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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


public class NoteActivity extends Activity{

    public BlocNotes monBlocNotes;
    public EditText nomTitre;
    public EditText contenu;
    public TextView date;
    public Spinner importance;
    public String laDate;
    public Note noteActuelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        nomTitre = (EditText) findViewById(R.id.titreNote);
        contenu = (EditText) findViewById(R.id.contenuNote);
        date = (TextView) findViewById(R.id.dateNote);
        importance = (Spinner) findViewById(R.id.spinner);


        monBlocNotes = new BlocNotes(this);

        String[] niveauImportance = new String[] {"Très important","Important","Normal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, niveauImportance);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        importance.setAdapter(adapter);

        if(getIntent().hasExtra("idNote")){

            int idNote = getIntent().getIntExtra("idNote", -1);
            noteActuelle = monBlocNotes.getNoteById(idNote);
            nomTitre.setText(noteActuelle.getTitre());
            contenu.setText(noteActuelle.getContenu());
            importance.setSelection(noteActuelle.getNiveauImportance());
            laDate = noteActuelle.getDateModif();

        }else{
            Date theDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            laDate = sdf.format(theDate);
        }

        date.setText(laDate);



    }

    public void onBackPressed() {

        if(noteActuelle == null) {
            noteActuelle = new Note(nomTitre.getText().toString(), contenu.getText().toString(), importance.getSelectedItemPosition(), laDate, "");
            monBlocNotes.ajouterNote(noteActuelle);
        }else{
            noteActuelle.setTitre(nomTitre.getText().toString());
            noteActuelle.setContenu(contenu.getText().toString());
            noteActuelle.setNiveauImportance(importance.getSelectedItemPosition());
            noteActuelle.setDateModif(laDate);
            monBlocNotes.updateNote(noteActuelle);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
