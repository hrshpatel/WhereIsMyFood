package com.conestoga.whereismyfood.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.conestoga.whereismyfood.databinding.ItemOrdersBinding;
import com.conestoga.whereismyfood.models.OrderDetail;

import java.util.ArrayList;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.ViewHolder> {

    ArrayList<OrderDetail> mOrderDetailList;

    public OrdersListAdapter(ArrayList<OrderDetail> mOrderDetailList) {
        this.mOrderDetailList = mOrderDetailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemOrdersBinding itemOrdersBinding = ItemOrdersBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemOrdersBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetail orderDetail = mOrderDetailList.get(position);
        holder.mItemOrdersBinding.setOrderDetails(orderDetail);
        holder.mItemOrdersBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mOrderDetailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemOrdersBinding mItemOrdersBinding;

        ViewHolder(@NonNull ItemOrdersBinding itemOrdersBinding) {
            super(itemOrdersBinding.getRoot());

            mItemOrdersBinding = itemOrdersBinding;
        }
    }
}
