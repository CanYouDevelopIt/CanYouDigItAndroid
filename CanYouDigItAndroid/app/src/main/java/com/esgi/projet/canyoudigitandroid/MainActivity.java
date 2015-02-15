package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.w3c.dom.Text;

import java.util.List;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final String STATE_BLOC_NOTES = "STATE_BLOC_NOTES";
    private static final String STATE_RECHERCHE = "RECHERCHE";
    private BlocNotes monBlocNotes;
    private NoteListAdapter nAdapter;
    private EditText editTexteRechercheNotes;
    private Spinner trierParGroupe;
    private Spinner trierParDefaut;
    private boolean afficherArchives = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ListView listNotes = (ListView) findViewById(R.id.listNote);
        Button buttonAfficherNotes = (Button) findViewById(R.id.afficherNotes);
        Button buttonAfficherArchives = (Button) findViewById(R.id.afficherArchives);

        editTexteRechercheNotes = (EditText) findViewById(R.id.rechercheNote);
        trierParGroupe = (Spinner) findViewById(R.id.spinnerTrierGroupe);
        trierParDefaut = (Spinner) findViewById(R.id.spinnerTrier);

        if(savedInstanceState != null){
            editTexteRechercheNotes.setText(savedInstanceState.getString(STATE_RECHERCHE));
        }

        monBlocNotes = new BlocNotes(this);

        ArrayAdapter<String> groupeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        groupeAdapter.add(getString(R.string.default_groupe_value));
        groupeAdapter.addAll(monBlocNotes.getMesGroupesNotes());
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

                afficherArchives = false;

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

                afficherArchives = true;

                Button buttonAjouter = (Button) findViewById(R.id.ajouterNote);
                buttonAjouter.setVisibility(View.INVISIBLE);
            }
        });

        trierParDefaut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trierListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        trierParGroupe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trierListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        editTexteRechercheNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {trierListView();}

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    public void trierListView(){
        nAdapter.clear();

        String conditionOrderBy = "";
        String conditionWhereGroupe = "";
        String conditionWhereRecherche = "";

        switch (trierParDefaut.getSelectedItemPosition()){
            case 0: conditionOrderBy = "date_modif"; break;
            case 1: conditionOrderBy = "titre"; break;
            case 2: conditionOrderBy ="niveau_importance"; break;
        }

        if (trierParGroupe.getSelectedItem() != null && !trierParGroupe.getSelectedItem().toString().equals(getString(R.string.default_groupe_value))){
            conditionWhereGroupe = trierParGroupe.getSelectedItem().toString();
        }

        conditionWhereRecherche = editTexteRechercheNotes.getText().toString();

        monBlocNotes.selectFromNbdd(conditionOrderBy,conditionWhereGroupe,conditionWhereRecherche);

        if (!afficherArchives)
            nAdapter.addAll(monBlocNotes.getMesNotes());
        else
            nAdapter.addAll(monBlocNotes.getMesArchives());

        nAdapter.notifyDataSetChanged();
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

    public void onBackPressed() {
        // Nous permet de quitter l'application correctement sans passer par des Activity édités précédemment
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
