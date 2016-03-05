package com.impressions.jiit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Dhruv on 7/24/2015.
 */
public class DatabaseHelper_latest extends SQLiteOpenHelper {
    private static final String MY_TAG = "the_custom_message";
    public DatabaseHelper_latest(Context context) {
        super(context, "imp_db_details", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table latest_event (JSON_latest LONGTEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists latest_event");


        onCreate(db);
    }
    public boolean insertData(String JSON_latest) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("JSON_latest", JSON_latest);

        long result = db.insert("latest_event", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    public void droptable(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Drop table if exists latest_event");

    }
    public void createtable(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table latest_event (JSON_latest LONGTEXT )");
    }
    public Cursor getlogin(){


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from latest_event",null);

        return res;
    }

    public void onlogout() {
        Log.i(MY_TAG, "Logging out!");
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("latest_event",null,null);
        Log.i(MY_TAG, "Logged Out!");

    }
}