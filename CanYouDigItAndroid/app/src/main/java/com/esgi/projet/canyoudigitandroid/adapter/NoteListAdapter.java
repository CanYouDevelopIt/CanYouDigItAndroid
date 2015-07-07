package com.esgi.projet.canyoudigitandroid.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.projet.canyoudigitandroid.R;
import com.esgi.projet.canyoudigitandroid.fragment.NoteFragment;
import com.esgi.projet.canyoudigitandroid.model.BlocNotes;
import com.esgi.projet.canyoudigitandroid.model.Note;
import com.esgi.projet.canyoudigitandroid.model.OnSwipeTouchListener;

import org.w3c.dom.Text;

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
    public View getView(int position, View convertView, final ViewGroup parent) {

        final Note note = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_list_note_layout, parent, false);
        }

        TextView tvTitreNote = (TextView) convertView.findViewById(R.id.titreNote);
        TextView tvDateNote = (TextView) convertView.findViewById(R.id.dateNote);
        ImageView btnDupliquerNote = (ImageView) convertView.findViewById(R.id.dupliquerNote);
        //ImageView btnSupprimerNote = (ImageView) convertView.findViewById(R.id.supprimerNote);
        //ImageView btnArchiverNote = (ImageView) convertView.findViewById(R.id.archiverNote);
        ImageView bthRappel = (ImageView) convertView.findViewById(R.id.rappelNote);

        if(!note.getDthRappel().equals("")) {
            bthRappel.setVisibility(View.VISIBLE);
        }else{
            bthRappel.setVisibility(View.GONE);
        }
        //if(note.getArchive()){
        //  btnArchiverNote.setImageResource(R.drawable.desarchiver);
        //}else{
        //  btnArchiverNote.setImageResource(R.drawable.archiver);
        //}

        tvTitreNote.setText(note.getTitre());
        tvDateNote.setText(note.getDateModif());
        switch (note.getNiveauImportance()){
            case 0: convertView.setBackgroundResource(R.color.rouge); break;
            case 1: convertView.setBackgroundResource(R.color.orange); break;
            case 2: convertView.setBackgroundResource(R.color.bleu); break;
        }
        convertView.setOnTouchListener(new OnSwipeTouchListener(getContext()){

            public void onSwipeRight(){
                if(note.getArchive()){
                    monBlocNotes.archiveToNote(note);
                    Toast.makeText(getContext(), R.string.note_desarchiver, Toast.LENGTH_SHORT).show();

                }else{
                    monBlocNotes.ajouterArchive(note);
                    Toast.makeText(getContext(), R.string.note_archiver, Toast.LENGTH_SHORT).show();
                }
                items.remove(note);
                notifyDataSetChanged();
            }

            public void onSwipeLeft(){
                Toast.makeText(getContext(), R.string.note_supprimer, Toast.LENGTH_SHORT).show();
                if(note.getArchive()){
                    monBlocNotes.supprimerArchive(note);
                }else{
                    monBlocNotes.supprimerNote(note);
                }
                items.remove(note);
                notifyDataSetChanged();
            }

        });

       convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NoteFragment noteFragment = new NoteFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("idNote",note.getId());
                noteFragment.setArguments(bundle);

                final Context context = parent.getContext();
                FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFrameActivty, noteFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        /*btnArchiverNote.setOnClickListener(new View.OnClickListener() {
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
        });*/

        /*btnSupprimerNote.setOnClickListener(new View.OnClickListener() {
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
        });*/

        btnDupliquerNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note nouvelleNote = new Note(note.getTitre(), note.getContenu(), note.getNiveauImportance(), note.getDateModif(), note.getGroupeNotes(), note.getDthRappel());
                if(note.getArchive()){
                    monBlocNotes.ajouterArchive(nouvelleNote);
                }else{
                    monBlocNotes.ajouterNote(nouvelleNote);
                }
                items.add(nouvelleNote);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}