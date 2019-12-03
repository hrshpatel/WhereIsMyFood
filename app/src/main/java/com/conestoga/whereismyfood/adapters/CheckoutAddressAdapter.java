package com.conestoga.whereismyfood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.conestoga.whereismyfood.BR;
import com.conestoga.whereismyfood.databinding.ItemAddressCheckoutBinding;
import com.conestoga.whereismyfood.models.AddressDetails;

import java.util.ArrayList;

public class CheckoutAddressAdapter extends RecyclerView.Adapter<CheckoutAddressAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<AddressDetails> mAddressList;
    private int selectedIndex = 0;
    private boolean isBinding;

    public CheckoutAddressAdapter(Context mContext, ArrayList<AddressDetails> mAddressList) {
        this.mContext = mContext;
        this.mAddressList = mAddressList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAddressCheckoutBinding itemBinding = ItemAddressCheckoutBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        isBinding = true;
        holder.itemBinding.setVariable(com.conestoga.whereismyfood.BR.addressModel, mAddressList.get(position));
        holder.itemBinding.executePendingBindings();
        if (selectedIndex == position) {
            holder.itemBinding.radioBtnSelect.setChecked(true);
        } else {
            holder.itemBinding.radioBtnSelect.setChecked(false);
        }

        holder.itemBinding.radioBtnSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!isBinding) {
                    selectedIndex = position;
                    notifyDataSetChanged();
                }
            }
        });

        isBinding = false;
    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAddressCheckoutBinding itemBinding;

        ViewHolder(@NonNull ItemAddressCheckoutBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            this.itemBinding = itemViewBinding;

        }
    }
}
