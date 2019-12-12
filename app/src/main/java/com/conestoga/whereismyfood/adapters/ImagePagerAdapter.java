package com.conestoga.whereismyfood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.utils.CommonUtils;

import java.util.ArrayList;

public class ImagePagerAdapter extends PagerAdapter {
    private ArrayList<String> imagePathList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ImageView mImageView;

    public ImagePagerAdapter(Context context, ArrayList<String> imagePathList) {
        this.mContext = context;
        this.imagePathList = imagePathList;

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0 == imagePathList.size() ? 1 : imagePathList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
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

            CommonUtils.loadImage(mContext, imageUri, mImageView);
            return itemView;
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
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
