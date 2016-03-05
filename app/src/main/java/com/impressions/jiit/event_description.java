package com.impressions.jiit;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class event_description extends Activity {
    public static String code_=null;
    public static String title_=null;
    public static int y=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);

        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
         TextView title = (TextView)findViewById(R.id.title);
        title.setText("" + CustomListAdapter_status_scapp.title_clicked);
        y=CustomListAdapter_status_scapp.x;
        if(y==1){

                    title.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Intent intent = new Intent("com.impressions.jiit.useless");
                            startActivity(intent);
                            return false;
                        }
                    });
        }
        TextView description=(TextView)findViewById(R.id.description);
        description.setText("" + CustomListAdapter_status_scapp.description_clicked);
        TextView time=(TextView)findViewById(R.id.time);
        time.setText(""+CustomListAdapter_status_scapp.time_clicked);
        TextView date=(TextView)findViewById(R.id.date);
        date.setText(""+CustomListAdapter_status_scapp.date_clicked);
        TextView venue=(TextView)findViewById(R.id.venue);
        venue.setText(""+CustomListAdapter_status_scapp.venue_clicked);
        TextView contact=(TextView)findViewById(R.id.contact);
        contact.setText(""+CustomListAdapter_status_scapp.contact_clicked);
        TextView rules=(TextView)findViewById(R.id.rules);
        rules.setText(""+CustomListAdapter_status_scapp.rules_clicked);
        TextView requirement=(TextView)findViewById(R.id.requirement);
        requirement.setText("" + CustomListAdapter_status_scapp.requirement_clicked);
        ImageView pic=(ImageView)findViewById(R.id.imageView12);
        Picasso.with(getBaseContext()).load("http://jiitimpressions.com/impressions_app/event_pics/" + CustomListAdapter_status_scapp.code_clicked + ".jpg").error(R.drawable.no_image).into(pic);
       // Toast.makeText(getBaseContext(),"http://dhruvkaushal.xyz/impressions/event_pics/" + CustomListAdapter_status_scapp.code_clicked + ".jpg",Toast.LENGTH_LONG).show();
        TextView whatsapp=(TextView)findViewById(R.id.share_whatsapp);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey!\n Check out this Jiit Impressions 2016 Event \n" + CustomListAdapter_status_scapp.title_clicked + "\nDownload Impressions 2016 Official App from the Playstore \nhttps://play.google.com/store/apps/details?id=com.impresssions.jiit"


                );
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });






        TextView register=(TextView)findViewById(R.id.register_event);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CustomListAdapter_status_scapp.type_clicked.contains("0")){
                    code_=CustomListAdapter_status_scapp.code_clicked;
                    title_ =CustomListAdapter_status_scapp.title_clicked;
                    //Intent intent=new Intent("com.impressions.jiit.event_registration");
                    try {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.setPackage("com.goyayy.app");
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "" + code_);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                    catch (Exception e){

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(event_description.this);
                        alertDialogBuilder.setMessage("You are just one step away!\nYou need to download GoYayy app for event participation!")
                                .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.goyayy.app")));
                                        } catch (Exception e) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.goyayy.app")));
                                        }


                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                        ;
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }



                    //startActivity(intent);
                }
                else if(CustomListAdapter_status_scapp.type_clicked.contains("1")){

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(event_description.this);
                    alertDialogBuilder.setMessage("You need to manually register for this event!!\n")
                            .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(""+CustomListAdapter_status_scapp.link_clicked));
                                    startActivity(browserIntent);

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                    ;
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    }
                else if(CustomListAdapter_status_scapp.type_clicked.contains("2")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(event_description.this);
                    alertDialogBuilder.setMessage("Registration for this event has not been started yet!\n")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            })
                    ;
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else{
                    Toast.makeText(getBaseContext(),"Something went wrong!!",Toast.LENGTH_LONG).show();


                }
            }


        });


    }


}
