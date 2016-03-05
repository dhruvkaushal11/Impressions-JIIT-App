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

import com.squareup.picasso.Picasso;

public class CustomListAdapter_status_scapp extends ArrayAdapter<String> {

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
    public static int x=0;
    private final String[] type;
    private final String[] link ;

    public static String title_clicked="";
    public  static String code_clicked="";
    public static String description_clicked="";
    public static String time_clicked ="";
    public static String date_clicked="";
    public static String venue_clicked="";
    public static String rules_clicked="";
    public static String contact_clicked="";
    public static  String requirement_clicked="" ;

    public static String type_clicked="";
    public static  String link_clicked="" ;
    private int flag=1;

    public static int from_sell=0;
    public CustomListAdapter_status_scapp(Activity context, String[] code, String[] title, String[] date, String[] time, String[] venue,String[] description,String[] rules,String[] contact,String[] requirement,String[] type,String[] link) {
        super(context, R.layout.status_item_scapp, code);
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
        if (position == 9) {
            x=0;
            try{
                rowView = inflater.inflate(R.layout.status_item_scapp_all, null, true);
            }
            catch (Exception p){
                p.printStackTrace();
            }
            RelativeLayout onclick = (RelativeLayout) rowView.findViewById(R.id.see_all_rel);

            onclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("com.impressions.jiit.search");
                    context.startActivity(intent);
                    from_sell = 1;

                }
            });
            return rowView;


        }
        else if(position==6)
        {

            rowView = inflater.inflate(R.layout.status_item_scapp, null, true);
            //Toast.makeText(getContext(),""+username[position], Toast.LENGTH_LONG).show();
            TextView title1 = (TextView) rowView.findViewById(R.id.title);
            TextView post1 = (TextView) rowView.findViewById(R.id.message);
            title1.setText(title[position]);
            post1.setText(description[position]);
            RelativeLayout onclick = (RelativeLayout) rowView.findViewById(R.id.relclick);
            onclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title_clicked = title[position];
                    code_clicked = code[position];
                    description_clicked = description[position];
                    time_clicked = time[position];
                    date_clicked = date[position];
                    venue_clicked = venue[position];
                    rules_clicked = rules[position];
                    contact_clicked = contact[position];
                    requirement_clicked = requirement[position];
                    type_clicked = type[position];
                    link_clicked = link[position];
                    x=1;
                    Intent intent = new Intent("com.impressions.jiit.event_description");
                    context.startActivity(intent);
                }
            });
            ImageView dp_scapp = (ImageView) rowView.findViewById(R.id.memories_Dp);
            flag = 1;

            Picasso.with(context).load("http://jiitimpressions.com/impressions_app/event_pics/" + code[position] + ".jpg").error(R.drawable.scrapp).into(dp_scapp);

            return rowView;


        }
        else {
            x=0;
            rowView = inflater.inflate(R.layout.status_item_scapp, null, true);
            //Toast.makeText(getContext(),""+username[position], Toast.LENGTH_LONG).show();
            TextView title1 = (TextView) rowView.findViewById(R.id.title);
            TextView post1 = (TextView) rowView.findViewById(R.id.message);
            title1.setText(title[position]);
            post1.setText(description[position]);
            RelativeLayout onclick = (RelativeLayout) rowView.findViewById(R.id.relclick);
            onclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title_clicked = title[position];
                    code_clicked = code[position];
                    description_clicked = description[position];
                    time_clicked = time[position];
                    date_clicked = date[position];
                    venue_clicked = venue[position];
                    rules_clicked = rules[position];
                    contact_clicked = contact[position];
                    requirement_clicked = requirement[position];
                    type_clicked = type[position];
                    link_clicked = link[position];
                    Intent intent = new Intent("com.impressions.jiit.event_description");
                    context.startActivity(intent);
                }
            });
            ImageView dp_scapp = (ImageView) rowView.findViewById(R.id.memories_Dp);
            flag = 1;

            Picasso.with(context).load("http://jiitimpressions.com/impressions_app/event_pics/" + code[position] + ".jpg").error(R.drawable.scrapp).into(dp_scapp);

            return rowView;
        }
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
