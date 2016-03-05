package com.impressions.jiit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class sponsor_description extends AppCompatActivity {
    String code="";
    String name="";
    String description="";
    String link="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_description);
        code=CustomListAdapter_sponsor.code_clicked;
        name=CustomListAdapter_sponsor.name_clicked;
        description=CustomListAdapter_sponsor.description_clicked;
        link=CustomListAdapter_sponsor.link_clicked;

        TextView name1= (TextView)findViewById(R.id.name);
        name1.setText(name);

        TextView close= (TextView)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView description1= (TextView)findViewById(R.id.description_sponsor);
        description1.setText(description);

        TextView link1= (TextView)findViewById(R.id.link);
        link1.setText(link);

        ImageView img=(ImageView)findViewById(R.id.imageView_sponsor);
        Picasso.with(this).load("http://jiitimpressions.com/impressions_app/sponsor_pics/" + code + ".jpg").error(R.drawable.no_image).into(img);


    }

}
