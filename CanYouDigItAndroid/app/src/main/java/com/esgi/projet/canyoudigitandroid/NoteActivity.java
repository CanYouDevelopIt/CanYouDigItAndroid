package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class NoteActivity extends Activity {

    EditText nomTitre;
    EditText contenu;
    TextView date;
    ListView importance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        nomTitre = (EditText) findViewById(R.id.titreNote);
        contenu = (EditText) findViewById(R.id.contenuNote);
        date = (TextView) findViewById(R.id.dateNote);
        importance = (ListView) findViewById(R.id.importanceNote);

        String[] niveauImportance = new String[] {"Très important","Important","Normal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, niveauImportance);
        importance.setAdapter(adapter);

    }



    public void onBackPressed() {
        Note nouvelleNote = new Note(nomTitre.getText().toString(), contenu.getText().toString(), 1, "");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("nouvelleNote", nouvelleNote);
        startActivity(intent);
    }

}
