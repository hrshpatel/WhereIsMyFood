package com.conestoga.whereismyfood.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.activities.ShowSubscriptionActivity;
import com.conestoga.whereismyfood.models.SubscriptionModel;
import com.conestoga.whereismyfood.utils.AppSharedPref;
import com.conestoga.whereismyfood.utils.CommonUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeViewHolder> {

    private final AppSharedPref mSharedPref;
    private final Context mContext;
    private ArrayList<SubscriptionModel> mSubscriptionModelList;
    private ImagePagerAdapter imagePagerAdapter;

    public HomeListAdapter(Context context, ArrayList<SubscriptionModel> subscriptionModelList) {
        this.mContext = context;
        mSharedPref = AppSharedPref.getInstance(mContext);
        this.mSubscriptionModelList = subscriptionModelList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);

        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

        final SubscriptionModel subscriptionModel = mSubscriptionModelList.get(position);

        holder.txtPrice.setText("$" + subscriptionModel.getPrice());
        holder.txtSubName.setText(subscriptionModel.getSubName());
        holder.txtVendorName.setText(subscriptionModel.getVendorName());
        holder.txtRating.setText(subscriptionModel.getRatings());
        holder.txtRatingCount.setText(subscriptionModel.getRatingCount() + " Rating(s)");

        imagePagerAdapter = new ImagePagerAdapter(mContext, subscriptionModel.getImageList());
        holder.itemViewPager.setAdapter(imagePagerAdapter);
        holder.itemViewPager.setOffscreenPageLimit(0);
        holder.itemTabLayout.setupWithViewPager(holder.itemViewPager, true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowSubscriptionActivity.class);
                intent.putExtra(CommonUtils.INTENT_SUB_ID, subscriptionModel.getSubId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSubscriptionModelList.size();
    }

    public void setSubscriptionList(ArrayList<SubscriptionModel> mSubscriptionModelList) {
        this.mSubscriptionModelList = mSubscriptionModelList;
        notifyDataSetChanged();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtSubName;
        private final ViewPager itemViewPager;
        private final TabLayout itemTabLayout;
        private final TextView txtVendorName;
        private final TextView txtRating;
        private final TextView txtRatingCount;
        private final TextView txtPrice;

        HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemViewPager = itemView.findViewById(R.id.recycler_view_pager);
            itemTabLayout = itemView.findViewById(R.id.recycler_image_tab);
            txtSubName = itemView.findViewById(R.id.item_home_txt_name);
            txtVendorName = itemView.findViewById(R.id.item_home_txt_vendor_name);
            txtRating = itemView.findViewById(R.id.item_home_txt_rating);
            txtRatingCount = itemView.findViewById(R.id.item_home_txt_rating_count);
            txtPrice = itemView.findViewById(R.id.item_home_txt_price);
        }
    }
}
