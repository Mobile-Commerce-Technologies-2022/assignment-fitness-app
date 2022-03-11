package com.group16.fitnessapp.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.group16.fitnessapp.constants.STATE;

import java.time.Duration;
import java.time.LocalDateTime;

public class ActivityModel {

    private final STATE TYPE;
    private LocalDateTime timeBeforeActivity;
    private LocalDateTime timeAfterActivity;
    private Long duration;

    public ActivityModel(STATE state) {
        this.TYPE = state;
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public ActivityModel(STATE TYPE, LocalDateTime BEFORE_ACTIVITY) {
        this.TYPE = TYPE;
        this.timeBeforeActivity = BEFORE_ACTIVITY;
    }

    public STATE getTYPE() {
        return TYPE;
    }

    public LocalDateTime getTimeBeforeActivity() {
        return timeBeforeActivity;
    }

    public LocalDateTime getTimeAfterActivity() {
        return timeAfterActivity;
    }

    public Long getDURATION() {
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
