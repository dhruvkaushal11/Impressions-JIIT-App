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
public class DatabaseHelper_enroll extends SQLiteOpenHelper {
    private static final String MY_TAG = "the_custom_message";
    public DatabaseHelper_enroll (Context context) {
        super(context, "imp_db_details", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table details (enroll varchar(100),name varchar(800),year varchar(100),email varchar(800),phone varchar(800),college varchar(800))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists details");


        onCreate(db);
    }
    public boolean insertData(String enroll,String name,String year,String email,String phone,String college) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("enroll", enroll);
        contentValues.put("name", name);
        contentValues.put("year", year);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("college", college);


        long result = db.insert("details", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    public void createtable(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table details (enroll varchar(100),name varchar(800),year varchar(100),email varchar(800),phone varchar(800),college varchar(800))");
    }
    public Cursor getlogin(){


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from details",null);

        return res;
    }

    public void onlogout() {
        Log.i(MY_TAG, "Logging out!");
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("details",null,null);
        Log.i(MY_TAG, "Logged Out!");

    }
}