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
public class DatabaseHelper_notification extends SQLiteOpenHelper {
    private static final String MY_TAG = "the_custom_message";
    public DatabaseHelper_notification(Context context) {
        super(context, "imp_db_details", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table notification (id auto increment primary key,message longtext)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists notification");


        onCreate(db);
    }
    public boolean insertData(String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("message", message);
        long result = db.insert("notification", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    public void droptable(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Drop table if exists notification");

    }
    public void createtable(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table notification (id auto increment primary key,message longtext)");
    }
    public Cursor getlogin(){


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from notification order by id desc",null);

        return res;
    }

    public void onlogout() {
        Log.i(MY_TAG, "Logging out!");
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("notification",null,null);
        Log.i(MY_TAG, "Logged Out!");

    }
}