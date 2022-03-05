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

        String TYPE = "TYPE";
        String TIME_BEFORE_ACTIVITY = "TIME_BEFORE_ACTIVITY";
        String TIME_AFTER_ACTIVITY = "TIME_AFTER_ACTIVITY";
        String DURATION = "DURATION";

        String statement = "CREATE TABLE IF NOT EXISTS ACTIVITIES (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TYPE + " TEXT, " +
                TIME_BEFORE_ACTIVITY + " TEXT, " +
                TIME_AFTER_ACTIVITY + " TEXT, " +
                DURATION +" NUMERIC )";

        sqLiteDatabase.execSQL(statement);

        statement = "CREATE TABLE IF NOT EXISTS SCORES (ID INTEGER PRIMARY KEY AUTOINCREMENT, GAME TEXT, SCORE DOUBLE, DATE REAL)";

        sqLiteDatabase.execSQL(statement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
