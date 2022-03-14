package com.group16.fitnessapp.utils;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.group16.fitnessapp.R;
import com.group16.fitnessapp.constants.STATE;

public class FragManager {
    private FragmentManager manager;

    private FragManager(){}

    private enum Singleton {
        INSTANCE;

        private final FragManager instance;

        Singleton() {
            instance = new FragManager();
        }

        private FragManager getInstance() {
            return instance;
        }
    }

    public static FragManager getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    public void removeFragment(Context context, Fragment f, STATE state) {
        this.manager = ((AppCompatActivity) context).getSupportFragmentManager();

        Fragment temp_f = this.manager.findFragmentByTag(String.valueOf(state));
        if(temp_f != null) {
            this.manager.beginTransaction().remove(f).commitNow();
            this.manager.popBackStackImmediate();
            Log.i("FragManager", "Removing " + state);
        }
    }

    public void addFragment(Context context, Fragment f, STATE state) {
        this.manager = ((AppCompatActivity) context).getSupportFragmentManager();

        Fragment temp_f = this.manager.findFragmentByTag(String.valueOf(state));
        if(temp_f == null) {
            manager.beginTransaction().add(R.id.fl_component, f, String.valueOf(state)).commitNow();
            Log.i("FragManager", "Adding " + state);
        }
    }
}
