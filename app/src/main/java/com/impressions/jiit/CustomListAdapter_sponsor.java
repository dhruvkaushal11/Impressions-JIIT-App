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

import com.squareup.picasso.Picasso;

public class CustomListAdapter_sponsor extends ArrayAdapter<String> {

    protected static Activity context = null;
    private final String[] code;
    private final String[] name;
    private final String[] description;
    private final String[] link;

    public static String code_clicked="";

    public static String name_clicked="";
    public static String description_clicked="";
    public static String link_clicked="";


    private int flag=1;

    public static int from_sell=0;
    public CustomListAdapter_sponsor(Activity context, String[] code, String[] name, String[] description, String[] link) {
        super(context, R.layout.status_item_sponsor, code);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.code=code;
        this.name=name;
        this.description=description;
        this.link=link;



    }

    public View getView(final int position, final View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = null;

            try{
                rowView = inflater.inflate(R.layout.status_item_sponsor, null, true);
            }
            catch (Exception o){
                o.printStackTrace();
            }
            //Toast.makeText(getContext(),""+username[position], Toast.LENGTH_LONG).show();

        ImageView img=(ImageView)rowView.findViewById(R.id.memories_Dp);
        Picasso.with(context).load("http://jiitimpressions.com/impressions_app/sponsor_pics/" + code[position] + ".jpg").error(R.drawable.scrapp).into(img);



        RelativeLayout onclick = (RelativeLayout) rowView.findViewById(R.id.relclick);
            onclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    code_clicked = code[position];
                    name_clicked = name[position];
                    description_clicked = description[position];
                    link_clicked = link[position];
                    Intent intent = new Intent("com.impressions.jiit.sponsor_description");
                    context.startActivity(intent);
                }
            });

            return rowView;

    }

}
