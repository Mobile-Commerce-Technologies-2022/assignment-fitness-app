package com.group16.fitnessapp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.group16.fitnessapp.constants.STATE;
import com.group16.fitnessapp.models.ActivityModel;
import com.group16.fitnessapp.utils.DatabaseLoader;
import com.group16.fitnessapp.utils.UtilsLoader;

import java.time.LocalDateTime;


public abstract class ActivityFragment extends Fragment {
    protected ActivityModel activityModel;

    public ActivityFragment(ActivityModel activityModel) {
        this.activityModel = activityModel;
    }

    @Override
    public abstract View onCreateView(@NonNull LayoutInflater inflater,
                                      ViewGroup parent,
                                      Bundle savedInstanceState);

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public abstract void onViewCreated(@NonNull View view, Bundle savedInstanceState);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.activityModel != null && this.activityModel.getState() != STATE.REST) {
            DatabaseLoader loader = new DatabaseLoader(getActivity());
            loader.addActivityRecord(this.activityModel);
            Log.i("ActivityFragment", "Logged activity starting time");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(this.activityModel != null && this.activityModel.getState() != STATE.REST) {
            this.activityModel.setTimeAfterActivity(LocalDateTime.now());
            String msg = String.format("You have just %s for %s",
                    this.activityModel.getState(),
                    UtilsLoader.getInstance().secondToHHMMSS(this.activityModel.getDuration()));
//            String str = "You have just " + this.activityModel.getState()
//                    + " for " + UtilsLoader.getInstance().secondToHHMMSS(this.activityModel.getDuration());
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
