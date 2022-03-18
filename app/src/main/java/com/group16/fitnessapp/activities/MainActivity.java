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

@SuppressLint("MissingPermission")
public class MainActivity extends AppCompatActivity implements LocationListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 16;
    private final int TIME_INTERVAL = 2500;
    private final int MIN_DISTANCE_M = 0;
    private final FragFactory ff = new FragFactory();
    private LocationManager locationManager;
    private STATE state = STATE.REST; // default resting
    private Fragment fragment;
    private Location currentLocation;
    private static final float SPEED_WALKING_THRESHOLD = 4f;
    private static final float SPEED_RUNNING_THRESHOLD = 9f;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // check location permission
        requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        loadLocationTracker();
        fragment = ff.getFragment(this.state, null); //initialize greeting fragment
    }

    @Override
    protected void onStart() {
        super.onStart();
        // initialize greeting fragment
        FragManager.getInstance().addFragment(this, fragment, this.state);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * control Fragment Activities based on speed. replace current fragment if state has changed
     * @param location current location
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        STATE prevState = this.state;
        Location prevLocation = this.currentLocation;
        if(this.currentLocation != null) {
            if(isEqual(this.currentLocation, location)) {
                this.state = STATE.REST;
            } else{
                float speed = location.getSpeed();
                if(speed > 0f && speed <= SPEED_WALKING_THRESHOLD) { // WALKING
                    this.state = STATE.WALKING;
                } else if(speed >= SPEED_WALKING_THRESHOLD
                        && speed <= SPEED_RUNNING_THRESHOLD) { // RUNNING
                    this.state = STATE.RUNNING;
                } else if(speed > SPEED_RUNNING_THRESHOLD) { // IN_VAN
                    this.state = STATE.IN_VAN;
                }
            }
        }

        this.currentLocation = location; // update current location
        // cast current location to update map
        if(this.state == STATE.WALKING || this.state == STATE.IN_VAN) {
            this.sendMessage(location);
        }

        if(this.currentLocation != null && prevLocation != null) {
            Log.d(TAG, String.format(" [%s, %s] [%f, %f] [%s]",
                    prevState,
                    this.state,
                    Math.abs(this.currentLocation.getLatitude() - prevLocation.getLatitude()),
                    Math.abs(this.currentLocation.getLongitude() - prevLocation.getLongitude()),
                    isEqual(this.currentLocation, prevLocation)));
        }

        if(this.state != prevState) {
            FragManager.getInstance().removeFragment(this, this.fragment, prevState);
            this.fragment = ff.getFragment(this.state, location);
            FragManager.getInstance().addFragment(this, this.fragment, this.state);
        }
    }
    /**
     * Initialize Location Manager
     */
    public void loadLocationTracker() {
        try {
            this.locationManager =
                    (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    TIME_INTERVAL, MIN_DISTANCE_M, MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Pub/Sub to Map Fragment to update real-time location
     * @param location current location
     */
    private void sendMessage(Location location) {
        Log.e(TAG, "Broadcasting Message");
        Intent intent = new Intent("update-user-location");
        // You can also include some extra data.
        intent.putExtra("my-location",  new MyLocation(location));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private boolean checkPermissions(String permission) {
        return  PackageManager.PERMISSION_GRANTED ==
                ActivityCompat.checkSelfPermission(this, permission);
    }

    private void requestPermissions(String permission) {
        if(!checkPermissions(permission)) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permission},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private boolean isEqual(Location l1, Location l2) {
        return l1 != null && l2 != null &&
                Math.abs(l1.getLatitude() - l2.getLatitude()) == 0 &&
                Math.abs(l1.getLongitude() - l2.getLongitude()) == 0;
    }
}