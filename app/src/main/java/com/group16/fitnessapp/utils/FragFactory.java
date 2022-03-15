package com.group16.fitnessapp.utils;

import android.annotation.SuppressLint;
import android.location.Location;

import androidx.fragment.app.Fragment;

import com.group16.fitnessapp.fragments.GreetingFragment;
import com.group16.fitnessapp.fragments.MapFragment;
import com.group16.fitnessapp.fragments.MusicPlayerFragment;
import com.group16.fitnessapp.constants.STATE;
import com.group16.fitnessapp.models.ActivityModel;

import java.time.LocalDateTime;

public class FragFactory {

    @SuppressLint("NewApi")
    public Fragment getFragment(STATE state, Location location) {
        Fragment f;
        LocalDateTime now = LocalDateTime.now();
        switch (state) {
            case WALKING:
                f = new MapFragment(new ActivityModel(STATE.WALKING, now, location));
                break;
            case IN_VAN:
                f = new MapFragment(new ActivityModel(STATE.IN_VAN, now, location));
                break;
            case RUNNING:
                f =  new MusicPlayerFragment(new ActivityModel(STATE.RUNNING, now, location));
                break;
            case REST:
            default:
                f = new GreetingFragment(new ActivityModel(STATE.REST));
                break;
        }
        return f;
    }
}
