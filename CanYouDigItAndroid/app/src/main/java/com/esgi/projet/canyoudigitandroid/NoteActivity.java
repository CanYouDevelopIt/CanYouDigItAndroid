package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class NoteActivity extends Activity{

    public BlocNotes monBlocNotes;
    public EditText nomTitre;
    public EditText contenu;
    public TextView date;
    public Spinner importance;
    public String laDate;
    public Note noteActuelle;
    public Spinner groupe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        nomTitre = (EditText) findViewById(R.id.titreNote);
        contenu = (EditText) findViewById(R.id.contenuNote);
        date = (TextView) findViewById(R.id.dateNote);
        importance = (Spinner) findViewById(R.id.spinner);
        groupe = (Spinner) findViewById(R.id.groupeSpinner);


        monBlocNotes = new BlocNotes(this);

        // Chargement du spinner Importance
        String[] niveauImportance = new String[] {"Très important","Important","Normal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, niveauImportance);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        importance.setAdapter(adapter);

        //Chargement du Spinner Groupe
        String defaultGroupeValue = getString(R.string.default_groupe_value);
        List<String> listGroupes = ajoutDefaultValueGroupe(defaultGroupeValue);
        String[] tableGroupes = listGroupes.toArray(new String[listGroupes.size()]);
        ArrayAdapter<String> groupeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, tableGroupes);
        groupeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupe.setAdapter(groupeAdapter);


        if(getIntent().hasExtra("idNote")){

            int idNote = getIntent().getIntExtra("idNote", -1);
            noteActuelle = monBlocNotes.getNoteById(idNote);
            nomTitre.setText(noteActuelle.getTitre());
            contenu.setText(noteActuelle.getContenu());
            importance.setSelection(noteActuelle.getNiveauImportance());
            for(int i =0; i< tableGroupes.length;i++){
                if(tableGroupes[i].equals(noteActuelle.getGroupeNotes())){
                    groupe.setSelection(i);
                    break;
                }
            }

            laDate = noteActuelle.getDateModif();

        }else{
            Date theDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            laDate = sdf.format(theDate);
        }

        date.setText(laDate);
    }

    public void onBackPressed() {

        String nomGroupe = "";

        if(!nomTitre.getText().toString().equals("")) {
            if(groupe.getSelectedItem() != null){
                nomGroupe = groupe.getSelectedItem().toString();
            }
            if(noteActuelle == null) {
                noteActuelle = new Note(nomTitre.getText().toString(), contenu.getText().toString(), importance.getSelectedItemPosition(), laDate,nomGroupe);
                monBlocNotes.ajouterNote(noteActuelle);
            }else{
                noteActuelle.setTitre(nomTitre.getText().toString());
                noteActuelle.setContenu(contenu.getText().toString());
                noteActuelle.setNiveauImportance(importance.getSelectedItemPosition());
                noteActuelle.setGroupeNotes(nomGroupe);
                noteActuelle.setDateModif(laDate);
                monBlocNotes.updateNote(noteActuelle);
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public List<String> ajoutDefaultValueGroupe(String defaultGroupeValue){

        List<String> listGroupes = monBlocNotes.getMesGroupesNotes();

        if(!listGroupes.contains(defaultGroupeValue)){
            Log.v("NOTE ACTIVITY", "Ajout de la valeur par défaut d'un groupe");
            listGroupes.add(defaultGroupeValue);
        }
        return listGroupes;
    }
}
