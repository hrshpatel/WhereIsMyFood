package com.conestoga.whereismyfood.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.fragments.HomeFragment;
import com.conestoga.whereismyfood.utils.AppSharedPref;
import com.google.android.material.navigation.NavigationView;

import static androidx.core.view.GravityCompat.START;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar mToolbar;
    private TextView toolbar_title;
    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout mMainView;
    private NavigationView mNavigationView;
    private TextView mTxtDrwrName;
    private TextView mTxtDrwrEmail;
    private ImageView mIvEdtProfile;
    private ImageView mIvAdd;
    private AppSharedPref mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeView();
        setListeners();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                setDefaultFragment(HomeFragment.newInstance());
                break;

            case R.id.nav_logout:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(R.string.logout_prompt);

                dialog.setPositiveButton(getString(R.string.set_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSharedPref.logout();
                        finish();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

                dialog.setNegativeButton(getString(R.string.set_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                break;

            case R.id.nav_order:
                Intent intent = new Intent(HomeActivity.this, OrdersActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drwr_iv_edit_profile:
                Intent intent = new Intent(this, EditProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.toolbar_iv_add:
                intent = new Intent(this, AddSubsciptionActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(START)) {
            mDrawerLayout.closeDrawer(START);
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.exit_alert_string);

            dialog.setPositiveButton(getString(R.string.set_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            dialog.setNegativeButton(getString(R.string.set_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

    private void initializeView() {

        mSharedPref = AppSharedPref.getInstance(this);

        mToolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        mIvAdd = findViewById(R.id.toolbar_iv_add);

        toolbar_title.setText(R.string.str_home);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mMainView = findViewById(R.id.main_view);
        mNavigationView = findViewById(R.id.nav_view);
        mTxtDrwrName = mNavigationView.getHeaderView(0).findViewById(R.id.drwr_txt_name);
        mTxtDrwrEmail = mNavigationView.getHeaderView(0).findViewById(R.id.drwr_txt_email);
        mIvEdtProfile = mNavigationView.getHeaderView(0).findViewById(R.id.drwr_iv_edit_profile);
        setDrawerLayout();
        setDefaultFragment(HomeFragment.newInstance());
    }

    private void setListeners() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mIvEdtProfile.setOnClickListener(this);
    }

    /**
     * This method is used to set Default Fragment.
     *
     * @Date : 10/31/2019
     * @author : Harsh Patel
     */
    private void setDefaultFragment(Fragment defaultFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.add(R.id.act_home_fragment_container, defaultFragment);

        fragmentTransaction.commit();
        mDrawerLayout.closeDrawer(START);
    }

    private void setDrawerLayout() {

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        if (mSharedPref.getUserType().equalsIgnoreCase("0")) {
            nav_Menu.findItem(R.id.nav_order).setTitle(R.string.str_order_history);
        } else {
            nav_Menu.findItem(R.id.nav_order).setTitle(R.string.str_current_order);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0);
                mMainView.setTranslationX(slideOffset * drawerView.getWidth());

                // Apply animation to image view
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, this.getTheme());
        toggle.setHomeAsUpIndicator(drawable);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerVisible(START)) {
                    mDrawerLayout.closeDrawer(START);
                } else {
                    mDrawerLayout.openDrawer(START);
                }
            }
        });

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mDrawerLayout.setScrimColor(ContextCompat.getColor(this, android.R.color.transparent));

        mTxtDrwrEmail.setText(mSharedPref.getEmailId());
        if (mSharedPref.getUserType().equals("1")) {
            mIvAdd.setVisibility(View.VISIBLE);
            mTxtDrwrName.setText(mSharedPref.getVendorName());
            mIvAdd.setOnClickListener(this);
        } else {
            mTxtDrwrName.setText(mSharedPref.getFirstName() + " " + mSharedPref.getLastName());
        }


    }

}
