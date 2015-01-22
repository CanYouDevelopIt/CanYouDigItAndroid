package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private BlocNotes monBlocNotes;
    Button buttonAjouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monBlocNotes = new BlocNotes();
        buttonAjouter = (Button) findViewById(R.id.ajouterNote);


            Log.v("LOG = ","PAS NULL");
            Note nouvelleNote = getIntent().getExtras().getParcelable("nouvelleNote");
            if (nouvelleNote != null) {
                Log.v("LOG = ", "nouvelleNote");
                monBlocNotes.ajouterNote(nouvelleNote);
            } else {
                Log.v("LOG = ", "Note null");
            }
    }

    public void ajouterUneNote(View v){
        Intent intent = new Intent(this,NoteActivity.class);
        startActivity(intent);
    }



}
