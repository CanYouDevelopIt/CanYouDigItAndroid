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
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class ParametrageActivity extends Activity {

    private BlocNotes monBlocNotes;
    private EditText groupeToAdd;
    private ListView listLayoutGroupe;
    private GroupeListAdapter gAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametrage);

        Intent i = getIntent();
        monBlocNotes = (BlocNotes) i.getParcelableExtra("monBlocNotes");
        monBlocNotes = new BlocNotes(this);
        listLayoutGroupe = (ListView) findViewById(R.id.listGroupes);
        groupeToAdd   = (EditText)findViewById(R.id.ajouteGroupe);

        gAdapter = new GroupeListAdapter(this, R.layout.my_list_note_layout, monBlocNotes, monBlocNotes.getMesGroupesNotes());
        listLayoutGroupe.setAdapter(gAdapter);
    }

    public void createGroupe(View view){
        String newGroupe = groupeToAdd.getText().toString();
        monBlocNotes.ajouterGroupeNotes(newGroupe);
        refreshThisActivity();
    }

    private void refreshThisActivity() {
        Intent refresh = new Intent(this, ParametrageActivity.class);
        startActivity(refresh);
        this.finish();
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
