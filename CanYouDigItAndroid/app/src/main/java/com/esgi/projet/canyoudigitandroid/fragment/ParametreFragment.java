package com.esgi.projet.canyoudigitandroid.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.esgi.projet.canyoudigitandroid.adapter.GroupeListAdapter;
import com.esgi.projet.canyoudigitandroid.R;
import com.esgi.projet.canyoudigitandroid.model.BlocNotes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ParametreFragment extends Fragment {

    private final String TAG = this.getClass().getName();

    private BlocNotes monBlocNotes;
    private EditText groupeToAdd;
    private ListView listLayoutGroupe;
    private GroupeListAdapter gAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parametre, container, false);

        monBlocNotes = new BlocNotes(getActivity());
        listLayoutGroupe = (ListView) view.findViewById(R.id.listGroupes);
        groupeToAdd   = (EditText) view.findViewById(R.id.ajouteGroupe);
        ImageView imageCreerGroupe = (ImageView) view.findViewById(R.id.imgAjouterGroupe);
        final Spinner spinnerLangage = (Spinner) view.findViewById(R.id.spinnerLanguage);

        spinnerLangage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    Locale locale = new Locale("fr");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getActivity().getApplicationContext().getResources().updateConfiguration(config, null);
                }else{
                    Locale locale = new Locale("");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getActivity().getApplicationContext().getResources().updateConfiguration(config, null);
                }
             }
             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

                imageCreerGroupe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newGroupe = groupeToAdd.getText().toString();
                        groupeToAdd.setText("");
                        monBlocNotes.ajouterGroupeNotes(newGroupe);
                        gAdapter = new GroupeListAdapter(getActivity(), R.layout.my_list_note_layout, monBlocNotes, monBlocNotes.getMesGroupesNotes());
                        gAdapter.notifyDataSetChanged();
                    }
                });

        gAdapter = new GroupeListAdapter(getActivity(), R.layout.my_list_note_layout, monBlocNotes, monBlocNotes.getMesGroupesNotes());
        listLayoutGroupe.setAdapter(gAdapter);
        registerForContextMenu(listLayoutGroupe);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        if (v.getId()==R.id.listGroupes) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(monBlocNotes.getMesGroupesNotes().get(info.position));
            menu.add(getResources().getString(R.string.popup_menu_edit));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int itemSelectedPosition = (int) info.position;

        if(item.getTitle().equals(getResources().getString(R.string.popup_menu_edit))){
            final EditText userInput = new EditText(getActivity());

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());
            final String oldGroupe = monBlocNotes.getMesGroupesNotes().get(itemSelectedPosition);
            alertDialogBuilder.setTitle(getResources().getString(R.string.popup_menu_modifier)+oldGroupe);
            alertDialogBuilder.setView(userInput);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton(getResources().getString(R.string.popup_menu_edition_valider),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            String newGroupe = userInput.getText().toString();
                            if(newGroupe != null && !newGroupe.equals("")) {
                                monBlocNotes.modifyGroupe(itemSelectedPosition,oldGroupe,newGroupe);
                                gAdapter.notifyDataSetChanged();
                                showToastMessage(getResources().getString(R.string.popup_menu_edition_reussi));
                            }else{
                                showToastMessage(getResources().getString(R.string.popup_menu_edition_rate));
                            }
                        }
                    });
            alertDialogBuilder.setNegativeButton(getResources().getString(R.string.popup_menu_edition_cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
        return true;
    }

    public void showToastMessage(String message){
        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}
