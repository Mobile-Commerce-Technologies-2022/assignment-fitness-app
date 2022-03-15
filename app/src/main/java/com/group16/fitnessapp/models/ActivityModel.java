package com.group16.fitnessapp.models;

import android.location.Location;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.group16.fitnessapp.constants.STATE;

import java.time.Duration;
import java.time.LocalDateTime;

public class ActivityModel {

    private final STATE state;
    private LocalDateTime timeBeforeActivity;
    private LocalDateTime timeAfterActivity;
    private Long duration;
    private Location initialLocation;

    public ActivityModel(STATE state) {
        this.state = state;
    }

    public Location getInitialLocation() {
        return initialLocation;
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public ActivityModel(STATE state, LocalDateTime BEFORE_ACTIVITY) {
        this.state = state;
        this.timeBeforeActivity = BEFORE_ACTIVITY;
    }

    public ActivityModel(STATE state, LocalDateTime BEFORE_ACTIVITY, Location initialLoc) {
        this.state = state;
        this.timeBeforeActivity = BEFORE_ACTIVITY;
        this.initialLocation = initialLoc;
    }
    public STATE getState() {
        return state;
    }

    public LocalDateTime getTimeBeforeActivity() {
        return timeBeforeActivity;
    }

    public LocalDateTime getTimeAfterActivity() {
        return timeAfterActivity;
    }

    public Long getDuration() {
        return duration;
    }

    public void setTimeAfterActivity(LocalDateTime AFTER_ACTIVITY) {
        this.timeAfterActivity = AFTER_ACTIVITY;
        this.setDuration();
    }

    private void setDuration() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            //noinspection Since15
            this.duration = Duration.between(this.timeBeforeActivity, this.timeAfterActivity).toSeconds();
        }
    }
}
