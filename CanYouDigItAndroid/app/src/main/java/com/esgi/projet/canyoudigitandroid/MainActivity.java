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
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final String STATE_BLOC_NOTES = "STATE_BLOC_NOTES";
    private static final String STATE_RECHERCHE = "RECHERCHE";
    private BlocNotes monBlocNotes;
    private NoteListAdapter nAdapter;
    public EditText editTexteRechercheNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAjouter = (Button) findViewById(R.id.ajouterNote);
        ListView listNotes = (ListView) findViewById(R.id.listNote);
        Button buttonAfficherNotes = (Button) findViewById(R.id.afficherNotes);
        Button buttonAfficherArchives = (Button) findViewById(R.id.afficherArchives);
        editTexteRechercheNotes = (EditText) findViewById(R.id.rechercheNote);

        if(savedInstanceState != null){
            editTexteRechercheNotes.setText(savedInstanceState.getString(STATE_RECHERCHE));
        }

        monBlocNotes = new BlocNotes(this);

        nAdapter = new NoteListAdapter(this, R.layout.my_list_note_layout, monBlocNotes);
        listNotes.setAdapter(nAdapter);

        buttonAfficherNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonAfficherArchives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    /**
     * helper to show what happens when all data is new
     */
    private void reloadAllData(){
        nAdapter.setMonBlocNotes(monBlocNotes);
        nAdapter.notifyDataSetChanged();
    }

}
