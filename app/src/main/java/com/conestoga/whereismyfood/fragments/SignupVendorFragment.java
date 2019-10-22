package com.conestoga.whereismyfood.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.conestoga.whereismyfood.R;
import com.google.android.material.tabs.TabLayout;

public class SignupVendorFragment extends Fragment {

    private TabLayout mTabLayout;

    public SignupVendorFragment() {
        // Required empty public constructor
    }

    public static SignupVendorFragment newInstance(TabLayout mTabLayout) {
        SignupVendorFragment fragment = new SignupVendorFragment();
        fragment.mTabLayout = mTabLayout;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_vendor, container, false);
    }

}
