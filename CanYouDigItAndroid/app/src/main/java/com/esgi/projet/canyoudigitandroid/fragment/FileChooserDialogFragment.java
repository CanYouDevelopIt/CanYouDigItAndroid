package com.esgi.projet.canyoudigitandroid.fragment;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.projet.canyoudigitandroid.MainActivity;
import com.esgi.projet.canyoudigitandroid.R;
import com.esgi.projet.canyoudigitandroid.adapter.FileChooserAdapter;
import com.esgi.projet.canyoudigitandroid.model.BlocNotes;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileChooserDialogFragment extends DialogFragment implements View.OnClickListener,AdapterView.OnItemClickListener{



    private static final String METHOD = "METHOD";
    private static final String EXPORT = "EXPORT";
    private static final String IMPORT = "IMPORT";
    private static final String EXPORT_FILE_NAME = "/export.xml";
    private static final String ROOT_DIRECTORY = "/";
    private static final String XML_EXTENSION = ".xml";

    private Button buttonUp;
    private TextView textFolfder;

    private ListView dialogListView;
    private File root;
    private File curFolder;
    private List<String> fileList = new ArrayList<String>();
    private Button yesExport,cancelExport;
    private boolean mustExport = false;
    private FileFilter ff;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_file_chooser_dialog,null);

        Bundle args = getArguments();
        String method = args.getString(METHOD);

        yesExport = (Button) view.findViewById(R.id.yesExport);
        cancelExport = (Button) view.findViewById(R.id.cancelExport);
        if(method.equals(IMPORT)){
            yesExport.setVisibility(View.GONE);
            cancelExport.setVisibility(View.GONE);
        }else{
            mustExport = true;
            yesExport.setOnClickListener(this);
            cancelExport.setOnClickListener(this);
        }

        createFileFilters();

        root = new File(ROOT_DIRECTORY);

        curFolder = root;
        textFolfder = (TextView) view.findViewById(R.id.folder);
        textFolfder.setText(root.getAbsolutePath());

        buttonUp = (Button) view.findViewById(R.id.up);
        buttonUp.setOnClickListener(this);

        dialogListView = (ListView)view.findViewById(R.id.dialoglist);
        listDirectory(root);
        dialogListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.up){
            listDirectory(curFolder.getParentFile());
        }

        if(v.getId()==R.id.yesExport){
            dismiss();
            String fileToExportTo = textFolfder.getText().toString()+EXPORT_FILE_NAME;

            BlocNotes exprotBlocNotes = new BlocNotes(getActivity());
            boolean exportIsASuccess = exprotBlocNotes.exportNotes(getActivity(),fileToExportTo);
            if(exportIsASuccess ){
              Toast.makeText(getActivity(),fileToExportTo, Toast.LENGTH_LONG).show();
            }else{
              Toast.makeText(getActivity(),R.string.export_fail, Toast.LENGTH_SHORT).show();
            }
        }
        
        if(v.getId()==R.id.cancelExport){
            dismiss();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String fullPath = textFolfder.getText().toString()+ROOT_DIRECTORY+fileList.get(position);
            File selected = new File(fullPath);
            if(selected.isDirectory()){
                listDirectory(selected);
            }else{
                BlocNotes importBlocNotes = new BlocNotes(getActivity());
                String selectedFile = selected.toString();

                boolean isASuccess = importBlocNotes.importNotes(selectedFile);
                if(isASuccess) {
                    Toast.makeText(getActivity(), R.string.import_success, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }else{
                    Toast.makeText(getActivity(), R.string.import_fail, Toast.LENGTH_SHORT).show();
                }
            }
    }
    void listDirectory(File f){
        if(f.equals(root)){
            buttonUp.setEnabled(false);
            yesExport.setEnabled(false);
        }else{
            buttonUp.setEnabled(true);
            yesExport.setEnabled(true);
        }

        curFolder = f;
        textFolfder.setText(f.getPath());
        File[] files = f.listFiles(ff);

        fileList.clear();
        for(File file : files){
            if(file.canWrite() && !file.getName().startsWith(".")) {
                        fileList.add(file.getName());
            }
        }

        FileChooserAdapter fileChooserAdapter = new FileChooserAdapter(getActivity(),R.layout.my_list_file_layout,fileList);
        dialogListView.setAdapter(fileChooserAdapter);
        fileChooserAdapter.notifyDataSetChanged();
        dialogListView.setOnItemClickListener(this);
    }

    public void createFileFilters(){
        // créations des filtres
        if(mustExport){
            ff = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            };

        }else{
            ff = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory() || pathname.getPath().endsWith(XML_EXTENSION);
                }
            };
        }
    }
}
