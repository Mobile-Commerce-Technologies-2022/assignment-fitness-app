package com.group16.fitnessapp.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.group16.fitnessapp.R;
import com.group16.fitnessapp.constants.STATE;
import com.group16.fitnessapp.models.ActivityModel;

public class MapFragment extends ActivityFragment {
    private static final String TAG = MapFragment.class.getSimpleName();
    private Location loc;
    private final BroadcastReceiver mMessageReceiver;
    private GoogleMap mMap;
    // The entry point to the Fused Location Provider.
    private static final int DEFAULT_ZOOM = 15;

    public MapFragment(ActivityModel activityModel) {
        super(activityModel);
        this.loc = activityModel.getInitialLocation();

        this.mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                MyLocation myLocation = (MyLocation) intent.getSerializableExtra("my-location");
                loc = myLocation.getLocation();
                if(mMap != null) {
                    mMap.clear();
                    LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.setMinZoomPreference(DEFAULT_ZOOM);
                }
                Log.e(TAG, "Receiver: " + loc.toString());
            }
        };
        Log.i(TAG, "MapFragment model initialized");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_map, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        TextView tv = view.findViewById(R.id.tv_map);
        tv.setText(String.valueOf(activityModel.getState()));
        ImageView iv = view.findViewById(R.id.iv_map);
        Drawable drawable = getActivity().getDrawable((activityModel.getState() == STATE.WALKING) ? R.drawable.walk : R.drawable.in_van);
        iv.setImageDrawable(drawable);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @SuppressLint("MissingPermission")
    private final OnMapReadyCallback callback = googleMap -> {
            mMap = googleMap;
            mMap.setMyLocationEnabled(true);
            LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.setMinZoomPreference(DEFAULT_ZOOM);
        Log.e(TAG, "Google Map ready");
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(this.mMessageReceiver,
                new IntentFilter("update-user-location"));
        Log.e(TAG, "onCreate ready");
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(this.mMessageReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(this.mMessageReceiver,
                new IntentFilter("update-user-location"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
