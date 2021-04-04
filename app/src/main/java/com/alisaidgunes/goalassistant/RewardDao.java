package com.alisaidgunes.goalassistant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class RewardDao {
    public void insert(GoalAsisstantDB gdb,String name,int rewardopday){
        SQLiteDatabase db = gdb.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("rewardname",name);
        cv.put("rewardtopday", rewardopday);
        db.insertOrThrow("reward",null,cv);
        db.close();
    }
    public void update(GoalAsisstantDB gdb,int id,String name ,int rewardopday){
        SQLiteDatabase db = gdb.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("rewardname",name);
        cv.put("rewardtopday", rewardopday);
        db.update("reward",cv,"id=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public void delete(GoalAsisstantDB gdb,int id){
        SQLiteDatabase db = gdb.getWritableDatabase();
        db.delete("reward","id=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public ArrayList<Reward> getAllInfo(GoalAsisstantDB gdb){
        ArrayList<Reward> allInfo = new ArrayList<>();
        SQLiteDatabase db = gdb.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT*FROM reward",null);
        while (c.moveToNext()){
            Reward reward  = new Reward(c.getInt(c.getColumnIndex("id")),c.getString(c.getColumnIndex("rewardname")),c.getInt(c.getColumnIndex("rewardtopday")));
            allInfo.add(reward);
        }
        return allInfo;
    }
}
