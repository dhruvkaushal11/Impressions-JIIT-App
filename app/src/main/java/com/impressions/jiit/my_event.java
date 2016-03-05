package com.impressions.jiit;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class my_event extends AppCompatActivity {
    ProgressDialog progress;
    String myJSON;
    JSONArray peoples = null;
    int flag=1;
    ArrayList<String> titleid = new ArrayList<String>();
    ArrayList<String> codeid = new ArrayList<String>();
    ArrayList<String> descriptionid = new ArrayList<String>();
    ArrayList<String> timeid = new ArrayList<String>();
    ArrayList<String> dateid = new ArrayList<String>();
    ArrayList<String> venueid = new ArrayList<String>();
    ArrayList<String> ruleid = new ArrayList<String>();
    ArrayList<String> contactid = new ArrayList<String>();
    ArrayList<String> requirementid = new ArrayList<String>();

    ArrayList<String> link = new ArrayList<String>();
    ArrayList<String> type = new ArrayList<String>();

    DatabaseHelper_my_event Db4;
    String enroll="";
    DatabaseHelper_enroll myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event);
        showList_offline();
        myDb = new DatabaseHelper_enroll(getBaseContext());
        Cursor res = null;
        try {
            res = myDb.getlogin();


        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                enroll = res.getString(0);
                if (enroll == "" || enroll == null) {
                    return;
                }
                else {
                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String device_id = tm.getDeviceId();
                    my_event(enroll,device_id);
                }

            }
        } else {

        }
        }
        catch (Exception e) {

            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = tm.getDeviceId();
            my_event(null,device_id);
            e.printStackTrace();
        }

    }


    private void my_event(final String search_string,final String imei) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progress = new ProgressDialog(my_event.this);
                progress.setTitle("Loading");
                progress.setMessage("Wait searching for Event ...");
                progress.show();
                titleid = new ArrayList<String>();
                codeid = new ArrayList<String>();
                descriptionid = new ArrayList<String>();
                timeid = new ArrayList<String>();
                dateid = new ArrayList<String>();
                venueid = new ArrayList<String>();
                ruleid = new ArrayList<String>();
                contactid = new ArrayList<String>();
                requirementid = new ArrayList<String>();
                type = new ArrayList<String>();
                link = new ArrayList<String>();


            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("imei", imei));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://jiitimpressions.com/impressions_app/myevent.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e){
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            public void onPostExecute(String result) {

                try {
                    myJSON = result;
                // Toast.makeText(getActivity(),""+result,Toast.LENGTH_SHORT).show();

                    if (myJSON.contains("result")) {
                        progress.dismiss();
                        showList();
                        try{
                            //writing in offline database
                            Db4=new DatabaseHelper_my_event(my_event.this);
                            Db4.droptable();
                            Db4.createtable();
                            Db4.insertData(myJSON);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(getBaseContext(), "Oops Something went wrong!\n Please check your Internet connection or Try again later!", Toast.LENGTH_LONG).show();

                }


            }
        }
        LoginAsync la = new LoginAsync();
        la.execute();



    }
    protected void showList() {
        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray("result");
            if(peoples.length()==0){

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("You haven't registered for any event yet!\n\n ").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            for (int i = 0; i <peoples.length(); i++) {

                try {
                    JSONObject c = peoples.getJSONObject(i);
                    codeid.add(c.getString("code"));
                    titleid.add(c.getString("title"));
                    dateid.add(c.getString("date"));
                    timeid.add(c.getString("time"));
                    venueid.add(c.getString("venue"));
                    descriptionid.add(c.getString("description"));
                    ruleid.add(c.getString("rules"));
                    contactid.add(c.getString("contact"));
                    requirementid.add(c.getString("requirement"));
                    link.add(c.getString("link"));
                    type.add(c.getString("type"));
                }
                catch (NullPointerException e){


                }
                catch (RuntimeException v){

                }
            }
            ListView list;
            CustomListAdapter_my_event adapter=new CustomListAdapter_my_event(my_event.this,codeid.toArray(new String[codeid.size()]),titleid.toArray(new String[titleid.size()]),dateid.toArray(new String[dateid.size()]),timeid.toArray(new String[timeid.size()]),venueid.toArray(new String[venueid.size()]),descriptionid.toArray(new String[descriptionid.size()]),ruleid.toArray(new String[ruleid.size()]),contactid.toArray(new String[contactid.size()]),requirementid.toArray(new String[requirementid.size()]),type.toArray(new String[type.size()]),link.toArray(new String[link.size()]));
            list = (ListView)findViewById(R.id.my_event_list);
            list.setAdapter(adapter);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    protected void showList_offline() {
        try {

            try {
                Db4 = new DatabaseHelper_my_event(my_event.this);
                Cursor res2 = Db4.getlogin();
                if (res2.getCount() > 0) {
                    while (res2.moveToNext()) {
                        myJSON = res2.getString(0);
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();

            }
           try {
               JSONObject jsonObj = new JSONObject(myJSON);

               peoples = jsonObj.getJSONArray("result");
               if (peoples.length() == 0) {


               }
               for (int i = 0; i < peoples.length(); i++) {

                   try {
                       JSONObject c = peoples.getJSONObject(i);
                       codeid.add(c.getString("code"));
                       titleid.add(c.getString("title"));
                       dateid.add(c.getString("date"));
                       timeid.add(c.getString("time"));
                       venueid.add(c.getString("venue"));
                       descriptionid.add(c.getString("description"));
                       ruleid.add(c.getString("rules"));
                       contactid.add(c.getString("contact"));
                       requirementid.add(c.getString("requirement"));
                       link.add(c.getString("link"));
                       type.add(c.getString("type"));
                   } catch (NullPointerException e) {


                   } catch (RuntimeException v) {

                   }
               }
               ListView list;
               CustomListAdapter_my_event adapter = new CustomListAdapter_my_event(my_event.this, codeid.toArray(new String[codeid.size()]), titleid.toArray(new String[titleid.size()]), dateid.toArray(new String[dateid.size()]), timeid.toArray(new String[timeid.size()]), venueid.toArray(new String[venueid.size()]), descriptionid.toArray(new String[descriptionid.size()]), ruleid.toArray(new String[ruleid.size()]), contactid.toArray(new String[contactid.size()]), requirementid.toArray(new String[requirementid.size()]), type.toArray(new String[type.size()]), link.toArray(new String[link.size()]));
               list = (ListView) findViewById(R.id.my_event_list);
               list.setAdapter(adapter);
           }
           catch (Exception e){
               e.printStackTrace();

           }

        } catch (Exception le) {
            le.printStackTrace();
        }

    }
}
