package com.group16.fitnessapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.group16.fitnessapp.models.ActivityModel;

public class DatabaseLoader extends SQLiteOpenHelper {

    public DatabaseLoader(@Nullable Context context) {

        super(context, "ACTIVITIES.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String statement = "CREATE TABLE IF NOT EXISTS ACTIVITIES (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "TYPE TEXT, "
                + "TIME_BEFORE_ACTIVITY TEXT, "
                + "TIME_AFTER_ACTIVITY TEXT, "
                +"DURATION NUMERIC )";


        sqLiteDatabase.execSQL(statement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addActivityRecord(ActivityModel activityModel) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("TYPE", String.valueOf(activityModel.getTYPE()));
        contentValues.put("TIME_BEFORE_ACTIVITY", String.valueOf(activityModel.getTimeBeforeActivity()));
        contentValues.put("TIME_AFTER_ACTIVITY", String.valueOf(activityModel.getTimeAfterActivity()));
        contentValues.put("DURATION", activityModel.getDuration());

        long activities_table = database.insert("ACTIVITIES", null, contentValues);

        database.close();
        return activities_table != -1;
    }

}