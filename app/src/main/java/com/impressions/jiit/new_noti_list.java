package com.impressions.jiit;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class new_noti_list extends AppCompatActivity {
    DatabaseHelper_notification myDb3;
    String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_noti_list);
        TextView t=(TextView)findViewById(R.id.noti_text);
        t.setText(""+CustomListAdapter_noti.str);
        //myDb3.droptable();


    }
}
