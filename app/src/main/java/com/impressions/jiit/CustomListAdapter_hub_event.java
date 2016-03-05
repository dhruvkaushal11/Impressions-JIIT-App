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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomListAdapter_hub_event extends ArrayAdapter<String> {

    protected static Activity context = null;
    private final String[] hub_code;
    private final String[] hub_name;

    private final String[] hub_faculty;
    public static String type_clicked="";

    public static String type_clicked_detail="";
    private int flag=1;

    public static int from_sell=0;
    public CustomListAdapter_hub_event(Activity context, String[] hub_code, String[] hub_name,String[] hub_faculty) {
        super(context, R.layout.activity_hub_type_activity, hub_code);
        // TODO Auto-generated constructor stub
        this.context=context;

        this.hub_code=hub_code;

        this.hub_name=hub_name;
         this.hub_faculty=hub_faculty;
    }

    public View getView(final int position, final View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView=null;

            try{
                rowView = inflater.inflate(R.layout.event_type_item, null, true);
            }
            catch (Exception r){
                r.printStackTrace();
            }
            //Toast.makeText(getContext(),""+username[position], Toast.LENGTH_LONG).show();
            TextView title1 = (TextView) rowView.findViewById(R.id.event_type_title);
            title1.setText(hub_name[position]+"");
        RelativeLayout onclick = (RelativeLayout) rowView.findViewById(R.id.event_type_id);
            onclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type_clicked = hub_code[position];
                    type_clicked_detail=hub_faculty[position];
                    Intent intent = new Intent("com.impressions.jiit.hub_type_activity");
                    context.startActivity(intent);
                }
            });

            return rowView;

    }

}
