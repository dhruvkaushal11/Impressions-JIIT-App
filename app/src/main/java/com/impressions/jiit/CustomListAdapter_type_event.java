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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomListAdapter_type_event extends ArrayAdapter<String> {

    protected static Activity context = null;
    private final String[] type_event;
    public static String type_clicked="";
    private int flag=1;

    public static int from_sell=0;
    public CustomListAdapter_type_event(Activity context,String[] type_event) {
        super(context, R.layout.status_item_scapp, type_event);
        // TODO Auto-generated constructor stub
        this.context=context;

        this.type_event=type_event;

    }

    public View getView(final int position, final View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = null;

            try{
                rowView = inflater.inflate(R.layout.event_type_item, null, true);
            }
            catch (Exception s){
                s.printStackTrace();
            }
            //Toast.makeText(getContext(),""+username[position], Toast.LENGTH_LONG).show();
            TextView title1 = (TextView) rowView.findViewById(R.id.event_type_title);
      // title1.setTextColor(0xffffffff);
            title1.setText(type_event[position]);

        RelativeLayout onclick = (RelativeLayout) rowView.findViewById(R.id.event_type_id);
            onclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type_clicked = type_event[position];
                    Intent intent = new Intent("com.impressions.jiit.event_type_activity");
                    context.startActivity(intent);
                }
            });

            return rowView;

    }

}
