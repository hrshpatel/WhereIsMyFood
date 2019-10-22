package com.conestoga.whereismyfood.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.adapters.ViewPagerAdapter;
import com.conestoga.whereismyfood.fragments.SignupUserFragment;
import com.conestoga.whereismyfood.fragments.SignupVendorFragment;
import com.conestoga.whereismyfood.utils.CommonUtils;
import com.google.android.material.tabs.TabLayout;

public class SignupActivity extends AppCompatActivity {

    private TabLayout mTabLayoutLogin;
    private ViewPager mViewPagerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        CommonUtils.hideSoftKeyboard(this);
        setContentView(R.layout.activity_signup);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initializeViews();
        setListeners();
    }

    private void initializeViews() {

        mTabLayoutLogin = findViewById(R.id.tablayout_signup);
        mViewPagerLogin = findViewById(R.id.viewPager_signup);
        findViewById(R.id.act_signup_iv_vendor_bg).setVisibility(View.INVISIBLE);

        setupViewPager();
    }

    private void setListeners() {

        mTabLayoutLogin.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CommonUtils.hideSoftKeyboard(SignupActivity.this);
                if (tab.getPosition() == 0) {
                    findViewById(R.id.act_signup_iv_user_bg).setVisibility(View.VISIBLE);
                    findViewById(R.id.act_signup_iv_vendor_bg).setVisibility(View.INVISIBLE);
                } else if (tab.getPosition() == 1) {
                    findViewById(R.id.act_signup_iv_user_bg).setVisibility(View.INVISIBLE);
                    findViewById(R.id.act_signup_iv_vendor_bg).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(SignupUserFragment.newInstance(mTabLayoutLogin), getString(R.string.user_title));
        adapter.addFragment(SignupVendorFragment.newInstance(mTabLayoutLogin), getString(R.string.vendor_title));
        mViewPagerLogin.setAdapter(adapter);
        mTabLayoutLogin.setupWithViewPager(this.mViewPagerLogin);
        mTabLayoutLogin.setSelectedTabIndicatorHeight(0);
    }

}
