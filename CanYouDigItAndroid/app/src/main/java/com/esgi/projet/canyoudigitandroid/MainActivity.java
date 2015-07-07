package com.esgi.projet.canyoudigitandroid;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.projet.canyoudigitandroid.fragment.MainFragment;
import com.esgi.projet.canyoudigitandroid.fragment.NoteFragment;
import com.esgi.projet.canyoudigitandroid.fragment.ParametreFragment;
import com.esgi.projet.canyoudigitandroid.model.BlocNotes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends ActionBarActivity {

    private static final String KEY_FRAGMENT = "FRAGMENT_KEY";
    private String mFragment;

    private final MainFragment mMainFragment = new MainFragment();
    private final ParametreFragment mParametreFragment = new ParametreFragment();
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getFragmentManager().getBackStackEntryCount() == 0) finish();
            }
        });

        if (mFragment != null) {
            if(mFragment.equals(mMainFragment.getClass().getSimpleName())){ showMainFragment();}
            else if(mFragment.equals(mParametreFragment.getClass().getSimpleName())){showParametreFragment();}
            else{showNoteFragment();}
        }else{
            showMainFragment();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_parametres:
                showParametreFragment();
                return true;
            case R.id.action_export:
                // --------
                BlocNotes exprotBlocNotes = new BlocNotes(this);
                String exportFileDirectory = getFilesDir().toString();
                exprotBlocNotes.exportNotes(exportFileDirectory);
                Toast.makeText(this, R.string.export_success, Toast.LENGTH_LONG).show();

                return true;
            case R.id.action_import:
                BlocNotes importBlocNotes = new BlocNotes(this);
                String importFileDirectory = getFilesDir().toString();
                importBlocNotes.importNotes(importFileDirectory);
                Toast.makeText(this, R.string.import_success, Toast.LENGTH_LONG).show();
                finish();startActivity(getIntent());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFragment(final Fragment fragment) {
        if (fragment == null) {
            return;
        }

        mFragment = fragment.getClass().getSimpleName();

        final FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();

        //ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.mainFrameActivty, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void showMainFragment(){
        showFragment(this.mMainFragment);
    }

    public void showParametreFragment() {
            showFragment(this.mParametreFragment);
    }

    public void showNoteFragment() { showFragment(new NoteFragment());}

    public void ajouterUneNote(View v){ showFragment(new NoteFragment());}

    @Override
    public void onBackPressed(){
        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }else
            super.onBackPressed();
    }

}
