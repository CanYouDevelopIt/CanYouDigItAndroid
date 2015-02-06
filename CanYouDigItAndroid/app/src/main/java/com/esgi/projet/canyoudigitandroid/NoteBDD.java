package com.esgi.projet.canyoudigitandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jkn1092 on 06/02/2015.
 */
public class NoteBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "notes.db";

    private static final String TABLE_NOTES = "table_notes";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_TITRE = "titre";
    private static final int NUM_COL_TITRE = 1;
    private static final String COL_CONTENU = "contenu";
    private static final int NUM_COL_CONTENU = 2;
    private static final String COL_NIVEAU_IMPORTANCE = "niveau_importance";
    private static final int NUM_COL_NIVEAU_IMPORTANCE = 3;
    private static final String COL_DATE_MODIF = "date_modif";
    private static final int NUM_COL_DATE_MODIF = 4;
    private static final String COL_GROUPE = "groupe";
    private static final int NUM_COL_GROUPE = 5;

    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public NoteBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertLivre(Note n){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_TITRE, n.getTitre());
        values.put(COL_CONTENU, n.getContenu());
        values.put(COL_NIVEAU_IMPORTANCE, n.getNiveauImportance());
        values.put(COL_DATE_MODIF, n.getDateModif());
        values.put(COL_GROUPE, n.getGroupeNotes());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_NOTES, null, values);
    }

    public int updateLivre(int id, Note n){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        //ContentValues values = new ContentValues();
        //values.put(COL_ISBN, livre.getIsbn());
        //values.put(COL_TITRE, livre.getTitre());
        //return bdd.update(TABLE_NOTES, values, COL_ID + " = " +id, null);
        return id;
    }

    public int removeLivreWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_NOTES, COL_ID + " = " +id, null);
    }

}
