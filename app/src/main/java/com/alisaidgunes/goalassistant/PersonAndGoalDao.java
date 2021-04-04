package com.alisaidgunes.goalassistant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PersonAndGoalDao {
    public void insert(GoalAsisstantDB gdb,String name,String goal) {
        SQLiteDatabase db = gdb.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("goal",goal);
        db.insertOrThrow("personandgoal",null,cv);
        db.close();
    }
    public PersonAndGoal getInfo(GoalAsisstantDB gdb){
        PersonAndGoal  personAndGoal = new PersonAndGoal();
        SQLiteDatabase db = gdb.getReadableDatabase();
        Cursor cursor =db.rawQuery("SELECT * FROM personandgoal",null);
        while (cursor.moveToNext()){
            personAndGoal = new PersonAndGoal( cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("goal")));
        }
        return personAndGoal;
    }
    public void delete(GoalAsisstantDB gdb,int id) {
        SQLiteDatabase db = gdb.getWritableDatabase();
        db.delete("personandgoal","id=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public void update(GoalAsisstantDB gdb,String name,String goal){
        SQLiteDatabase db = gdb.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("goal",goal);
        db.update("personandgoal",cv,null,null);
        db.close();
    }
    public int count(GoalAsisstantDB gdb){
        int returnValue = 0;
        SQLiteDatabase db = gdb.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT count(*) as sonuc FROM personandgoal",null);
        while (c.moveToNext()){
            returnValue = c.getInt(c.getColumnIndex("sonuc"));
        }
        return returnValue;
    }
}
