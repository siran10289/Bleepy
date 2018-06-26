package bleepy.pack.com.bleepy.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.Expose;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bleepy.pack.com.bleepy.R;

import static bleepy.pack.com.bleepy.utils.Constants.AUDIO_PATH;
import static iknow.android.utils.BaseUtils.getContext;


@SuppressWarnings("unused")
public class AppUtils {

    private static final String TAG = AppUtils.class.getName();
    private static MediaPlayer mediaPlayer;
    private static String fileCapturedPhoto;
    private static String fileName = "rallio.jpg";
    //Check for internet connection

    @SuppressLint("MissingPermission")
    public static String getDeviceImieNumber(Context ctx) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            Log.e(TAG, "##### getDeviceImieNumber ##########" + telephonyManager.getDeviceId().toString());
            return telephonyManager.getDeviceId().toString();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return "";
    }
    public static String getDeviceID(Context context){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }


    public static boolean checkProvidersEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled;
        boolean network_enabled;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            gps_enabled = false;
        }
        /*try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            network_enabled = false;
        }*/

        //return (gps_enabled && network_enabled);
        return gps_enabled;
    }


    public static float getDeviceWidth(Context mContext) {
        float deviceWidth;
        Point size = new Point();
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            windowManager.getDefaultDisplay().getSize(size);
            deviceWidth = size.x;
        } else {
            Display display = windowManager.getDefaultDisplay();
            deviceWidth = display.getWidth();
        }
        return deviceWidth;
    }

    public static int getDeviceDensity() {
        return (int) Resources.getSystem().getDisplayMetrics().density;
    }

    public static float getDeviceHeight(Context mContext) {
        float deviceHeight;
        Point size = new Point();
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            windowManager.getDefaultDisplay().getSize(size);
            deviceHeight = size.y;
        } else {
            Display display = windowManager.getDefaultDisplay();
            deviceHeight = display.getHeight();
        }
        return deviceHeight;
    }

    public static void hideSoftKeyBoard(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                view.clearFocus();
            }

        } catch (Exception e) {

        }
    }

    public static void openSoftKeyBoard(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, 0);
            }
        } catch (Exception e) {

        }
    }


    public static void makeToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }


    /**
     * Get URI to image received from capture by camera.
     */
    public static Uri getCaptureImageOutputUri(Context context) {
        Uri outputFileUri = null;
        File getImage = context.getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }


    public static Uri getPickImageResultUri(Intent data, Context context) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri(context) : data.getData();
    }


    public static String getBuildVersionOfTheApp(Context context) {
        try {
            PackageInfo info;
            info = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0";
    }

    public static String encodeTobase64(Bitmap image) {
        if (image == null)
            return "";

        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        while (imageEncoded.indexOf("\n") != -1) {
            imageEncoded = imageEncoded.replace("\n", "");
        }
        AppUtils.largeLog("imageEncoded", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }
    public static void showTextViewsAsMandatory(TextView... tvs) {
        for (TextView tv : tvs) {
            tv.setText(Html.fromHtml(tv.getText().toString() + "<font color=\"#ff0000\">" + "* " + "</font>"));
        }
    }
    public static void enableEditText(EditText... ets) {
        for (EditText et : ets) {
            et.setEnabled(true);
        }
    }


    public static Bitmap createBlackAndWhite(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);

                // use 128 as threshold, above -> white, below -> black
                if (gray > 128)
                    gray = 255;
                else
                    gray = 0;
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, gray, gray, gray));
            }
        }
        return bmOut;
    }


    public static Bitmap resizeBitmap(Bitmap bitmap, int newMaxWidthOrHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.d("AppUtil resizeBitmap", "Bitmap Current Width/Height: (" + width + "," + height + ")");

        int newWidth = 0;
        int newHeight = 0;

        if (width > height) {
            newWidth = newMaxWidthOrHeight;
            newHeight = (newMaxWidthOrHeight * height) / width;
        } else if (width < height) {
            newHeight = newMaxWidthOrHeight;
            newWidth = (newMaxWidthOrHeight * width) / height;
        } else if (width == height) {
            newHeight = newMaxWidthOrHeight;
            newWidth = newMaxWidthOrHeight;
        }

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

       /* Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                width, height, matrix, true);*/
        Bitmap resizedBitmap = Bitmap.createBitmap(200, 89, bitmap.getConfig());
        Canvas canvas = new Canvas(resizedBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, matrix, null);

        return resizedBitmap;
    }

    public static void imageReSize(Context context, ImageView iv_image) {
        int screenWidth;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(screenWidth, screenWidth / 2);
        iv_image.setLayoutParams(parms);
    }
    public static Bitmap compressImage(Bitmap bmp) {
        final float maxHeight = 1280.0f;
        final float maxWidth = 1280.0f;
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.RGB_565);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        if (bmp != null) {
            bmp.recycle();
        }

        return scaledBitmap;
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }



    public static int convertStringToIntSeconds(String seconds) {

        //min =  +"";
        return Integer.parseInt(seconds);
    }

    public static String getMessage(int hour, int min) {
        Log.e("AppUtils", "@@@@@@@@@@@@@ :" + hour + "min" + min);
        if (hour < 12) {
            return "Good morning";
        } else if ((hour >= 12 && hour < 17) || (hour == 17 && min <= 30)) {
            return "Good afternoon";
        } else {
            return "Good evening";
        }
    }


    public static JSONObject loadJSONFromAsset(Context ctx, String fileName) {
        String jsonStr = null;
        try {
            InputStream is = ctx.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonStr = new String(buffer, "UTF-8");
            return new JSONObject(jsonStr);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static void largeLog(String tag, String content) {
        if (content != null) {
            if (content.length() > 4000) {
                Log.e(tag, content.substring(0, 4000));
                largeLog(tag, content.substring(4000));
            } else {
                Log.e(tag, content);
            }
        } else {
            Log.e(tag, "NULL");
        }
    }

    public static long collectTimeDiffFrom(long fromMilliSeconds) {
        long diffInSeconds = (fromMilliSeconds - (new Date().getTime())) / 1000;
        Log.d("Time Difference", "---diffInSeconds------:  " + diffInSeconds);
        //if (diffInSeconds < 0 || diffInSeconds > 24 * 60 * 60) {
        //    diffInSeconds = 10 * 60;  //10 Minutes
        //}
        return diffInSeconds;
    }

    public static String convertTimeToReadableFormat(int totalSeconds) {
        Log.d("AppUtils", "convertTimeToReadableFormat: " + totalSeconds);
        int hours = (totalSeconds / 3600);
        int minutes = (totalSeconds - (hours * 3600)) / 60;
        int seconds = totalSeconds - (hours * 3600) - (minutes * 60);

        String message = "";
        if (hours > 1) {
            message += hours + " hours ";
        } else if (hours > 0) {
            message += hours + " hour ";
        }

        if (minutes > 1) {
            message += minutes + " minutes ";
        } else if (minutes > 0) {
            message += minutes + " minute ";
        }

        if (hours <= 0) {
            if (seconds > 1) {
                message += seconds + " seconds ";
            } else if (seconds > 0) {
                message += seconds + " second ";
            }
        }

        if (message.length() == 0) {
            //message = "0 second";
            message = "0:00";
        }
        Log.d("AppUtils", "convertTimeToReadableFormat: " + message);
        return message;
    }

    public static String convertHourMinuteFromSeconds(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        return hours + "," + minutes;
    }

    public static int getSpinnerIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }


    public static boolean isServicesRunning(String serviceName, Context mcontext) {
        boolean servicesRunning = false;

        ActivityManager am = (ActivityManager) mcontext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningAppProcesses = am.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningAppProcesses) {
            if (runningServiceInfo.service.getClassName().equals(serviceName)) {
                servicesRunning = true;
            }
        }
        return servicesRunning;
    }


    public static int getDeviceWidth(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return width;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        File tempFile = null;
        /*ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);*/

        String path = null;
        try {
            tempFile = saveBitmap(inImage);
            Log.e("Path:", tempFile.getPath());
            Log.e("AbsolutePath:", tempFile.getAbsolutePath());
            Log.e("ParentPath:", tempFile.getParent());
            Log.e("ParentPath:", tempFile.getParentFile().getPath());
            path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), tempFile.getPath(), fileName, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error:", e.toString());
        }
        return Uri.parse(path);
    }

    public static File saveBitmap(Bitmap bmp) {
        File file = null;
        //String _time = "";

       /* Calendar cal = Calendar.getInstance();
        int millisecond = cal.get(Calendar.MILLISECOND);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);
        _time = "image_" + hourofday + "" + minute + "" + second + ""
                + millisecond + ".png";*/

        String file_path = Environment.getExternalStorageDirectory() + "/Rallio/";
        try {
            File dir = new File(file_path);
            if (!dir.exists())
                dir.mkdirs();
            file = new File(dir, fileName);
            FileOutputStream fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            Log.e("error in saving image", e.getMessage());
        }
        return file;
    }

    public static File DownloadBitmap(Bitmap bmp, String folderName) {
        File file = null;
        String _time = "";

        Calendar cal = Calendar.getInstance();
        int millisecond = cal.get(Calendar.MILLISECOND);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);
        _time = "image_" + hourofday + "" + minute + "" + second + "" + millisecond + ".png";

        String file_path = Environment.getExternalStorageDirectory() + folderName;
        try {
            File dir = new File(file_path);
            if (!dir.exists())
                dir.mkdirs();
            file = new File(dir, _time + fileName);
            FileOutputStream fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            Log.e("error in saving image", e.getMessage());
        }
        return file;
    }

    public static Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getTimeStamp(String formatedDate) {

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("PST"));
        Date date = null;
        try {
            date = dateFormatter.parse(formatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date.getTime() / 1000);

    }



    public static Date getDate(String dateString) { //Todo
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd '/' hh:mm:ss");
        Date date = null;
        try {
            date = format.parse(dateString.substring(0, 19));
            Log.e("ConvertedDate:", date.toString());
        } catch (ParseException e) {
            Log.e("Error:", e.toString());
        }
        return date;
    }
    public static Date StringToDate(String dateString,String givenFormat) {
        SimpleDateFormat format = new SimpleDateFormat(givenFormat);
        Calendar cal = Calendar.getInstance();
        //TimeZone tz = cal.getTimeZone();
       // Log.e("Time zone: ", tz.getDisplayName());
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = format.parse(dateString);
            Log.e("Date:",date.toString());
        } catch (ParseException e) {
            Log.e("Error:", e.toString());
        }
        return date;
    }

    public static String getCurrentTime(String timeFormat, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat);
        Calendar cal = Calendar.getInstance();
        dateFormat.setTimeZone(cal.getTimeZone());
        cal.setTime(date);
        String currentTime = dateFormat.format(cal.getTime());
        Log.e("OriginalDateTime:",currentTime);
        return currentTime;
    }
    public static String setCurrentTime(String timeFormat, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat);
        Calendar cal = Calendar.getInstance();
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTime(date);
        String currentTime = dateFormat.format(cal.getTime());
        Log.e("OriginalDateTime:",currentTime);
        return currentTime;
    }

    public static String formatDateToString(Date date, String format, String timeZone) {
        // null check
        if (date == null) return null;
        // create SimpleDateFormat object with input format
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // default system timezone if passed null or empty
        if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
            timeZone = Calendar.getInstance().getTimeZone().getID();
        }
        // set timezone to SimpleDateFormat
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        // return Date in required format with timezone as String
        Log.e("ConvertedTime:", sdf.format(date));
        return sdf.format(date);
    }


    public static float dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static String getCurrentTimeZone() {
        Calendar cal = Calendar.getInstance();
        long milliDiff = cal.get(Calendar.ZONE_OFFSET);
        String[] ids = TimeZone.getAvailableIDs();
        String name = null;
        for (String id : ids) {
            TimeZone tz = TimeZone.getTimeZone(id);
            if (tz.getRawOffset() == milliDiff) {
                // Found a match.
                name = id;
                break;
            }
        }
        return name;
    }

    public static void expand(final View v) {
        v.measure(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayoutCompat.LayoutParams.MATCH_PARENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
    public static void revealExpand(final View myView){
        myView.measure(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight()) / 2;

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
        anim.start();

    }
    public static void revealCollapse(final View myView){
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;
        int initialRadius = myView.getWidth() / 2;

        Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.GONE);
            }
        });
        anim.start();

    }
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }



    public static void showSnackBarWithAction(Context context,String message, CoordinatorLayout coordinatorLayout) {
        final Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
        snackbar.setActionTextColor(Color.GREEN);
        View view1=snackbar.getView();
        view1.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        TextView tv=(TextView)view1.findViewById(android.support.design.R.id.snackbar_text);
        tv.setMaxLines(5);
        tv.setLineSpacing(0,1.2f);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/AvenirNextLTPro-DemiCn.otf"));

        snackbar.show();
    }

    public static void showSnackBar(Context context,String message, CoordinatorLayout coordinatorLayout){
        Snackbar snackbar= Snackbar.make(coordinatorLayout,message, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.GREEN);
        View view1=snackbar.getView();
        view1.setBackgroundColor(ContextCompat.getColor(context,R.color.popup_color));
        TextView tv=(TextView)view1.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        //tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/AvenirNextLTPro-DemiCn.otf"));
        snackbar.show();
    }


    public static String displayTimeZone(TimeZone tz) {

        long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset()) - TimeUnit.HOURS.toMinutes(hours);
        // avoid -4:-30 issue
        minutes = Math.abs(minutes);

        String result = "";
        if (hours > 0) {
            result = String.format("(GMT+%d:%02d) %s", hours, minutes, tz.getID());
        } else {
            result = String.format("(GMT%d:%02d) %s", hours, minutes, tz.getID());
        }

        return result;

    }

    public static String getTimeZoneValue(TimeZone tz) {
        long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset()) - TimeUnit.HOURS.toMinutes(hours);
        // avoid -4:-30 issue
        minutes = Math.abs(minutes);

        String result = "";
        if (hours > 0) {
            result = String.format("GMT+%d:%02d", hours, minutes);
        } else {
            result = String.format("GMT%d:%02d", hours, minutes);
        }

        return result;
    }

    public void generateHashkey(Context mContext) {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo("com.pack.rallio", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                System.out.println("keyhash :" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            // Log.d(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            //Log.d(TAG, e.getMessage(), e);
        }
    }

    public static String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public static Bitmap createDrawableFromView(Activity activity, View view) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
            view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
            view.buildDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        }catch (Exception e){
            return null;
        }



    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }



    public static Integer getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageInt;
    }
    public static String getPrice(String input)
    {
        String output = "";

        Pattern pattern = Pattern.compile("\\d{1,3}[,\\.]?(\\d{1,2})?");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find())
        {
            output = matcher.group(0);
        }

        return output;
    }
    public static String getCurrencySymbol(String price){
        return price.replace(NumberFormat.getCurrencyInstance().getCurrency().getSymbol(), "");
    }
    public static File createVideoFile(String fileName) {
        File storageDir = new File(AUDIO_PATH);
        storageDir.mkdirs();
        File videoFile = null;
        try {
            videoFile = File.createTempFile(fileName, ".mp3", storageDir);

        } catch (Exception e) {
            Log.e("Exception:",e.toString());
        }
        return videoFile;
    }
    public static String stringForTime(long timeMs) { //todo
        final long hr = TimeUnit.MILLISECONDS.toHours(timeMs);
        final long min = TimeUnit.MILLISECONDS.toMinutes(timeMs - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(timeMs - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        return String.format("%02d:%02d",  min, sec);
    }
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

}


