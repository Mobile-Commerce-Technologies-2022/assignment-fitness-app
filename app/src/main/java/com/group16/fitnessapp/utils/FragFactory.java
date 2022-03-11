package com.group16.fitnessapp.utils;

import androidx.fragment.app.Fragment;

import com.group16.fitnessapp.fragments.GreetingFragment;
import com.group16.fitnessapp.fragments.MapFragment;
import com.group16.fitnessapp.fragments.MusicPlayerFragment;
import com.group16.fitnessapp.constants.STATE;

public class FragFactory {

    public Fragment getFragment(STATE state) {
        Fragment f = null;
        switch (state) {
            case WALKING:
            case IN_VAN:
                f = new MapFragment();
                break;
            case RUNNING:
                f =  new MusicPlayerFragment();
                break;
            case REST:
                f = new GreetingFragment();
                break;
            default:
                break;
        }
        return f;
    }
}
