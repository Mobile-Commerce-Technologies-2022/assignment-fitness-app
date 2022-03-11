package com.group16.fitnessapp.controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ActivityController extends SQLiteOpenHelper {

    public ActivityController(@Nullable Context context) {

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

}
