package com.group16.fitnessapp.fragments;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.group16.fitnessapp.R;
import com.group16.fitnessapp.models.ActivityModel;

import java.util.Date;
import java.util.Locale;

public class GreetingFragment extends ActivityFragment {

    public GreetingFragment(ActivityModel activityModel) {
        super(activityModel);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.frag_greeting, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        TextClock clock = view.findViewById(R.id.tc_clock);
        TextView date = view.findViewById(R.id.tv_date);

        date.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        clock.refreshTime();
        Log.i("Greeting/Rest", "Processing user activity");
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
