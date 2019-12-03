package com.conestoga.whereismyfood.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.conestoga.whereismyfood.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class CommonUtils {

    public static final String BASE_URL = "http://192.168.0.21/Capstone_project/apis/";
    public static final String INTENT_SUB_ID = "SUB_ID";
    public static final String INTENT_SUB_MODEL = "Sub_model";
    public final static Pattern FIRST_LAST_NAME_PATTERN = Pattern.compile("^[A-Za-z0-9]+[A-Za-z-\\.\\-\\_\\']*$");
    public final static Pattern DISH_NAME_PATTERN = Pattern.compile("^[A-Za-z0-9]+[a-zA-Z0-9\\(\\)\\.\\-\\_\\{\\}\\@\\*\\s*]*$");
    private final static Pattern INVALID_EMAIL_PATTERN = Pattern.compile("^[0-9-]+[_0-9-]*(\\.[_0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    public final static Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9-]+[_A-Za-z0-9-]*(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private final static Pattern PASSWORD_VALIDATION = Pattern.compile("[A-Za-z0-9\\@\\#\\_\\'\\^\\*\\=\\:\\-\\+\\`]+$");
    public final static Pattern PHONE_NO_PATTERN = Pattern.compile("^[0-9]{10}$");
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

    public static boolean checkDishName(String dishName) {
        return DISH_NAME_PATTERN.matcher(dishName).matches();
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
     * @Date :  10/18/2019
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

    /**
     * This method is responsible for getting file Path from Image URI
     *
     * @Date : 11/21/2019
     * @author : Harsh Patel
     */
    public static String getFilePath(Context context, Uri data) {
        String path = "";

        // For non-gallery application
        path = data.getPath();

        // For gallery application
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(data, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            path = cursor.getString(columnIndex);
            cursor.close();
        }
        return path;
    }

    /**
     * This method is responsible for getting Bitmap from Image URI
     *
     * @Date : 11/21/2019
     * @author : Harsh Patel
     */
    public static Bitmap getBitmapFromUri(Context context, Uri data) {
        Bitmap bitmap = null;

        // Starting fetch image from file
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(data);
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            // BitmapFactory.decodeFile(path, options);
            BitmapFactory.decodeStream(is, null, options);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            is = context.getContentResolver().openInputStream(data);
            bitmap = BitmapFactory.decodeStream(is, null, options);
            if (bitmap == null) {
                Toast.makeText(context, R.string.image_not_loaded, Toast.LENGTH_SHORT).show();
                return null;
            }
            is.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * This method is responsible for getting rotated Bitmap from Image.
     *
     * @Date : 12/07/2019
     * @author : Harsh Patel
     */
    public static Bitmap getRotatedBitmap(String path, Bitmap bitmap) {
        Bitmap rotatedBitmap = bitmap;
        Matrix matrix = new Matrix();
        ExifInterface exif = null;
        int orientation = 1;
        try {
            if (path != null) {
                // Getting Exif information of the file
                exif = new ExifInterface(path);
            }
            if (exif != null) {
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.preRotate(270);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.preRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.preRotate(180);
                        break;
                }
                // Rotates the image according to the orientation
                rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()
                        , matrix, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    /**
     * This method is responsible for geting URI from Bitmap
     *
     * @Date : 11/21/2019
     * @author : Harsh Patel
     */
    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage
                , "Title", null);
        return Uri.parse(path);
    }

    /**
     * This method is responsible for getting Path from Image URI
     *
     * @Date : 11/21/2019
     * @author : Harsh Patel
     */
    public static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            String filePath = cursor.getString(idx);
            cursor.close();
            return filePath;
        } else {
            Toast.makeText(context, R.string.read_image_fail, Toast.LENGTH_SHORT).show();
            return null;
        }
    }

}
