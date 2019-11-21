package com.conestoga.whereismyfood.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.conestoga.whereismyfood.R;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;

public class ImagePagerAdapter extends PagerAdapter {
    private ArrayList<String> imagePathList;
    private Activity mContext;
    private LayoutInflater mLayoutInflater;
    private ImageView mImageView;

    public ImagePagerAdapter(Activity context, ArrayList<String> imagePathList) {
        this.mContext = context;
        this.imagePathList = imagePathList;

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0 == imagePathList.size() ? 1 : imagePathList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.image_pager, container, false);
        if (imagePathList.size() == 0) {
            mImageView = itemView.findViewById(R.id.image_view_pager);
            mImageView.setImageResource(R.drawable.placeholder);
            container.addView(itemView);

            return itemView;
        } else {
            mImageView = itemView.findViewById(R.id.image_view_pager);
            container.addView(itemView);

            final String imageUri = imagePathList.get(position);

            Glide.with(mContext)
                    .load(imageUri)
                    .into(mImageView);

            return itemView;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try {
            // Remove the view from the container
            container.removeView((View) object);
            // Try to clear resources used for displaying this view
            mImageView = ((View) object).findViewById(R.id.image_view_pager);
            // Remove any resources used by this view
            mImageView = null;
            unbindDrawables((View) object, position);
            // Invalidate the object
            object = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unbindDrawables(View view, int position) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                if (position == i) {
                    continue;
                }
                unbindDrawables(((ViewGroup) view).getChildAt(i), position);
                ((ViewGroup) view).removeViewAt(i);
            }
        }
    }
}
