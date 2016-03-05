package com.impressions.jiit;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;
import java.util.ArrayList;

import retrofit.RestAdapter;
/**
 * Created by DHruv on 7/29/2015.
 */
public class UpdatesFragment extends Fragment {
    ArrayList<String> titleid = new ArrayList<String>();
    ArrayList<String> codeid = new ArrayList<String>();
    ArrayList<String> descriptionid = new ArrayList<String>();
    ArrayList<String> timeid = new ArrayList<String>();
    ArrayList<String> dateid = new ArrayList<String>();
    ArrayList<String> venueid = new ArrayList<String>();
    ArrayList<String> ruleid = new ArrayList<String>();
    ArrayList<String> contactid = new ArrayList<String>();
    ArrayList<String> requirementid = new ArrayList<String>();
    ArrayList<String> link = new ArrayList<String>();
    ArrayList<String> type = new ArrayList<String>();





    ArrayList<String> event_type = new ArrayList<String>();


    ArrayList<String> hub_code = new ArrayList<String>();
    ArrayList<String> hub_name = new ArrayList<String>();
    ArrayList<String> hub_faculty = new ArrayList<String>();




    ArrayList<String> sponsor_code = new ArrayList<String>();
    ArrayList<String> sponsor_name = new ArrayList<String>();
    ArrayList<String> sponsor_description = new ArrayList<String>();
    ArrayList<String> sponsor_link = new ArrayList<String>();


    String myJSON;
    JSONArray peoples = null;
    View reg;
    int flag=1;
    DatabaseHelper_latest Db1;

    DatabaseHelper_popular Db2;
    private static final String API_URL = "http://jiitimpressions.com/impressions_app";
    private static final String API_KEY = "-------";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reg=inflater.inflate(R.layout.updates_layout,null);
        Intent intentService = new Intent(getActivity(), service1.class);
        getActivity().startService(intentService);

        final ViewPager viewPager = (ViewPager)reg.findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(getActivity());
        viewPager.setAdapter(adapter);

       // rate_us_on_play_store();

        showList_latest_offline();
        flag=1;
        checkforinternet();
        if(flag==1) {
            rate_us_on_play_store();
            get_event_Type task2 = new get_event_Type();
            task2.execute();

            get_hub_Type task3 = new get_hub_Type();
            task3.execute();

            get_latest task = new get_latest();
            task.execute();
        }
        else{

            showList_latest_offline();
        }
        flag=1;
        checkforinternet();
        if(flag>0) {
            RelativeLayout memories = (RelativeLayout) reg.findViewById(R.id.lol);
            memories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("com.impressions.jiit.previous_year_memories");
                    startActivity(intent);
                }
            });
        }
        else{
            Toast.makeText(getActivity(),"No Internet Connection!",Toast.LENGTH_LONG).show();
        }
        return reg;
    }
    private void checkforinternet() {
        ConnectivityManager cm2 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni2 = cm2.getActiveNetworkInfo();
        if (ni2 == null) {
            //Toast.makeText(MainActivity.this, "Not Connected to Internet!!", Toast.LENGTH_LONG).show();
            flag = 0;
            return;
        }
    }

    private class get_latest extends AsyncTask<Void, Void,
            Curator> {
        RestAdapter restAdapter;

        @Override
        protected void onPreExecute() {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .build();
        }

        @Override
        protected Curator doInBackground(Void... params) {
            IApiMethods methods = restAdapter.create(IApiMethods.class);
            Curator curators=null;
            try {
                curators = methods.getCurators(API_KEY);


            }
            catch (Exception e){
                e.printStackTrace();
            }

            return curators;
        }

        @Override
        protected void onPostExecute(Curator curators) {
            try {
                 myJSON=new Gson().toJson(curators);
                if(myJSON.contains("result")){
                    try{
                        //writing in offline database
                        Db1=new DatabaseHelper_latest(getActivity());
                        Db1.droptable();
                        Db1.createtable();
                        Db1.insertData(myJSON);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                for (Curator.Dataset dataset : curators.result) {

                    if(dataset.code==null){
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setMessage("Something went wrong! Please Restart this app!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                    else {
                        codeid.add(dataset.code);
                    }
                    titleid.add( dataset.title);

                    if(dataset.date==null){
                        dateid.add("Not decided yet!");
                    }
                    else{
                        dateid.add( dataset.date);
                    }

                    timeid.add( dataset.time);

                    if(dataset.venue==null){
                        venueid.add("Not decided yet!");
                    }
                    else{
                        venueid.add( dataset.venue);
                    }
                    descriptionid.add( dataset.description);

                    if(dataset.rules==null){
                        ruleid.add("No Rules Apply!");
                    }
                    else{
                        ruleid.add( dataset.rules);
                    }
                    contactid.add( dataset.contact);

                    if(dataset.requirement==null){
                        requirementid.add("No Requirements!");
                    }
                    else{
                        requirementid.add(dataset.requirement);
                    }
                    link.add( dataset.link);
                    type.add( dataset.type);

                }
                showList();
                }
                else{

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setMessage("Problem reaching servers at the moment!\n Please make sure you are logged in to your Wifi or Try again later!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }

            }
            catch (Exception e){
                e.printStackTrace();
            }

            titleid = new ArrayList<String>();
            codeid = new ArrayList<String>();
            descriptionid = new ArrayList<String>();
            timeid = new ArrayList<String>();
            dateid = new ArrayList<String>();
            venueid = new ArrayList<String>();
            ruleid = new ArrayList<String>();
            contactid = new ArrayList<String>();
            requirementid = new ArrayList<String>();
            link = new ArrayList<String>();
            type = new ArrayList<String>();
        }



    }


    private class get_popular extends AsyncTask<Void, Void,
            Curator> {
        RestAdapter restAdapter;

        @Override
        protected void onPreExecute() {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .build();
        }

        @Override
        protected Curator doInBackground(Void... params) {
            IApiMethods_popular methods = restAdapter.create(IApiMethods_popular.class);
            Curator curators=null;
            try{
                curators = methods.getCurators(API_KEY);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return curators;
        }

        @Override
        protected void onPostExecute(Curator curators) {
            try{
                myJSON=new Gson().toJson(curators);
                if(myJSON.contains("result")){
                    try{
                        //writing in offline database
                        Db2=new DatabaseHelper_popular(getActivity());
                        Db2.droptable();
                        Db2.createtable();
                        Db2.insertData(myJSON);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

            for (Curator.Dataset dataset : curators.result) {

                if (dataset.code == null) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setMessage("Something went wrong! Please Restart this app!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else {
                    codeid.add(dataset.code);
                }
                titleid.add(dataset.title);

                if (dataset.date == null) {
                    dateid.add("Not decided yet!");
                }
                else {
                    dateid.add(dataset.date);
                }

                timeid.add(dataset.time);

                if (dataset.venue == null) {
                    venueid.add("Not decided yet!");
                } else {
                    venueid.add(dataset.venue);
                }
                descriptionid.add(dataset.description);

                if (dataset.rules == null) {
                    ruleid.add("No Rules Apply!");
                } else {
                    ruleid.add(dataset.rules);
                }
                contactid.add(dataset.contact);

                if (dataset.requirement == null) {
                    requirementid.add("No Requirements!");
                } else {
                    requirementid.add(dataset.requirement);
                }
                link.add(dataset.link);
                type.add(dataset.type);
                }
                showList_popular();
                }
                else{

                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setMessage("Problem reaching servers at the moment!\n Please make sure you are logged in to your Wifi or Try again later!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                }

            }
            catch (Exception e){
                e.printStackTrace();
            }

            titleid = new ArrayList<String>();
            codeid = new ArrayList<String>();
            descriptionid = new ArrayList<String>();
            timeid = new ArrayList<String>();
            dateid = new ArrayList<String>();
            venueid = new ArrayList<String>();
            ruleid = new ArrayList<String>();
            contactid = new ArrayList<String>();
            requirementid = new ArrayList<String>();
            link = new ArrayList<String>();
            type = new ArrayList<String>();
        }
    }




    private class get_sponsor extends AsyncTask<Void, Void,
            Curator_sponsor> {
        RestAdapter restAdapter;

        @Override
        protected void onPreExecute() {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .build();
        }

        @Override
        protected Curator_sponsor doInBackground(Void... params) {
            IApiMethods_sponsor methods = restAdapter.create(IApiMethods_sponsor.class);
            Curator_sponsor curators=null;
            try{
                curators = methods.getCurators(API_KEY);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return curators;
        }

        @Override
        protected void onPostExecute(Curator_sponsor curators) {
            try{
                myJSON=new Gson().toJson(curators);
                if(myJSON.contains("result")) {

                    for (Curator_sponsor.Dataset dataset : curators.result) {


                        sponsor_code.add(dataset.code);

                        sponsor_name.add(dataset.name);
                        sponsor_description.add(dataset.description);
                        sponsor_link.add(dataset.link);

                        showList_sponsor();
                    }
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }

            sponsor_code = new ArrayList<String>();
             sponsor_name = new ArrayList<String>();
            sponsor_description = new ArrayList<String>();
            sponsor_link = new ArrayList<String>();

        }
    }

    private void rate_us_on_play_store() {
        SharedPreferences sharedpreferences;

        String noti = "total_open";

        String MyPREFERENCES = "IMP_pref" ;
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int x;
        x = sharedpreferences.getInt(noti, -1);
        if (x == 10) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setIcon(R.drawable.scraap).setMessage("Rate us on Play Store!")
                    .setPositiveButton("Rate", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.impressions.jiit")));
                            } catch (Exception e) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.impressions.jiit")));
                            }
                        }
                    })
                    .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
            ;
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            SharedPreferences preferences = getActivity().getSharedPreferences(MyPREFERENCES, 0);
            preferences.edit().remove(noti).commit();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(noti, ++x);
            editor.commit();


        }
        else if(x<10) {

            SharedPreferences preferences = getActivity().getSharedPreferences(MyPREFERENCES, 0);
            preferences.edit().remove(noti).commit();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(noti, ++x);
            editor.commit();
        }
        else{
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(noti, 0);
            editor.commit();
           // Toast.makeText(getActivity(),"else",Toast.LENGTH_LONG).show();

        }

    }

   protected void showList_popular() {

       TwoWayView list;
       try{
           CustomListAdapter_status_scapp adapter=new CustomListAdapter_status_scapp(getActivity(),codeid.toArray(new String[codeid.size()]),titleid.toArray(new String[titleid.size()]),dateid.toArray(new String[dateid.size()]),timeid.toArray(new String[timeid.size()]),venueid.toArray(new String[venueid.size()]),descriptionid.toArray(new String[descriptionid.size()]),ruleid.toArray(new String[ruleid.size()]),contactid.toArray(new String[contactid.size()]),requirementid.toArray(new String[requirementid.size()]),type.toArray(new String[type.size()]),link.toArray(new String[link.size()]));
       list = (TwoWayView )reg.findViewById(R.id.lvItems2);
       list.setAdapter(adapter);

       }
       catch (Exception e){
           e.printStackTrace();
       }


    }
    protected void showList() {

        TwoWayView list;
        try{

            CustomListAdapter_status_scapp adapter=new CustomListAdapter_status_scapp(getActivity(),codeid.toArray(new String[codeid.size()]),titleid.toArray(new String[titleid.size()]),dateid.toArray(new String[dateid.size()]),timeid.toArray(new String[timeid.size()]),venueid.toArray(new String[venueid.size()]),descriptionid.toArray(new String[descriptionid.size()]),ruleid.toArray(new String[ruleid.size()]),contactid.toArray(new String[contactid.size()]),requirementid.toArray(new String[requirementid.size()]),type.toArray(new String[type.size()]),link.toArray(new String[link.size()]));
        list = (TwoWayView)reg.findViewById(R.id.lvItems);


            list.setAdapter(adapter);

        }
        catch (Exception e){
            e.printStackTrace();
        }

            get_popular task = new get_popular();
            task.execute();




    }
    protected void showList_popular_offline() {
        try {

            try {
                Db2 = new DatabaseHelper_popular(getActivity());
                Cursor res2 = Db2.getlogin();
                if (res2.getCount() > 0) {
                    while (res2.moveToNext()) {
                        myJSON = res2.getString(0);
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            titleid = new ArrayList<String>();
            codeid = new ArrayList<String>();
            descriptionid = new ArrayList<String>();
            timeid = new ArrayList<String>();
            dateid = new ArrayList<String>();
            venueid = new ArrayList<String>();
            ruleid = new ArrayList<String>();
            contactid = new ArrayList<String>();
            requirementid = new ArrayList<String>();
            link = new ArrayList<String>();
            type = new ArrayList<String>();


           try {
               JSONObject jsonObj = new JSONObject(myJSON);
               peoples = jsonObj.getJSONArray("result");
               for (int i = 0; i < peoples.length(); i++) {

                   try {
                       JSONObject c = peoples.getJSONObject(i);
                       codeid.add(c.getString("code"));
                       titleid.add(c.getString("title"));
                       dateid.add(c.getString("date"));
                       timeid.add(c.getString("time"));
                       venueid.add(c.getString("venue"));
                       descriptionid.add(c.getString("description"));
                       ruleid.add(c.getString("rules"));
                       contactid.add(c.getString("contact"));
                       requirementid.add(c.getString("requirement"));
                       link.add(c.getString("link"));
                       type.add(c.getString("type"));

                   } catch (NullPointerException e) {


                   }
               }
           }
           catch (Exception e) {
               e.printStackTrace();
           }


        }
        catch (Exception e){
            e.printStackTrace();
        }

        TwoWayView list;
        CustomListAdapter_status_scapp adapter=new CustomListAdapter_status_scapp(getActivity(),codeid.toArray(new String[codeid.size()]),titleid.toArray(new String[titleid.size()]),dateid.toArray(new String[dateid.size()]),timeid.toArray(new String[timeid.size()]),venueid.toArray(new String[venueid.size()]),descriptionid.toArray(new String[descriptionid.size()]),ruleid.toArray(new String[ruleid.size()]),contactid.toArray(new String[contactid.size()]),requirementid.toArray(new String[requirementid.size()]),type.toArray(new String[type.size()]),link.toArray(new String[link.size()]));
        list = (TwoWayView)reg.findViewById(R.id.lvItems2);
        list.setAdapter(adapter);


        titleid = new ArrayList<String>();
        codeid = new ArrayList<String>();
        descriptionid = new ArrayList<String>();
        timeid = new ArrayList<String>();
        dateid = new ArrayList<String>();
        venueid = new ArrayList<String>();
        ruleid = new ArrayList<String>();
        contactid = new ArrayList<String>();
        requirementid = new ArrayList<String>();
        link = new ArrayList<String>();
        type = new ArrayList<String>();

        get_sponsor task4 = new get_sponsor();
        task4.execute();

    }



    protected void showList_latest_offline() {
        try {
                try {
                    Db1 = new DatabaseHelper_latest(getActivity());
                    Cursor res1 = Db1.getlogin();
                    if (res1.getCount() > 0) {
                        while (res1.moveToNext()) {
                            myJSON = res1.getString(0);
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                titleid = new ArrayList<String>();
                codeid = new ArrayList<String>();
                descriptionid = new ArrayList<String>();
                timeid = new ArrayList<String>();
                dateid = new ArrayList<String>();
                venueid = new ArrayList<String>();
                ruleid = new ArrayList<String>();
                contactid = new ArrayList<String>();
                requirementid = new ArrayList<String>();
                link = new ArrayList<String>();
                type = new ArrayList<String>();


           try {
               JSONObject jsonObj = new JSONObject(myJSON);
               peoples = jsonObj.getJSONArray("result");
               for (int i = 0; i < peoples.length(); i++) {

                   try {
                       JSONObject c = peoples.getJSONObject(i);
                       codeid.add(c.getString("code"));
                       titleid.add(c.getString("title"));
                       dateid.add(c.getString("date"));
                       timeid.add(c.getString("time"));
                       venueid.add(c.getString("venue"));
                       descriptionid.add(c.getString("description"));
                       ruleid.add(c.getString("rules"));
                       contactid.add(c.getString("contact"));
                       requirementid.add(c.getString("requirement"));
                       link.add(c.getString("link"));
                       type.add(c.getString("type"));

                   } catch (NullPointerException e) {


                   } catch (RuntimeException v) {

                   }
               }
           }
           catch (Exception e){
               e.printStackTrace();
           }



        } catch (Exception d) {
            d.printStackTrace();
        }
        TwoWayView list;
        CustomListAdapter_status_scapp adapter=new CustomListAdapter_status_scapp(getActivity(),codeid.toArray(new String[codeid.size()]),titleid.toArray(new String[titleid.size()]),dateid.toArray(new String[dateid.size()]),timeid.toArray(new String[timeid.size()]),venueid.toArray(new String[venueid.size()]),descriptionid.toArray(new String[descriptionid.size()]),ruleid.toArray(new String[ruleid.size()]),contactid.toArray(new String[contactid.size()]),requirementid.toArray(new String[requirementid.size()]),type.toArray(new String[type.size()]),link.toArray(new String[link.size()]));
        list = (TwoWayView)reg.findViewById(R.id.lvItems);
        list.setAdapter(adapter);


        titleid = new ArrayList<String>();
        codeid = new ArrayList<String>();
        descriptionid = new ArrayList<String>();
        timeid = new ArrayList<String>();
        dateid = new ArrayList<String>();
        venueid = new ArrayList<String>();
        ruleid = new ArrayList<String>();
        contactid = new ArrayList<String>();
        requirementid = new ArrayList<String>();
        link = new ArrayList<String>();
        type = new ArrayList<String>();
        showList_popular_offline();

    }




    private class get_event_Type extends AsyncTask<Void, Void,
            Curator_event_type> {
        RestAdapter restAdapter;

        @Override
        protected void onPreExecute() {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .build();
        }

        @Override
        protected Curator_event_type doInBackground(Void... params) {
            IApiMethods_event_type methods = restAdapter.create(IApiMethods_event_type.class);
            Curator_event_type curators=null;
            try {
                curators = methods.getCurators(API_KEY);


            }
            catch (Exception e){
                e.printStackTrace();
            }

            return curators;
        }

        @Override
        protected void onPostExecute(Curator_event_type curators) {
            try {
                myJSON=new Gson().toJson(curators);
                if(myJSON.contains("result")){
                   /* try{
                        //writing in offline database
                        Db1=new DatabaseHelper_latest(getActivity());
                        Db1.droptable();
                        Db1.createtable();
                        Db1.insertData(myJSON);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }*/

                    for (Curator_event_type.Dataset dataset : curators.result) {

                        if(dataset.type==null){
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setMessage("Something went wrong! Please Restart this app!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                        else {
                            event_type.add(dataset.type);
                        }


                    }
                    showList_event_Type();
                }
                else{

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setMessage("Problem reaching servers at the moment!\n Please make sure you are logged in to your Wifi or Try again later!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }

            }
            catch (Exception e){
                e.printStackTrace();
            }


            event_type = new ArrayList<String>();
        }



    }
    protected void showList_event_Type() {

        TwoWayView list;
        try{

            CustomListAdapter_type_event adapter=new CustomListAdapter_type_event(getActivity(),event_type.toArray(new String[event_type.size()]));
            list = (TwoWayView)reg.findViewById(R.id.faculty_contact);


            list.setAdapter(adapter);

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    private class get_hub_Type extends AsyncTask<Void, Void,
            Curator_hub_type> {
        RestAdapter restAdapter;

        @Override
        protected void onPreExecute() {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .build();
        }

        @Override
        protected Curator_hub_type doInBackground(Void... params) {
            IApiMethods_hub_type methods = restAdapter.create(IApiMethods_hub_type.class);
            Curator_hub_type curators=null;
            try {
                curators = methods.getCurators(API_KEY);


            }
            catch (Exception e){
                e.printStackTrace();
            }

            return curators;
        }

        @Override
        protected void onPostExecute(Curator_hub_type curators) {
            try {
                myJSON=new Gson().toJson(curators);
                if(myJSON.contains("result")){
                   // Toast.makeText(getActivity(),""+myJSON,Toast.LENGTH_LONG).show();
                   /* try{
                        //writing in offline database
                        Db1=new DatabaseHelper_latest(getActivity());
                        Db1.droptable();
                        Db1.createtable();
                        Db1.insertData(myJSON);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }*/

                    for (Curator_hub_type.Dataset dataset : curators.result) {

                        if(dataset.code==null){
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setMessage("Something went wrong! Please Restart this app!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                        else {
                            hub_code.add(dataset.code);
                            hub_name.add(dataset.name);
                            hub_faculty.add(dataset.faculty_detail);
                        }



                    }
                    showList_hub_Type();
                }
                else{

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setMessage("Problem reaching servers at the moment!\n Please make sure you are logged in to your Wifi or Try again later!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }

            }
            catch (Exception e){
                e.printStackTrace();
            }


            hub_code = new ArrayList<String>();

            hub_name = new ArrayList<String>();

            hub_faculty = new ArrayList<String>();
        }



    }
    protected void showList_hub_Type() {

        TwoWayView list;
        try{

            CustomListAdapter_hub_event adapter=new CustomListAdapter_hub_event(getActivity(),hub_code.toArray(new String[hub_code.size()]),hub_name.toArray(new String[hub_name.size()]),hub_faculty.toArray(new String[hub_faculty.size()]));
            list = (TwoWayView)reg.findViewById(R.id.lvItems3);
            list.setAdapter(adapter);

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    protected void showList_sponsor() {

        TwoWayView list;
        try{

            CustomListAdapter_sponsor adapter=new CustomListAdapter_sponsor(getActivity(),sponsor_code.toArray(new String[sponsor_code.size()]),sponsor_name.toArray(new String[sponsor_name.size()]),sponsor_description.toArray(new String[sponsor_description.size()]),sponsor_link.toArray(new String[sponsor_link.size()]));
            list = (TwoWayView)reg.findViewById(R.id.lvItems4);
            list.setAdapter(adapter);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }





}
