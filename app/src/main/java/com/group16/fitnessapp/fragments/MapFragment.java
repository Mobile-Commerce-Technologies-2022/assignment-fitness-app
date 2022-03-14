package com.group16.fitnessapp.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.group16.fitnessapp.R;

public class MapFragment extends ActivityFragment {
    private static final String TAG = MapFragment.class.getSimpleName();
    private MyLocation loc;
    private BroadcastReceiver mMessageReceiver;
    private GoogleMap mMap;
    // The entry point to the Fused Location Provider.
    private final LatLng defaultLocation = new LatLng(45.4266016667,  -75.69085334);
    private static final int DEFAULT_ZOOM = 15;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_map, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            googleMap.addMarker(new MarkerOptions().position(defaultLocation).title("Your Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
            googleMap.setMinZoomPreference(DEFAULT_ZOOM);
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                loc = (MyLocation) intent.getSerializableExtra("my-location");
                mMap.clear();
                LatLng currentLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
                mMap.addMarker(new MarkerOptions().position(currentLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                mMap.setMinZoomPreference(DEFAULT_ZOOM);
                Log.d(TAG, "Receiver: " + loc.toString());
            }
        };

        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(this.mMessageReceiver,
                new IntentFilter("update-user-location"));
    }




    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(this.mMessageReceiver);
        super.onPause();
    }
}
