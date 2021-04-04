package com.alisaidgunes.goalassistant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PunishmentDao {
    public void insert(GoalAsisstantDB gdb,String name,int punishmentopday){
        SQLiteDatabase db = gdb.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("punishmentname",name);
        cv.put("punishmentopday",punishmentopday);
        db.insertOrThrow("punishment",null,cv);
        db.close();
    }
    public void update(GoalAsisstantDB gdb,int id,String name,int punishmentopday){
        SQLiteDatabase db = gdb.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("punishmentname",name);
        cv.put("punishmentopday",punishmentopday);
        db.update("punishment",cv,"id=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public void delete(GoalAsisstantDB gdb,int id){
        SQLiteDatabase db = gdb.getWritableDatabase();
        db.delete("punishment","id=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public ArrayList<Punishment> getAllInfo(GoalAsisstantDB gdb){
        ArrayList<Punishment> allInfo = new ArrayList<>();
        SQLiteDatabase db = gdb.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT*FROM punishment",null);
        while (c.moveToNext()){
            Punishment punishment  = new Punishment(c.getInt(c.getColumnIndex("id")),c.getString(c.getColumnIndex("punishmentname")),c.getInt(c.getColumnIndex("punishmentopday")));
            allInfo.add(punishment);
        }
        return allInfo;
    }
    public Punishment searchWithName(GoalAsisstantDB gdb,String name){
        Punishment punishment = new Punishment();
        SQLiteDatabase db= gdb.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT*FROM punishment WHERE name="+name,null);
        while (c.moveToNext()){
            punishment = new Punishment(c.getInt(c.getColumnIndex("id")),c.getString(c.getColumnIndex("punishmentname")),c.getInt(c.getColumnIndex("punishmentopday")));
        }
        return punishment;
    }
    public Punishment searchWithId(GoalAsisstantDB gdb,int id){
        Punishment punishment = new Punishment();
        SQLiteDatabase db= gdb.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT*FROM punishment WHERE name="+id,null);
        while (c.moveToNext()){
            punishment = new Punishment(c.getInt(c.getColumnIndex("id")),c.getString(c.getColumnIndex("punishmentname")),c.getInt(c.getColumnIndex("punishmentopday")));
        }
        return punishment;
    }
    public int count(GoalAsisstantDB gdb){
        int returnValue = 0;
        SQLiteDatabase db = gdb.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT count(*) as sonuc FROM punishment",null);
        while (c.moveToNext()){
            returnValue = c.getInt(c.getColumnIndex("sonuc"));
        }
           return returnValue;
    }



}
