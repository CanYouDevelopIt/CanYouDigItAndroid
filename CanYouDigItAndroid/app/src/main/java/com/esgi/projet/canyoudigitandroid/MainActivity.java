package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
    private BlocNotes monBlocNotes;
    public EditText editTexteRechercheNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAjouter = (Button) findViewById(R.id.ajouterNote);
        TableLayout tableLayoutNotes = (TableLayout) findViewById(R.id.tableNote);
        editTexteRechercheNotes = (EditText) findViewById(R.id.rechercheNote);

        if(savedInstanceState != null){
            editTexteRechercheNotes.setText(savedInstanceState.getString(STATE_RECHERCHE));
        }

        monBlocNotes = new BlocNotes(this);

        for(final Note n: monBlocNotes.getMesNotes()){
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            Log.v("MainActivity","NiveauImportance = " +n.getNiveauImportance());

            switch (n.getNiveauImportance()){
                case 0: row.setBackgroundColor(Color.RED);
                case 1: row.setBackgroundColor(Color.GREEN);
                case 2: row.setBackgroundColor(Color.YELLOW);
            }

            TextView textTitreNote = new TextView(this);
            textTitreNote.setText(n.getTitre() + " " + n.getDateModif());
            row.addView(textTitreNote);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    afficherUneNote(n.getId());
                }
            });

            tableLayoutNotes.addView(row,lp);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(STATE_RECHERCHE, editTexteRechercheNotes.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editTexteRechercheNotes.setText(savedInstanceState.getString(STATE_RECHERCHE));
    }

    public void ajouterUneNote(View v){
        Intent intent = new Intent(this,NoteActivity.class);
        startActivity(intent);
    }

    public void afficherUneNote(int idNote){
        Intent intent = new Intent(this,NoteActivity.class);
        intent.putExtra("idNote",idNote);
        startActivity(intent);
    }

    public void parametrerGroupes(View v){
        Intent intent = new Intent(this,ParametrageActivity.class);
        startActivity(intent);
    }



}
