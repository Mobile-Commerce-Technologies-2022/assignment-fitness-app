package com.group16.fitnessapp.fragments;

import android.location.Location;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class MyLocation implements Serializable {
    private final Double longitude;
    private final Double latitude;

    public MyLocation(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    @NonNull
    @Override
    public String toString() {
        return "MyLocation{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
