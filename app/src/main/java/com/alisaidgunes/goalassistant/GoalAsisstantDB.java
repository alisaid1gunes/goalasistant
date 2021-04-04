package com.alisaidgunes.goalassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GoalAsisstantDB extends SQLiteOpenHelper {
    private static final int Version = 1;
    private static final String DatabaseName = "goalassistant";

    public GoalAsisstantDB(@Nullable Context context) {
        super(context, DatabaseName, null,Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE 'personandgoal' ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT, `goal` TEXT )");
        db.execSQL("CREATE TABLE 'punishment' ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `punishmentname` TEXT, `punishmentopday` INTEGER )");
        db.execSQL("CREATE TABLE 'reward' ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `rewardname` TEXT, `rewardtopday` INTEGER )");
        db.execSQL("CREATE TABLE 'datetracker' ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `goalisokay` INTEGER, `point` INTEGER )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS personandgoal");
        db.execSQL(" DROP TABLE IF EXISTS punishmnet");
        db.execSQL(" DROP TABLE IF EXISTS reward");
        db.execSQL(" DROP TABLE IF EXISTS datetracker");
        onCreate(db);
    }
}
