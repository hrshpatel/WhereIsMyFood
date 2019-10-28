package com.conestoga.whereismyfood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.conestoga.whereismyfood.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEdtEmail;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private TextView mTxtSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeView();
        setListeners();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_login_btn_login:

                break;

            case R.id.act_login_txt_signup:
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initializeView() {
        mEdtEmail = findViewById(R.id.act_login_edt_email);
        mEdtPassword = findViewById(R.id.act_login_edt_password);

        mBtnLogin = findViewById(R.id.act_login_btn_login);

        mTxtSignUp = findViewById(R.id.act_login_txt_signup);
    }

    private void setListeners() {
        mBtnLogin.setOnClickListener(this);
        mTxtSignUp.setOnClickListener(this);
    }

    private void validateData(){

    }

}
