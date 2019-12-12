package com.conestoga.whereismyfood.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.apiutils.APIClient;
import com.conestoga.whereismyfood.apiutils.APIInterface;
import com.conestoga.whereismyfood.models.UserDetails;
import com.conestoga.whereismyfood.response.SignUp;
import com.conestoga.whereismyfood.utils.CommonUtils;
import com.conestoga.whereismyfood.utils.ProgressDialogUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.GsonBuilder;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupUserFragment extends Fragment implements View.OnClickListener {

    private TabLayout mTabLayout;
    private EditText mEdtFirstName;
    private EditText mEdtLastName;
    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private EditText mEdtCnfrmPwd;
    private Button mBtnSignup;
    private APIInterface mApiInterface;

    public SignupUserFragment() {
        // Required empty public constructor
    }

    public static SignupUserFragment newInstance(TabLayout mTabLayout) {
        SignupUserFragment fragment = new SignupUserFragment();
        fragment.mTabLayout = mTabLayout;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeView(view);
        setListeners();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frg_signup_btn_signup:
                validateUserInput();
                break;
        }
    }

    private void initializeView(View view) {
        mApiInterface = APIClient.getClient().create(APIInterface.class);

        mEdtFirstName = view.findViewById(R.id.frg_signup_edt_fname);
        mEdtLastName = view.findViewById(R.id.frg_signup_edt_last_name);
        mEdtEmail = view.findViewById(R.id.frg_signup_edt_email);
        mEdtPassword = view.findViewById(R.id.frg_signup_edt_password);
        mEdtCnfrmPwd = view.findViewById(R.id.frg_signup_edt_cnfm_pwd);

        mBtnSignup = view.findViewById(R.id.frg_signup_btn_signup);
    }

    /**
     * Sets listeners on all required user interfaces
     *
     * @Date : 19/10/2019
     */
    private void setListeners() {
        mBtnSignup.setOnClickListener(this);
    }

    private void validateUserInput() {
        String mStrFirstName = mEdtFirstName.getText().toString().trim();
        String mStrLastName = mEdtLastName.getText().toString().trim();
        String mStrEmail = mEdtEmail.getText().toString().trim();
        String mStrPwd = mEdtPassword.getText().toString().trim().trim();
        String mStrcnfrmPwd = mEdtCnfrmPwd.getText().toString().trim();

        if (CommonUtils.isNullString(mStrFirstName)) {
            Toast.makeText(getActivity(), "" + getResources().getString(R.string.toast_no_firstname)
                    , Toast.LENGTH_SHORT).show();
        } else if (mStrFirstName.length() < 2 || mStrFirstName.length() > 30) {
            Toast.makeText(getActivity()
                    , "" + getResources().getString(R.string.toast_first_name_length_invalid)
                    , Toast.LENGTH_SHORT).show();
        } else if (!CommonUtils.checkName(mStrFirstName)) {
            Toast.makeText(getActivity()
                    , "" + getResources().getString(R.string.toast_no_valid_firstname)
                    , Toast.LENGTH_SHORT).show();
        } else if (!CommonUtils.isNullString(mStrLastName) && (mStrLastName.length() < 2 || mStrLastName.length() > 30)) {
            Toast.makeText(getActivity()
                    , "" + getResources().getString(R.string.toast_last_name_length_invalid)
                    , Toast.LENGTH_SHORT).show();
        } else if (!CommonUtils.isNullString(mStrLastName) && !CommonUtils.checkName(mStrLastName)) {
            Toast.makeText(getActivity()
                    , "" + getResources().getString(R.string.toast_no_valid_lastname)
                    , Toast.LENGTH_SHORT).show();
        } else if (CommonUtils.isNullString(mStrEmail)) {
            Toast.makeText(getActivity(), "" + getResources().getString(R.string.toast_no_email)
                    , Toast.LENGTH_SHORT).show();
        } else if (!CommonUtils.checkEmail(mStrEmail)) {
            Toast.makeText(getActivity()
                    , "" + getResources().getString(R.string.toast_email_format_wrong)
                    , Toast.LENGTH_SHORT).show();
        } else if (CommonUtils.isNullString(mStrPwd)) {
            Toast.makeText(getActivity(), "" + getResources().getString(R.string.toast_no_password)
                    , Toast.LENGTH_SHORT).show();
        } else if (mStrPwd.length() < 8 || mStrPwd.length() > 30) {
            Toast.makeText(getActivity()
                    , "" + getResources().getString(R.string.toast_password_length_invalid)
                    , Toast.LENGTH_SHORT).show();
        } else if (!CommonUtils.checkPassword(mStrPwd)) {
            Toast.makeText(getActivity()
                    , "" + getResources().getString(R.string.toast_no_valid_password)
                    , Toast.LENGTH_SHORT).show();
        } else if (CommonUtils.isNullString(mStrcnfrmPwd)) {
            Toast.makeText(getActivity()
                    , "" + getResources().getString(R.string.toast_no_confirm_password)
                    , Toast.LENGTH_SHORT).show();
        } else if (!CommonUtils.checkPassword(mStrcnfrmPwd)) {
            Toast.makeText(getActivity()
                    , "" + getResources().getString(R.string.toast_no_valid_password)
                    , Toast.LENGTH_SHORT).show();
        } else if (!(mStrcnfrmPwd).equals(mStrPwd)) {
            Toast.makeText(getActivity()
                    , "" + getResources().getString(R.string.toast_password_mismatch)
                    , Toast.LENGTH_SHORT).show();
        } else {
            if (CommonUtils.isInternetAvailable(getActivity())) {
                UserDetails userDetails = new UserDetails();
                userDetails.setFirstName(mStrFirstName);
                userDetails.setLastName(mStrLastName);
                userDetails.setEmailId(mStrEmail);
                userDetails.setPassword(mStrPwd);
                userDetails.setUserType("0");

                ProgressDialogUtil.showProgress(getActivity(), "Loading", "Please Wait...", false);

                Call<SignUp> registerApiCall = mApiInterface.registerUser(userDetails);

                registerApiCall.enqueue(new Callback<SignUp>() {
                    @Override
                    public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                        Log.e("/////////////", "Response : " + response.body());
                        SignUp signUpResponse = response.body();
                        Log.e("/////////////", "UserName : " + signUpResponse.getMessage());
                        Log.e("/////////////", "UserName : " + signUpResponse.getSuccess());

                        ProgressDialogUtil.dismissProgress();

                        if (signUpResponse.getSuccess() == 0) {
                            Toast.makeText(getActivity(), "" + signUpResponse.getMessage()
                                    , Toast.LENGTH_SHORT).show();
                        } else if (signUpResponse.getSuccess() == 1) {
                            Toast.makeText(getActivity(), "Registered successfully. Please login", Toast.LENGTH_LONG).show();
                            if (getActivity() != null) {
                                getActivity().finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUp> call, Throwable t) {
                        call.cancel();
                        ProgressDialogUtil.dismissProgress();
                        Toast.makeText(getActivity(), getResources().getString(R.string.str_something_went_worng)
                                , Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

}
