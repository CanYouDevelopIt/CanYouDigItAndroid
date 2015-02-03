package com.esgi.projet.canyoudigitandroid;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jkn1092 on 22/01/2015.
 */
public class BlocNotes implements Parcelable {

    private List<Note> mesNotes;
    private List<Note> mesArchives;
    private List<String> mesGroupesNotes;

    public BlocNotes() {
        mesNotes = new ArrayList<Note>();
        mesArchives = new ArrayList<Note>();
        mesGroupesNotes = new ArrayList<String>();
    }

    public BlocNotes(Parcel in) {

        mesNotes = new ArrayList<Note>();
        mesArchives = new ArrayList<Note>();
        mesGroupesNotes = new ArrayList<String>();

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
    }

    public void supprimerNote(Note uneNote){
        for(Note n: mesNotes){
           if(n.equals(uneNote)){
               mesNotes.remove(n);
           }
        }
    }

    public void ajouterArchive(Note uneArchive){
        this.supprimerNote(uneArchive);
        mesArchives.add(uneArchive);
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
