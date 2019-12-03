package com.conestoga.whereismyfood.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.databinding.ItemReviewBinding;
import com.conestoga.whereismyfood.models.ReviewDetails;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<ReviewDetails> reviewDetailList;

    public ReviewAdapter(ArrayList<ReviewDetails> reviewDetailList) {
        this.reviewDetailList = reviewDetailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemReviewBinding reviewBinding = ItemReviewBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(reviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewDetails reviewDetails = reviewDetailList.get(position);

        holder.itemReviewBinding.setReviewModel(reviewDetails);
        holder.itemReviewBinding.executePendingBindings();

        Glide.with(holder.itemView.getContext())
                .load(reviewDetails.getUserImage())
                .placeholder(R.drawable.ic_user)
                .into(holder.itemReviewBinding.ivUserImg);

    }

    @Override
    public int getItemCount() {
        return reviewDetailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemReviewBinding itemReviewBinding;

        ViewHolder(@NonNull ItemReviewBinding itemReviewBinding) {
            super(itemReviewBinding.getRoot());

            this.itemReviewBinding = itemReviewBinding;
        }
    }
}
