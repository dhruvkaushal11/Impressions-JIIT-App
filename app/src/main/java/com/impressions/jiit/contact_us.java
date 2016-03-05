package com.impressions.jiit;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dhruv it is Contact US Form/Feedback on 7/29/2015.
 */
public class contact_us extends Fragment {

    private String message;
    View rootView;
    EditText editText;
    EditText editText2;
    int flag=1;
    String email;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =inflater.inflate(R.layout.contact_us, null);
        editText=(EditText)rootView.findViewById(R.id.message);
        editText2=(EditText)rootView.findViewById(R.id.email_id);
        ImageView imageView=(ImageView)rootView.findViewById(R.id.send_message);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message=editText.getText().toString();
                email=editText2.getText().toString();
                if(email.length()==0){
                    email="Anonymous";
                }
                if(message.length()==0){
                    Toast.makeText(getActivity(), "Please enter some text!", Toast.LENGTH_LONG).show();
                    return;
                }
                checkforinternet();
                if(flag==1){
                   login(email,message);
                }
                else{
                    Toast.makeText(getActivity(), "Not Connected to Internet!", Toast.LENGTH_LONG).show();
                    flag=1;
                }


            }
        });
        return rootView;



    }
    private void checkforinternet() {
        ConnectivityManager cm2 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni2 = cm2.getActiveNetworkInfo();
        if (ni2 == null) {
            //Toast.makeText(MainActivity.this, "Not Connected to Internet!!", Toast.LENGTH_LONG).show();
            flag = 0;
            return;
        }
    }
    private void login(final String email, final String message) {

        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getActivity(), "Please wait", "Submitting feedback!");
            }
            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email",email));
                nameValuePairs.add(new BasicNameValuePair("message", message));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://jiitimpressions.com/impressions_app/contact_us.php");
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
            public void onPostExecute(String result){

                try {
                    String s = result.trim();
                    loadingDialog.dismiss();
                    if (s.contains("success")) {
                        editText = (EditText) rootView.findViewById(R.id.message);
                        editText.setText("");
                        editText2 = (EditText) rootView.findViewById(R.id.email_id);
                        editText2.setText("");
                        TextView t = (TextView) rootView.findViewById(R.id.status);
                        t.setText("Thanks for your feedback!");
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setMessage("Thanks for your Feedback!");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        Toast.makeText(getActivity(), "Oops Something went wrong! Try again later! "+ s, Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    loadingDialog.dismiss();
                    Toast.makeText(getActivity(), "Oops Something went wrong! Please check your Internet connection or Try again later!", Toast.LENGTH_LONG).show();

                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute();

    }


}
