package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class ParametrageActivity extends Activity {

    public BlocNotes monBlocNotes;

    EditText groupeToAdd;

    MaBaseSQLite BDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametrage);

        Intent i = getIntent();
        monBlocNotes = (BlocNotes) i.getParcelableExtra("monBlocNotes");

        groupeToAdd   = (EditText)findViewById(R.id.ajouteGroupe);

        if(monBlocNotes == null){
            Log.v("PARAMETRAGE","NULL y'a un problème ça doit pas etre null et ça marche normalement j'ai testé !");
        }else{
            Log.v("PARAMETRAGE","PAS NULL nb notes = " + monBlocNotes.getMesNotes().size());
        }
    }

    public void createGroupe(View view){

        Log.v("PARAMETRAGE","Je suis dans Create Groupe View ="+groupeToAdd.getText());
        String newGroupe = groupeToAdd.getText().toString();
        GroupeBDD groupeBDD = new GroupeBDD(this);
        groupeBDD.open();
        Long id = groupeBDD.insertGroupe(newGroupe);
        if(id<0){
            showMessage("Le groupe "+newGroupe+" n'a pas pu être créer." );
        }else{
            showMessage("Le groupe "+newGroupe+" a été créer." );
        }
        groupeBDD.close();

        // Refresh la table :P que je n'ai pas encore créer :3
       // showMessage(groupeBDD.getAllData());

    }
    public void showMessage(String message){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }


}
