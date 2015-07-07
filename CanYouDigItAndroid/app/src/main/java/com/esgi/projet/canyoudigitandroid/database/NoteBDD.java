package com.esgi.projet.canyoudigitandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.esgi.projet.canyoudigitandroid.database.MaBaseSQLite;
import com.esgi.projet.canyoudigitandroid.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jkn1092 on 06/02/2015.
 */
public class NoteBDD {

    private static final int VERSION_BDD = 3;
    private static final String NOM_BDD = "notes.db";

    private static final String TABLE_NOTES = "table_notes";
    private static final String COL_ID = "id";
    private static final String COL_TITRE = "titre";
    private static final String COL_CONTENU = "contenu";
    private static final String COL_NIVEAU_IMPORTANCE = "niveau_importance";
    private static final String COL_DATE_MODIF = "date_modif";
    private static final String COL_GROUPE = "groupe";
    private static final String COL_ARCHIVE = "archive";
    private static final String COL_DTH_RAPPEL = "dth_rappel";

    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public NoteBDD(Context context){
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertNote(Note n){
        ContentValues values = new ContentValues();
        values.put(COL_TITRE, n.getTitre());
        values.put(COL_CONTENU, n.getContenu());
        values.put(COL_NIVEAU_IMPORTANCE, n.getNiveauImportance());
        values.put(COL_DATE_MODIF, n.getDateModif());
        values.put(COL_GROUPE, n.getGroupeNotes());
        values.put(COL_ARCHIVE, n.getArchive());
        values.put(COL_DTH_RAPPEL, n.getDthRappel());
        return bdd.insert(TABLE_NOTES, null, values);
    }

    public int updateNote(Note n){
        ContentValues values = new ContentValues();
        values.put(COL_TITRE, n.getTitre());
        values.put(COL_CONTENU, n.getContenu());
        values.put(COL_NIVEAU_IMPORTANCE, n.getNiveauImportance());
        values.put(COL_DATE_MODIF, n.getDateModif());
        values.put(COL_GROUPE, n.getGroupeNotes());
        values.put(COL_ARCHIVE, n.getArchive());
        values.put(COL_DTH_RAPPEL, n.getDthRappel());
        return bdd.update(TABLE_NOTES, values, COL_ID + " = " + n.getId(), null);
    }

    public int removeNoteWithID(int id){
        return bdd.delete(TABLE_NOTES, COL_ID + " = " + id, null);
    }

    public List<Note> getMesNotes(Boolean archive, String conditionOrderBy, String conditionWhereGroupe, String conditionWhereRecherche){
        List<Note> mesNotes = new ArrayList<Note>();

        String conditionWhere = "";

        int archiveValue;
        if(archive == false){
            archiveValue = 0;
        }else{
            archiveValue = 1;
        }

        switch (conditionOrderBy){
            case "date_modif":
                conditionOrderBy += " DESC"; break;
            case "titre":
                conditionOrderBy += " ASC"; break;
            case "niveau_importance":
                conditionOrderBy += " ASC"; break;
            default:
                conditionOrderBy = COL_DATE_MODIF + " DESC"; break;
        }

        if(conditionWhereGroupe != "")
            conditionWhere += " AND " + COL_GROUPE + "= '" + conditionWhereGroupe + "'";

        if(conditionWhereRecherche != "")
            conditionWhere += " AND " + COL_TITRE + " LIKE '%" + conditionWhereRecherche + "%'";

        open();
        String[] listeColonnes ={COL_ID,COL_TITRE,COL_CONTENU,COL_NIVEAU_IMPORTANCE,COL_DATE_MODIF,COL_GROUPE,COL_DTH_RAPPEL};
        Cursor cursor = bdd.query(TABLE_NOTES,listeColonnes,COL_ARCHIVE+"= "+archiveValue + conditionWhere,null,null,null,conditionOrderBy);

        while(cursor.moveToNext()){

            int colIndex = cursor.getColumnIndex(COL_ID);
            int colID = cursor.getInt(colIndex);

            int colIndexTitre = cursor.getColumnIndex(COL_TITRE);
            String colTitre = cursor.getString(colIndexTitre);

            int colIndexContenu = cursor.getColumnIndex(COL_CONTENU);
            String colContenu = cursor.getString(colIndexContenu);

            int colIndexNiveau = cursor.getColumnIndex(COL_NIVEAU_IMPORTANCE);
            int colNiveau = cursor.getInt(colIndexNiveau);

            int colIndexDateModif = cursor.getColumnIndex(COL_DATE_MODIF);
            String colDateModif = cursor.getString(colIndexDateModif);

            int colIndexGroupe = cursor.getColumnIndex(COL_GROUPE);
            String colGroupe = cursor.getString(colIndexGroupe);

            int colIndexDthRappel = cursor.getColumnIndex(COL_DTH_RAPPEL);
            String colDthRappel = cursor.getString(colIndexDthRappel);

            Note n = new Note(colID,colTitre,colContenu,colNiveau,colDateModif,colGroupe,archive,colDthRappel);
            mesNotes.add(n);
        }

        return mesNotes;
    }

    public SQLiteDatabase getMyReadableDatabase(){
        Log.v("export", "Récuperation readable databsae");
        return maBaseSQLite.getReadableDatabase();
    }

}
