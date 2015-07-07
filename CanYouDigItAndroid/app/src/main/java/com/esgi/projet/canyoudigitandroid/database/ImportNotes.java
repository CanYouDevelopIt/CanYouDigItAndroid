package com.esgi.projet.canyoudigitandroid.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.esgi.projet.canyoudigitandroid.model.BlocNotes;
import com.esgi.projet.canyoudigitandroid.model.Note;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pascal on 05/07/2015.
 */
public class ImportNotes {

    private static final String TAG = "ImportNotes";
    private BlocNotes blocNotes;

    public ImportNotes(String fileDirectory,BlocNotes _blocNotes){

        blocNotes = _blocNotes;
        File myFile = new File(fileDirectory+"/export.xml");
        String path = myFile.getPath();
        Log.v(TAG, "Path = " + path);

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(path);
            parse(inputStream);
        } catch (FileNotFoundException e) {
            Log.v(TAG, "Erreur import Notes");
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.v(TAG, "Erreur Close InputStream Notes");
                e.printStackTrace();
            }
        }
    }


    public void parse(InputStream inputStream){
        Log.v(TAG, "Parse import Notes");
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        try{
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            parser.setInput(inputStream,null);

            String dbURL;
            String table = null;
            Note uneNote = null;
            String text = null;
            String columnName = null;
            int eventType = parser.getEventType();
            while(eventType !=XmlPullParser.END_DOCUMENT){
                String tagname = parser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if(tagname.equalsIgnoreCase("export-database")){
                            dbURL = parser.getAttributeValue(0);
                        }
                        if(tagname.equalsIgnoreCase("table")){
                            table = parser.getAttributeValue(0);
                        }
                        if(tagname.equalsIgnoreCase("row")){
                            if(table.equals("table_notes")){
                                uneNote = new Note();
                            }
                        }
                        if(tagname.equalsIgnoreCase("col")){
                            columnName = parser.getAttributeValue(0);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(tagname.equalsIgnoreCase("col")){

                            if(table.equals("table_notes") && uneNote!=null){

                                if(columnName.equals("id")){
                                    // Ici nous n'allons pas récupérer l'id
                                    //uneNote.setId(Integer.parseInt(text));
                                }else if (columnName.equals("titre")){
                                    uneNote.setTitre(text);
                                }else if (columnName.equals("contenu")){
                                    uneNote.setContenu(text);
                                }else if (columnName.equals("niveau_importance")){
                                    uneNote.setNiveauImportance(Integer.parseInt(text));
                                }else if (columnName.equals("date_modif")){
                                    uneNote.setDateModif(text);
                                }else if (columnName.equals("groupe")){
                                    uneNote.setGroupeNotes(text);
                                }else if (columnName.equals("archive")){
                                    uneNote.setArchive(Boolean.valueOf(text));
                                }else if (columnName.equals("dth_rappel")){
                                    uneNote.setDthRappel(text);
                                }
                            }

                            if(table.equals("table_groupe")){
                                if (columnName.equals("_id")){
                                    // pour le group pas besoin de récuperé l'id
                                }else if (columnName.equals("nom_groupe")){
                                    blocNotes.ajouterGroupeNotes(text);
                                }
                            }
                        }
                        // Fin d'une ligne - on ajoute la note dans la liste
                        if(tagname.equalsIgnoreCase("row")) {
                            if(uneNote !=null && table.equals("table_notes")){
                                Log.v(TAG, "Une "+uneNote.toString());
                                blocNotes.ajouterNote(uneNote);
                            }
                        }

                    default:
                        break;

                }

                eventType = parser.next();
            }

        }catch(Exception e){
            Log.v(TAG, "Erreur lors de l'import des notes.");
            e.printStackTrace();
        }
    }
}
