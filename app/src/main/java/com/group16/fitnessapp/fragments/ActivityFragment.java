package com.group16.fitnessapp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.group16.fitnessapp.models.ActivityModel;



public abstract class ActivityFragment extends Fragment {

    protected ActivityModel activityModel = null;

    @Override
    public abstract View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public abstract void onViewCreated(@NonNull View view, Bundle savedInstanceState);
}
