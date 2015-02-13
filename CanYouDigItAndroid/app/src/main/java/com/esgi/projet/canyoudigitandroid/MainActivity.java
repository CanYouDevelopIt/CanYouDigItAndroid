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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final String STATE_BLOC_NOTES = "STATE_BLOC_NOTES";
    private static final String STATE_RECHERCHE = "RECHERCHE";
    private BlocNotes monBlocNotes;
    private NoteListAdapter nAdapter;
    private EditText editTexteRechercheNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listNotes = (ListView) findViewById(R.id.listNote);
        Button buttonAfficherNotes = (Button) findViewById(R.id.afficherNotes);
        Button buttonAfficherArchives = (Button) findViewById(R.id.afficherArchives);
        editTexteRechercheNotes = (EditText) findViewById(R.id.rechercheNote);
        Spinner trierParGroupe = (Spinner) findViewById(R.id.spinnerTrierGroupe);

        if(savedInstanceState != null){
            editTexteRechercheNotes.setText(savedInstanceState.getString(STATE_RECHERCHE));
        }

        monBlocNotes = new BlocNotes(this);

        ArrayAdapter<String> groupeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, monBlocNotes.getMesGroupesNotes());
        groupeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trierParGroupe.setAdapter(groupeAdapter);

        nAdapter = new NoteListAdapter(this, R.layout.my_list_note_layout, monBlocNotes, monBlocNotes.getMesNotes());
        listNotes.setAdapter(nAdapter);

        buttonAfficherNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nAdapter.clear();
                nAdapter.addAll(monBlocNotes.getMesNotes());
                nAdapter.notifyDataSetChanged();

                Button buttonAjouter = (Button) findViewById(R.id.ajouterNote);
                buttonAjouter.setVisibility(View.VISIBLE);
            }
        });

        buttonAfficherArchives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nAdapter.clear();
                nAdapter.addAll(monBlocNotes.getMesArchives());
                nAdapter.notifyDataSetChanged();

                Button buttonAjouter = (Button) findViewById(R.id.ajouterNote);
                buttonAjouter.setVisibility(View.INVISIBLE);
            }
        });

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

    public void parametrerGroupes(View v){
        Intent intent = new Intent(this,ParametrageActivity.class);
        startActivity(intent);
    }

}
