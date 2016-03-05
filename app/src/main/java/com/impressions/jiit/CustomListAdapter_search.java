package com.impressions.jiit;
/**
 * Created by Dhruv on 8/11/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class CustomListAdapter_search extends ArrayAdapter<String> {

    protected static Activity context = null;
    private final String[] title;
    private final String[] code;
    private final String[] description;
    private final String[] time ;
    private final String[] date;
    private final String[] venue;
    private final String[] rules;
    private final String[] contact;
    private final String[] requirement ;
    private final String[] type;
    private final String[] link ;



    private int flag=1;
    public CustomListAdapter_search(Activity context, String[] code, String[] title, String[] date, String[] time, String[] venue,String[] description,String[] rules,String[] contact,String[] requirement,String[] type,String[] link) {
        super(context, R.layout.status_item_scapp_search, code);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.code=code;
        this.title=title;
        this.description=description;
        this.date=date;
        this.time=time;
        this.venue=venue;
        this.rules=rules;
        this.contact=contact;
        this.requirement=requirement;
        this.type=type;
        this.link=link;

    }

    public View getView(final int position, final View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = null;
        try{
            rowView = inflater.inflate(R.layout.status_item_scapp_search, null, true);
        }

        catch (Exception i){
            i.printStackTrace();
        }
            //Toast.makeText(getContext(),""+username[position], Toast.LENGTH_LONG).show();
        TextView title1 = (TextView) rowView.findViewById(R.id.title);
        TextView post1 = (TextView) rowView.findViewById(R.id.message);
        title1.setText(title[position]);
        post1.setText(description[position]);

        RelativeLayout onclick=(RelativeLayout)rowView.findViewById(R.id.relclick_search);

        onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomListAdapter_status_scapp.title_clicked = title[position];
                // Toast.makeText(getContext(),""+code[position],Toast.LENGTH_LONG).show();
                CustomListAdapter_status_scapp.code_clicked = code[position];
                CustomListAdapter_status_scapp.description_clicked = description[position];
                CustomListAdapter_status_scapp.time_clicked = time[position];
                CustomListAdapter_status_scapp.date_clicked = date[position];
                CustomListAdapter_status_scapp.venue_clicked = venue[position];
                CustomListAdapter_status_scapp.rules_clicked = rules[position];
                CustomListAdapter_status_scapp.contact_clicked = contact[position];
                CustomListAdapter_status_scapp.requirement_clicked = requirement[position];
                CustomListAdapter_status_scapp.type_clicked = type[position];
                CustomListAdapter_status_scapp.link_clicked = link[position];
                Intent intent = new Intent("com.impressions.jiit.event_description");
                context.startActivity(intent);
            }
        });
        ImageView dp_scapp = (ImageView) rowView.findViewById(R.id.imageView_dp);


                Picasso.with(context).load("http://jiitimpressions.com/impressions_app/event_pics/" + code[position] + ".jpg").resize(50,50).error(R.drawable.scrapp).into(dp_scapp);



            return rowView;
    }


    private void checkforinternet() {
        ConnectivityManager cm2 = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni2 = cm2.getActiveNetworkInfo();
        if (ni2 == null) {
            //Toast.makeText(MainActivity.this, "Not Connected to Internet!!", Toast.LENGTH_LONG).show();
            flag = 0;
            return;
        }
    }
}
