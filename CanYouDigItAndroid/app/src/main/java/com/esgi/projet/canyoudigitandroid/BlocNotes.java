package com.esgi.projet.canyoudigitandroid;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jkn1092 on 22/01/2015.
 */
public class BlocNotes implements Parcelable {

    private List<Note> mesNotes;
    private List<Note> mesArchives;
    private List<String> mesGroupesNotes;
    private NoteBDD nbdd;
    private GroupeBDD gbdd;

    public BlocNotes(Context context) {
        nbdd = new NoteBDD(context);
        gbdd = new GroupeBDD(context);

        mesNotes = nbdd.getMesNotes(false);
        mesArchives = nbdd.getMesNotes(true);
        mesGroupesNotes = gbdd.getAllData();
    }

    public BlocNotes(Parcel in) {

        mesNotes = nbdd.getMesNotes(false);
        mesArchives = nbdd.getMesNotes(true);
        mesGroupesNotes = gbdd.getAllData();

        in.readTypedList(mesNotes, Note.CREATOR);
        in.readTypedList(mesArchives, Note.CREATOR);
        in.readStringList(mesGroupesNotes);
    }

    public List<Note> getMesNotes() {
        return mesNotes;
    }

    public void setMesNotes(List<Note> mesNotes) {
        this.mesNotes = mesNotes;
    }

    public List<String> getMesGroupesNotes() {
        return mesGroupesNotes;
    }

    public void setMesGroupesNotes(List<String> groupesNotes) {
        this.mesGroupesNotes = groupesNotes;
    }

    public void ajouterNote(Note uneNote){
        mesNotes.add(uneNote);
        nbdd.insertNote(uneNote);
    }

    public void supprimerNote(Note uneNote){
        for(Note n: mesNotes){
           if(n.equals(uneNote)){
               mesNotes.remove(n);
               nbdd.removeNoteWithID(n.getId());
           }
        }
    }

    public Note getNoteById(int idNote){
        for(Note n: mesNotes){
            if(n.getId() == idNote){
                return n;
            }
        }
        return null;
    }

    public void ajouterArchive(Note uneArchive){
        this.supprimerNote(uneArchive);
        mesArchives.add(uneArchive);
        uneArchive.setArchive(true);
        nbdd.updateNote(uneArchive);
    }

    public void supprimerArchive(Note uneArchive){
        for(Note a: mesArchives){
            if(a.equals(uneArchive)){
                mesArchives.remove(a);
            }
        }
    }

    public void ajouterGroupeNotes(String nomGroupe){
        if(!mesGroupesNotes.contains(nomGroupe)) {
            mesGroupesNotes.add(nomGroupe);
        }
    }

    public void supprimerGroupeNotes(String nomGroupe){
        mesGroupesNotes.remove(nomGroupe);

        for(Note n: mesNotes){
            if(n.getGroupeNotes().equals(nomGroupe)){
                n.setGroupeNotes("");
            }
        }

        for(Note a: mesArchives){
            if(a.getGroupeNotes().equals(nomGroupe)){
                a.setGroupeNotes("");
            }
        }
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeTypedList(mesNotes);
        dest.writeTypedList(mesArchives);
        dest.writeStringList(mesGroupesNotes);
    }

    public static final Parcelable.Creator<BlocNotes> CREATOR = new Parcelable.Creator<BlocNotes>()
    {
        @Override
        public BlocNotes createFromParcel(Parcel source)
        {
            return new BlocNotes(source);
        }

        @Override
        public BlocNotes[] newArray(int size)
        {
            return new BlocNotes[size];
        }
    };

}
