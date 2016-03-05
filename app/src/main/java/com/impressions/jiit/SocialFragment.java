package com.impressions.jiit;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by dhruv on 7/29/2015.
 */
public class SocialFragment extends Fragment {
    View rootView;

    ArrayList<String> text = new ArrayList<String>();
    DatabaseHelper_notification Db1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.social_layout, null);
        return rootView;

    }


}











