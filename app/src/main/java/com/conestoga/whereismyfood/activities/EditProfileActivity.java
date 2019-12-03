package com.conestoga.whereismyfood.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.adapters.AddressListAdapter;
import com.conestoga.whereismyfood.apiutils.APIClient;
import com.conestoga.whereismyfood.apiutils.APIInterface;
import com.conestoga.whereismyfood.customviews.RoundedImageView;
import com.conestoga.whereismyfood.models.AddressDetails;
import com.conestoga.whereismyfood.models.UserDetails;
import com.conestoga.whereismyfood.response.AddAddress;
import com.conestoga.whereismyfood.response.GetUserDetails;
import com.conestoga.whereismyfood.response.SignUp;
import com.conestoga.whereismyfood.utils.AppSharedPref;
import com.conestoga.whereismyfood.utils.CommonUtils;
import com.conestoga.whereismyfood.utils.ProgressDialogUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private RoundedImageView mImgProfilePic;
    private RelativeLayout mRelFirstName;
    private EditText mEdtFirstName;
    private RelativeLayout mRelVendorName;
    private EditText mEdtVName;
    private RelativeLayout mRelLastName;
    private EditText mEdtLastName;
    private EditText mEdtPhone;
    private EditText mEdtCurrPwd;
    private EditText mEdtNewPwd;
    private EditText mEdtCnfrmNewPwd;
    private ImageView mIvAdd;
    private RecyclerView mRecyclerAddresses;
    private Toolbar mToolbar;
    private TextView toolbarTitle;
    private TextView toolbartxtSave;
    private APIInterface mApiInterface;
    private EditText mEdtEmail;
    private AppSharedPref sharedPref;
    private UserDetails mUserDetails;
    private ArrayList<AddressDetails> mAddressArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setUpToolbar();
        initializeView();
        setListeners();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_edit_profile_img_profilePic:
                break;
            case R.id.toolbar_txt_save_edit:
                validateData();
                break;
            case R.id.act_edit_profile_iv_add:
                showAddressDialog();
                break;
        }
    }

    private void setUpToolbar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        mToolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getString(R.string.str_edt_profile));

        toolbartxtSave = findViewById(R.id.toolbar_txt_save_edit);
        toolbartxtSave.setVisibility(View.VISIBLE);
        toolbartxtSave.setText(getString(R.string.text_Save));
        setSupportActionBar(mToolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }

    }

    private void initializeView() {
        sharedPref = AppSharedPref.getInstance(this);
        mApiInterface = APIClient.getClient().create(APIInterface.class);

        mImgProfilePic = findViewById(R.id.act_edit_profile_img_profilePic);
        mRelFirstName = findViewById(R.id.rel_first_name);
        mEdtFirstName = findViewById(R.id.act_edit_profile_edt_firstName);
        mRelVendorName = findViewById(R.id.rel_vendor_name);
        mEdtVName = findViewById(R.id.act_edit_profile_edt_v_Name);
        mRelLastName = findViewById(R.id.rel_last_name);
        mEdtLastName = findViewById(R.id.act_edit_profile_edt_lastName);
        mEdtPhone = findViewById(R.id.act_edit_profile_edt_phone);
        mEdtEmail = findViewById(R.id.act_edit_profile_edt_email);
        mEdtCurrPwd = findViewById(R.id.act_edit_profile_edt_curr_pwd);
        mEdtNewPwd = findViewById(R.id.act_edit_profile_edt_new_pwd);
        mEdtCnfrmNewPwd = findViewById(R.id.act_edit_profile_edt_cnfrm_new_pwd);
        mIvAdd = findViewById(R.id.act_edit_profile_iv_add);
        mRecyclerAddresses = findViewById(R.id.act_edit_profile_recycler_addresses);

        mRecyclerAddresses.setNestedScrollingEnabled(false);
        if (sharedPref.getUserType().equals("1")) {
            mRelVendorName.setVisibility(View.VISIBLE);
            mRelFirstName.setVisibility(View.GONE);
            mRelLastName.setVisibility(View.GONE);
        }

        getUserDetailsApi();
    }

    private void setListeners() {
        mImgProfilePic.setOnClickListener(this);
        toolbartxtSave.setOnClickListener(this);
        mIvAdd.setOnClickListener(this);
    }

    private void getUserDetailsApi() {
        Call<GetUserDetails> userDetailsApi = mApiInterface.getUserDetails(sharedPref.getEmailId());

        ProgressDialogUtil.showProgress(EditProfileActivity.this, "Loading", "Please Wait...", false);

        userDetailsApi.enqueue(new Callback<GetUserDetails>() {
            @Override
            public void onResponse(Call<GetUserDetails> call, Response<GetUserDetails> response) {

                ProgressDialogUtil.dismissProgress();
                GetUserDetails userDetailsResponse = response.body();

                if (userDetailsResponse.getSuccess().equals("0")) {
                    Toast.makeText(EditProfileActivity.this, "" + userDetailsResponse.getMessage()
                            , Toast.LENGTH_SHORT).show();
                } else if (userDetailsResponse.getSuccess().equals("1")) {
                    mUserDetails = userDetailsResponse.getUserDetails();
                    mAddressArray = userDetailsResponse.getUserDetails().getAddressDetailsList();
                    setUserData();
                }

            }

            @Override
            public void onFailure(Call<GetUserDetails> call, Throwable t) {
                call.cancel();
                ProgressDialogUtil.dismissProgress();
                Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.str_something_went_worng)
                        , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUserData() {
        mEdtPhone.setText(mUserDetails.getPhoneNo());
        mEdtEmail.setText(mUserDetails.getEmailId());
        mEdtFirstName.setText(mUserDetails.getFirstName());
        mEdtLastName.setText(mUserDetails.getLastName());
        mEdtVName.setText(mUserDetails.getVendorName());

        mRecyclerAddresses.setLayoutManager(new LinearLayoutManager(this));

        AddressListAdapter adapter = new AddressListAdapter(this, mAddressArray);
        mRecyclerAddresses.setAdapter(adapter);
    }

    private void validateData() {

        String mStrFirstName;
        String mStrLastName;
        String strPhoneNumber;
        UserDetails userDetails = new UserDetails();
        if (sharedPref.getUserType().equals("0")) {
            mStrFirstName = mEdtFirstName.getText().toString().trim();
            mStrLastName = mEdtLastName.getText().toString().trim();
            strPhoneNumber = mEdtPhone.getText().toString().trim();
            String strCurrentPwd = mEdtCurrPwd.getText().toString().trim();
            String strNewPwd = mEdtNewPwd.getText().toString().trim();
            String strCnfrmNewPwd = mEdtCnfrmNewPwd.getText().toString().trim();

            if (CommonUtils.isNullString(mStrFirstName)) {
                Toast.makeText(EditProfileActivity.this, "" + getResources().getString(R.string.toast_no_firstname)
                        , Toast.LENGTH_SHORT).show();
                return;
            } else if (mStrFirstName.length() < 2 || mStrFirstName.length() > 30) {
                Toast.makeText(EditProfileActivity.this
                        , "" + getResources().getString(R.string.toast_first_name_length_invalid)
                        , Toast.LENGTH_SHORT).show();
                return;
            } else if (!CommonUtils.checkDishName(mStrFirstName)) {
                Toast.makeText(EditProfileActivity.this
                        , "" + getResources().getString(R.string.toast_no_valid_firstname)
                        , Toast.LENGTH_SHORT).show();
                return;
            } else if (!CommonUtils.isNullString(mStrLastName) && ((mStrLastName.length() < 2
                    || mStrLastName.length() > 30))) {
                Toast.makeText(this
                        , "" + getResources().getString(R.string.toast_last_name_length_invalid)
                        , Toast.LENGTH_SHORT).show();
                return;
            } else if (!CommonUtils.isNullString(mStrLastName) && !CommonUtils.checkName(mStrLastName)) {
                Toast.makeText(EditProfileActivity.this
                        , "" + getResources().getString(R.string.toast_no_valid_lastname)
                        , Toast.LENGTH_SHORT).show();
                return;
            } else if (!CommonUtils.isNullString(strPhoneNumber) && !CommonUtils.checkPhoneNo(strPhoneNumber)) {
                Toast.makeText(this, "" + getResources().getString(R.string.toast_invalid_phone)
                        , Toast.LENGTH_SHORT).show();
                return;
            }
            if (!CommonUtils.isNullString(strCurrentPwd)) {
                if (!CommonUtils.isNullString(strCurrentPwd) && (strCurrentPwd.length() < 8 || strCurrentPwd.length() > 30)) {
                    Toast.makeText(this, "" + getResources().getString(R.string.toast_password_length_invalid)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CommonUtils.isNullString(strCurrentPwd) && !CommonUtils.checkPassword(strCurrentPwd)) {
                    Toast.makeText(this, "" + getResources().getString(R.string.toast_no_valid_password)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CommonUtils.isNullString(strCurrentPwd) && CommonUtils.isNullString(strNewPwd)) {
                    Toast.makeText(this, "" + getResources().getString(R.string.toast_no_new_password)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if ((!CommonUtils.isNullString(strCurrentPwd) &&
                        strNewPwd.length() < 8 || strNewPwd.length() > 15)) {
                    Toast.makeText(this, "" + getResources().getString(R.string.toast_new_password_length_invalid)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CommonUtils.isNullString(strCurrentPwd) && !CommonUtils.checkPassword(strNewPwd)) {
                    Toast.makeText(this, "" + getResources().getString(R.string.toast_no_valid_new_password)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CommonUtils.isNullString(strCurrentPwd) && CommonUtils.isNullString(strCnfrmNewPwd)) {
                    Toast.makeText(this
                            , "" + getResources().getString(R.string.toast_no_confirm_password)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CommonUtils.isNullString(strCurrentPwd) && !CommonUtils.checkPassword(strCnfrmNewPwd)) {
                    Toast.makeText(this
                            , "" + getResources().getString(R.string.toast_no_valid_password)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CommonUtils.isNullString(strCurrentPwd) && !(strCnfrmNewPwd).equals(strNewPwd)) {
                    Toast.makeText(this
                            , "" + getResources().getString(R.string.toast_password_mismatch)
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            userDetails.setUserType(sharedPref.getUserType());
            userDetails.setEmailId(sharedPref.getEmailId());
            userDetails.setFirstName(mStrFirstName);
            userDetails.setLastName(mStrLastName);
            userDetails.setPhoneNo(strPhoneNumber);

            if (!CommonUtils.isNullString(strCurrentPwd)) {
                userDetails.setOldPassword(strCurrentPwd);
                userDetails.setNewPassword(strNewPwd);
            }
        } else {
            String mStrVendorName = mEdtVName.getText().toString().trim();
            strPhoneNumber = mEdtPhone.getText().toString().trim();
            String strCurrentPwd = mEdtCurrPwd.getText().toString().trim();
            String strNewPwd = mEdtNewPwd.getText().toString().trim();
            String strCnfrmNewPwd = mEdtCnfrmNewPwd.getText().toString().trim();

            if (CommonUtils.isNullString(mStrVendorName)) {
                Toast.makeText(EditProfileActivity.this, "" + getResources().getString(R.string.toast_no_vname)
                        , Toast.LENGTH_SHORT).show();
                return;
            } else if (mStrVendorName.length() < 2 || mStrVendorName.length() > 30) {
                Toast.makeText(EditProfileActivity.this
                        , "" + getResources().getString(R.string.toast_v_name_length_invalid)
                        , Toast.LENGTH_SHORT).show();
                return;
            } else if (!CommonUtils.checkName(mStrVendorName)) {
                Toast.makeText(EditProfileActivity.this
                        , "" + getResources().getString(R.string.toast_no_valid_vname)
                        , Toast.LENGTH_SHORT).show();
                return;
            } else if (!CommonUtils.isNullString(strPhoneNumber) && !CommonUtils.checkPhoneNo(strPhoneNumber)) {
                Toast.makeText(this, "" + getResources().getString(R.string.toast_invalid_phone)
                        , Toast.LENGTH_SHORT).show();
                return;
            }
            if (!CommonUtils.isNullString(strCurrentPwd)) {
                if (!CommonUtils.isNullString(strCurrentPwd) && (strCurrentPwd.length() < 8 || strCurrentPwd.length() > 30)) {
                    Toast.makeText(this, "" + getResources().getString(R.string.toast_password_length_invalid)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CommonUtils.isNullString(strCurrentPwd) && !CommonUtils.checkPassword(strCurrentPwd)) {
                    Toast.makeText(this, "" + getResources().getString(R.string.toast_no_valid_password)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CommonUtils.isNullString(strCurrentPwd) && CommonUtils.isNullString(strNewPwd)) {
                    Toast.makeText(this, "" + getResources().getString(R.string.toast_no_new_password)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if ((!CommonUtils.isNullString(strCurrentPwd) &&
                        strNewPwd.length() < 8 || strNewPwd.length() > 15)) {
                    Toast.makeText(this, "" + getResources().getString(R.string.toast_new_password_length_invalid)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CommonUtils.isNullString(strCurrentPwd) && !CommonUtils.checkPassword(strNewPwd)) {
                    Toast.makeText(this, "" + getResources().getString(R.string.toast_no_valid_new_password)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CommonUtils.isNullString(strCurrentPwd) && CommonUtils.isNullString(strCnfrmNewPwd)) {
                    Toast.makeText(this
                            , "" + getResources().getString(R.string.toast_no_confirm_password)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CommonUtils.isNullString(strCurrentPwd) && !CommonUtils.checkPassword(strCnfrmNewPwd)) {
                    Toast.makeText(this
                            , "" + getResources().getString(R.string.toast_no_valid_password)
                            , Toast.LENGTH_SHORT).show();
                    return;
                } else if (!CommonUtils.isNullString(strCurrentPwd) && !(strCnfrmNewPwd).equals(strNewPwd)) {
                    Toast.makeText(this
                            , "" + getResources().getString(R.string.toast_password_mismatch)
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            userDetails.setUserType(sharedPref.getUserType());
            userDetails.setEmailId(sharedPref.getEmailId());
            userDetails.setVendorName(mStrVendorName);
            userDetails.setPhoneNo(strPhoneNumber);

            if (!CommonUtils.isNullString(strCurrentPwd)) {
                userDetails.setOldPassword(strCurrentPwd);
                userDetails.setNewPassword(strNewPwd);
            }
        }

        userDetails.setId(sharedPref.getUserId());
        CommonUtils.hideSoftKeyboard(this);
        if (CommonUtils.isInternetAvailable(EditProfileActivity.this)) {
            ProgressDialogUtil.showProgress(EditProfileActivity.this, "Loading", "Please Wait...", false);

            Call<SignUp> editProfileApi = mApiInterface.updateAccount(userDetails);

            editProfileApi.enqueue(new Callback<SignUp>() {
                @Override
                public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                    Log.e("/////////////", "Response : " + response.body());
                    SignUp signUpResponse = response.body();
                    Log.e("/////////////", "UserName : " + signUpResponse.getMessage());
                    Log.e("/////////////", "UserName : " + signUpResponse.getSuccess());

                    ProgressDialogUtil.dismissProgress();

                    if (signUpResponse.getSuccess() == 0) {
                        Toast.makeText(EditProfileActivity.this, "" + signUpResponse.getMessage()
                                , Toast.LENGTH_SHORT).show();
                    } else if (signUpResponse.getSuccess() == 1) {
                        Toast.makeText(EditProfileActivity.this, signUpResponse.getMessage(), Toast.LENGTH_LONG).show();

                        sharedPref.setPhoneNumber(mEdtPhone.getText().toString());
                        sharedPref.setLastName(mEdtLastName.getText().toString());
                        sharedPref.setFirstName(mEdtFirstName.getText().toString());
                        sharedPref.setVendorName(mEdtVName.getText().toString());
                    }
                }

                @Override
                public void onFailure(Call<SignUp> call, Throwable t) {
                    call.cancel();
                    ProgressDialogUtil.dismissProgress();
                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.str_something_went_worng)
                            , Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showAddressDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_address_dialog);
        dialog.setCancelable(false);

        Button btnSave = dialog.findViewById(R.id.dialog_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_btn_cancel);

        final EditText edtSuiteNo = dialog.findViewById(R.id.dialog_edt_suite_number);
        final EditText edtStreetName = dialog.findViewById(R.id.dialog_edt_street_name);
        final EditText edtZipCode = dialog.findViewById(R.id.dialog_edt_zipcode);
        final EditText edtCity = dialog.findViewById(R.id.dialog_edt_city);
        final EditText edtProvince = dialog.findViewById(R.id.dialog_edt_province);
        final EditText edtPhone = dialog.findViewById(R.id.dialog_edt_phone);
        final EditText edtName = dialog.findViewById(R.id.dialog_edt_name);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*else if (!CommonUtils.checkName(strStreetName)) {
                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.invalid_street_name), Toast.LENGTH_SHORT).show();
                } */
                String strSuiteNumber = edtSuiteNo.getText().toString();
                String strStreetName = edtStreetName.getText().toString();
                String strZipCode = edtZipCode.getText().toString();
                String strCity = edtCity.getText().toString();
                String strProvince = edtProvince.getText().toString();
                String strPhone = edtPhone.getText().toString();
                String strName = edtName.getText().toString();

                if (CommonUtils.isNullString(strName)) {
                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.toast_no_name), Toast.LENGTH_SHORT).show();
                } else if (CommonUtils.isNullString(strSuiteNumber)) {
                    Toast.makeText(EditProfileActivity.this
                            , "" + getResources().getString(R.string.no_suite_no)
                            , Toast.LENGTH_SHORT).show();
                } else if (strSuiteNumber.length() < 2 || strSuiteNumber.length() > 30) {
                    Toast.makeText(EditProfileActivity.this
                            , "" + getResources().getString(R.string.toast_suite_length_invalid)
                            , Toast.LENGTH_SHORT).show();
                } else if (CommonUtils.isNullString(strStreetName)) {
                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.toast_no_street), Toast.LENGTH_SHORT).show();
                } else if (CommonUtils.isNullString(strZipCode)) {
                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.no_zipcode), Toast.LENGTH_SHORT).show();
                } else if (!CommonUtils.checkZipCode(strZipCode)) {
                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.invalid_zipcode), Toast.LENGTH_SHORT).show();
                } else if (CommonUtils.isNullString(strCity)) {
                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.no_city), Toast.LENGTH_SHORT).show();
                } else if (strCity.length() < 2 || strCity.length() > 30) {
                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.toast_city_length_invalid), Toast.LENGTH_SHORT).show();
                } else if (CommonUtils.isNullString(strProvince)) {
                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.no_province), Toast.LENGTH_SHORT).show();
                } else if (strProvince.length() < 2 || strProvince.length() > 30) {
                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.toast_province_length_invalid), Toast.LENGTH_SHORT).show();
                } else if (!CommonUtils.isNullString(strPhone) && !CommonUtils.checkPhoneNo(strPhone)) {
                    Toast.makeText(EditProfileActivity.this, "" + getResources().getString(R.string.toast_invalid_phone)
                            , Toast.LENGTH_SHORT).show();
                } else {
                    if (CommonUtils.isInternetAvailable(EditProfileActivity.this)) {
                        AddressDetails addressDetails = new AddressDetails();
                        addressDetails.setUserId(sharedPref.getUserId());
                        addressDetails.setCity(strCity);
                        addressDetails.setEmailId(sharedPref.getEmailId());
                        addressDetails.setPhoneNo(strPhone);
                        addressDetails.setProvince(strProvince);
                        addressDetails.setStreet(strStreetName);
                        addressDetails.setSuite_no(strSuiteNumber);
                        addressDetails.setZipCode(strZipCode);
                        addressDetails.setName(strName);

                        ProgressDialogUtil.showProgress(EditProfileActivity.this, "Loading", "Please Wait...", false);

                        Call<AddAddress> addAddressApi = mApiInterface.addAddress(addressDetails);

                        addAddressApi.enqueue(new Callback<AddAddress>() {
                            @Override
                            public void onResponse(Call<AddAddress> call, Response<AddAddress> response) {
                                Log.e("/////////////", "Response : " + response.body());
                                AddAddress addAddressResponse = response.body();
                                Log.e("/////////////", "UserName : " + addAddressResponse.getMessage());
                                Log.e("/////////////", "UserName : " + addAddressResponse.getSuccess());

                                ProgressDialogUtil.dismissProgress();

                                if (addAddressResponse.getSuccess().equals("0")) {
                                    Toast.makeText(EditProfileActivity.this, "" + addAddressResponse.getMessage()
                                            , Toast.LENGTH_SHORT).show();
                                } else if (addAddressResponse.getSuccess().equals("1")) {
                                    Toast.makeText(EditProfileActivity.this, "Address added successfully.", Toast.LENGTH_LONG).show();
                                    mAddressArray.clear();
                                    mAddressArray.addAll(addAddressResponse.getAddressDetailList());
                                    mRecyclerAddresses.getAdapter().notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<AddAddress> call, Throwable t) {
                                call.cancel();
                                ProgressDialogUtil.dismissProgress();
                                Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.str_something_went_worng)
                                        , Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
