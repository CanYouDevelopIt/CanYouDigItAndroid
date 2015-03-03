package com.esgi.projet.canyoudigitandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
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

    private BlocNotes monBlocNotes;
    private List<Note> items;
    private int layoutResourceId;
    private Context context;

    public NoteListAdapter(Context context, int layoutResourceId, BlocNotes _monBlocNotes, List<Note> _items) {
        super(context, layoutResourceId, _items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.monBlocNotes = _monBlocNotes;
        this.items = _items;
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

        if(note.getArchive()){
            btnArchiverNote.setText(R.string.desarchiver);
        }else{
            btnArchiverNote.setText(R.string.archiver);
        }

        tvTitreNote.setText(note.getTitre());
        switch (note.getNiveauImportance()){
            case 0: convertView.setBackgroundResource(R.color.rouge); break;
            case 1: convertView.setBackgroundResource(R.color.orange); break;
            case 2: convertView.setBackgroundResource(R.color.bleu); break;


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
                if(note.getArchive()){
                    monBlocNotes.archiveToNote(note);
                }else{
                    monBlocNotes.ajouterArchive(note);
                }
                items.remove(note);
                notifyDataSetChanged();
            }
        });

        btnSupprimerNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(note.getArchive()){
                    monBlocNotes.supprimerArchive(note);
                }else{
                    monBlocNotes.supprimerNote(note);
                }
                items.remove(note);
                notifyDataSetChanged();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

}