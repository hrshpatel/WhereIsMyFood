package com.conestoga.whereismyfood.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.adapters.ImagePagerAdapter;
import com.conestoga.whereismyfood.apiutils.APIClient;
import com.conestoga.whereismyfood.apiutils.APIInterface;
import com.conestoga.whereismyfood.models.SubscriptionModel;
import com.conestoga.whereismyfood.response.SignUp;
import com.conestoga.whereismyfood.utils.AppSharedPref;
import com.conestoga.whereismyfood.utils.CommonUtils;
import com.conestoga.whereismyfood.utils.ProgressDialogUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.common.collect.Range;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import com.conestoga.whereismyfood.databinding.ActivityAddSubsciptionBinding;

import retrofit2.Response;

/**
 * {@link AddSubsciptionActivity} is used to add new subscription when logged in user is vendor type.
 *
 * @Date : 1/12/2019
 */
public class AddSubsciptionActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final int STORAGE_PERMS = 452;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private AwesomeValidation mAwesomeValidation;
    private APIInterface mApiInterface;
    private AppSharedPref mSharedPref;
    private File mFileImagePath;

    private ArrayList<File> mImageFileList;
    private ArrayList<String> mImagePathList;
    private ImagePagerAdapter imagePagerAdapter;
    private ActivityAddSubsciptionBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_subsciption);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_subsciption);

        setUpToolbar();
        initView();
        addValidations();
        setListeners();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.act_sub_chk_monday:
                if (b) {
                    mBinding.actSubLinMonday.setVisibility(View.VISIBLE);
                } else {
                    mBinding.actSubLinMonday.setVisibility(View.GONE);
                }
                break;
            case R.id.act_sub_chk_tuesday:
                if (b) {
                    mBinding.actSubLinTuesday.setVisibility(View.VISIBLE);
                } else {
                    mBinding.actSubLinTuesday.setVisibility(View.GONE);
                }
                break;
            case R.id.act_sub_chk_wednesday:
                if (b) {
                    mBinding.actSubLinWednesday.setVisibility(View.VISIBLE);
                } else {
                    mBinding.actSubLinWednesday.setVisibility(View.GONE);
                }
                break;
            case R.id.act_sub_chk_thursday:
                if (b) {
                    mBinding.actSubLinThursday.setVisibility(View.VISIBLE);
                } else {
                    mBinding.actSubLinThursday.setVisibility(View.GONE);
                }
                break;
            case R.id.act_sub_chk_friday:
                if (b) {
                    mBinding.actSubLinFriday.setVisibility(View.VISIBLE);
                } else {
                    mBinding.actSubLinFriday.setVisibility(View.GONE);
                }
                break;
            case R.id.act_sub_chk_sat:
                if (b) {
                    mBinding.actSubLinSat.setVisibility(View.VISIBLE);
                } else {
                    mBinding.actSubLinSat.setVisibility(View.GONE);
                }
                break;
            case R.id.act_sub_chk_sun:
                if (b) {
                    mBinding.actSubLinSun.setVisibility(View.VISIBLE);
                } else {
                    mBinding.actSubLinSun.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_PERMS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {
                    if (!(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                        Snackbar snackbar = Snackbar.make(mBinding.actAddSubRoot, getString(R.string.permission_never_asked)
                                , Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction(getString(R.string.allow), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        });
                        snackbar.show();
                    }
                }
                break;
        }
    }

    /**
     * Sets listeners on all required user interfaces
     *
     * @Date : 30/10/2019
     */
    private void setListeners() {
        mBinding.actSubChkMonday.setOnCheckedChangeListener(this);
        mBinding.actSubChkTuesday.setOnCheckedChangeListener(this);
        mBinding.actSubChkWednesday.setOnCheckedChangeListener(this);
        mBinding.actSubChkThursday.setOnCheckedChangeListener(this);
        mBinding.actSubChkFriday.setOnCheckedChangeListener(this);
        mBinding.actSubChkSat.setOnCheckedChangeListener(this);
        mBinding.actSubChkSun.setOnCheckedChangeListener(this);

        mBinding.actSubBtnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (AddSubsciptionActivity.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED &&
                            AddSubsciptionActivity.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                                        , Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                STORAGE_PERMS);
                    } else {
                        selectImage();
                    }
                } else {
                    selectImage();
                }
            }
        });

        mBinding.actSubBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAwesomeValidation.validate()) {
                    if (mBinding.actSubChkMonday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtMonDishName.getText().toString())) {
                        mBinding.actSubEdtMonDishName.setError(getString(R.string.str_no_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtMonDishName.getBottom());
                    } else if (mBinding.actSubChkMonday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtMonDishName.getText().toString())) {
                        mBinding.actSubEdtMonDishName.setError(getString(R.string.str_invalid_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtMonDishName.getBottom());
                    } else if (mBinding.actSubChkMonday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtMonDesc.getText().toString())) {
                        mBinding.actSubEdtMonDesc.setError(getString(R.string.str_no_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtMonDesc.getBottom());
                    } else if (mBinding.actSubChkMonday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtMonDesc.getText().toString())) {
                        mBinding.actSubEdtMonDesc.setError(getString(R.string.str_invalid_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtMonDesc.getBottom());
                    } else if (mBinding.actSubChkMonday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtMonIngredients.getText().toString())) {
                        mBinding.actSubEdtMonIngredients.setError(getString(R.string.str_no_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtMonIngredients.getBottom());
                    } else if (mBinding.actSubChkMonday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtMonIngredients.getText().toString())) {
                        mBinding.actSubEdtMonIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtMonIngredients.getBottom());
                    } else if (mBinding.actSubChkTuesday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtTuesDishName.getText().toString())) {
                        mBinding.actSubEdtTuesDishName.setError(getString(R.string.str_no_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtTuesDishName.getBottom());
                    } else if (mBinding.actSubChkTuesday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtTuesDishName.getText().toString())) {
                        mBinding.actSubEdtTuesDishName.setError(getString(R.string.str_invalid_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtTuesDishName.getBottom());
                    } else if (mBinding.actSubChkTuesday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtTuesDesc.getText().toString())) {
                        mBinding.actSubEdtTuesDesc.setError(getString(R.string.str_no_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtTuesDesc.getBottom());
                    } else if (mBinding.actSubChkTuesday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtTuesDesc.getText().toString())) {
                        mBinding.actSubEdtTuesDesc.setError(getString(R.string.str_invalid_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtTuesDesc.getBottom());
                    } else if (mBinding.actSubChkTuesday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtTuesIngredients.getText().toString())) {
                        mBinding.actSubEdtTuesIngredients.setError(getString(R.string.str_no_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtTuesIngredients.getBottom());
                    } else if (mBinding.actSubChkTuesday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtTuesIngredients.getText().toString())) {
                        mBinding.actSubEdtTuesIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtTuesIngredients.getBottom());
                    } else if (mBinding.actSubChkWednesday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtWedDishName.getText().toString())) {
                        mBinding.actSubEdtWedDishName.setError(getString(R.string.str_no_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtWedDishName.getBottom());
                    } else if (mBinding.actSubChkWednesday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtWedDishName.getText().toString())) {
                        mBinding.actSubEdtWedDishName.setError(getString(R.string.str_invalid_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtWedDishName.getBottom());
                    } else if (mBinding.actSubChkWednesday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtWedDesc.getText().toString())) {
                        mBinding.actSubEdtWedDesc.setError(getString(R.string.str_no_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtWedDesc.getBottom());
                    } else if (mBinding.actSubChkWednesday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtWedDesc.getText().toString())) {
                        mBinding.actSubEdtWedDesc.setError(getString(R.string.str_invalid_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtWedDesc.getBottom());
                    } else if (mBinding.actSubChkWednesday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtWedIngredients.getText().toString())) {
                        mBinding.actSubEdtWedIngredients.setError(getString(R.string.str_no_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtWedIngredients.getBottom());
                    } else if (mBinding.actSubChkWednesday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtWedIngredients.getText().toString())) {
                        mBinding.actSubEdtWedIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtWedIngredients.getBottom());
                    } else if (mBinding.actSubChkThursday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtThursDishName.getText().toString())) {
                        mBinding.actSubEdtThursDishName.setError(getString(R.string.str_no_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtThursDishName.getBottom());
                    } else if (mBinding.actSubChkThursday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtThursDishName.getText().toString())) {
                        mBinding.actSubEdtThursDishName.setError(getString(R.string.str_invalid_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtThursDishName.getBottom());
                    } else if (mBinding.actSubChkThursday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtThursDesc.getText().toString())) {
                        mBinding.actSubEdtThursDesc.setError(getString(R.string.str_no_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtThursDesc.getBottom());
                    } else if (mBinding.actSubChkThursday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtThursDesc.getText().toString())) {
                        mBinding.actSubEdtThursDesc.setError(getString(R.string.str_invalid_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtThursDesc.getBottom());
                    } else if (mBinding.actSubChkThursday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtThursIngredients.getText().toString())) {
                        mBinding.actSubEdtThursIngredients.setError(getString(R.string.str_no_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtThursIngredients.getBottom());
                    } else if (mBinding.actSubChkThursday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtThursIngredients.getText().toString())) {
                        mBinding.actSubEdtThursIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtThursIngredients.getBottom());
                    } else if (mBinding.actSubChkFriday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtFriDishName.getText().toString())) {
                        mBinding.actSubEdtFriDishName.setError(getString(R.string.str_no_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtFriDishName.getBottom());
                    } else if (mBinding.actSubChkFriday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtFriDishName.getText().toString())) {
                        mBinding.actSubEdtFriDishName.setError(getString(R.string.str_invalid_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtFriDishName.getBottom());
                    } else if (mBinding.actSubChkFriday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtFriDesc.getText().toString())) {
                        mBinding.actSubEdtFriDesc.setError(getString(R.string.str_no_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtFriDesc.getBottom());
                    } else if (mBinding.actSubChkFriday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtFriDesc.getText().toString())) {
                        mBinding.actSubEdtFriDesc.setError(getString(R.string.str_invalid_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtFriDesc.getBottom());
                    } else if (mBinding.actSubChkFriday.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtFriIngredients.getText().toString())) {
                        mBinding.actSubEdtFriIngredients.setError(getString(R.string.str_no_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtFriIngredients.getBottom());
                    } else if (mBinding.actSubChkFriday.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtFriIngredients.getText().toString())) {
                        mBinding.actSubEdtFriIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtFriIngredients.getBottom());
                    } else if (mBinding.actSubChkSat.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtSatDishName.getText().toString())) {
                        mBinding.actSubEdtSatDishName.setError(getString(R.string.str_no_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtSatDishName.getBottom());
                    } else if (mBinding.actSubChkSat.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtSatDishName.getText().toString())) {
                        mBinding.actSubEdtSatDishName.setError(getString(R.string.str_invalid_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtSatDishName.getBottom());
                    } else if (mBinding.actSubChkSat.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtSatDesc.getText().toString())) {
                        mBinding.actSubEdtSatDesc.setError(getString(R.string.str_no_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtSatDesc.getBottom());
                    } else if (mBinding.actSubChkSat.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtSatDesc.getText().toString())) {
                        mBinding.actSubEdtSatDesc.setError(getString(R.string.str_invalid_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtSatDesc.getBottom());
                    } else if (mBinding.actSubChkSat.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtSatIngredients.getText().toString())) {
                        mBinding.actSubEdtSatIngredients.setError(getString(R.string.str_no_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtSatIngredients.getBottom());
                    } else if (mBinding.actSubChkSat.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtSatIngredients.getText().toString())) {
                        mBinding.actSubEdtSatIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtSatIngredients.getBottom());
                    } else if (mBinding.actSubChkSun.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtSunDishName.getText().toString())) {
                        mBinding.actSubEdtSunDishName.setError(getString(R.string.str_no_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtSunDishName.getBottom());
                    } else if (mBinding.actSubChkSun.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtSunDishName.getText().toString())) {
                        mBinding.actSubEdtSunDishName.setError(getString(R.string.str_invalid_dish_name));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtSunDishName.getBottom());
                    } else if (mBinding.actSubChkSun.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtSunDesc.getText().toString())) {
                        mBinding.actSubEdtSunDesc.setError(getString(R.string.str_no_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtSunDesc.getBottom());
                    } else if (mBinding.actSubChkSun.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtSunDesc.getText().toString())) {
                        mBinding.actSubEdtSunDesc.setError(getString(R.string.str_invalid_description));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtSunDesc.getBottom());
                    } else if (mBinding.actSubChkSun.isChecked() && CommonUtils.isNullString(mBinding.actSubEdtSunIngredients.getText().toString())) {
                        mBinding.actSubEdtSunIngredients.setError(getString(R.string.str_no_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtSunIngredients.getBottom());
                    } else if (mBinding.actSubChkSun.isChecked() && !CommonUtils.checkDishName(mBinding.actSubEdtSunIngredients.getText().toString())) {
                        mBinding.actSubEdtSunIngredients.setError(getString(R.string.str_invalid_ingredients));
                        mBinding.actSubScrollView.smoothScrollTo(0, mBinding.actSubEdtSunIngredients.getBottom());
                    } else {
                        if (CommonUtils.isInternetAvailable(AddSubsciptionActivity.this)) {
                            SubscriptionModel subscriptionModel = new SubscriptionModel();

                            subscriptionModel.setUserId(mSharedPref.getUserId());
                            subscriptionModel.setPhone_no(mSharedPref.getPhoneNumber());
                            subscriptionModel.setEmailId(mSharedPref.getEmailId());
                            subscriptionModel.setSubDescription(mBinding.actSubEdtDescription.getText().toString());
                            subscriptionModel.setSubName(mBinding.actSubEdtName.getText().toString());
                            subscriptionModel.setVendorName(mBinding.actSubEdtVendorName.getText().toString());
                            subscriptionModel.setPrice(mBinding.actSubEdtPrice.getText().toString());

                            if (mBinding.actSubChkMonday.isChecked()) {
                                subscriptionModel.setMonday(true);
                                subscriptionModel.setDishNameMon(mBinding.actSubEdtMonDishName.getText().toString());
                                subscriptionModel.setIngredientsMon(mBinding.actSubEdtMonIngredients.getText().toString());
                                subscriptionModel.setDishDescMon(mBinding.actSubEdtMonDesc.getText().toString());
                            }

                            if (mBinding.actSubChkTuesday.isChecked()) {
                                subscriptionModel.setTuesday(true);
                                subscriptionModel.setDishNameTue(mBinding.actSubEdtTuesDishName.getText().toString());
                                subscriptionModel.setIngredientsTue(mBinding.actSubEdtTuesIngredients.getText().toString());
                                subscriptionModel.setDishDescTue(mBinding.actSubEdtTuesDesc.getText().toString());
                            }

                            if (mBinding.actSubChkWednesday.isChecked()) {
                                subscriptionModel.setWednesday(true);
                                subscriptionModel.setDishNameWed(mBinding.actSubEdtWedDishName.getText().toString());
                                subscriptionModel.setIngredientsWed(mBinding.actSubEdtWedIngredients.getText().toString());
                                subscriptionModel.setDishDescWed(mBinding.actSubEdtWedDesc.getText().toString());
                            }

                            if (mBinding.actSubChkThursday.isChecked()) {
                                subscriptionModel.setThursday(true);
                                subscriptionModel.setDishNameThurs(mBinding.actSubEdtThursDishName.getText().toString());
                                subscriptionModel.setIngredientsThurs(mBinding.actSubEdtThursIngredients.getText().toString());
                                subscriptionModel.setDishDescThurs(mBinding.actSubEdtThursDesc.getText().toString());
                            }

                            if (mBinding.actSubChkFriday.isChecked()) {
                                subscriptionModel.setFriday(true);
                                subscriptionModel.setDishNameFri(mBinding.actSubEdtFriDishName.getText().toString());
                                subscriptionModel.setIngredientsFri(mBinding.actSubEdtFriIngredients.getText().toString());
                                subscriptionModel.setDishDescFri(mBinding.actSubEdtFriDesc.getText().toString());
                            }

                            if (mBinding.actSubChkSat.isChecked()) {
                                subscriptionModel.setSaturday(true);
                                subscriptionModel.setDishNameSat(mBinding.actSubEdtSatDishName.getText().toString());
                                subscriptionModel.setIngredientsSat(mBinding.actSubEdtSatIngredients.getText().toString());
                                subscriptionModel.setDishDescSat(mBinding.actSubEdtSatDesc.getText().toString());
                            }

                            if (mBinding.actSubChkSun.isChecked()) {
                                subscriptionModel.setSunday(true);
                                subscriptionModel.setDishNameSun(mBinding.actSubEdtSunDishName.getText().toString());
                                subscriptionModel.setIngredientsSun(mBinding.actSubEdtSunIngredients.getText().toString());
                                subscriptionModel.setDishDescSun(mBinding.actSubEdtSunDesc.getText().toString());
                            }

                            ProgressDialogUtil.showProgress(AddSubsciptionActivity.this, "Loading", "Please Wait...", false);

                            ArrayList<MultipartBody.Part> partArrayList = new ArrayList<>();
                            for (int i = 0; i < mImageFileList.size(); i++) {
                                Log.d("//////////", "requestUploadSurvey: survey image " + i + "  " + mImagePathList.get(i));
                                RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), mImageFileList.get(i));
                                partArrayList.add(MultipartBody.Part.createFormData("file_" + i, mImageFileList.get(i).getName(), surveyBody));
                            }

                            Call<SignUp> addSubApi = mApiInterface.addSubscription(RequestBody.create(MediaType.parse("application/json"),
                                    new Gson().toJson(subscriptionModel))
                                    , partArrayList);

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
                                        finish();
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

    /**
     * this method is used to selecting the option for image pickup from camera/gallery
     *
     * @Date : 11/21/2019
     * @author :Harsh Patel
     */
    private void selectImage() {
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.from_library),
                getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddSubsciptionActivity.this);
        builder.setTitle(getString(R.string.add_pic));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result=Utility.checkPermission(MainActivity.this);

                if (items[item].equals(getString(R.string.take_photo))) {
                    //if(result)
                    cameraIntent();

                } else if (items[item].equals(getString(R.string.from_library))) {
                    //if(result)
                    galleryIntent();

                } else if (items[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * this method is used to pass intent to  gallery
     *
     * @Date : 11/21/2019
     * @author :Harsh Patel
     */
    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_FILE);
    }

    /**
     * this method is used to pass intent to  camera
     *
     * @Date : 11/21/2019
     * @author :Harsh Patel
     */
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void addValidations() {
        mAwesomeValidation.addValidation(this, R.id.act_sub_edt_name, CommonUtils.DISH_NAME_PATTERN
                , R.string.str_enter_valid_subscription_name);
        mAwesomeValidation.addValidation(mBinding.actSubEdtVendorName, CommonUtils.DISH_NAME_PATTERN
                , getResources().getString(R.string.toast_no_valid_vname));
        mAwesomeValidation.addValidation(mBinding.actSubEdtDescription, CommonUtils.DISH_NAME_PATTERN, getString(R.string.str_invalid_description));
        mAwesomeValidation.addValidation(mBinding.actSubEdtPrice, Range.greaterThan(1f), getString(R.string.str_enter_price));
    }

    private void setUpToolbar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        mBinding.includeToolbar.toolbarTitle.setText(getString(R.string.str_add_sub));

        setSupportActionBar(mBinding.includeToolbar.toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }

    }

    private void initView() {
        mImageFileList = new ArrayList<>();
        mImagePathList = new ArrayList<>();

        mSharedPref = AppSharedPref.getInstance(this);

        mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        mApiInterface = APIClient.getClient().create(APIInterface.class);

        imagePagerAdapter = new ImagePagerAdapter(this, mImagePathList);
        mBinding.actAddSubViewPager.setAdapter(imagePagerAdapter);
        mBinding.actAddSubViewPager.setOffscreenPageLimit(0);
        mBinding.actAddSubTabLayout.setupWithViewPager(mBinding.actAddSubViewPager, true);

        mBinding.actSubEdtVendorName.setText(mSharedPref.getVendorName());
    }

    /**
     * this method is used to set Camera image to imageview
     *
     * @param data response from camera after capturing image
     * @Date : 11/21/2019
     * @author :Harsh Patel
     */
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if (thumbnail != null) {
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        }

        mFileImagePath = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            mFileImagePath.createNewFile();
            fo = new FileOutputStream(mFileImagePath);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mImageFileList.add(mFileImagePath);
        mImagePathList.add(mFileImagePath.getAbsolutePath());
        imagePagerAdapter = new ImagePagerAdapter(this, mImagePathList);
        mBinding.actAddSubViewPager.setAdapter(imagePagerAdapter);
    }

    /**
     * this method is used to set Gallery image to imageview
     *
     * @param data response from gallery after selecting image
     * @Date : 11/21/2019
     * @author : Harsh Patel
     */
    private void onSelectFromGalleryResult(Intent data) {

        if (data != null) {
            String path = CommonUtils.getFilePath(this, data.getData());
            Bitmap bitmap = CommonUtils.getBitmapFromUri(this, data.getData());

            if (bitmap != null) {
                Bitmap rotatedBitmap = CommonUtils.getRotatedBitmap(path, bitmap);

                Uri tempUri = CommonUtils.getImageUri(this, rotatedBitmap);

                String fileUri = CommonUtils.getRealPathFromURI(this, tempUri);

                if (fileUri != null) {
                    mFileImagePath = new File(fileUri);
                    mImageFileList.add(mFileImagePath);
                    mImagePathList.add(mFileImagePath.getAbsolutePath());
                    imagePagerAdapter = new ImagePagerAdapter(this, mImagePathList);
                    mBinding.actAddSubViewPager.setAdapter(imagePagerAdapter);
                }

            } else {
                Toast.makeText(this, R.string.invalid_image_file, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
