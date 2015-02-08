package com.esgi.projet.canyoudigitandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jkn1092 on 06/02/2015.
 */

public class MaBaseSQLite extends SQLiteOpenHelper {

    private static final String TABLE_NOTES = "table_notes";
    private static final String COL_ID = "id";
    private static final String COL_TITRE = "titre";
    private static final String COL_CONTENU = "contenu";
    private static final String COL_NIVEAU_IMPORTANCE = "niveau_importance";
    private static final String COL_DATE_MODIF = "date_modif";
    private static final String COL_GROUPE = "groupe";
    private static final String COL_ARCHIVE = "archive";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_NOTES + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TITRE + " TEXT NOT NULL, "
            + COL_CONTENU + " TEXT NOT NULL, " + COL_NIVEAU_IMPORTANCE + " INTEGER NOT NULL, "
            + COL_DATE_MODIF + " TEXT NOT NULL, " + COL_GROUPE + " TEXT, " + COL_ARCHIVE + " BOOLEAN);";

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
    private static final int VERSION_BDD = 2;
    private static final String NOM_BDD = "notes.db";
    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
    // Données de la table GROUPE
    private static final String TABLE_GROUPE = "table_groupe";
    private static final String UID_GROUPE = "_id";
    private static final String NOM_GROUPE = "nom_groupe";

    private static final String CREATE_TABLE_GROUPE = "CREATE TABLE "+TABLE_GROUPE+" ("+UID_GROUPE+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NOM_GROUPE+" VARCHAR(255));";
    private static final String DROP_TABLE_GROUPE = "DROP TABLE IF EXISTS " + TABLE_GROUPE + ";";

    private Context context;
    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    public MaBaseSQLite(Context context){
        super(context,NOM_BDD, null, VERSION_BDD);
        this.context=context;
    }

    public MaBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("MaBaseSQLite","Création TABLE NOTE");
        db.execSQL(CREATE_BDD);
        Log.v("MaBaseSQLite", "Création TABLE GROUPE");
        db.execSQL(CREATE_TABLE_GROUPE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_NOTES + ";");
        db.execSQL(DROP_TABLE_GROUPE);
        onCreate(db);
    }
}

