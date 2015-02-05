package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final String STATE_BLOC_NOTES = "STATE_BLOC_NOTES";
    private static final String STATE_RECHERCHE = "RECHERCHE";
    public BlocNotes monBlocNotes;
    public EditText editTexteRechercheNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAjouter = (Button) findViewById(R.id.ajouterNote);
        TableLayout tableLayoutNotes = (TableLayout) findViewById(R.id.tableNote);
        editTexteRechercheNotes = (EditText) findViewById(R.id.rechercheNote);

        if(savedInstanceState != null){
            monBlocNotes = (BlocNotes)savedInstanceState.getParcelable(STATE_BLOC_NOTES);
            editTexteRechercheNotes.setText(savedInstanceState.getString(STATE_RECHERCHE));
        }

        if(getIntent().hasExtra("monBlocNotes")){
            monBlocNotes = (BlocNotes)getIntent().getParcelableExtra("monBlocNotes");
        }

        if(monBlocNotes == null){
            monBlocNotes = new BlocNotes();
        }

        if(getIntent().hasExtra("nouvelleNote")) {
            Note nouvelleNote = (Note)getIntent().getParcelableExtra("nouvelleNote");
            monBlocNotes.ajouterNote(nouvelleNote);
        }

        for(Note n: monBlocNotes.getMesNotes()){
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView textTitreNote = new TextView(this);

            textTitreNote.setText(n.getTitre() + " " + n.getDateModif());
            row.addView(textTitreNote);
            tableLayoutNotes.addView(row,lp);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(STATE_BLOC_NOTES, monBlocNotes);
        savedInstanceState.putString(STATE_RECHERCHE, editTexteRechercheNotes.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        monBlocNotes = (BlocNotes)savedInstanceState.getParcelable(STATE_BLOC_NOTES);
        editTexteRechercheNotes.setText(savedInstanceState.getString(STATE_RECHERCHE));
    }

    public void ajouterUneNote(View v){
        Intent intent = new Intent(this,NoteActivity.class);
        intent.putExtra("monBlocNotes",(Parcelable)monBlocNotes);
        startActivity(intent);
    }

    public void parametrerGroupes(View v){
        Intent intent = new Intent(this,ParametrageActivity.class);
        intent.putExtra("monBlocNotes",(Parcelable)monBlocNotes);
        startActivity(intent);
    }



}
