package com.impressions.jiit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Dhruv on 7/29/2015.
 */
public class aboutus extends Fragment {

    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =inflater.inflate(R.layout.aboutus, null);
        ImageView img=(ImageView)rootView.findViewById(R.id.facebook);
        TextView play=(TextView)rootView.findViewById(R.id.play_store);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.impressions.jiit")));

            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/466135503503062/"));
                   startActivity(browserIntent);
               }
               catch(Exception e){
                   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/jiit.impressions/"));
                   startActivity(browserIntent);
                }
            }
        });
        TextView dk=(TextView)rootView.findViewById(R.id.textView5);
        dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100000592668610/"));
                    startActivity(browserIntent);
                }
                catch(Exception e){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/dhruv.kaushal11/"));
                    startActivity(browserIntent);

                }
            }
        });

        return rootView;



    }

}
