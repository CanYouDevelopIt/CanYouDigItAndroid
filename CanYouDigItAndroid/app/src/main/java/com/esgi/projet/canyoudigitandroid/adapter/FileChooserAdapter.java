package com.esgi.projet.canyoudigitandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.projet.canyoudigitandroid.R;
import com.esgi.projet.canyoudigitandroid.model.BlocNotes;

import java.util.List;

/**
 * Created by pascal on 12/07/2015.
 */
public class FileChooserAdapter extends ArrayAdapter<String> {

    private List<String> items;
    private int layoutResourceId;
    private Context context;
    private static final String XML_EXTENSION = ".xml";

    public FileChooserAdapter(Context context, int layoutResourceId, List<String> _items){
        super(context, layoutResourceId, _items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = _items;
    }


    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final String fileName = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_list_file_layout, parent, false);
        }

        TextView file = (TextView) convertView.findViewById(R.id.nomFichier);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageFichier);
        if(fileName.endsWith(XML_EXTENSION)){
            imageView.setImageResource(R.drawable.file);
        }else{
            imageView.setImageResource(R.drawable.folder);
        }

        file.setText(fileName.toString());
        return convertView;
    }
}
