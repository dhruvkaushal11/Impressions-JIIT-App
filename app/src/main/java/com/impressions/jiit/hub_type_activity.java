package com.impressions.jiit;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class hub_type_activity extends AppCompatActivity {

    ProgressDialog progress;
    String myJSON;
    JSONArray peoples = null;
    String clicked="";

    ArrayList<String> event_type = new ArrayList<String>();


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_type_activity);
        clicked=CustomListAdapter_hub_event.type_clicked;
        TextView abc=(TextView)findViewById(R.id.faculty_contact);
        abc.setText("Faculty Incharge\n"+CustomListAdapter_hub_event.type_clicked_detail);
        if(clicked==""||clicked==null){
            clicked="";
        }
        hub_of_this_type(clicked);

    }


    public void hub_of_this_type(final String search_string) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progress = new ProgressDialog(hub_type_activity.this);
                progress.setTitle("Loading");
                progress.setMessage("Wait searching for Event ...");
                progress.show();
                ListView list;
                list = (ListView)findViewById(R.id.event_type_list);
                list.setAdapter(null);
                event_type = new ArrayList<String>();

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
                nameValuePairs.add(new BasicNameValuePair("param", search_string));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://jiitimpressions.com/impressions_app/hub_of_this_type.php");
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

                myJSON = result;
                // Toast.makeText(getActivity(),""+result,Toast.LENGTH_SHORT).show();
                try {
                    if (myJSON.contains("result")) {
                        progress.dismiss();
                        showList();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(getBaseContext(), "Oops Something went wrong! Please check your Internet connection or Try again later!", Toast.LENGTH_LONG).show();

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
                alertDialogBuilder.setMessage("Sorry! No results found!");
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
            CustomListAdapter_search adapter=new CustomListAdapter_search(hub_type_activity.this,codeid.toArray(new String[codeid.size()]),titleid.toArray(new String[titleid.size()]),dateid.toArray(new String[dateid.size()]),timeid.toArray(new String[timeid.size()]),venueid.toArray(new String[venueid.size()]),descriptionid.toArray(new String[descriptionid.size()]),ruleid.toArray(new String[ruleid.size()]),contactid.toArray(new String[contactid.size()]),requirementid.toArray(new String[requirementid.size()]),type.toArray(new String[type.size()]),link.toArray(new String[link.size()]));
            list = (ListView)findViewById(R.id.event_type_list);
            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
