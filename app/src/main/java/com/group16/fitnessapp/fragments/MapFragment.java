package com.group16.fitnessapp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.group16.fitnessapp.R;
import com.group16.fitnessapp.constants.STATE;
import com.group16.fitnessapp.models.ActivityModel;

import java.time.LocalDateTime;

public class MapFragment extends ActivityFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.frag_map, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.activityModel = new ActivityModel(STATE.RUNNING, LocalDateTime.now());
        Toast.makeText(getActivity(),"User walking...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
