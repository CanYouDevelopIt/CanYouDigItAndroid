package com.esgi.projet.canyoudigitandroid;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jkn1092 on 22/01/2015.
 */

public class Note implements Parcelable {

    private String titre;
    private String contenu;
    private int niveauImportance;
    private String dateModif;
    private String groupeNotes;

    public Note(String _titre, String _contenu, int _niveauImportance, String _groupeNotes) {
        titre = _titre;
        contenu = _contenu;
        niveauImportance = _niveauImportance;
        groupeNotes = _groupeNotes;
    }

    public Note(Parcel in) {
        titre = in.readString();
        contenu = in.readString();
        niveauImportance = in.readInt();
        groupeNotes = in.readString();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>()
    {
        @Override
        public Note createFromParcel(Parcel source)
        {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size)
        {
            return new Note[size];
        }
    };

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getNiveauImportance() {
        return niveauImportance;
    }

    public void setNiveauImportance(int niveauImportance) {
        this.niveauImportance = niveauImportance;
    }

    public String getDateModif() {
        return dateModif;
    }

    public void setDateModif(String dateModif) {
        this.dateModif = dateModif;
    }

    public String getGroupeNotes() {
        return groupeNotes;
    }

    public void setGroupeNotes(String groupeNotes) {
        this.groupeNotes = groupeNotes;
    }

    public boolean equals(Object n){
        if(n instanceof Note){
            if(titre == ((Note) n).getTitre()){
                return true;
            }
        }
        return false;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(titre);
        dest.writeString(contenu);
        dest.writeInt(niveauImportance);
        dest.writeString(dateModif);
        dest.writeString(groupeNotes);

    }
}
