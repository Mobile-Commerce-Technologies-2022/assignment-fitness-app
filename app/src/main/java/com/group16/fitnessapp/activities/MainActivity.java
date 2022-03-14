package com.group16.fitnessapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
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

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // check location permission
        if (!checkPermissions()) {
            requestPermissions();
        }
        loadLocationTracker();
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onStart() {
        super.onStart();

        STATE state = STATE.WALKING;
        Fragment f = ff.getFragment(state);
        FragManager.getInstance().addFragment(this, f, state);

        Location lastKnownLocation = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        sendMessage(lastKnownLocation);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.sendMessage(location);
        // STATE Controller
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
        Log.d(TAG, "Broadcasting Message");
        Intent intent = new Intent("update-user-location");
        // You can also include some extra data.
        intent.putExtra("my-location", new MyLocation(location));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private boolean checkPermissions() {
        return  PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }
}