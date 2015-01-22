package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


public class NoteActivity extends Activity {

    EditText nomTitre;
    EditText contenu;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        nomTitre = (EditText) findViewById(R.id.titreNote);
        contenu = (EditText) findViewById(R.id.contenuNote);
        date = (TextView) findViewById(R.id.dateNote);

    }

    public void onBackPressed() {
        Log.v("LOG", "ENVOIEEEEEEEEEEEEEEEE");

        Note nouvelleNote = new Note(nomTitre.getText().toString(), contenu.getText().toString(), 1, "");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("nouvelleNote", nouvelleNote);
        startActivity(intent);
    }

}
