package com.conestoga.whereismyfood.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.databinding.ActivityShowSubscriptionBinding;

public class ShowSubscriptionActivity extends AppCompatActivity {

    ActivityShowSubscriptionBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_subscription);
    }
}
