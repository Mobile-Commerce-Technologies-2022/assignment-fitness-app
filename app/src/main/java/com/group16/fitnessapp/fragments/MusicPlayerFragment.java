package com.group16.fitnessapp.fragments;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.group16.fitnessapp.R;
import com.group16.fitnessapp.constants.STATE;
import com.group16.fitnessapp.models.ActivityModel;
import com.group16.fitnessapp.utils.DatabaseLoader;
import com.group16.fitnessapp.utils.UtilsLoader;

import java.time.LocalDateTime;

public class MusicPlayerFragment extends ActivityFragment {
    private MediaPlayer mediaPlayer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
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
        super.onDestroy();
        this.mediaPlayer.release();
        this.mediaPlayer = null;
        this.activityModel.setTimeAfterActivity(LocalDateTime.now());
        String str = "You have just " + this.activityModel.getTYPE()
                + " for " + UtilsLoader.getInstance().secondToHHMMSS(this.activityModel.getDuration());
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        DatabaseLoader loader = new DatabaseLoader(getActivity());
        loader.addActivityRecord(this.activityModel);
    }
}
