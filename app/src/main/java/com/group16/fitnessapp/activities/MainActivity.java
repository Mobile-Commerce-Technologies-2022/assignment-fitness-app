package com.group16.fitnessapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.group16.fitnessapp.R;
import com.group16.fitnessapp.constants.STATE;
import com.group16.fitnessapp.utils.FragFactory;
import com.group16.fitnessapp.utils.FragManager;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private FragFactory ff = new FragFactory();

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//        }

        STATE state = STATE.RUNNING;
        Fragment f = ff.getFragment(state);
        FragManager.getInstance().addFragment(this, f, state);
//        FragManager.getInstance().removeFragment(this, f, state);

    }
}