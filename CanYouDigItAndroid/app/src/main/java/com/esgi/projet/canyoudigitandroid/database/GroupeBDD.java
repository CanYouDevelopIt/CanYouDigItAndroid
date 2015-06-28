package com.esgi.projet.canyoudigitandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pascal on 08/02/2015.
 */
public class GroupeBDD {

    private static final int VERSION_BDD = 3;
    private static final String NOM_BDD = "notes.db";

    private static final String TABLE_GROUPE = "table_groupe";
    private static final String UID_GROUPE = "_id";
    private static final String NOM_GROUPE = "nom_groupe";


    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public GroupeBDD(Context context){
        maBaseSQLite = new MaBaseSQLite(context);
    }

    public void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public long insertGroupe(String nomDuGroupe){
        if(nomDuGroupe == null || nomDuGroupe.equals("")){
            return -1;
        }
        open();
        ContentValues values = new ContentValues();
        values.put(NOM_GROUPE,nomDuGroupe);
        return bdd.insert(TABLE_GROUPE, null, values);
    }
    public long modifyGroupe(String oldGroupeName,String newGroupeName){
        if(newGroupeName == null || newGroupeName.equals("")){
            return -1;
        }
        open();
        ContentValues values = new ContentValues();
        values.put(NOM_GROUPE,newGroupeName);
        String[] arguments = {oldGroupeName};
        return bdd.update(TABLE_GROUPE,values, NOM_GROUPE +" = ? ", arguments);
    }
    public List<String> getAllData(){
        open();
        String[] listeColonnes ={UID_GROUPE,NOM_GROUPE};
        Cursor cursor = bdd.query(TABLE_GROUPE, listeColonnes,null,null,null,null,null);

        List<String> groupeNotes = new ArrayList<String>();
        while(cursor.moveToNext()){
            // On récupère le UID
            int index = cursor.getColumnIndex(UID_GROUPE);
            int cursorIndex = cursor.getInt(index);
            //On recupère le Nom Du groupe
            int positionNomGroupe = cursor.getColumnIndex(NOM_GROUPE);
            String nomDuGroupe = cursor.getString(positionNomGroupe);

            groupeNotes.add(nomDuGroupe);
        }
        close();
        return groupeNotes;
    }

    public int deleteGroupe(String nomGroupe) {
        open();
        Log.v("GROUPEBDD", "Suppresion de " + nomGroupe);
        String[] arguments = {nomGroupe};
        return bdd.delete(TABLE_GROUPE, NOM_GROUPE + " = ?", arguments);
    }
}
