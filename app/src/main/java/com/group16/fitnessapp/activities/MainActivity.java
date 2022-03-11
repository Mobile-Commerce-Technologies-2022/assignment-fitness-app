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

        STATE state = STATE.RUNNING;
        Fragment f = ff.getFragment(state);
        FragManager.getInstance().addFragment(this, f, state);

        new android.os.Handler().postDelayed(
                () -> {
                    Log.i("tag","A Kiss after 5 seconds");
                    FragManager.getInstance().removeFragment(MainActivity.this, f, state);

                    Fragment f1 = ff.getFragment(STATE.REST);
                    FragManager.getInstance().addFragment(MainActivity.this, f1, STATE.REST);
                }, 5000);
    }
}