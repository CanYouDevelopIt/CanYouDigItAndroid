package com.esgi.projet.canyoudigitandroid.fragment;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.esgi.projet.canyoudigitandroid.adapter.NoteListAdapter;
import com.esgi.projet.canyoudigitandroid.R;
import com.esgi.projet.canyoudigitandroid.model.BlocNotes;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private static final String STATE_BLOC_NOTES = "STATE_BLOC_NOTES";
    private static final String STATE_RECHERCHE = "RECHERCHE";
    private static final String STATE_ARCHIVE = "ARCHIVE";
    private BlocNotes monBlocNotes;
    private NoteListAdapter nAdapter;
    private EditText editTexteRechercheNotes;
    private Spinner trierParGroupe;
    private Spinner trierParDefaut;
    private boolean afficherArchives = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ListView listNotes = (ListView) rootView.findViewById(R.id.listNote);
        ImageView imageAfficherNotes = (ImageView) rootView.findViewById(R.id.afficherNotes);
        ImageView imageAfficherArchives = (ImageView) rootView.findViewById(R.id.afficherArchives);
        View imgParametre = (View) rootView.findViewById(R.id.parametreGroupes);

        editTexteRechercheNotes = (EditText) rootView.findViewById(R.id.rechercheNote);
        trierParGroupe = (Spinner) rootView.findViewById(R.id.spinnerTrierGroupe);
        trierParDefaut = (Spinner) rootView.findViewById(R.id.spinnerTrier);

        if(savedInstanceState != null){
            editTexteRechercheNotes.setText(savedInstanceState.getString(STATE_RECHERCHE));
            afficherArchives = savedInstanceState.getBoolean(STATE_ARCHIVE);
        }

        monBlocNotes = new BlocNotes(getActivity());

        ArrayAdapter<String> groupeAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item);
        groupeAdapter.add(getString(R.string.default_groupe_value));
        groupeAdapter.addAll(monBlocNotes.getMesGroupesNotes());
        groupeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trierParGroupe.setAdapter(groupeAdapter);

        if(afficherArchives){
            nAdapter = new NoteListAdapter(getActivity(), R.layout.my_list_note_layout, monBlocNotes,monBlocNotes.getMesArchives());
        }else{
            nAdapter = new NoteListAdapter(getActivity(), R.layout.my_list_note_layout, monBlocNotes,monBlocNotes.getMesNotes());
        }
        listNotes.setAdapter(nAdapter);

        imageAfficherNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nAdapter.clear();
                nAdapter.addAll(monBlocNotes.getMesNotes());
                nAdapter.notifyDataSetChanged();

                afficherArchives = false;

                ImageView buttonAjouter = (ImageView) rootView.findViewById(R.id.ajouterNote);
                buttonAjouter.setVisibility(View.VISIBLE);
            }
        });

        imageAfficherArchives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nAdapter.clear();
                nAdapter.addAll(monBlocNotes.getMesArchives());
                nAdapter.notifyDataSetChanged();

                afficherArchives = true;

                ImageView buttonAjouter = (ImageView) rootView.findViewById(R.id.ajouterNote);
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

        imgParametre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parametrerGroupes(rootView);
            }
        });

        return rootView;
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

    public void parametrerGroupes(View v){
        ParametreFragment parametrerFragment = new ParametreFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameActivty, parametrerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
