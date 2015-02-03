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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends Activity {

    static final String STATE_BLOC_NOTES = "STATE_BLOC_NOTES";
    private BlocNotes monBlocNotes = new BlocNotes();
    Button buttonAjouter;
    TableLayout tableLayoutNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            monBlocNotes = savedInstanceState.getParcelable(STATE_BLOC_NOTES);
            Log.v("TAG","Ancien");
        }else {
            Log.v("TAG","NEw");
            monBlocNotes = new BlocNotes();
        }

        setContentView(R.layout.activity_main);

        buttonAjouter = (Button) findViewById(R.id.ajouterNote);
        tableLayoutNotes = (TableLayout) findViewById(R.id.tableNote);


        if(getIntent().hasExtra("nouvelleNote")) {
            Log.v("TAG","JAI UNE NEW NOTE");
            Note nouvelleNote = getIntent().getExtras().getParcelable("nouvelleNote");
            monBlocNotes.ajouterNote(nouvelleNote);
        }else{
            Log.v("TAG","JAI PAS DE new NOTE");
        }

        for(Note n: monBlocNotes.getMesNotes()){

            Log.v("Note : ", n.getTitre());

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
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.v("onSaveInstanceState","Mais j'ai save ce foutu bloc notes");
        savedInstanceState.putParcelable(STATE_BLOC_NOTES, monBlocNotes);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("onRestoreInstanceState","Je restaure !!!!!!!");
        monBlocNotes = savedInstanceState.getParcelable(STATE_BLOC_NOTES);

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

    public void ajouterUneNote(View v){
        Log.v("TAG","Bundle");
        Bundle b = new Bundle();
        onSaveInstanceState(b);

        Intent intent = new Intent(this,NoteActivity.class);
        startActivity(intent);
    }



}
