package com.impressions.jiit;

/**
 * Created by Dhruv on 12/21/2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Dhruv on 12/8/2015.
 */
public class autostart extends BroadcastReceiver
{
    public void onReceive(Context arg0, Intent arg1)
    {
        Intent intent = new Intent(arg0,service1.class);
        arg0.startService(intent);
        Log.i("Autostart", "started");
    }
}