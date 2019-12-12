package com.conestoga.whereismyfood.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.adapters.CheckoutAddressAdapter;
import com.conestoga.whereismyfood.apiutils.APIClient;
import com.conestoga.whereismyfood.apiutils.APIInterface;
import com.conestoga.whereismyfood.databinding.ActivityCheckoutBinding;
import com.conestoga.whereismyfood.models.AddressDetails;
import com.conestoga.whereismyfood.models.OrderDetail;
import com.conestoga.whereismyfood.models.SubscriptionModel;
import com.conestoga.whereismyfood.models.UserDetails;
import com.conestoga.whereismyfood.response.CheckOut;
import com.conestoga.whereismyfood.response.GetUserDetails;
import com.conestoga.whereismyfood.response.SignUp;
import com.conestoga.whereismyfood.utils.AppSharedPref;
import com.conestoga.whereismyfood.utils.CommonUtils;
import com.conestoga.whereismyfood.utils.ProgressDialogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * {@link CheckoutActivity} is used to perform checkout operations like selecting meal days and pay.
 *
 * @author : Harsh Patel
 * @Date :  19/10/2019
 */
public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ActivityCheckoutBinding mBinding;
    private UserDetails mUserDetails;
    private ArrayList<AddressDetails> mAddressArray;
    private AppSharedPref sharedPref;
    private SubscriptionModel mSubModel;
    private AwesomeValidation mAwesomeValidation;
    private String selectedTime;
    private HashMap<String, Boolean> daysSelected;
    private float price;
    private ArrayList<String> daysArray;
    private float originalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);

        setToolbar();
        initView();
        setListeners();
        setValidations();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        daysSelected.put(compoundButton.getText().toString(), b);

        daysArray = new ArrayList<>();

        for (Map.Entry<String, Boolean> dayEntry :
                daysSelected.entrySet()) {
            if (dayEntry.getValue()) {
                daysArray.add(dayEntry.getKey());
            }
        }

        price = Float.valueOf(mSubModel.getPrice());
        float selectedDaysNo = daysArray.size();
        float totalDaysNo = mSubModel.getDailyDetailList().size();
        price = selectedDaysNo * (originalPrice / totalDaysNo);

        mSubModel.setPrice(String.valueOf(price));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_txt_save_edit:
                if (mAwesomeValidation.validate()) {
                    if (!mBinding.chkMonday.isChecked() && !mBinding.chkTuesday.isChecked() && !mBinding.chkWednesday.isChecked() &&
                            !mBinding.chkThursday.isChecked() && !mBinding.chkFriday.isChecked() && !mBinding.chkSaturday.isChecked()
                            && !mBinding.chkSunday.isChecked()) {
                        Toast.makeText(this, R.string.str_select_day, Toast.LENGTH_SHORT).show();
                    } else if (CommonUtils.isNullString(selectedTime)) {
                        Toast.makeText(this, R.string.str_invalid_time, Toast.LENGTH_SHORT).show();
                    } else {
                        if (CommonUtils.isInternetAvailable(this)) {
                            OrderDetail orderDetail = new OrderDetail();

                            if (mUserDetails.getAddressDetailsList().size() > 0) {
                                orderDetail.setAddressId(mUserDetails.getAddressDetailsList()
                                        .get(((CheckoutAddressAdapter) mBinding.recyclerAddresses.getAdapter())
                                                .getSelectedIndex()).getAddressId());
                            }

                            orderDetail.setDaysSelected(daysArray.toString());
                            orderDetail.setEmailId(mBinding.edtEmail.getText().toString());
                            orderDetail.setOrderTotal(String.valueOf(price));
                            orderDetail.setPhoneNumber(mUserDetails.getPhoneNo());
                            orderDetail.setReceiverName(mBinding.edtReceiverName.getText().toString());
                            orderDetail.setSubId(mSubModel.getSubId());
                            orderDetail.setUserId(mUserDetails.getId());
                            orderDetail.setTime(selectedTime);
                            orderDetail.setSubName(mSubModel.getSubName());
                            orderDetail.setVendorName(mSubModel.getVendorName());

                            ProgressDialogUtil.showProgress(this, "Loading", "Please Wait...", false);
                            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                            Call<CheckOut> checkOutApi = apiInterface.checkOut(orderDetail);

                            checkOutApi.enqueue(new Callback<CheckOut>() {
                                @Override
                                public void onResponse(Call<CheckOut> call, Response<CheckOut> response) {
                                    ProgressDialogUtil.dismissProgress();

                                    CheckOut checkOut = response.body();
                                    if (checkOut.getSuccess().equalsIgnoreCase("0")) {
                                        Toast.makeText(CheckoutActivity.this, "" + checkOut.getMessage()
                                                , Toast.LENGTH_LONG).show();
                                    } else if (checkOut.getSuccess().equalsIgnoreCase("1")) {
                                        Toast.makeText(CheckoutActivity.this, "" + checkOut.getMessage()
                                                , Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckOut> call, Throwable t) {
                                    call.cancel();
                                    ProgressDialogUtil.dismissProgress();
                                    Toast.makeText(CheckoutActivity.this, getResources().getString(R.string.str_something_went_worng)
                                            , Toast.LENGTH_LONG).show();
                                }
                            });


                        }

                    }
                }
                break;

            case R.id.txt_select_time:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        selectedTime = selectedHour + ":" + selectedMinute;
                        mBinding.txtSelectTime.setText(selectedTime);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Used to set validations for user selections
     *
     * @Date : 19/10/2019
     */
    private void setValidations() {
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        mAwesomeValidation.addValidation(mBinding.edtReceiverName, CommonUtils.DISH_NAME_PATTERN, getString(R.string.invalid_receiver_name));
        mAwesomeValidation.addValidation(mBinding.edtEmail, CommonUtils.EMAIL_PATTERN, getString(R.string.toast_email_format_wrong));
        mAwesomeValidation.addValidation(mBinding.edtPhone, CommonUtils.PHONE_NO_PATTERN, getString(R.string.toast_invalid_phone));
    }

    /**
     * Sets listeners on all required user interfaces
     *
     * @Date : 19/10/2019
     */
    private void setListeners() {
        mBinding.toolbarInclude.toolbarTxtSaveEdit.setVisibility(View.VISIBLE);
        mBinding.toolbarInclude.toolbarTxtSaveEdit.setText(R.string.str_checkout);
        mBinding.toolbarInclude.toolbarTxtSaveEdit.setOnClickListener(this);
        mBinding.txtSelectTime.setOnClickListener(this);

        mBinding.chkMonday.setOnCheckedChangeListener(this);
        mBinding.chkTuesday.setOnCheckedChangeListener(this);
        mBinding.chkWednesday.setOnCheckedChangeListener(this);
        mBinding.chkThursday.setOnCheckedChangeListener(this);
        mBinding.chkFriday.setOnCheckedChangeListener(this);
        mBinding.chkSaturday.setOnCheckedChangeListener(this);
        mBinding.chkSunday.setOnCheckedChangeListener(this);
    }

    private void initView() {
        sharedPref = AppSharedPref.getInstance(this);

        daysSelected = new HashMap<>();

        mBinding.recyclerAddresses.setNestedScrollingEnabled(false);
        if (getIntent().getParcelableExtra(CommonUtils.INTENT_SUB_MODEL) != null) {
            mSubModel = getIntent().getParcelableExtra(CommonUtils.INTENT_SUB_MODEL);
            mBinding.setSubModel(mSubModel);
            originalPrice = Float.parseFloat(mSubModel.getPrice());
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
