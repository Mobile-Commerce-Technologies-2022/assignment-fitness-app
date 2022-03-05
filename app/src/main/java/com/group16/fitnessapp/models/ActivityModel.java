package com.group16.fitnessapp.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.group16.fitnessapp.constants.STATE;

import java.time.Duration;
import java.time.LocalDateTime;

public class ActivityModel {

    private final STATE TYPE;
    private final LocalDateTime BEFORE_ACTIVITY;
    private final LocalDateTime AFTER_ACTIVITY;
    private final Long DURATION;

    @RequiresApi(api = Build.VERSION_CODES.S)
    public ActivityModel(STATE TYPE, LocalDateTime BEFORE_ACTIVITY, LocalDateTime AFTER_ACTIVITY) {
        this.TYPE = TYPE;
        this.BEFORE_ACTIVITY = BEFORE_ACTIVITY;
        this.AFTER_ACTIVITY = AFTER_ACTIVITY;
        //noinspection Since15
        this.DURATION = Duration.between(this.BEFORE_ACTIVITY, this.AFTER_ACTIVITY).toSeconds();
    }

    public STATE getTYPE() {
        return TYPE;
    }

    public LocalDateTime getBEFORE_ACTIVITY() {
        return BEFORE_ACTIVITY;
    }

    public LocalDateTime getAFTER_ACTIVITY() {
        return AFTER_ACTIVITY;
    }

    public Long getDURATION() {
        return DURATION;
    }
}
