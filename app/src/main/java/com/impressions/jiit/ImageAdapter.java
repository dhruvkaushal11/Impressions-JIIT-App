package com.impressions.jiit;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dhruv on 12/20/2015.
 */
public class ImageAdapter extends PagerAdapter {
    Context context;
    int i=0;
    private Timer myTimer;
    ImageView imageView;
    private int[] GalImages = new int[] {
            1
    };
    ImageAdapter(Context context){

        this.context=context;
    }
    @Override
    public int getCount() {

        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        imageView = new ImageView(context);
        int padding =10;
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(context).load("http://jiitimpressions.com/impressions_app/" +GalImages[position] +".jpg").error(R.drawable.default_banner).into(imageView);
        ((ViewPager) container).addView(imageView, i);

        return imageView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}