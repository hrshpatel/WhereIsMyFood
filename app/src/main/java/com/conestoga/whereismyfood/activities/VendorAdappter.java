package com.conestoga.whereismyfood.activities;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.conestoga.whereismyfood.R;

import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;


import androidx.recyclerview.widget.RecyclerView;

public class VendorAdappter extends RecyclerView.Adapter<ViewHolder> {

    private static final String TAG = "VendorAdapter";
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Callback mCallback;
    private List<Vendor> mVendorList;

    public VendorAdappter(List<Vendor> vendorList) {
        mVendorList = vendorList;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(@NonNull com.conestoga.whereismyfood.activities.ViewHolder holder, int position) {
            holder.onBind(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_vendor, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_empty, parent, false));
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (mVendorList != null && mVendorList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mVendorList != null && mVendorList.size() > 0) {
            return mVendorList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<Vendor> sportList) {
        mVendorList.addAll(sportList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onEmptyViewRetryClick();
    }

    public class ViewHolder extends com.conestoga.whereismyfood.activities.ViewHolder {

        @BindView(R.id.thumbnail)
        ImageView coverImageView;

        @BindView(R.id.title)
        TextView titleTextView;

        @BindView(R.id.vendorTitle)
        TextView nameTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            coverImageView.setImageDrawable(null);
            titleTextView.setText("");
            nameTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final Vendor mVendor = mVendorList.get(position);

            if (mVendor.getImageUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(mVendor.getImageUrl())
                        .into(coverImageView);
            }

            if (mVendor.getTitle() != null) {
                titleTextView.setText(mVendor.getTitle());
            }

            if (mVendor.getInfo() != null) {
                nameTextView.setText(mVendor.getInfo());
            }

            itemView.setOnClickListener(v -> {
                if (mVendor.getImageUrl() != null) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(mVendor.getImageUrl()));
                        itemView.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: Image url is not correct");
                    }
                }
            });
        }
    }

    public class EmptyViewHolder extends ViewHolder {

        @BindView(R.id.tv_message)
        TextView messageTextView;
        @BindView(R.id.buttonRetry)
        TextView buttonRetry;

        EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            buttonRetry.setOnClickListener(v -> mCallback.onEmptyViewRetryClick());
        }

        @Override
        protected void clear() {

        }

    }

}
