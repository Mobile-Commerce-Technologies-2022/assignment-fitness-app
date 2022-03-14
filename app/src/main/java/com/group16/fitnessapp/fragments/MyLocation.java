package com.group16.fitnessapp.fragments;

import android.location.Location;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class MyLocation implements Serializable {
    private Double longitude;
    private Double latitude;
    private Location loc;
    public MyLocation(Location location) {
        if(location != null) {
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
            this.loc = location;
        }
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
                ", speed=" + loc.getSpeed();
    }

    public Location getLocation() {
        return loc;
    }
}
