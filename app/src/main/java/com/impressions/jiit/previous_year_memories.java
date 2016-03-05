package com.impressions.jiit;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.GridView;
import android.widget.Toast;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

public class previous_year_memories extends AppCompatActivity {
    ArrayList<String> code = new ArrayList<String>();
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_year_memories);

        /*for(int i=0;i<50;i++){
            code.add(""+i);
        }
        GridView gd;
        try{

            CustomListAdapter_memories adapter=new CustomListAdapter_memories(this,code.toArray(new String[code.size()]));
            gd = (GridView)findViewById(R.id.gridView);
            gd.setAdapter(adapter);
        }
        catch (Exception e){
            e.printStackTrace();
        }*/


            webView = (WebView) findViewById(R.id.webView1);
            webView.getSettings().setJavaScriptEnabled(true);

            webView.loadUrl("http://jiitimpressions.com/impressions_app/memories.php");
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("file:///android_asset/myerrorpage.html");


            }

       });


        // Toast.makeText(this,""+code,Toast.LENGTH_LONG).show();

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
