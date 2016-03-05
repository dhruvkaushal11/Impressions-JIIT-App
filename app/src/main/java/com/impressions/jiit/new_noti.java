package com.impressions.jiit;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class new_noti extends AppCompatActivity {
    DatabaseHelper_notification myDb3;
    String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_noti);
        try {
            myDb3 = new DatabaseHelper_notification(getBaseContext());
            Cursor res = myDb3.getlogin();
            if (res.getCount() > 0) {
                while (res.moveToNext()) {
                    result=res.getString(1);
                    break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        TextView t=(TextView)findViewById(R.id.noti_text);
        t.setText(""+result);
        //myDb3.droptable();


    }
}
