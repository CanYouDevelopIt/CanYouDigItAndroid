package com.esgi.projet.canyoudigitandroid.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.esgi.projet.canyoudigitandroid.adapter.GroupeListAdapter;
import com.esgi.projet.canyoudigitandroid.R;
import com.esgi.projet.canyoudigitandroid.model.BlocNotes;

public class ParametreFragment extends Fragment {

    private BlocNotes monBlocNotes;
    private EditText groupeToAdd;
    private ListView listLayoutGroupe;
    private GroupeListAdapter gAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parametre, container, false);

        monBlocNotes = new BlocNotes(getActivity());
        listLayoutGroupe = (ListView) view.findViewById(R.id.listGroupes);
        groupeToAdd   = (EditText) view.findViewById(R.id.ajouteGroupe);
        ImageView imageCreerGroupe = (ImageView) view.findViewById(R.id.imgAjouterGroupe);

        imageCreerGroupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGroupe = groupeToAdd.getText().toString();
                groupeToAdd.setText("");
                monBlocNotes.ajouterGroupeNotes(newGroupe);
                gAdapter = new GroupeListAdapter(getActivity(), R.layout.my_list_note_layout, monBlocNotes, monBlocNotes.getMesGroupesNotes());
                gAdapter.notifyDataSetChanged();
            }
        });

        gAdapter = new GroupeListAdapter(getActivity(), R.layout.my_list_note_layout, monBlocNotes, monBlocNotes.getMesGroupesNotes());
        listLayoutGroupe.setAdapter(gAdapter);

        return view;
    }

}
