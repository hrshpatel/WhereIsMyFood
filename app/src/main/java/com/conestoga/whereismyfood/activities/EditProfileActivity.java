package com.conestoga.whereismyfood.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.customviews.RoundedImageView;

public class EditProfileActivity extends AppCompatActivity {

    private RoundedImageView mImgProfilePic;
    private RelativeLayout mRelFirstName;
    private TextView mTxtFirstName;
    private EditText mEdtFirstName;
    private RelativeLayout mRelVendorName;
    private TextView mTxtVName;
    private EditText mEdtVName;
    private RelativeLayout mRelLastName;
    private TextView mTxtLastName;
    private EditText mEdtLastName;
    private TextView mTxtPhone;
    private EditText mEdtPhone;
    private TextView mTxtEmail;
    private EditText mEdtEmail;
    private TextView mTxtCurrPwd;
    private EditText mEdtCurrPwd;
    private TextView mTxtNewPwd;
    private EditText mEdtNewPwd;
    private TextView mTxtCnfrmNewPwd;
    private EditText mEdtCnfrmNewPwd;
    private ImageView mIvAdd;
    private RecyclerView mRecyclerAddresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initializeView();
    }

    private void initializeView() {
        mImgProfilePic = findViewById(R.id.act_edit_profile_img_profilePic);
        mRelFirstName = findViewById(R.id.rel_first_name);
        mTxtFirstName = findViewById(R.id.act_edit_profile_txt_firstName);
        mEdtFirstName = findViewById(R.id.act_edit_profile_edt_firstName);
        mRelVendorName = findViewById(R.id.rel_vendor_name);
        mTxtVName = findViewById(R.id.act_edit_profile_txt_v_name);
        mEdtVName = findViewById(R.id.act_edit_profile_edt_v_Name);
        mRelLastName = findViewById(R.id.rel_last_name);
        mTxtLastName = findViewById(R.id.act_edit_profile_txt_lastName);
        mEdtLastName = findViewById(R.id.act_edit_profile_edt_lastName);
        mTxtPhone = findViewById(R.id.act_edit_profile_txt_phone);
        mEdtPhone = findViewById(R.id.act_edit_profile_edt_phone);
        mTxtEmail = findViewById(R.id.act_edit_profile_txt_email);
        mEdtEmail = findViewById(R.id.act_edit_profile_edt_email);
        mTxtCurrPwd = findViewById(R.id.act_edit_profile_txt_curr_pwd);
        mEdtCurrPwd = findViewById(R.id.act_edit_profile_edt_curr_pwd);
        mTxtNewPwd = findViewById(R.id.act_edit_profile_txt_new_pwd);
        mEdtNewPwd = findViewById(R.id.act_edit_profile_edt_new_pwd);
        mTxtCnfrmNewPwd = findViewById(R.id.act_edit_profile_txt_cnfrm_new_pwd);
        mEdtCnfrmNewPwd = findViewById(R.id.act_edit_profile_edt_cnfrm_new_pwd);
        mIvAdd = findViewById(R.id.act_edit_profile_iv_add);
        mRecyclerAddresses = findViewById(R.id.act_edit_profile_recycler_addresses);
    }

}
