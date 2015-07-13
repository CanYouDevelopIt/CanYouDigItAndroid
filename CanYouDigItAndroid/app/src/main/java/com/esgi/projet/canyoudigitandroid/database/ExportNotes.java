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

    private static final String TAG = "ExportNotes";


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

    private boolean exportIsASuccess = true;
    public ExportNotes(String fileDirectory,SQLiteDatabase db){
        sdb = db;
        File myFile = new File(fileDirectory);

        try {
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            mbufferos = new BufferedOutputStream(fOut);
            exportData();
        } catch (IOException e) {
            Log.v(TAG,"Erreur dans xportNotes");
            exportIsASuccess = false;
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
            Log.v(TAG,"Erreur lors du parsing.");
            exportIsASuccess = false;
            e.printStackTrace();
            }finally{
            try {
                closeBuffer();
            } catch (IOException e) {
                Log.v(TAG,"Erreur fermeture buffer");
                exportIsASuccess = false;
                e.printStackTrace();
            }
        }
    }

    private void closeBuffer() throws IOException {
            if (mbufferos != null) {
                mbufferos.close();
                Log.v(TAG,"Fermeture buffer réussi.");
            }
    }


    private void exportTable(String tableName) throws IOException {
        startTable(tableName);

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
        mbufferos.write(stg.getBytes());
    }
    public void startTable(String tableName) throws IOException {
        String stg = START_TABLE + tableName + CLOSING_WITH_TICK;
        mbufferos.write(stg.getBytes());
    }

    public void startRow() throws IOException {
        mbufferos.write(START_ROW.getBytes());
    }

    public void addColumn(String name, String val) throws IOException {
        String stg = START_COL + name + CLOSING_WITH_TICK + val + END_COL;
        mbufferos.write(stg.getBytes());
    }
    public void endRow() throws IOException {
        mbufferos.write(END_ROW.getBytes());
    }
    public void endTable() throws IOException {
        mbufferos.write(END_TABLE.getBytes());
    }

    public void endDbExport() throws IOException {
        mbufferos.write(END_DB.getBytes());
    }

    public boolean isExportASuccess(){
        return exportIsASuccess;
    }

}
