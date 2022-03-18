package com.group16.fitnessapp.fragments;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.group16.fitnessapp.R;
import com.group16.fitnessapp.constants.STATE;
import com.group16.fitnessapp.models.ActivityModel;

import java.time.LocalDateTime;

public class MusicPlayerFragment extends ActivityFragment {
    private MediaPlayer mediaPlayer;

    public MusicPlayerFragment(ActivityModel activityModel) {
        super(activityModel);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.frag_music, parent, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.activityModel = new ActivityModel(STATE.RUNNING, LocalDateTime.now());
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mediaPlayer = MediaPlayer.create(getActivity(), R.raw.levels);
        this.mediaPlayer.setLooping(true);
        this.mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        this.mediaPlayer.release();
        this.mediaPlayer = null;
        super.onDestroy();
    }
}
