package com.impressions.jiit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dhruv on 7/24/2015.
 */
public class DatabaseHelper_my_event extends SQLiteOpenHelper {
    private static final String MY_TAG = "the_custom_message";
    public DatabaseHelper_my_event(Context context) {

        super(context, "imp_db_details", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table my_event (JSON_my_event LONGTEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists my_event");


        onCreate(db);
    }
    public boolean insertData(String JSON_my_event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("JSON_my_event", JSON_my_event);

        long result = db.insert("my_event", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    public void droptable(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Drop table if exists my_event");

    }
    public void createtable(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table my_event (JSON_my_event LONGTEXT )");
    }
    public Cursor getlogin(){


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from my_event",null);

        return res;
    }

    public void onlogout() {
        Log.i(MY_TAG, "Logging out!");
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("my_event",null,null);
        Log.i(MY_TAG, "Logged Out!");

    }
}