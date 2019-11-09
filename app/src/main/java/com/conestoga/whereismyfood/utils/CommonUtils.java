package com.conestoga.whereismyfood.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.conestoga.whereismyfood.R;
import com.conestoga.whereismyfood.activities.HomeActivity;

import java.util.regex.Pattern;

public class CommonUtils {

    public static final String BASE_URL = "http://10.0.2.2/Capstone_project/apis/";
    private final static Pattern FIRST_LAST_NAME_PATTERN = Pattern.compile("^[A-Za-z0-9]+[A-Za-z-\\.\\-\\_\\']*$");
    private final static Pattern INVALID_EMAIL_PATTERN = Pattern.compile("^[0-9-]+[_0-9-]*(\\.[_0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private final static Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9-]+[_A-Za-z0-9-]*(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private final static Pattern PASSWORD_VALIDATION = Pattern.compile("[A-Za-z0-9\\@\\#\\_\\'\\^\\*\\=\\:\\-\\+\\`]+$");
    private final static Pattern PHONE_NO_PATTERN = Pattern.compile("^[0-9]{10}$");
    private final static Pattern ZIP_CODE_PATTERN = Pattern.compile("^[ABCEGHJKLMNPRSTVXY][0-9][ABCEGHJKLMNPRSTVWXYZ] ?[0-9][ABCEGHJKLMNPRSTVWXYZ][0-9]$");

    /**
     * checks if the name is correct or not
     *
     * @param name name to verify
     * @return true if name matches the pattern
     * @Date :  19/10/2019
     * @author : Harsh Patel
     */
    public static boolean checkName(String name) {
        return FIRST_LAST_NAME_PATTERN.matcher(name).matches();
    }

    /**
     * checks if phone number is correct or not
     *
     * @param phoneNo Phone number to verify
     * @return true if name matches the pattern
     * @Date :  26/10/2019
     * @author : Harsh Patel
     */
    public static boolean checkPhoneNo(String phoneNo) {
        return PHONE_NO_PATTERN.matcher(phoneNo).matches();
    }

    /**
     * checks if zip code is correct or not
     *
     * @param zipCode Zip code to verify
     * @return true if zip code matches the pattern
     * @Date :  11/01/2019
     * @author : Harsh Patel
     */
    public static boolean checkZipCode(String zipCode) {
        return ZIP_CODE_PATTERN.matcher(zipCode).matches();
    }

    /**
     * This function hides the keyboard when ever not needed.
     *
     * @Date :  10/18/2017
     * @author : Harsh Patel
     * @version : 0.0.1
     */
    public static void hideSoftKeyboard(Activity context) {
        try {
            View view = context.getCurrentFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception ignored) {
//            ignored.printStackTrace();
        }
    }

    /**
     * this method is used to check if the input email is valid or not.
     *
     * @param email email to verify
     * @return true if email is correct
     * @author : Harsh Patel
     */
    public static boolean checkEmail(String email) {
        return !INVALID_EMAIL_PATTERN.matcher(email).matches() && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * checks if the string is blank or not.
     *
     * @param string .
     * @return true if string is blank
     * @Date :  10/19/2019
     * @author : Harsh Patel
     */
    public static boolean isNullString(String string) {
        try {
            return string == null || string.trim().equalsIgnoreCase("null") || string.trim().length() <= 0 || string.trim().equals("");
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * checks if the password is correct or not
     *
     * @param password password to verify
     * @return true if password matches the pattern
     * @Date :  21/10/2019
     * @author : Harsh Patel
     */
    public static boolean checkPassword(String password) {
        return PASSWORD_VALIDATION.matcher(password).matches();
    }

    /**
     * to check if there is internet connection or not
     *
     * @Date :  10/21/2019
     * @author : Harsh Patel
     */
    public static boolean isInternetAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = null;
            if (cm != null) netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                Toast.makeText(context, "" + context.getResources().getString(R.string.internetmsg), Toast.LENGTH_SHORT).show();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void hideLoading() {
    }

    public static void showLoading(HomeActivity homeActivity) {
    }
}
