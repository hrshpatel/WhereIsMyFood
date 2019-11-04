package com.conestoga.whereismyfood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.models.AddressDetails;
import com.conestoga.whereismyfood.utils.AppSharedPref;

import java.util.ArrayList;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressHolder> {

    private final ArrayList<AddressDetails> mAddressDetailsList;
    private final AppSharedPref mSharedPref;
    private Context mContext;

    public AddressListAdapter(Context context, ArrayList<AddressDetails> addressDetailsList) {
        this.mContext = context;
        this.mAddressDetailsList = addressDetailsList;
        mSharedPref = AppSharedPref.getInstance(mContext);
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address, parent, false);

        return new AddressHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        AddressDetails addressDetails = mAddressDetailsList.get(position);

        if (mSharedPref.getUserType().equals("0")) {
            holder.txtName.setText(addressDetails.getName());
            holder.txtStreet.setText(addressDetails.getStreet());
            holder.txtUnit.setText(addressDetails.getSuite_no());
            holder.txtCity.setText(addressDetails.getCity() + ", " + addressDetails.getZipCode());
            holder.txtCountry.setText("Canada");
            holder.txtPhone.setText(addressDetails.getPhoneNo());
        }
    }

    @Override
    public int getItemCount() {
        return mAddressDetailsList.size();
    }

    class AddressHolder extends RecyclerView.ViewHolder {
        private final TextView txtName;
        private final TextView txtStreet;
        private final TextView txtUnit;
        private final TextView txtCity;
        private final TextView txtCountry;
        private final TextView txtPhone;

        AddressHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.itm_adrs_txt_name);
            txtStreet = itemView.findViewById(R.id.itm_adrs_txt_street);
            txtUnit = itemView.findViewById(R.id.itm_adrs_txt_unit);
            txtCity = itemView.findViewById(R.id.itm_adrs_txt_city);
            txtCountry = itemView.findViewById(R.id.itm_adrs_txt_country);
            txtPhone = itemView.findViewById(R.id.itm_adrs_txt_phone);
        }
    }
}
