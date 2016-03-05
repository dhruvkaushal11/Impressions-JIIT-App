package com.impressions.jiit;

/**
 * Created by Dhruv on 12/21/2015.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dhruv on 12/8/2015.
 */
public class service1 extends Service
{
    public static String text="";
    private Timer myTimer;
    SharedPreferences sharedpreferences;
    int n;
    int flag=1;
    public static final String MyPREFERENCES = "MyPrefs_noti" ;
    public static final String noti = "total_noti";
    DatabaseHelper_notification myDb3;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
        Log.d("", "onDestroy");
    }

    @Override
    public void onStart(Intent intent, int startid)
    {
        myDb3 = new DatabaseHelper_notification(getBaseContext());
try{        myDb3.createtable();}
catch(Exception e){e.printStackTrace();}
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                flag=1;
                checkforinternet();
                if (flag == 1) {
                    notification();
                }
            }
        }, 0, 60000);

    }
    private void checkforinternet() {
        ConnectivityManager cm2 = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni2 = cm2.getActiveNetworkInfo();
        if (ni2 == null) {
            flag=0;
            return;
        }

    }

    private void notification() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        int x;
        x = sharedpreferences.getInt(noti, -1);
        if (x == -1) {
            set_new_noti();
        } else {
            check_for_new_noti();
        }
    }
    private void check_for_new_noti() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        class LoginAsync extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://impressions.esy.es/total_noti.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
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
                    n = Integer.parseInt(result.trim());
                }
                catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (sharedpreferences.getInt(noti, -1) == n) {
                        //  Toast.makeText(getBaseContext(), "No new Notification", Toast.LENGTH_LONG).show();
                    } else {
                        int old = sharedpreferences.getInt(noti, -1);
                        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, 0);
                        preferences.edit().remove(noti).commit();
                        show_new_notification(n - old);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt(noti, n);
                        editor.commit();
                        // Toast.makeText(getBaseContext(), "" + (n - old) + "New Notification is pending", Toast.LENGTH_LONG).show();
                    }

                }

            }
        }
        LoginAsync la = new LoginAsync();
        la.execute();


    }


    private void show_new_notification(final int limit) {


        class LoginAsync extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                try {
                    nameValuePairs.add(new BasicNameValuePair("limit", "" + limit));

                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }

                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://impressions.esy.es/show_noti.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
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

                    if (result.contains("<") || result == null) {

                    } else {
                        //Toast.makeText(getBaseContext(), "New Notification is" + result, Toast.LENGTH_LONG).show();
                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        @SuppressWarnings("deprecation")

                        Notification notification = new Notification(R.drawable.scrapp, "" + result, System.currentTimeMillis());
                        Intent notificationIntent = new Intent(getApplicationContext(), new_noti.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, notificationIntent, 0);
                        notification.sound = Uri.parse("android.resource://com.impressions.jiit/" + R.raw.noti);
                        notification.defaults |= Notification.DEFAULT_VIBRATE;
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;
                        notification.setLatestEventInfo(getBaseContext(), "" + result, "" + result, pendingIntent);
                        notificationManager.notify(9999, notification);
                        text = result;
                        myDb3 = new DatabaseHelper_notification(getBaseContext());
                        myDb3.insertData(result);
                          //  Toast.makeText(getApplication(), "Adding " + result, Toast.LENGTH_LONG).show();


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute();

    }

    private void set_new_noti() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        class LoginAsync extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://impressions.esy.es/total_noti.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
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
                int n;
                try {
                    n = Integer.parseInt(result.trim());

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt(noti, n);
                    editor.commit();
                } catch (Exception e) {
                    // Toast.makeText(getBaseContext(), "Servers not Reachable!", Toast.LENGTH_LONG).show();
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute();
    }

}