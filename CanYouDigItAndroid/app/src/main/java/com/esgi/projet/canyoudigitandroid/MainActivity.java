package com.esgi.projet.canyoudigitandroid;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.esgi.projet.canyoudigitandroid.fragment.MainFragment;
import com.esgi.projet.canyoudigitandroid.fragment.NoteFragment;
import com.esgi.projet.canyoudigitandroid.fragment.ParametreFragment;
import com.esgi.projet.canyoudigitandroid.model.BlocNotes;

public class MainActivity extends FragmentActivity {

    private static final String KEY_FRAGMENT = "FRAGMENT_KEY";
    private String mFragment;

    private final MainFragment mMainFragment = new MainFragment();
    private final ParametreFragment mParametreFragment = new ParametreFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
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
