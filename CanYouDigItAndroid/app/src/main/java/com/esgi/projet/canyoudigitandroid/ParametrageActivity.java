package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.Toast;


public class ParametrageActivity extends Activity {

    public BlocNotes monBlocNotes;

    EditText groupeToAdd;

    private TableLayout tableLayoutGroupe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametrage);

        Intent i = getIntent();
        monBlocNotes = (BlocNotes) i.getParcelableExtra("monBlocNotes");
        monBlocNotes = new BlocNotes(this);
        tableLayoutGroupe = (TableLayout) findViewById(R.id.tabGroupes);

        groupeToAdd   = (EditText)findViewById(R.id.ajouteGroupe);

        if(monBlocNotes == null){
            Log.v("PARAMETRAGE","NULL y'a un problème ça doit pas etre null et ça marche normalement j'ai testé !");
        }else{
            Log.v("PARAMETRAGE","PAS NULL nb notes = " + monBlocNotes.getMesNotes().size());
        }

        initGroupeTabe();

    }

    public void createGroupe(View view){

        Log.v("PARAMETRAGE","Je suis dans Create Groupe View ="+groupeToAdd.getText());
        String newGroupe = groupeToAdd.getText().toString();
        monBlocNotes.ajouterGroupeNotes(newGroupe);
        refreshThisActivity();
    }

    private void refreshThisActivity() {
        Intent refresh = new Intent(this, ParametrageActivity.class);
        startActivity(refresh);
        this.finish();
    }

    public void initGroupeTabe(){

        for(final String nomDuGroupe: monBlocNotes.getMesGroupesNotes()){
            final TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            row.setBackgroundColor(Color.GREEN);

            TextView textTitreNote = new TextView(this);
            textTitreNote.setText(nomDuGroupe);

            Button btnSupprimer = new Button(this);
            btnSupprimer.setText("Supprimer");
            btnSupprimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monBlocNotes.supprimerGroupeNotes(nomDuGroupe);
                    tableLayoutGroupe.removeView(row);
                }
            });
            row.addView(btnSupprimer);
            row.addView(textTitreNote);

            tableLayoutGroupe.addView(row,lp);
        }
    }


    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
