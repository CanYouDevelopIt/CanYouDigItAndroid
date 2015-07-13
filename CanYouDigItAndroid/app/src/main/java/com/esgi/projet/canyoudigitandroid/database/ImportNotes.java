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

    private static final String TABLE_NOTES = "table_notes";
    private static final String COL_ID = "id";
    private static final String COL_TITRE = "titre";
    private static final String COL_CONTENU = "contenu";
    private static final String COL_NIVEAU_IMPORTANCE = "niveau_importance";
    private static final String COL_DATE_MODIF = "date_modif";
    private static final String COL_GROUPE = "groupe";
    private static final String COL_ARCHIVE = "archive";
    private static final String COL_DTH_RAPPEL = "dth_rappel";


    private static final String TABLE_GROUPE = "table_groupe";
    private static final String UID_GROUPE = "_id";
    private static final String NOM_GROUPE = "nom_groupe";


    private static final String BALISE_XML_ROW = "row";
    private static final String BALISE_XML_COL = "col";
    private static final String BALISE_XML_TABLE = "table";

    private static final String TAG = "ImportNotes";
    private BlocNotes blocNotes;
    private boolean importIsASuccess = true;

    public ImportNotes(String fileDirectory,BlocNotes _blocNotes){

        blocNotes = _blocNotes;
        File myFile = new File(fileDirectory);
        String path = myFile.getPath();

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(path);
            parse(inputStream);
        } catch (FileNotFoundException e) {
            importIsASuccess = false;
            Log.v(TAG, "Erreur import avec l'inputsream Notes");
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                importIsASuccess = false;
                Log.v(TAG, "Erreur lors de la fermeture  de l'InputStream Notes");
                e.printStackTrace();
            }
        }
    }


    public void parse(InputStream inputStream){
        Log.v(TAG, "Démarrage de l'import des Notes");
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        try{
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            parser.setInput(inputStream,null);

            String table = null;
            Note uneNote = null;
            String text = null;
            String columnName = null;
            int eventType = parser.getEventType();
            while(eventType !=XmlPullParser.END_DOCUMENT){
                String tagname = parser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if(tagname.equalsIgnoreCase(BALISE_XML_TABLE)){
                            table = parser.getAttributeValue(0);
                        }
                        if(tagname.equalsIgnoreCase(BALISE_XML_ROW)){
                            if(table.equals(TABLE_NOTES)){
                                uneNote = new Note();
                            }
                        }
                        if(tagname.equalsIgnoreCase(BALISE_XML_COL)){
                            columnName = parser.getAttributeValue(0);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(tagname.equalsIgnoreCase(BALISE_XML_COL)){

                            if(table.equals(TABLE_NOTES) && uneNote!=null){
                                if(columnName.equals(COL_ID)){
                                    // Ici nous n'allons pas récupérer l'id
                                    //uneNote.setId(Integer.parseInt(text));
                                }else if (columnName.equals(COL_TITRE)){
                                    uneNote.setTitre(text);
                                }else if (columnName.equals(COL_CONTENU)){
                                    uneNote.setContenu(text);
                                }else if (columnName.equals(COL_NIVEAU_IMPORTANCE)){
                                    uneNote.setNiveauImportance(Integer.parseInt(text));
                                }else if (columnName.equals(COL_DATE_MODIF)){
                                    uneNote.setDateModif(text);
                                }else if (columnName.equals(COL_GROUPE)){
                                    uneNote.setGroupeNotes(text);
                                }else if (columnName.equals(COL_ARCHIVE)){
                                    boolean isArchive = Integer.parseInt(text) == 1;
                                    uneNote.setArchive(isArchive);
                                }else if (columnName.equals(COL_DTH_RAPPEL)){
                                    uneNote.setDthRappel(text);
                                }
                            }

                            if(table.equals(TABLE_GROUPE)){
                                if (columnName.equals(UID_GROUPE)){
                                    // pour le group pas besoin de récuperé l'id
                                }else if (columnName.equals(NOM_GROUPE)){
                                    Log.v(TAG, "Ajout d'un nom de groupe à la BDD");
                                    blocNotes.ajouterGroupeNotes(text);
                                }
                            }
                        }
                        // Fin d'une ligne - on ajoute la note dans la liste
                        if(tagname.equalsIgnoreCase(BALISE_XML_ROW)) {
                            if(uneNote !=null && table.equals(TABLE_NOTES)){
                                Log.v(TAG, "Ajout d'une note à la BDD");
                                blocNotes.ajouterNote(uneNote);
                            }
                        }

                    default:
                        break;

                }

                eventType = parser.next();
            }

        }catch(Exception e){
            importIsASuccess = false;
            Log.v(TAG, "Erreur lors de l'import des notes.");
            e.printStackTrace();
        }
    }
    public boolean isImportASuccess(){
        return importIsASuccess;
    }
}
