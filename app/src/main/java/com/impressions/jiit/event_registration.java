package com.impressions.jiit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class event_registration extends AppCompatActivity {
    String code=null;
    String title=null;
    TextView eventname;
    ProgressDialog progress;
    String enroll=null;
    String name=null;
    String email=null;
    String phone=null;
    String year=null;
    String college=null;
    String myJSON;
    JSONArray peoples = null;
    DatabaseHelper_enroll myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);
        code=event_description.code_;
        title=event_description.title_;
        if(code==null||title==null||code==""||title==""){
            Toast.makeText(getBaseContext(),"Oops!Something went Wrong! Please Restart your App!",Toast.LENGTH_LONG).show();
            return;
        }
        else{
            eventname=(TextView)findViewById(R.id.event_title);
            eventname.setText(""+title);
            try {
                myDb = new DatabaseHelper_enroll(getBaseContext());
                Cursor res = myDb.getlogin();
                if (res.getCount() > 0) {
                    while (res.moveToNext()) {
                        EditText nameET = (EditText) findViewById(R.id.name);
                        EditText enrollET = (EditText) findViewById(R.id.enroll);
                        EditText yearET = (EditText) findViewById(R.id.year);
                        EditText emailET = (EditText) findViewById(R.id.email);
                        EditText phoneET = (EditText) findViewById(R.id.phone);
                        EditText collegeET = (EditText) findViewById(R.id.college);
                        enrollET.setText(res.getString(0));
                        nameET.setText(res.getString(1));
                        yearET.setText(res.getString(2));
                        emailET.setText(res.getString(3));
                        phoneET.setText(res.getString(4));
                        collegeET.setText(res.getString(5));
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            Button register=(Button)findViewById(R.id.final_register);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText nameET = (EditText) findViewById(R.id.name);
                    name = nameET.getText().toString();

                    EditText enrollET = (EditText) findViewById(R.id.enroll);
                    enroll = enrollET.getText().toString();


                    EditText yearET = (EditText) findViewById(R.id.year);
                    year = yearET.getText().toString();

                    EditText emailET = (EditText) findViewById(R.id.email);
                    email = emailET.getText().toString();

                    EditText phoneET = (EditText) findViewById(R.id.phone);
                    phone = phoneET.getText().toString();

                    EditText collegeET = (EditText) findViewById(R.id.college);
                    college = collegeET.getText().toString();
                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String device_id = tm.getDeviceId();

                    if (name.length() == 0 || enroll.length() == 0 || year.length() == 0 || email.length() == 0 || phone.length() == 0 || college.length() == 0) {
                        Toast.makeText(getBaseContext(), "Please fill the complete form before submitting!", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        try {
                            register_for_event(code, name, enroll, year, email, phone, college,device_id);
                            myDb = new DatabaseHelper_enroll(getBaseContext());
                            myDb.createtable();
                            Cursor res = myDb.getlogin();
                            if (res.getCount() > 0) {

                            } else {

                                myDb.insertData(enroll, name, year, email, phone, college);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }


    }

    private void register_for_event(final String code, final String name, final String enroll, final String year, final String email, final String phone, final String college,final String IMEI) {

        class LoginAsync extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progress = new ProgressDialog(event_registration.this);
                progress.setTitle("Please wait!");
                progress.setMessage("Don't Press the back button! ...");
                progress.show();
            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("code", code));
                nameValuePairs.add(new BasicNameValuePair("name", name));

                nameValuePairs.add(new BasicNameValuePair("enroll", enroll));

                nameValuePairs.add(new BasicNameValuePair("year", year));

                nameValuePairs.add(new BasicNameValuePair("email", email));

                nameValuePairs.add(new BasicNameValuePair("phone", phone));

                nameValuePairs.add(new BasicNameValuePair("college", college));


                nameValuePairs.add(new BasicNameValuePair("imei", IMEI));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://jiitimpressions.com/impressions_app/register.php");
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


                // Toast.makeText(getActivity(),""+result,Toast.LENGTH_SHORT).show();
                try {
                        myJSON = result;
                        progress.dismiss();
                        if(result.contains("user_registered")){

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(event_registration.this);
                            alertDialogBuilder.setMessage("Congratulation! Your Response for this event has been recorded!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        }
                        else if(result.contains("Already registered")){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(event_registration.this);
                            alertDialogBuilder.setMessage("You have already registered for this event!");
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();


                            try {
                                myDb = new DatabaseHelper_enroll(getBaseContext());
                                Cursor res = myDb.getlogin();
                                if (res.getCount() > 0) {

                                }
                                else{
                                    myDb.insertData(enroll, name, year, email, phone, college);
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    else{
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(event_registration.this);
                            alertDialogBuilder.setMessage("OOPS! Something went wrong! Please try again later!");
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }

                }
                catch (Exception e){
                    e.printStackTrace();
                    try {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(event_registration.this);
                        alertDialogBuilder.setMessage("OOPS! Something went wrong! Please try again later!");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                    catch(Exception j){
                        j.printStackTrace();

                    }
                }


            }
        }
        LoginAsync la = new LoginAsync();
        la.execute();



    }

}
