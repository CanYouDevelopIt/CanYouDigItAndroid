package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jkn1092 on 12/02/2015.
 */
public class NoteListAdapter extends ArrayAdapter<Note> {

    public BlocNotes monBlocNotes;
    private int layoutResourceId;
    private Context context;

    public NoteListAdapter(Context context, int layoutResourceId, BlocNotes monBlocNotes) {
        super(context, layoutResourceId, monBlocNotes.getMesNotes());
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.monBlocNotes = monBlocNotes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Note note = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_list_note_layout, parent, false);
        }

        // Lookup view for data population
        TextView tvTitreNote = (TextView) convertView.findViewById(R.id.titreNote);
        Button btnSupprimerNote = (Button) convertView.findViewById(R.id.supprimerNote);
        Button btnArchiverNote = (Button) convertView.findViewById(R.id.archiverNote);

        tvTitreNote.setText(note.getTitre());
        switch (note.getNiveauImportance()){
            case 0: convertView.setBackgroundColor(Color.RED); break;
            case 1: convertView.setBackgroundColor(Color.GREEN); break;
            case 2: convertView.setBackgroundColor(Color.YELLOW); break;


        }

        tvTitreNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NoteActivity.class);
                intent.putExtra("idNote",note.getId());
                getContext().startActivity(intent);
            }
        });

        btnArchiverNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monBlocNotes.updateNote(note);
            }
        });

        btnSupprimerNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monBlocNotes.supprimerNote(note);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    public void setMonBlocNotes(BlocNotes _monBlocNotes){
    }


}