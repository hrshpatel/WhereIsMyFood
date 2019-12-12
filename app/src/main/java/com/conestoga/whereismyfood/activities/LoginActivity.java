package com.conestoga.whereismyfood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.apiutils.APIClient;
import com.conestoga.whereismyfood.apiutils.APIInterface;
import com.conestoga.whereismyfood.models.UserDetails;
import com.conestoga.whereismyfood.response.SignUp;
import com.conestoga.whereismyfood.utils.AppSharedPref;
import com.conestoga.whereismyfood.utils.CommonUtils;
import com.conestoga.whereismyfood.utils.ProgressDialogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private TextView mTxtSignUp;
    private APIInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppSharedPref.getInstance(this).getIsLogin()) {
            finish();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_login);

            initializeView();
            setListeners();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_login_btn_login:
                CommonUtils.hideSoftKeyboard(this);
                validateData();
                break;

            case R.id.act_login_txt_signup:
                mEdtEmail.setText("");
                mEdtPassword.setText("");
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initializeView() {
        mApiInterface = APIClient.getClient().create(APIInterface.class);

        mEdtEmail = findViewById(R.id.act_login_edt_email);
        mEdtPassword = findViewById(R.id.act_login_edt_password);

        mBtnLogin = findViewById(R.id.act_login_btn_login);

        mTxtSignUp = findViewById(R.id.act_login_txt_signup);
    }

    /**
     * Sets listeners on all required user interfaces
     *
     * @Date : 18/10/2019
     */
    private void setListeners() {
        mBtnLogin.setOnClickListener(this);
        mTxtSignUp.setOnClickListener(this);
    }

    private void validateData() {
        String mStrEmail = mEdtEmail.getText().toString().trim();
        String mStrPwd = mEdtPassword.getText().toString().trim().trim();

        if (CommonUtils.isNullString(mStrEmail)) {
            Toast.makeText(this, "" + getResources().getString(R.string.toast_no_email)
                    , Toast.LENGTH_SHORT).show();
        } else if (!CommonUtils.checkEmail(mStrEmail)) {
            Toast.makeText(this
                    , "" + getResources().getString(R.string.toast_email_format_wrong)
                    , Toast.LENGTH_SHORT).show();
        } else if (CommonUtils.isNullString(mStrPwd)) {
            Toast.makeText(this, "" + getResources().getString(R.string.toast_no_password)
                    , Toast.LENGTH_SHORT).show();
        } else if (mStrPwd.length() < 8 || mStrPwd.length() > 30) {
            Toast.makeText(this
                    , "" + getResources().getString(R.string.toast_password_length_invalid)
                    , Toast.LENGTH_SHORT).show();
        } else if (!CommonUtils.checkPassword(mStrPwd)) {
            Toast.makeText(this
                    , "" + getResources().getString(R.string.toast_no_valid_password)
                    , Toast.LENGTH_SHORT).show();
        } else {
            if (CommonUtils.isInternetAvailable(LoginActivity.this)) {
                final UserDetails userDetails = new UserDetails();
                userDetails.setEmailId(mStrEmail);
                userDetails.setPassword(mStrPwd);

                ProgressDialogUtil.showProgress(LoginActivity.this, "Loading", "Please Wait...", false);

                Call<SignUp> registerApiCall = mApiInterface.loginUser(userDetails);

                registerApiCall.enqueue(new Callback<SignUp>() {
                    @Override
                    public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                        SignUp signUpResponse = response.body();
                        ProgressDialogUtil.dismissProgress();

                        if (signUpResponse.getSuccess() == 0) {
                            Toast.makeText(LoginActivity.this, "" + signUpResponse.getMessage()
                                    , Toast.LENGTH_LONG).show();
                        } else if (signUpResponse.getSuccess() == 1) {
                            UserDetails responseDetails = signUpResponse.getUserDetails();
                            AppSharedPref sharedPref = AppSharedPref.getInstance(LoginActivity.this);

                            sharedPref.setUserId(responseDetails.getId());
                            sharedPref.setUserType(responseDetails.getUserType());
                            sharedPref.setEmailId(responseDetails.getEmailId());
                            sharedPref.setImageUrl(responseDetails.getProfilePicUrl());

                            if (responseDetails.getUserType().equals("0")) {
                                sharedPref.setFirstName(responseDetails.getFirstName());
                                sharedPref.setLastName(responseDetails.getLastName());
                            } else {
                                sharedPref.setVendorName(responseDetails.getVendorName());
                            }
                            sharedPref.setPhoneNumber(responseDetails.getPhoneNo());

                            sharedPref.setIsLogin(true);
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUp> call, Throwable t) {
                        call.cancel();
                        ProgressDialogUtil.dismissProgress();
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.str_something_went_worng)
                                , Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

    }

}
