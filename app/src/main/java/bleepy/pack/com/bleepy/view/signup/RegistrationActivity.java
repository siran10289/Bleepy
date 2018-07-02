package bleepy.pack.com.bleepy.view.signup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.firebase.iid.FirebaseInstanceId;


import javax.inject.Inject;

import bleepy.pack.com.bleepy.MainActivity;
import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.DaggerSignupComponent;
import bleepy.pack.com.bleepy.di.component.SignupComponent;
import bleepy.pack.com.bleepy.di.module.SignupModule;
import bleepy.pack.com.bleepy.models.common.UserImageUploadRequest;
import bleepy.pack.com.bleepy.models.signup.SignupResponse;
import bleepy.pack.com.bleepy.utils.AppUtils;
import bleepy.pack.com.bleepy.utils.Constants;
import bleepy.pack.com.bleepy.utils.customdialog.AppDialogManager;
import bleepy.pack.com.bleepy.utils.customdialog.DialogListener;
import bleepy.pack.com.bleepy.view.activation.PersonalDeviceActivity;
import bleepy.pack.com.bleepy.view.activation.RegistrationKeyActivity;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import bleepy.pack.com.bleepy.view.signin.SigninActivity;
import butterknife.BindView;
import butterknife.OnClick;

import static bleepy.pack.com.bleepy.BleepyApplication.refreshedFCMToken;
import static bleepy.pack.com.bleepy.utils.Constants.ALERT_INTENT_CAMERA;
import static bleepy.pack.com.bleepy.utils.Constants.ALERT_INTENT_GALLERY;
import static bleepy.pack.com.bleepy.utils.Constants.ALERT_REMOVE;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_DEVICE_TYPE;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_IMAGE_URI;
import static bleepy.pack.com.bleepy.utils.Constants.REG_KEY_SCREEN;
import static bleepy.pack.com.bleepy.utils.Constants.SCREEN_TYPE;


public class RegistrationActivity extends BaseActivity implements SignupContract.RegisterationView,
        DialogListener.AlertDialogListener, DialogListener.SelectDeviceLisenter {
    @Inject
    SignupContract.Presenter mPresenter;
    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etSecondName)
    EditText etSecondName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etDesignation)
    EditText etDesignation;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etStaff)
    EditText etStaffID;
    @BindView(R.id.ivCapturedImage)
    ImageView ivCapturedImage;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.coordinatorLayout)CoordinatorLayout mCoordinatorLayout;
    private SignupComponent signupComponent;
    private String imageString = "";
    private Intent mIntent;
    String userProfilePictureUrl;
    int deviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        initComponent();
        setSupportActionBar(mToolBar);
        setDisplayHomeUp();
        toolbar_title.setText(R.string.singup);
        mPresenter.setRegistrationView(RegistrationActivity.this);
        loadProfileImage(null);
        setInputDoneListener();

    }

    private void initComponent() {
        this.signupComponent = DaggerSignupComponent.builder()
                .applicationComponent(getApplicationComponent())
                .signupModule(new SignupModule(this))
                .build();
        signupComponent.inject(this);
        mPresenter.setRegistrationView(this);
    }

    private void loadProfileImage(final byte[] byteArray) {
        if (byteArray != null) {
            Glide.with(RegistrationActivity.this).load(byteArray)
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.drawable.ic_add_profile)
                    .into(new BitmapImageViewTarget(ivCapturedImage) {
                        @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            ivCapturedImage.setImageDrawable(circularBitmapDrawable);
                            // AppUtils.imageReSize(RegistrationActivity.this, ivCapturedImage);
                            if (byteArray != null) {
                                imageString = AppUtils.encodeToBase64(circularBitmapDrawable.getBitmap(), Bitmap.CompressFormat.JPEG, 100);
                                imageString = "data:image/jpeg;base64," + imageString;
                            }

                        }
                    });
        } else {
            ivCapturedImage.setImageDrawable(ContextCompat.getDrawable(RegistrationActivity.this, R.drawable.ic_add_profile));
            imageString = "";
        }
    }

    private void setInputDoneListener() {
        etMobile.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerBtnClicked();
                }
                return handled;
            }
        });

    }


    @OnClick(R.id.btnRegister)
    public void registerBtnClicked() {
        mPresenter.uploadUserImage(imageString);
    }

    @OnClick(R.id.tvSignin)
    public void onSiginLinkClicked() {
       this.finish();
    }

    @OnClick(R.id.ivCapturedImage)
    public void onProfileImageClicked() {
        AppDialogManager.photoPickerDialog(RegistrationActivity.this, this);
    }

    private void uploadProfilePicture() {

    }

    private void onRegisterUser() {

    }


    @Override
    public void navigateToImageCropper(String imageURI) {
        Intent mIntent = new Intent(RegistrationActivity.this, ImageCropperActivity.class);
        mIntent.putExtra("FLAG", ALERT_INTENT_CAMERA);
        mIntent.putExtra(KEY_IMAGE_URI, imageURI);
        startActivityForResult(mIntent, Constants.CROPING_CODE);
        overridePendingTransition(R.anim.anim_right_left_enter, R.anim.anim_right_left_exit);
    }

    @Override
    public void setImageOnImageView(byte[] imageByteArray) {
        try {
            loadProfileImage(imageByteArray);
        } catch (Exception e) {
            Log.e("Exception:", e.toString());
        }
    }

    @Override
    public void showSelectDeviceTypeDialog(SignupResponse responseBody) {
        //showSnackBar(responseBody.getMeta().getMessage(),mCoordinatorLayout);
        Toast.makeText(RegistrationActivity.this, responseBody.getMeta().getMessage(), Toast.LENGTH_LONG).show();
        //showErrorMessage(responseBody.getMeta().getMessage());
        navigateToLogin();
        //AppDialogManager.showDeviceTypeDialog(RegistrationActivity.this, RegistrationActivity.this);
    }

    @Override
    public void setError(EditText editText, String message) {
        editText.setError(message);
    }

    @Override
    public String fcmToken() {
        if (refreshedFCMToken == null) {
            refreshedFCMToken = FirebaseInstanceId.getInstance().getToken();
        }
        return refreshedFCMToken;
    }


    @Override
    public String getFirstName() {
        return etFirstName.getText().toString().trim();
    }

    @Override
    public String getLastName() {
        return etSecondName.getText().toString().trim();
    }

    @Override
    public String getEmailID() {
        return etEmail.getText().toString().trim();
    }

    @Override
    public String getDesingnation() {
        return etDesignation.getText().toString().trim();
    }

    @Override
    public String getMobile() {
        return etMobile.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public String getStaffID() {
        return etStaffID.getText().toString().trim();
    }

    @Override
    public void setUserProfilePicUrl(String profilePicUrl) {
        this.userProfilePictureUrl = profilePicUrl;
    }

    @Override
    public String getUserProfilePicUrl() {
        return userProfilePictureUrl;
    }

    @Override
    public void navigateToDeviceRegistration() {
        mIntent = new Intent(RegistrationActivity.this, PersonalDeviceActivity.class);
        mIntent.putExtra(KEY_DEVICE_TYPE, deviceType);
        startNewActivity(mIntent);
    }

    @Override
    public void navigateToKeyRegistration() {
        mIntent = new Intent(RegistrationActivity.this, RegistrationKeyActivity.class);
        mIntent.putExtra(SCREEN_TYPE, REG_KEY_SCREEN);
        startNewActivity(mIntent);
        finish();
    }
    public void navigateToLogin() {
        mIntent = new Intent(RegistrationActivity.this, SigninActivity.class);
        startNewActivity(mIntent);
        finish();
    }


    @Override
    public void onOKPressed(String status) {
        if (status.equalsIgnoreCase(ALERT_INTENT_GALLERY)) {
            mPresenter.pickFromGalleryClick();
        } else if (status.equalsIgnoreCase(ALERT_INTENT_CAMERA)) {
            mPresenter.snapPhotoClick();
        } else if (status.equalsIgnoreCase(ALERT_REMOVE)) {
            loadProfileImage(null);
        }
    }

    @Override
    public void onCancelPressed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void setError(int resID, String message) {
        EditText editText = (EditText) findViewById(resID);
        editText.setError(message);
    }

    @Override
    public void personalDeviceSelected() {
        if (refreshedFCMToken == null) {
            refreshedFCMToken = FirebaseInstanceId.getInstance().getToken();
        }
        deviceType = 0;
        mPresenter.updateDeviceInfo( String.valueOf(deviceType),refreshedFCMToken);
    }

    @Override
    public void shardDeviceSelected() {
        if (refreshedFCMToken == null) {
            refreshedFCMToken = FirebaseInstanceId.getInstance().getToken();
        }
        deviceType = 1;
        mPresenter.updateDeviceInfo(String.valueOf(deviceType), refreshedFCMToken);
    }
}
