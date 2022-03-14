package com.group16.fitnessapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.group16.fitnessapp.R;
import com.group16.fitnessapp.constants.STATE;
import com.group16.fitnessapp.fragments.MyLocation;
import com.group16.fitnessapp.utils.FragFactory;
import com.group16.fitnessapp.utils.FragManager;


public class MainActivity extends AppCompatActivity implements LocationListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 16;
    private final int TIME_INTERVAL = 1000;
    private final int MIN_DISTANCE_M = 5;
    private final FragFactory ff = new FragFactory();
    private LocationManager locationManager = null;
    private STATE state = STATE.REST; // default resting
    private Fragment fragment;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//         check location permission
        requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        loadLocationTracker();
        fragment = ff.getFragment(this.state, null); //initialize greeting fragment
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onStart() {
        super.onStart();

        FragManager.getInstance().addFragment(this, fragment, this.state);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.sendMessage(location);
        // STATE Controller
        STATE prevState = this.state;
        float speed = location.getSpeed();
        if(speed > 0f && speed <= 4f) { // WALKING
            this.state = STATE.WALKING;
        } else if(speed >= 4f && speed <= 9f) { // RUNNING
            this.state = STATE.RUNNING;
        } else if(speed > 9f) { // IN_VAN
            this.state = STATE.IN_VAN;
        } else { //REST
            this.state = STATE.REST;
        }
        Log.e(TAG, String.format("Current STATE: %s, speed: %f", this.state, speed));
        if(this.state != prevState) {
            FragManager.getInstance().removeFragment(this, this.fragment, prevState);
            this.fragment = ff.getFragment(this.state, location);
            FragManager.getInstance().addFragment(this, this.fragment, this.state);
        }
    }

    @SuppressLint("MissingPermission")
    public void loadLocationTracker() {
        try {
            this.locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, this.TIME_INTERVAL ,this.MIN_DISTANCE_M, MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Location location) {
        Log.e(TAG, "Broadcasting Message");
        Intent intent = new Intent("update-user-location");
        // You can also include some extra data.
        intent.putExtra("my-location",  new MyLocation(location));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private boolean checkPermissions(String permission) {
        return  PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                permission);
    }

    private void requestPermissions(String permission) {
        if(!checkPermissions(permission)) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permission},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}