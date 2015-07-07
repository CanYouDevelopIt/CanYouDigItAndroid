package com.esgi.projet.canyoudigitandroid.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by pascal on 05/07/2015.
 */
public class ExportNotes {

    private SQLiteDatabase  sdb;



    private static final String CLOSING_WITH_TICK = "'>";
    private static final String START_DB = "<export-database name='";
    private static final String END_DB = "</export-database>";
    private static final String START_TABLE = "<table name='";
    private static final String END_TABLE = "</table>";
    private static final String START_ROW = "<row>";
    private static final String END_ROW = "</row>";
    private static final String START_COL = "<col name='";
    private static final String END_COL = "</col>";

    private BufferedOutputStream mbufferos;


    public ExportNotes(String fileDirectory,SQLiteDatabase db){
        Log.v("export", "Constructeur Export Notes");
        sdb = db;
        File myFile = new File(fileDirectory+"/export.xml");

        try {
            myFile.createNewFile();
            Log.v("export", "Absaloute path = " + myFile.getAbsolutePath());
            Log.v("export", "Path = " + myFile.getPath());
            FileOutputStream fOut = new FileOutputStream(myFile);
            mbufferos = new BufferedOutputStream(fOut);
            exportData();
        } catch (IOException e) {
            Log.v("export","Erreur dans ExportNotes");
            e.printStackTrace();
        }
    }

    public void exportData() {
        Log.v("export","exportData");
        // requête pour récupérer tous les noms des tables
        String sql = "SELECT * FROM sqlite_master";

        try {
            startDbExport(sdb.getPath());

            Cursor cur = sdb.rawQuery(sql, new String[0]);
            cur.moveToFirst();

            String tableName;
            while (cur.getPosition() < cur.getCount()) {
                tableName = cur.getString(cur.getColumnIndex("name"));

                // Tables a ignoré
                if (!tableName.equals("android_metadata")
                        && !tableName.equals("sqlite_sequence")) {
                    exportTable(tableName);
                }
            cur.moveToNext();
            }
            endDbExport();
            closeBuffer();
            }catch(IOException e){
                e.printStackTrace();
            }finally{
            try {
                closeBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeBuffer() throws IOException {
            if (mbufferos != null) {
                mbufferos.close();
            }
    }


    private void exportTable(String tableName) throws IOException {
        startTable(tableName);

        // get everything from the table
        String sql = "select * from " + tableName;
        Cursor cur = sdb.rawQuery(sql, new String[0]);
        int numcols = cur.getColumnCount();

        cur.moveToFirst();

        // création de la balise <row>
        // puis ajout des balises col avec leurs valeurs
        // puis fermeture de la ligne </row>
        while (cur.getPosition() < cur.getCount()) {
            startRow();
            String name;
            String val;
            for (int idx = 0; idx < numcols; idx++) {
                name = cur.getColumnName(idx);
                val = cur.getString(idx);
                addColumn(name, val);
            }

            endRow();
            cur.moveToNext();
        }

        cur.close();

        endTable();
    }

    public void startDbExport(String dbName) throws IOException {
        String stg = START_DB + dbName + CLOSING_WITH_TICK;
        Log.v("export",stg);
        mbufferos.write(stg.getBytes());
    }
    public void startTable(String tableName) throws IOException {
        String stg = START_TABLE + tableName + CLOSING_WITH_TICK;
        Log.v("export",stg);
        mbufferos.write(stg.getBytes());
    }

    public void startRow() throws IOException {
        Log.v("export",START_ROW);
        mbufferos.write(START_ROW.getBytes());
    }

    public void addColumn(String name, String val) throws IOException {
        String stg = START_COL + name + CLOSING_WITH_TICK + val + END_COL;
        Log.v("export",stg);
        mbufferos.write(stg.getBytes());
    }
    public void endRow() throws IOException {
        Log.v("export",END_ROW);
        mbufferos.write(END_ROW.getBytes());
    }
    public void endTable() throws IOException {
        Log.v("export",END_TABLE);
        mbufferos.write(END_TABLE.getBytes());
    }

    public void endDbExport() throws IOException {
        Log.v("export",END_TABLE);
        mbufferos.write(END_DB.getBytes());
    }
}
