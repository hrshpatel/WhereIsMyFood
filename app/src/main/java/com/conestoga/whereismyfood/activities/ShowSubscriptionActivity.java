package com.conestoga.whereismyfood.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.adapters.HomeListAdapter;
import com.conestoga.whereismyfood.adapters.ImagePagerAdapter;
import com.conestoga.whereismyfood.adapters.ReviewAdapter;
import com.conestoga.whereismyfood.apiutils.APIClient;
import com.conestoga.whereismyfood.apiutils.APIInterface;
import com.conestoga.whereismyfood.databinding.ActivityShowSubscriptionBinding;
import com.conestoga.whereismyfood.models.AddressDetails;
import com.conestoga.whereismyfood.models.DailyDetail;
import com.conestoga.whereismyfood.models.ReviewDetails;
import com.conestoga.whereismyfood.models.SubscriptionModel;
import com.conestoga.whereismyfood.response.AddAddress;
import com.conestoga.whereismyfood.response.GetSubById;
import com.conestoga.whereismyfood.response.GetSubscriptionResponse;
import com.conestoga.whereismyfood.response.ReviewResponse;
import com.conestoga.whereismyfood.utils.AppSharedPref;
import com.conestoga.whereismyfood.utils.CommonUtils;
import com.conestoga.whereismyfood.utils.ProgressDialogUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowSubscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityShowSubscriptionBinding mBinding;
    private APIInterface mApiInterface;
    private SubscriptionModel mSubscriptionModel;
    private ArrayList<ReviewDetails> mReviewDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_subscription);

        initView();
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
            case R.id.act_show_sub_txt_monday:
                if (mSubscriptionModel.isMonday() && mBinding.showLinMon.getVisibility() == View.VISIBLE) {
                    mBinding.showLinMon.setVisibility(View.GONE);
                    mBinding.actShowSubTxtMonday.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_add_black, 0);
                } else if (mSubscriptionModel.isMonday()) {
                    mBinding.showLinMon.setVisibility(View.VISIBLE);
                    mBinding.actShowSubTxtMonday.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_collapse, 0);

                }
                break;
            case R.id.act_show_sub_txt_tuesday:
                if (mSubscriptionModel.isTuesday() && mBinding.showLinTues.getVisibility() == View.VISIBLE) {
                    mBinding.showLinTues.setVisibility(View.GONE);
                    mBinding.actShowSubTxtTuesday.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_add_black, 0);
                } else if (mSubscriptionModel.isTuesday()) {
                    mBinding.showLinTues.setVisibility(View.VISIBLE);
                    mBinding.actShowSubTxtTuesday.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_collapse, 0);

                }
                break;
            case R.id.act_show_sub_txt_wednesday:
                if (mSubscriptionModel.isWednesday() && mBinding.showLinWed.getVisibility() == View.VISIBLE) {
                    mBinding.showLinWed.setVisibility(View.GONE);
                    mBinding.actShowSubTxtWednesday.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_add_black, 0);
                } else if (mSubscriptionModel.isWednesday()) {
                    mBinding.showLinWed.setVisibility(View.VISIBLE);
                    mBinding.actShowSubTxtWednesday.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_collapse, 0);

                }
                break;
            case R.id.act_show_sub_txt_thurs:
                if (mSubscriptionModel.isThursday() && mBinding.showLinThurs.getVisibility() == View.VISIBLE) {
                    mBinding.showLinThurs.setVisibility(View.GONE);
                    mBinding.actShowSubTxtThurs.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_add_black, 0);
                } else if (mSubscriptionModel.isThursday()) {
                    mBinding.showLinThurs.setVisibility(View.VISIBLE);
                    mBinding.actShowSubTxtThurs.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_collapse, 0);

                }
                break;
            case R.id.act_show_sub_txt_fri:
                if (mSubscriptionModel.isFriday() && mBinding.showLinFri.getVisibility() == View.VISIBLE) {
                    mBinding.showLinFri.setVisibility(View.GONE);
                    mBinding.actShowSubTxtFri.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_add_black, 0);
                } else if (mSubscriptionModel.isFriday()) {
                    mBinding.showLinFri.setVisibility(View.VISIBLE);
                    mBinding.actShowSubTxtFri.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_collapse, 0);

                }
                break;
            case R.id.act_show_sub_txt_sat:
                if (mSubscriptionModel.isSaturday() && mBinding.showLinSat.getVisibility() == View.VISIBLE) {
                    mBinding.showLinSat.setVisibility(View.GONE);
                    mBinding.actShowSubTxtSat.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_add_black, 0);
                } else if (mSubscriptionModel.isSaturday()) {
                    mBinding.showLinSat.setVisibility(View.VISIBLE);
                    mBinding.actShowSubTxtSat.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_collapse, 0);

                }
                break;
            case R.id.act_show_sub_txt_sun:
                if (mSubscriptionModel.isSunday() && mBinding.showLinSun.getVisibility() == View.VISIBLE) {
                    mBinding.showLinSun.setVisibility(View.GONE);
                    mBinding.actShowSubTxtSun.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_add_black, 0);
                } else if (mSubscriptionModel.isSunday()) {
                    mBinding.showLinSun.setVisibility(View.VISIBLE);
                    mBinding.actShowSubTxtSun.setCompoundDrawablesWithIntrinsicBounds(0, 0
                            , R.drawable.ic_collapse, 0);

                }
                break;
            case R.id.btn_checkout:
                Intent intent = new Intent(this, CheckoutActivity.class);
                intent.putExtra(CommonUtils.INTENT_SUB_MODEL, mSubscriptionModel);
                startActivity(intent);
                break;

            case R.id.txt_add_review:
                showReviewDialog();
                break;
        }
    }

    private void showReviewDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_review_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btnSave = dialog.findViewById(R.id.dialog_btn_save);
        Button btnCancel = dialog.findViewById(R.id.dialog_btn_cancel);

        final EditText edtComment = dialog.findViewById(R.id.edt_review);
        final RatingBar ratingBar = dialog.findViewById(R.id.add_review_ratting_bar);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingBar.getRating() < 0) {
                    Toast.makeText(ShowSubscriptionActivity.this, "Rate subscription to proceed.", Toast.LENGTH_SHORT).show();
                    return;
                }
                CommonUtils.hideSoftKeyboard(ShowSubscriptionActivity.this);
                if (CommonUtils.isInternetAvailable(getApplicationContext())) {
                    ReviewDetails reviewDetails = new ReviewDetails();
                    reviewDetails.setComments(edtComment.getText().toString());
                    reviewDetails.setRatings(ratingBar.getRating());
                    reviewDetails.setSubId(mSubscriptionModel.getSubId());
                    reviewDetails.setUserId(AppSharedPref.getInstance(getApplicationContext()).getUserId());

                    ProgressDialogUtil.showProgress(getApplicationContext(), "Loading", "Please Wait...", false);

                    Call<ReviewResponse> addReviewApi = mApiInterface.addReview(reviewDetails);

                    addReviewApi.enqueue(new Callback<ReviewResponse>() {
                        @Override
                        public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                            ReviewResponse reviewResponse = response.body();

                            ProgressDialogUtil.dismissProgress();

                            if (reviewResponse.getSuccess().equals("0")) {
                                Toast.makeText(getApplicationContext(), "" + reviewResponse.getMessage()
                                        , Toast.LENGTH_SHORT).show();
                            } else if (reviewResponse.getSuccess().equals("1")) {
                                Toast.makeText(getApplicationContext(), "Ratings added.", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                mReviewDetailList.clear();
                                mBinding.recyclerRatings.getAdapter().notifyDataSetChanged();
                                mReviewDetailList.addAll(reviewResponse.getReviewDetailsArrayList());
                                mBinding.recyclerRatings.getAdapter().notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<ReviewResponse> call, Throwable t) {
                            call.cancel();
                            ProgressDialogUtil.dismissProgress();
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_something_went_worng)
                                    , Toast.LENGTH_LONG).show();
                        }
                    });
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

    private void setToolbar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        mBinding.toolbarInclude.toolbarTitle.setText(mSubscriptionModel.getSubName());

        setSupportActionBar(mBinding.toolbarInclude.toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }

    }

    private void initView() {

        mApiInterface = APIClient.getClient().create(APIInterface.class);

        mBinding.setUserType(AppSharedPref.getInstance(this).getUserType());
        ProgressDialogUtil.showProgress(this, "Loading", "Please Wait...", false);
        final Call<GetSubById> subByIdApi = mApiInterface.getSubById(getIntent().getStringExtra(CommonUtils.INTENT_SUB_ID));

        subByIdApi.enqueue(new Callback<GetSubById>() {
            @Override
            public void onResponse(Call<GetSubById> call, Response<GetSubById> response) {
                ProgressDialogUtil.dismissProgress();

                GetSubById getSubById = response.body();
                if (getSubById.getSuccess().equals("0")) {
                    Toast.makeText(getApplicationContext(), "" + getSubById.getMessage()
                            , Toast.LENGTH_LONG).show();
                } else if (getSubById.getSuccess().equals("1")) {
                    mSubscriptionModel = getSubById.getSubscriptionModel();

                    if (mSubscriptionModel != null) {
                        setToolbar();
                        for (DailyDetail dailyDetail :
                                mSubscriptionModel.getDailyDetailList()) {
                            if (dailyDetail.getDay().equalsIgnoreCase("monday")) {
                                mSubscriptionModel.setDishDescMon(dailyDetail.getDishDesc());
                                mSubscriptionModel.setIngredientsMon(dailyDetail.getIngredients());
                                mSubscriptionModel.setDishNameMon(dailyDetail.getDishName());
                            } else if (dailyDetail.getDay().equalsIgnoreCase("tuesday")) {
                                mSubscriptionModel.setDishDescTue(dailyDetail.getDishDesc());
                                mSubscriptionModel.setIngredientsTue(dailyDetail.getIngredients());
                                mSubscriptionModel.setDishNameTue(dailyDetail.getDishName());
                            } else if (dailyDetail.getDay().equalsIgnoreCase("wednesday")) {
                                mSubscriptionModel.setDishDescWed(dailyDetail.getDishDesc());
                                mSubscriptionModel.setIngredientsWed(dailyDetail.getIngredients());
                                mSubscriptionModel.setDishNameWed(dailyDetail.getDishName());
                            } else if (dailyDetail.getDay().equalsIgnoreCase("thursday")) {
                                mSubscriptionModel.setDishDescThurs(dailyDetail.getDishDesc());
                                mSubscriptionModel.setIngredientsThurs(dailyDetail.getIngredients());
                                mSubscriptionModel.setDishNameThurs(dailyDetail.getDishName());
                            } else if (dailyDetail.getDay().equalsIgnoreCase("friday")) {
                                mSubscriptionModel.setDishDescFri(dailyDetail.getDishDesc());
                                mSubscriptionModel.setIngredientsFri(dailyDetail.getIngredients());
                                mSubscriptionModel.setDishNameFri(dailyDetail.getDishName());
                            } else if (dailyDetail.getDay().equalsIgnoreCase("Saturday")) {
                                mSubscriptionModel.setDishDescSat(dailyDetail.getDishDesc());
                                mSubscriptionModel.setIngredientsSat(dailyDetail.getIngredients());
                                mSubscriptionModel.setDishNameSat(dailyDetail.getDishName());
                            } else if (dailyDetail.getDay().equalsIgnoreCase("Sunday")) {
                                mSubscriptionModel.setDishDescSun(dailyDetail.getDishDesc());
                                mSubscriptionModel.setIngredientsSun(dailyDetail.getIngredients());
                                mSubscriptionModel.setDishNameSun(dailyDetail.getDishName());
                            }

                        }
                        mBinding.setSubModel(mSubscriptionModel);
                        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(ShowSubscriptionActivity.this, mSubscriptionModel.getImageList());
                        mBinding.actShowSubViewPager.setAdapter(imagePagerAdapter);
                        mBinding.actShowSubViewPager.setOffscreenPageLimit(0);
                        mBinding.actShowSubTabLayout.setupWithViewPager(mBinding.actShowSubViewPager, true);

                        mBinding.recyclerRatings.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        mReviewDetailList = mSubscriptionModel.getReviewDetailList();
                        mBinding.recyclerRatings.setAdapter(new ReviewAdapter(mReviewDetailList));
                    }
                }

            }

            @Override
            public void onFailure(Call<GetSubById> call, Throwable t) {
                call.cancel();
                ProgressDialogUtil.dismissProgress();
                Toast.makeText(ShowSubscriptionActivity.this, getResources().getString(R.string.str_something_went_worng)
                        , Toast.LENGTH_LONG).show();

            }
        });

    }

    private void setListeners() {
        mBinding.btnCheckout.setOnClickListener(this);
        mBinding.actShowSubTxtMonday.setOnClickListener(this);
        mBinding.actShowSubTxtTuesday.setOnClickListener(this);
        mBinding.actShowSubTxtWednesday.setOnClickListener(this);
        mBinding.actShowSubTxtThurs.setOnClickListener(this);
        mBinding.actShowSubTxtFri.setOnClickListener(this);
        mBinding.actShowSubTxtSat.setOnClickListener(this);
        mBinding.actShowSubTxtSun.setOnClickListener(this);

        mBinding.txtAddReview.setOnClickListener(this);
    }
}
