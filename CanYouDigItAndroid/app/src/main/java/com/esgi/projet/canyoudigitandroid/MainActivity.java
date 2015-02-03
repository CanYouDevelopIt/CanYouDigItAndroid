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
    private BlocNotes monBlocNotes;
    Button buttonAjouter;
    TableLayout tableLayoutNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            monBlocNotes = savedInstanceState.getParcelable(STATE_BLOC_NOTES);
            Log.v("TAG","Ancien");
        }

        if(monBlocNotes == null){
            Log.v("Blocnote","null");
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
    protected void onResume() {
        Log.v("onResume","Resume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.v("onRestart","Restart");
        super.onRestart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v("onStop","Stop");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.v("onStart","Start");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.v("onSaveInstanceState","Mais j'ai save ce foutu bloc notes");
        savedInstanceState.putParcelable(STATE_BLOC_NOTES, monBlocNotes);

        if(savedInstanceState.getParcelable(STATE_BLOC_NOTES) == null){
            Log.v("onSaveInstanceState","NULL");
        }else{
            Log.v("onSaveInstanceState","PAS NULL");

        }

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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

        Log.v("onRestoreInstanceState","Je restaure !!!!!!!");
    }



    public void ajouterUneNote(View v){
        Intent intent = new Intent(this,NoteActivity.class);
        startActivity(intent);
    }



}
