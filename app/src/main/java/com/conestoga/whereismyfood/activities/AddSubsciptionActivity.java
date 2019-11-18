package com.conestoga.whereismyfood.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.apiutils.APIClient;
import com.conestoga.whereismyfood.apiutils.APIInterface;
import com.conestoga.whereismyfood.models.SubscriptionModel;
import com.conestoga.whereismyfood.response.SignUp;
import com.conestoga.whereismyfood.utils.AppSharedPref;
import com.conestoga.whereismyfood.utils.CommonUtils;
import com.conestoga.whereismyfood.utils.ProgressDialogUtil;
import com.google.common.collect.Range;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSubsciptionActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private AwesomeValidation mAwesomeValidation;
    private ViewPager mViewPager;
    private EditText mEdtName;
    private EditText mEdtVendorName;
    private EditText mEdtDescription;
    private EditText mEdtPrice;
    private CheckBox mChkMonday;
    private LinearLayout mLinMonday;
    private EditText mEdtMonDishName;
    private EditText mEdtMonIngredients;
    private EditText mEdtMonDesc;
    private CheckBox mChkTuesday;
    private LinearLayout mLinTuesday;
    private EditText mEdtTuesDishName;
    private EditText mEdtTuesIngredients;
    private EditText mEdtTuesDesc;
    private CheckBox mChkWednesday;
    private LinearLayout mLinWednesday;
    private EditText mEdtWedDishName;
    private EditText mEdtWedIngredients;
    private EditText mEdtWedDesc;
    private CheckBox mChkThursday;
    private LinearLayout mLinThursday;
    private EditText mEdtThursDishName;
    private EditText mEdtThursIngredients;
    private EditText mEdtThursDesc;
    private CheckBox mChkFriday;
    private LinearLayout mLinFriday;
    private EditText mEdtFriDishName;
    private EditText mEdtFriIngredients;
    private EditText mEdtFriDesc;
    private CheckBox mChkSat;
    private LinearLayout mLinSat;
    private EditText mEdtSatDishName;
    private EditText mEdtSatIngredients;
    private EditText mEdtSatDesc;
    private CheckBox mChkSun;
    private LinearLayout mLinSun;
    private EditText mEdtSunDishName;
    private EditText mEdtSunIngredients;
    private EditText mEdtSunDesc;
    private Button mBtnSave;
    private Button mBtnAddPhoto;
    private ScrollView mScrollView;
    private APIInterface mApiInterface;
    private AppSharedPref mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subsciption);

        initView();
        addValidations();
        setListeners();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.act_sub_chk_monday:
                if (b) {
                    mLinMonday.setVisibility(View.VISIBLE);
                } else {
                    mLinMonday.setVisibility(View.GONE);
                }
                break;
            case R.id.act_sub_chk_tuesday:
                if (b) {
                    mLinTuesday.setVisibility(View.VISIBLE);
                } else {
                    mLinTuesday.setVisibility(View.GONE);
                }
                break;
            case R.id.act_sub_chk_wednesday:
                if (b) {
                    mLinWednesday.setVisibility(View.VISIBLE);
                } else {
                    mLinWednesday.setVisibility(View.GONE);
                }
                break;
            case R.id.act_sub_chk_thursday:
                if (b) {
                    mLinThursday.setVisibility(View.VISIBLE);
                } else {
                    mLinThursday.setVisibility(View.GONE);
                }
                break;
            case R.id.act_sub_chk_friday:
                if (b) {
                    mLinFriday.setVisibility(View.VISIBLE);
                } else {
                    mLinFriday.setVisibility(View.GONE);
                }
                break;
            case R.id.act_sub_chk_sat:
                if (b) {
                    mLinSat.setVisibility(View.VISIBLE);
                } else {
                    mLinSat.setVisibility(View.GONE);
                }
                break;
            case R.id.act_sub_chk_sun:
                if (b) {
                    mLinSun.setVisibility(View.VISIBLE);
                } else {
                    mLinSun.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void setListeners() {
        mChkMonday.setOnCheckedChangeListener(this);
        mChkTuesday.setOnCheckedChangeListener(this);
        mChkWednesday.setOnCheckedChangeListener(this);
        mChkThursday.setOnCheckedChangeListener(this);
        mChkFriday.setOnCheckedChangeListener(this);
        mChkSat.setOnCheckedChangeListener(this);
        mChkSun.setOnCheckedChangeListener(this);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAwesomeValidation.validate()) {
                    if (mChkMonday.isChecked() && CommonUtils.isNullString(mEdtMonDishName.getText().toString())) {
                        mEdtMonDishName.setError(getString(R.string.str_no_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtMonDishName.getBottom());
                    } else if (mChkMonday.isChecked() && !CommonUtils.checkDishName(mEdtMonDishName.getText().toString())) {
                        mEdtMonDishName.setError(getString(R.string.str_invalid_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtMonDishName.getBottom());
                    } else if (mChkMonday.isChecked() && CommonUtils.isNullString(mEdtMonDesc.getText().toString())) {
                        mEdtMonDesc.setError(getString(R.string.str_no_description));
                        mScrollView.smoothScrollTo(0, mEdtMonDesc.getBottom());
                    } else if (mChkMonday.isChecked() && !CommonUtils.checkDishName(mEdtMonDesc.getText().toString())) {
                        mEdtMonDesc.setError(getString(R.string.str_invalid_description));
                        mScrollView.smoothScrollTo(0, mEdtMonDesc.getBottom());
                    } else if (mChkMonday.isChecked() && CommonUtils.isNullString(mEdtMonIngredients.getText().toString())) {
                        mEdtMonIngredients.setError(getString(R.string.str_no_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtMonIngredients.getBottom());
                    } else if (mChkMonday.isChecked() && !CommonUtils.checkDishName(mEdtMonIngredients.getText().toString())) {
                        mEdtMonIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtMonIngredients.getBottom());
                    } else if (mChkTuesday.isChecked() && CommonUtils.isNullString(mEdtTuesDishName.getText().toString())) {
                        mEdtTuesDishName.setError(getString(R.string.str_no_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtTuesDishName.getBottom());
                    } else if (mChkTuesday.isChecked() && !CommonUtils.checkDishName(mEdtTuesDishName.getText().toString())) {
                        mEdtTuesDishName.setError(getString(R.string.str_invalid_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtTuesDishName.getBottom());
                    } else if (mChkTuesday.isChecked() && CommonUtils.isNullString(mEdtTuesDesc.getText().toString())) {
                        mEdtTuesDesc.setError(getString(R.string.str_no_description));
                        mScrollView.smoothScrollTo(0, mEdtTuesDesc.getBottom());
                    } else if (mChkTuesday.isChecked() && !CommonUtils.checkDishName(mEdtTuesDesc.getText().toString())) {
                        mEdtTuesDesc.setError(getString(R.string.str_invalid_description));
                        mScrollView.smoothScrollTo(0, mEdtTuesDesc.getBottom());
                    } else if (mChkTuesday.isChecked() && CommonUtils.isNullString(mEdtTuesIngredients.getText().toString())) {
                        mEdtTuesIngredients.setError(getString(R.string.str_no_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtTuesIngredients.getBottom());
                    } else if (mChkTuesday.isChecked() && !CommonUtils.checkDishName(mEdtTuesIngredients.getText().toString())) {
                        mEdtTuesIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtTuesIngredients.getBottom());
                    } else if (mChkWednesday.isChecked() && CommonUtils.isNullString(mEdtWedDishName.getText().toString())) {
                        mEdtWedDishName.setError(getString(R.string.str_no_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtWedDishName.getBottom());
                    } else if (mChkWednesday.isChecked() && !CommonUtils.checkDishName(mEdtWedDishName.getText().toString())) {
                        mEdtWedDishName.setError(getString(R.string.str_invalid_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtWedDishName.getBottom());
                    } else if (mChkWednesday.isChecked() && CommonUtils.isNullString(mEdtWedDesc.getText().toString())) {
                        mEdtWedDesc.setError(getString(R.string.str_no_description));
                        mScrollView.smoothScrollTo(0, mEdtWedDesc.getBottom());
                    } else if (mChkWednesday.isChecked() && !CommonUtils.checkDishName(mEdtWedDesc.getText().toString())) {
                        mEdtWedDesc.setError(getString(R.string.str_invalid_description));
                        mScrollView.smoothScrollTo(0, mEdtWedDesc.getBottom());
                    } else if (mChkWednesday.isChecked() && CommonUtils.isNullString(mEdtWedIngredients.getText().toString())) {
                        mEdtWedIngredients.setError(getString(R.string.str_no_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtWedIngredients.getBottom());
                    } else if (mChkWednesday.isChecked() && !CommonUtils.checkDishName(mEdtWedIngredients.getText().toString())) {
                        mEdtWedIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtWedIngredients.getBottom());
                    } else if (mChkThursday.isChecked() && CommonUtils.isNullString(mEdtThursDishName.getText().toString())) {
                        mEdtThursDishName.setError(getString(R.string.str_no_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtThursDishName.getBottom());
                    } else if (mChkThursday.isChecked() && !CommonUtils.checkDishName(mEdtThursDishName.getText().toString())) {
                        mEdtThursDishName.setError(getString(R.string.str_invalid_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtThursDishName.getBottom());
                    } else if (mChkThursday.isChecked() && CommonUtils.isNullString(mEdtThursDesc.getText().toString())) {
                        mEdtThursDesc.setError(getString(R.string.str_no_description));
                        mScrollView.smoothScrollTo(0, mEdtThursDesc.getBottom());
                    } else if (mChkThursday.isChecked() && !CommonUtils.checkDishName(mEdtThursDesc.getText().toString())) {
                        mEdtThursDesc.setError(getString(R.string.str_invalid_description));
                        mScrollView.smoothScrollTo(0, mEdtThursDesc.getBottom());
                    } else if (mChkThursday.isChecked() && CommonUtils.isNullString(mEdtThursIngredients.getText().toString())) {
                        mEdtThursIngredients.setError(getString(R.string.str_no_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtThursIngredients.getBottom());
                    } else if (mChkThursday.isChecked() && !CommonUtils.checkDishName(mEdtThursIngredients.getText().toString())) {
                        mEdtThursIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtThursIngredients.getBottom());
                    } else if (mChkFriday.isChecked() && CommonUtils.isNullString(mEdtFriDishName.getText().toString())) {
                        mEdtFriDishName.setError(getString(R.string.str_no_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtFriDishName.getBottom());
                    } else if (mChkFriday.isChecked() && !CommonUtils.checkDishName(mEdtFriDishName.getText().toString())) {
                        mEdtFriDishName.setError(getString(R.string.str_invalid_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtFriDishName.getBottom());
                    } else if (mChkFriday.isChecked() && CommonUtils.isNullString(mEdtFriDesc.getText().toString())) {
                        mEdtFriDesc.setError(getString(R.string.str_no_description));
                        mScrollView.smoothScrollTo(0, mEdtFriDesc.getBottom());
                    } else if (mChkFriday.isChecked() && !CommonUtils.checkDishName(mEdtFriDesc.getText().toString())) {
                        mEdtFriDesc.setError(getString(R.string.str_invalid_description));
                        mScrollView.smoothScrollTo(0, mEdtFriDesc.getBottom());
                    } else if (mChkFriday.isChecked() && CommonUtils.isNullString(mEdtFriIngredients.getText().toString())) {
                        mEdtFriIngredients.setError(getString(R.string.str_no_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtFriIngredients.getBottom());
                    } else if (mChkFriday.isChecked() && !CommonUtils.checkDishName(mEdtFriIngredients.getText().toString())) {
                        mEdtFriIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtFriIngredients.getBottom());
                    } else if (mChkSat.isChecked() && CommonUtils.isNullString(mEdtSatDishName.getText().toString())) {
                        mEdtSatDishName.setError(getString(R.string.str_no_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtSatDishName.getBottom());
                    } else if (mChkSat.isChecked() && !CommonUtils.checkDishName(mEdtSatDishName.getText().toString())) {
                        mEdtSatDishName.setError(getString(R.string.str_invalid_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtSatDishName.getBottom());
                    } else if (mChkSat.isChecked() && CommonUtils.isNullString(mEdtSatDesc.getText().toString())) {
                        mEdtSatDesc.setError(getString(R.string.str_no_description));
                        mScrollView.smoothScrollTo(0, mEdtSatDesc.getBottom());
                    } else if (mChkSat.isChecked() && !CommonUtils.checkDishName(mEdtSatDesc.getText().toString())) {
                        mEdtSatDesc.setError(getString(R.string.str_invalid_description));
                        mScrollView.smoothScrollTo(0, mEdtSatDesc.getBottom());
                    } else if (mChkSat.isChecked() && CommonUtils.isNullString(mEdtSatIngredients.getText().toString())) {
                        mEdtSatIngredients.setError(getString(R.string.str_no_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtSatIngredients.getBottom());
                    } else if (mChkSat.isChecked() && !CommonUtils.checkDishName(mEdtSatIngredients.getText().toString())) {
                        mEdtSatIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtSatIngredients.getBottom());
                    } else if (mChkSun.isChecked() && CommonUtils.isNullString(mEdtSunDishName.getText().toString())) {
                        mEdtSunDishName.setError(getString(R.string.str_no_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtSunDishName.getBottom());
                    } else if (mChkSun.isChecked() && !CommonUtils.checkDishName(mEdtSunDishName.getText().toString())) {
                        mEdtSunDishName.setError(getString(R.string.str_invalid_dish_name));
                        mScrollView.smoothScrollTo(0, mEdtSunDishName.getBottom());
                    } else if (mChkSun.isChecked() && CommonUtils.isNullString(mEdtSunDesc.getText().toString())) {
                        mEdtSunDesc.setError(getString(R.string.str_no_description));
                        mScrollView.smoothScrollTo(0, mEdtSunDesc.getBottom());
                    } else if (mChkSun.isChecked() && !CommonUtils.checkDishName(mEdtSunDesc.getText().toString())) {
                        mEdtSunDesc.setError(getString(R.string.str_invalid_description));
                        mScrollView.smoothScrollTo(0, mEdtSunDesc.getBottom());
                    } else if (mChkSun.isChecked() && CommonUtils.isNullString(mEdtSunIngredients.getText().toString())) {
                        mEdtSunIngredients.setError(getString(R.string.str_no_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtSunIngredients.getBottom());
                    } else if (mChkSun.isChecked() && !CommonUtils.checkDishName(mEdtSunIngredients.getText().toString())) {
                        mEdtSunIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mScrollView.smoothScrollTo(0, mEdtSunIngredients.getBottom());
                    } else {
                        if (CommonUtils.isInternetAvailable(AddSubsciptionActivity.this)) {
                            SubscriptionModel subscriptionModel = new SubscriptionModel();

                            subscriptionModel.setUserId(mSharedPref.getUserId());
                            subscriptionModel.setPhone_no(mSharedPref.getPhoneNumber());
                            subscriptionModel.setEmailId(mSharedPref.getEmailId());
                            subscriptionModel.setSubDescription(mEdtDescription.getText().toString());
                            subscriptionModel.setSubName(mEdtName.getText().toString());
                            subscriptionModel.setVendorName(mEdtVendorName.getText().toString());
                            subscriptionModel.setPrice(mEdtPrice.getText().toString());

                            if (mChkMonday.isChecked()) {
                                subscriptionModel.setMonday(true);
                                subscriptionModel.setDishNameMon(mEdtMonDishName.getText().toString());
                                subscriptionModel.setIngredientsMon(mEdtMonIngredients.getText().toString());
                                subscriptionModel.setDishDescMon(mEdtMonDesc.getText().toString());
                            }

                            if (mChkTuesday.isChecked()) {
                                subscriptionModel.setTuesday(true);
                                subscriptionModel.setDishNameTue(mEdtTuesDishName.getText().toString());
                                subscriptionModel.setIngredientsTue(mEdtTuesIngredients.getText().toString());
                                subscriptionModel.setDishDescTue(mEdtTuesDesc.getText().toString());
                            }

                            if (mChkWednesday.isChecked()) {
                                subscriptionModel.setWednesday(true);
                                subscriptionModel.setDishNameWed(mEdtWedDishName.getText().toString());
                                subscriptionModel.setIngredientsWed(mEdtWedIngredients.getText().toString());
                                subscriptionModel.setDishDescWed(mEdtWedDesc.getText().toString());
                            }

                            if (mChkThursday.isChecked()) {
                                subscriptionModel.setThursday(true);
                                subscriptionModel.setDishNameThurs(mEdtThursDishName.getText().toString());
                                subscriptionModel.setIngredientsThurs(mEdtThursIngredients.getText().toString());
                                subscriptionModel.setDishDescThurs(mEdtThursDesc.getText().toString());
                            }

                            if (mChkFriday.isChecked()) {
                                subscriptionModel.setFriday(true);
                                subscriptionModel.setDishNameFri(mEdtFriDishName.getText().toString());
                                subscriptionModel.setIngredientsFri(mEdtFriIngredients.getText().toString());
                                subscriptionModel.setDishDescFri(mEdtFriDesc.getText().toString());
                            }

                            if (mChkSat.isChecked()) {
                                subscriptionModel.setSaturday(true);
                                subscriptionModel.setDishNameSat(mEdtSatDishName.getText().toString());
                                subscriptionModel.setIngredientsSat(mEdtSatIngredients.getText().toString());
                                subscriptionModel.setDishDescSat(mEdtSatDesc.getText().toString());
                            }

                            if (mChkSun.isChecked()) {
                                subscriptionModel.setSunday(true);
                                subscriptionModel.setDishNameSun(mEdtSunDishName.getText().toString());
                                subscriptionModel.setIngredientsSun(mEdtSunIngredients.getText().toString());
                                subscriptionModel.setDishDescSun(mEdtSunDesc.getText().toString());
                            }

                            ProgressDialogUtil.showProgress(AddSubsciptionActivity.this, "Loading", "Please Wait...", false);

                            Call<SignUp> addSubApi = mApiInterface.addSubscription(subscriptionModel);

                            addSubApi.enqueue(new Callback<SignUp>() {
                                @Override
                                public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                                    SignUp signUpResponse = response.body();
                                    Log.e("/////////////", "UserName : " + signUpResponse.getMessage());
                                    Log.e("/////////////", "UserName : " + signUpResponse.getSuccess());

                                    ProgressDialogUtil.dismissProgress();

                                    if (signUpResponse.getSuccess() == 0) {
                                        Toast.makeText(AddSubsciptionActivity.this, "" + signUpResponse.getMessage()
                                                , Toast.LENGTH_SHORT).show();
                                    } else if (signUpResponse.getSuccess() == 1) {
                                        Toast.makeText(AddSubsciptionActivity.this,
                                                signUpResponse.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SignUp> call, Throwable t) {
                                    call.cancel();
                                    ProgressDialogUtil.dismissProgress();
                                    Toast.makeText(AddSubsciptionActivity.this, getResources().getString(R.string.str_something_went_worng)
                                            , Toast.LENGTH_LONG).show();
                                }

                            });

                        }
                    }

                }
            }
        });
    }

    private void addValidations() {
        mAwesomeValidation.addValidation(this, R.id.act_sub_edt_name, CommonUtils.DISH_NAME_PATTERN
                , R.string.str_enter_valid_subscription_name);
        mAwesomeValidation.addValidation(mEdtVendorName, CommonUtils.DISH_NAME_PATTERN
                , getResources().getString(R.string.toast_no_valid_vname));
        mAwesomeValidation.addValidation(mEdtDescription, CommonUtils.DISH_NAME_PATTERN, getString(R.string.str_invalid_description));
        mAwesomeValidation.addValidation(mEdtPrice, Range.greaterThan(1f), getString(R.string.str_enter_price));
    }

    private void initView() {
        mSharedPref = AppSharedPref.getInstance(this);

        mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        mApiInterface = APIClient.getClient().create(APIInterface.class);

        mBtnSave = findViewById(R.id.act_sub_btn_save);
        mBtnAddPhoto = findViewById(R.id.act_sub_btn_add_photo);

        mScrollView = findViewById(R.id.act_sub_scroll_view);

        mViewPager = findViewById(R.id.act_add_sub_view_pager);
        mEdtName = findViewById(R.id.act_sub_edt_name);
        mEdtVendorName = findViewById(R.id.act_sub_edt_vendor_name);
        mEdtDescription = findViewById(R.id.act_sub_edt_description);
        mEdtPrice = findViewById(R.id.act_sub_edt_price);
        mChkMonday = findViewById(R.id.act_sub_chk_monday);
        mLinMonday = findViewById(R.id.act_sub_lin_monday);
        mEdtMonDishName = findViewById(R.id.act_sub_edt_mon_dish_name);
        mEdtMonIngredients = findViewById(R.id.act_sub_edt_mon_ingredients);
        mEdtMonDesc = findViewById(R.id.act_sub_edt_mon_desc);
        mChkTuesday = findViewById(R.id.act_sub_chk_tuesday);
        mLinTuesday = findViewById(R.id.act_sub_lin_tuesday);
        mEdtTuesDishName = findViewById(R.id.act_sub_edt_tues_dish_name);
        mEdtTuesIngredients = findViewById(R.id.act_sub_edt_tues_ingredients);
        mEdtTuesDesc = findViewById(R.id.act_sub_edt_tues_desc);
        mChkWednesday = findViewById(R.id.act_sub_chk_wednesday);
        mLinWednesday = findViewById(R.id.act_sub_lin_wednesday);
        mEdtWedDishName = findViewById(R.id.act_sub_edt_wed_dish_name);
        mEdtWedIngredients = findViewById(R.id.act_sub_edt_wed_ingredients);
        mEdtWedDesc = findViewById(R.id.act_sub_edt_wed_desc);
        mChkThursday = findViewById(R.id.act_sub_chk_thursday);
        mLinThursday = findViewById(R.id.act_sub_lin_thursday);
        mEdtThursDishName = findViewById(R.id.act_sub_edt_thurs_dish_name);
        mEdtThursIngredients = findViewById(R.id.act_sub_edt_thurs_ingredients);
        mEdtThursDesc = findViewById(R.id.act_sub_edt_thurs_desc);
        mChkFriday = findViewById(R.id.act_sub_chk_friday);
        mLinFriday = findViewById(R.id.act_sub_lin_friday);
        mEdtFriDishName = findViewById(R.id.act_sub_edt_fri_dish_name);
        mEdtFriIngredients = findViewById(R.id.act_sub_edt_fri_ingredients);
        mEdtFriDesc = findViewById(R.id.act_sub_edt_fri_desc);
        mChkSat = findViewById(R.id.act_sub_chk_sat);
        mLinSat = findViewById(R.id.act_sub_lin_sat);
        mEdtSatDishName = findViewById(R.id.act_sub_edt_sat_dish_name);
        mEdtSatIngredients = findViewById(R.id.act_sub_edt_sat_ingredients);
        mEdtSatDesc = findViewById(R.id.act_sub_edt_sat_desc);
        mChkSun = findViewById(R.id.act_sub_chk_sun);
        mLinSun = findViewById(R.id.act_sub_lin_sun);
        mEdtSunDishName = findViewById(R.id.act_sub_edt_sun_dish_name);
        mEdtSunIngredients = findViewById(R.id.act_sub_edt_sun_ingredients);
        mEdtSunDesc = findViewById(R.id.act_sub_edt_sun_desc);

    }

}
