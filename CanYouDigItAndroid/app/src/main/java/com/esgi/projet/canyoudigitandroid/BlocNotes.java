package com.esgi.projet.canyoudigitandroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jkn1092 on 22/01/2015.
 */
public class BlocNotes {

    private List<Note> mesNotes;
    private List<Note> mesArchives;
    private List<String> mesGroupeNotes;

    public BlocNotes() {
        mesNotes = new ArrayList<Note>();
        mesArchives = new ArrayList<Note>();
        mesGroupeNotes = new ArrayList<String>();
    }

    public List<Note> getMesNotes() {
        return mesNotes;
    }

    public void setMesNotes(List<Note> mesNotes) {
        this.mesNotes = mesNotes;
    }

    public List<String> getGroupeNotes() {
        return mesGroupeNotes;
    }

    public void setGroupeNotes(List<String> groupeNotes) {
        this.mesGroupeNotes = groupeNotes;
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
        if(!mesGroupeNotes.contains(nomGroupe)) {
            mesGroupeNotes.add(nomGroupe);
        }
    }

    public void supprimerGroupeNotes(String nomGroupe){
        mesGroupeNotes.remove(nomGroupe);

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

}
