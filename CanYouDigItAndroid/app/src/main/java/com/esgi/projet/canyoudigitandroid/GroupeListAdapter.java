package com.esgi.projet.canyoudigitandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jkn1092 on 22/02/2015.
 */
public class GroupeListAdapter extends ArrayAdapter<String> {

    private BlocNotes monBlocNotes;
    private List<String> items;
    private int layoutResourceId;
    private Context context;

    public GroupeListAdapter(Context context, int layoutResourceId, BlocNotes _monBlocNotes, List<String> _items) {
        super(context, layoutResourceId, _items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.monBlocNotes = _monBlocNotes;
        this.items = _items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String groupe = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_list_groupe_layout, parent, false);
        }

        // Lookup view for data population
        TextView tvGroupe = (TextView) convertView.findViewById(R.id.titreGroupe);
        Button btnSupprimerGroupe = (Button) convertView.findViewById(R.id.supprimerGroupe);

        tvGroupe.setText(groupe.toString());

        btnSupprimerGroupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monBlocNotes.supprimerGroupeNotes(groupe);
                items.remove(groupe);
                notifyDataSetChanged();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

}
