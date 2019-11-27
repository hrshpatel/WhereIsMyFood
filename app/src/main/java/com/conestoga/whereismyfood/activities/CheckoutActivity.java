package com.conestoga.whereismyfood.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.adapters.CheckoutAddressAdapter;
import com.conestoga.whereismyfood.apiutils.APIClient;
import com.conestoga.whereismyfood.apiutils.APIInterface;
import com.conestoga.whereismyfood.databinding.ActivityCheckoutBinding;
import com.conestoga.whereismyfood.models.AddressDetails;
import com.conestoga.whereismyfood.models.SubscriptionModel;
import com.conestoga.whereismyfood.models.UserDetails;
import com.conestoga.whereismyfood.response.GetUserDetails;
import com.conestoga.whereismyfood.utils.AppSharedPref;
import com.conestoga.whereismyfood.utils.CommonUtils;
import com.conestoga.whereismyfood.utils.ProgressDialogUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityCheckoutBinding mBinding;
    private UserDetails mUserDetails;
    private ArrayList<AddressDetails> mAddressArray;
    private AppSharedPref sharedPref;
    private SubscriptionModel mSubModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);

        setToolbar();
        initView();
        setListeners();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_txt_save_edit:
                Log.e("/////////////", "Checkout");
                break;
        }
    }

    private void setListeners() {
        mBinding.toolbarInclude.toolbarTxtSaveEdit.setVisibility(View.VISIBLE);
        mBinding.toolbarInclude.toolbarTxtSaveEdit.setText(R.string.str_checkout);
        mBinding.toolbarInclude.toolbarTxtSaveEdit.setOnClickListener(this);
    }

    private void initView() {
        sharedPref = AppSharedPref.getInstance(this);

        if (getIntent().getParcelableExtra(CommonUtils.INTENT_SUB_MODEL) != null) {
            mSubModel = getIntent().getParcelableExtra(CommonUtils.INTENT_SUB_MODEL);
            mBinding.setSubModel(mSubModel);
        }

        APIInterface mApiInterface = APIClient.getClient().create(APIInterface.class);
        Call<GetUserDetails> userDetailsApi = mApiInterface.getUserDetails(sharedPref.getEmailId());

        ProgressDialogUtil.showProgress(this, "Loading", "Please Wait...", false);

        userDetailsApi.enqueue(new Callback<GetUserDetails>() {
            @Override
            public void onResponse(Call<GetUserDetails> call, Response<GetUserDetails> response) {

                ProgressDialogUtil.dismissProgress();
                GetUserDetails userDetailsResponse = response.body();

                if (userDetailsResponse.getSuccess().equals("0")) {
                    Toast.makeText(CheckoutActivity.this, "" + userDetailsResponse.getMessage()
                            , Toast.LENGTH_SHORT).show();
                } else if (userDetailsResponse.getSuccess().equals("1")) {
                    mUserDetails = userDetailsResponse.getUserDetails();
                    mAddressArray = userDetailsResponse.getUserDetails().getAddressDetailsList();
                    mBinding.setUserDetails(mUserDetails);
                    mBinding.recyclerAddresses.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mBinding.recyclerAddresses.setAdapter(new CheckoutAddressAdapter(getApplicationContext(), mAddressArray));
                }

            }

            @Override
            public void onFailure(Call<GetUserDetails> call, Throwable t) {
                call.cancel();
                ProgressDialogUtil.dismissProgress();
                Toast.makeText(CheckoutActivity.this, getResources().getString(R.string.str_something_went_worng)
                        , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setToolbar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        mBinding.toolbarInclude.toolbarTitle.setText(R.string.str_checkout);

        setSupportActionBar(mBinding.toolbarInclude.toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }

    }

}
