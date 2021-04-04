package com.alisaidgunes.goalassistant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DateTrackerDao {
    public void insert(GoalAsisstantDB gdb,int goalisokay,int point){
        SQLiteDatabase db = gdb.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("goalisokay",goalisokay);
        cv.put("point",point);
        db.insertOrThrow("datetracker",null,cv);
        db.close();
    }
    public void update(GoalAsisstantDB gdb,int id,int goalisokay,int point){
        SQLiteDatabase db  = gdb.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("goalisokay",goalisokay);
        cv.put("point",point);
        db.update("datetracker",cv,"id=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public void delete(GoalAsisstantDB gdb,int id){
        SQLiteDatabase db = gdb.getWritableDatabase();
        db.delete("datetracker","id=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public ArrayList<DateTracker> getAllInfo(GoalAsisstantDB gdb){
        ArrayList<DateTracker> allInfo = new ArrayList<>();
        SQLiteDatabase db = gdb.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT*FROM datetracker",null);
        while (c.moveToNext()){
            DateTracker dateTracker  = new DateTracker(c.getInt(c.getColumnIndex("id")), c.getInt(c.getColumnIndex("goalisokay")),c.getInt(c.getColumnIndex("point")));
            allInfo.add(dateTracker);
        }
        return allInfo;
    }

    public int count(GoalAsisstantDB gdb){
        int returnValue = 0;
        SQLiteDatabase db = gdb.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT count(*) as sonuc FROM datetracker",null);
        while (c.moveToNext()){
            returnValue = c.getInt(c.getColumnIndex("sonuc"));
        }
        return returnValue;
    }
}
