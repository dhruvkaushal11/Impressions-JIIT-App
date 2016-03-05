package com.impressions.jiit;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by dhruv on 7/29/2015.
 */
public class PrimaryFragment extends Fragment {
    View rootView;
TextView no;
    ArrayList<String> text = new ArrayList<String>();
    DatabaseHelper_notification Db1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.primary_layout, null);
        no=(TextView)rootView.findViewById(R.id.loading_status);
        showList_notification_offline();

        return rootView;

    }
    protected void showList_notification_offline() {
        try {
            try {
                Db1 = new DatabaseHelper_notification(getActivity());
                Cursor res1 = Db1.getlogin();
                if (res1.getCount() > 0) {
                    no.setText("");
                    while (res1.moveToNext()) {
                        text.add(res1.getString(1));

                        //Toast.makeText(getActivity(),""+res1.getString(0),Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception L)
        {
            L.printStackTrace();
        }


        ListView list;
        CustomListAdapter_noti adapter=new CustomListAdapter_noti(getActivity(),text.toArray(new String[text.size()]));
        list = (ListView)rootView.findViewById(R.id.notification);
        list.setAdapter(adapter);


    }




}











