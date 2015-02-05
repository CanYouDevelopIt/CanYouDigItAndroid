package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class ParametrageActivity extends Activity {

    BlocNotes monBlocNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametrage);

        Intent i = getIntent();
        monBlocNotes = (BlocNotes) i.getParcelableExtra("BlocNotes");

        if(monBlocNotes == null){
            Log.v("PARAMETRAGE","NULL y'a un problème ça doit pas etre null et ça marche normalement j'ai testé !");
        }else{
            Log.v("PARAMETRAGE","PAS NULL nb notes = " + monBlocNotes.getMesNotes().size());
        }

    }


}
