/*
 * Project name        : Rallio
 * File Name           : SplashScreenActivity.java
 * Author              : Siranjeevi R
 * Date of Creation    : 06-12-2015
 * Description         :
 * Version No.         : 2.0.1
 */
package bleepy.pack.com.bleepy.view.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import javax.inject.Inject;

import bleepy.pack.com.bleepy.BleepyApplication;
import bleepy.pack.com.bleepy.MainActivity;
import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.DaggerSignupComponent;
import bleepy.pack.com.bleepy.di.component.SignupComponent;
import bleepy.pack.com.bleepy.di.module.SignupModule;
import bleepy.pack.com.bleepy.models.common.WelcomeScreenResponse;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManagerImpl;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardActivity;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import bleepy.pack.com.bleepy.view.signup.SignupContract;

import static bleepy.pack.com.bleepy.BleepyApplication.refreshedFCMToken;
import static bleepy.pack.com.bleepy.utils.Constants.FCM_BUNDLE;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_CODE_ID;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_IS_VALID_USER;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_MY_OBJ;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_USERID;


public class SplashScreenActivity extends BaseActivity implements SignupContract.SplashScreenView {
    private boolean mIsBackButtonPressed;
    private static final int SPLASH_DURATION = 2000; //2 seconds
    private Intent mIntent;
    @Inject
    SignupContract.Presenter mPresenter;
    private SignupComponent signupComponent;
    PrefsManagerImpl mPrefsManager;
    Bundle mFCMBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();

    }
    private void init(){
        if(getIntent().getExtras()!=null){
            mFCMBundle=getIntent().getExtras();
        }
        refreshedFCMToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("FCMToken:",refreshedFCMToken+"");
        mPrefsManager=PrefsManagerImpl.getInstance(SplashScreenActivity.this);
        initComponent();
        mPresenter.getWelcomeScreens();
    }

    private void initComponent() {
        this.signupComponent = DaggerSignupComponent.builder()
                .applicationComponent(getApplicationComponent())
                .signupModule(new SignupModule(this))
                .build();
        signupComponent.inject(this);
        mPresenter.setSplashScreenView(this);
    }

    //handle back button press
    @Override
    public void onBackPressed() {
        mIsBackButtonPressed = true;
        super.onBackPressed();
    }


    @Override
    public void navigateToWelcomeScreen(WelcomeScreenResponse welcomeScreenResponse) {
        if (mPrefsManager.getBooleanKeyValueFromPrefs(KEY_IS_VALID_USER)) {
            mIntent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
            //mIntent.putExtra(KEY_USERID,mPrefsManager.getIntKeyValueFromPrefsByKey(KEY_USERID));
            if(mFCMBundle!=null&&mFCMBundle.getString(KEY_CODE_ID)!=null) {
                mIntent.putExtra(FCM_BUNDLE, mFCMBundle);
            }
            startActivity(mIntent);
            mFCMBundle=null;
            finish();
        } else {
            mIntent = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
            mIntent.putExtra(KEY_MY_OBJ,new Gson().toJson(welcomeScreenResponse).toString());
            startActivity(mIntent);
            finish();
        }

    }

    @Override
    public String getFCMToken() {
        return BleepyApplication.refreshedFCMToken;
    }

    @Override
    public String locationID() {
        return "1";
    }

    @Override
    public void showLoadErrorDialog() {

    }

    @Override
    public boolean isActivityActive() {
        return false;
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void openActivityForResult(Intent intent, int resultCode) {

    }

    @Override
    public void setResultAndCloseActivity(Intent extraIntent) {

    }

    @Override
    public void showErrorDialog(String errorMsg) {

    }

    @Override
    public void showErrorDialog(int resID) {

    }

    @Override
    public void showListenerDialog(String errorMsg) {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void setError(int resID, String message) {

    }

    @Override
    public void showNoNetworkDialog() {

    }
}