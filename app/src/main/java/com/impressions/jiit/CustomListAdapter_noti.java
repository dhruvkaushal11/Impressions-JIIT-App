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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;

public class CustomListAdapter_noti extends ArrayAdapter<String> {

    protected static Activity context = null;
    public static String str="";
    private final String[] text;
    public CustomListAdapter_noti(Activity context, String[] text) {
        super(context, R.layout.status_item_notification, text);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.text=text;
    }

    public View getView(final int position,final View view,ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = null;
        try{
            rowView = inflater.inflate(R.layout.status_item_notification, null, true);
        }
        catch (Exception u){
            u.printStackTrace();
        }
        //Toast.makeText(getContext(),""+username[position], Toast.LENGTH_LONG).show();
        TextView post1 = (TextView) rowView.findViewById(R.id.message);
        post1.setText(text[position]);
        RelativeLayout rel=(RelativeLayout)rowView.findViewById(R.id.relclick_notification);
        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = text[position];
                Intent intent = new Intent("com.impressions.jiit.new_noti_list");
                context.startActivity(intent);
            }
        });
        ImageView img=(ImageView) rowView.findViewById(R.id.noti_image);
        Random r = new Random();
        int i1 = r.nextInt(7);
     if(i1==0) {
         img.setImageResource(R.drawable.badge0);
     }
        else if(i1==1){
         img.setImageResource(R.drawable.badge1);
     }
     else if(i1==2){
         img.setImageResource(R.drawable.badge2);
     }
     else if(i1==3){
         img.setImageResource(R.drawable.badge3);
     }
     else if(i1==4){
         img.setImageResource(R.drawable.badge4);
     }
     else if(i1==5){
         img.setImageResource(R.drawable.badge5);
     }
     else if(i1==6){
         img.setImageResource(R.drawable.badge6);
     }

        return rowView;
    }


}
