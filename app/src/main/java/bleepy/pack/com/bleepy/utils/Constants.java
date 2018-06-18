package bleepy.pack.com.bleepy.utils;


import android.os.Environment;

/**
 * Created by siranjeevi on 06/7/18.
 */
public class Constants {

    public static final String DEV_DOMAINNAME = "http://bleepy.martgrid.com/mobileservices/";
    public static final String CELENT_DOMAINNAME = "http://mobileservices.bleepy.com/";
    public static String getDomainName() {
        return DEV_DOMAINNAME;
    }
    /*Common Constants*/
    public static final boolean DEBUG_MODE=true;
    public static final String AUDIO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bleepy/Recordings/";
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String UK_NUMBERS_PATTERN = "^(\\s?7\\d{3}|\\(?(7|07)\\d{3}\\)?)\\s?\\d{3}\\s?\\d{3}$";
    public static final String NAME_PATTERN = "^[A-Za-z\\-\\'\\. ]{1,}[\\.]{0,1}[A-Za-z\\-\\'\\. ]{0,}$";
    public static final String NAME_PATTERN_ONLY = "^[a-zA-Z0-9\\s-]*$";  //"^[A-Z][a-zA-Z\\s]*$"
    public static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d).{8,20}";
    public static final String POSTAL_CODE_PATTERN = "^[a-zA-Z]{1,2}[0-9][0-9A-Za-z]{0,1} {0,1}[0-9][A-Za-z]{2}$";


    public static final int REQUEST_CAPTURE_PHOTO = 5;
    public static final int REQUEST_GALLERY_PHOTO = 6;
    public static final int REQUEST_CODE_CLOSE = 100;
    public static final int CROPING_CODE = 301;
    public static final String KEY_IMAGE_URI = "KEY_IMAGE_URI";
    public static final String KEY_IMAGE_BYTE = "KEY_IMAGE_BYTE";
    public static final String KEY_PROFILE_JSON = "KEY_PROFILE_JSON";
    public static final String KEY_IS_VALID_USER = "KEY_IS_VALID_USER";
    public static final String DATE_TIME_FORMAT="dd/MM/yyyy' | 'hh:mm aa";
    public static final String DATE_FORMAT="MMM dd,yyyy";
    public static final String DATE_FORMAT2="dd/MM/yyyy";
    public final static String ALERT_INTENT_GALLERY = "ALERT_INTENT_GALLERY";
    public final static String ALERT_INTENT_CAMERA = "ALERT_INTENT_CAMERA";
    public final static String ALERT_REMOVE = "ALERT_REMOVE";
    public final static int STATUS_SUCCESS=1;
    public final static int STATUS_FAILURE=0;
    public final static String KEY_USERNAME="KEY_USERNAME";
    public final static String KEY_PASSWORD="KEY_PASSWORD";
    public final static String REG_KEY_SCREEN="REG_KEY_SCREEN";
    public final static String SCREEN_TYPE="SCREEN_TYPE";
    public final static String UNAME_PSWD_SCREEN="UNAME_PSWD_SCREEN";
    public final static String KEY_DEVICE_TYPE="DEVICE_TYPE";
    public final static String KEY_MY_OBJ="KEY_MY_OBJ";
    public final static String KEY_USERID="KEY_USERID";





}
