package com.impressions.jiit;
/**
 * Created by Dhruv on 8/11/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomListAdapter_memories extends ArrayAdapter<String> {
    private final String[] number;
    protected static Activity context = null;
    public CustomListAdapter_memories(Activity context,String[] number) {
        super(context, R.layout.status_item_memories,number);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.number=number;
    }
    public View getView(final int position, final View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
         View rowView=null;
       try {
           rowView = inflater.inflate(R.layout.status_item_memories, null, true);
       }
       catch(Exception d){
           d.printStackTrace();
       }
        ImageView abc=(ImageView)rowView.findViewById(R.id.memories_Dp);
        Picasso.with(context).load("http://jiitimpressions.com/impressions_app/memories_pics/" +number[position] + ".jpg").error(R.drawable.failed).into(abc);

        return rowView;
    }

}
