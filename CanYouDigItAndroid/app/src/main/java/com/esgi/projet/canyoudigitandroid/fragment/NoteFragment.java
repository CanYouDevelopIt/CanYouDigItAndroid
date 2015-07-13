package com.esgi.projet.canyoudigitandroid.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.projet.canyoudigitandroid.R;
import com.esgi.projet.canyoudigitandroid.model.BlocNotes;
import com.esgi.projet.canyoudigitandroid.model.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class NoteFragment extends Fragment {

    public BlocNotes monBlocNotes;
    public EditText nomTitre;
    public EditText contenu;
    public TextView date;
    public Spinner importance;
    public String laDate;
    public Note noteActuelle;
    public Spinner groupe;
    public Boolean noteModifier;
    public Button rappel;

    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_note, container, false);

        nomTitre = (EditText) rootView.findViewById(R.id.titreNote);
        contenu = (EditText) rootView.findViewById(R.id.contenuNote);
        date = (TextView) rootView.findViewById(R.id.dateNote);
        importance = (Spinner) rootView.findViewById(R.id.spinner);
        groupe = (Spinner) rootView.findViewById(R.id.groupeSpinner);
        rappel = (Button) rootView.findViewById(R.id.dthRappel);

        rappel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });

        monBlocNotes = new BlocNotes(getActivity());

        // Chargement du spinner Importance
        String[] niveauImportance = new String[] {"Urgent","Important","Normal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, niveauImportance);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        importance.setAdapter(adapter);

        //Chargement du Spinner Groupe
        String defaultGroupeValue = getString(R.string.default_groupe_value);
        List<String> listGroupes = ajoutDefaultValueGroupe(defaultGroupeValue);
        String[] tableGroupes = listGroupes.toArray(new String[listGroupes.size()]);
        ArrayAdapter<String> groupeAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, tableGroupes);
        groupeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupe.setAdapter(groupeAdapter);

        Bundle bundle = this.getArguments();

        if(bundle != null){

            int idNote = bundle.getInt("idNote", -1);
            noteActuelle = monBlocNotes.getNoteById(idNote);
            nomTitre.setText(noteActuelle.getTitre());
            contenu.setText(noteActuelle.getContenu());
            importance.setSelection(noteActuelle.getNiveauImportance());
            for(int i =0; i< tableGroupes.length;i++){
                if(tableGroupes[i].equals(noteActuelle.getGroupeNotes())){
                    groupe.setSelection(i);
                    break;
                }
            }
            laDate = noteActuelle.getDateModif();
            if(!noteActuelle.getDthRappel().equals("")){
                try {
                    long dth = Long.parseLong(noteActuelle.getDthRappel());
                    dth += 1000000;
                    Date dthRappel = new SimpleDateFormat("yyyyMMddHHmm").parse(Long.toString(dth));
                    rappel.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(dthRappel));
                } catch (ParseException e) {

                    e.printStackTrace();
                }
            }

            noteModifier = false;
        }else{
            Date theDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            laDate = sdf.format(theDate);
            noteModifier = true;
        }
        date.setText(laDate);
        return rootView;
    }

    public List<String> ajoutDefaultValueGroupe(String defaultGroupeValue){

        List<String> listGroupes = new ArrayList<String>();
        if(!listGroupes.contains(defaultGroupeValue)){
            listGroupes.add(defaultGroupeValue);
        }
        listGroupes.addAll(monBlocNotes.getMesGroupesNotes());

        return listGroupes;
    }

    @Override
    public void onDestroyView() {

        boolean afficherArchives = false;

        String nomGroupe = "";
        String dthRappel = "";

        if(rappel.getText() != "AJOUTER UN RAPPEL"){
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse((String) rappel.getText());
                dthRappel = new SimpleDateFormat("yyyyMMddHHmm").format(date);

                long dth = Long.parseLong(dthRappel);
                dth -= 1000000;
                dthRappel = Long.toString(dth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(groupe.getSelectedItem() != null && !groupe.getSelectedItem().toString().equals(getString(R.string.default_groupe_value))){
            nomGroupe = groupe.getSelectedItem().toString();
        }

        if(noteActuelle != null){
            if(!nomTitre.getText().toString().equals(noteActuelle.getTitre())){noteModifier = true;}
            if(!contenu.getText().toString().equals(noteActuelle.getContenu())){noteModifier = true;}
            if(importance.getSelectedItemPosition() != noteActuelle.getNiveauImportance()){noteModifier = true;}
            if(!nomGroupe.equals(noteActuelle.getGroupeNotes())){ noteModifier = true;}
        }

        if(noteModifier){
            Date theDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            laDate = sdf.format(theDate);
        }

        if(!nomTitre.getText().toString().equals("")) {
            if(noteActuelle == null) {
                noteActuelle = new Note(nomTitre.getText().toString(), contenu.getText().toString(), importance.getSelectedItemPosition(), laDate,nomGroupe,dthRappel);
                monBlocNotes.ajouterNote(noteActuelle);
            }else{
                noteActuelle.setTitre(nomTitre.getText().toString());
                noteActuelle.setContenu(contenu.getText().toString());
                noteActuelle.setNiveauImportance(importance.getSelectedItemPosition());
                noteActuelle.setGroupeNotes(nomGroupe);
                noteActuelle.setDateModif(laDate);
                noteActuelle.setDthRappel(dthRappel);
                monBlocNotes.updateNote(noteActuelle);
                afficherArchives = noteActuelle.getArchive();
            }
        }

        super.onDestroyView();
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
