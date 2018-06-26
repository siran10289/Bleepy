package bleepy.pack.com.bleepy.view.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.iid.FirebaseInstanceId;

import javax.inject.Inject;

import bleepy.pack.com.bleepy.MainActivity;
import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.DaggerSigninComponent;
import bleepy.pack.com.bleepy.di.component.SigninComponent;
import bleepy.pack.com.bleepy.di.module.SigninModule;
import bleepy.pack.com.bleepy.models.signin.SigninResponse;
import bleepy.pack.com.bleepy.utils.AppUtils;
import bleepy.pack.com.bleepy.utils.customdialog.AppDialogManager;
import bleepy.pack.com.bleepy.utils.customdialog.DialogListener;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardActivity;
import bleepy.pack.com.bleepy.view.HomeActivity;
import bleepy.pack.com.bleepy.view.activation.PersonalDeviceActivity;
import bleepy.pack.com.bleepy.view.activation.RegistrationKeyActivity;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import bleepy.pack.com.bleepy.view.signup.RegistrationActivity;
import bleepy.pack.com.bleepy.view.signup.SignupContract;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static bleepy.pack.com.bleepy.BleepyApplication.refreshedFCMToken;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_DEVICE_TYPE;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_USERID;
import static bleepy.pack.com.bleepy.utils.Constants.REG_KEY_SCREEN;
import static bleepy.pack.com.bleepy.utils.Constants.SCREEN_TYPE;


public class SigninActivity extends BaseActivity implements
        SigninContract.SigninView, DialogListener.SelectDeviceLisenter {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.cbRememberMe)
    CheckBox cbRememberMe;
    SigninComponent signinComponent;
    @Inject
    SigninContract.Presenter mPresenter;
    Intent mIntent;
    int deviceType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        init();
    }

    private void init() {
        initComponent();
        mPresenter.setSigninView(this);
        checkOldCredentials();

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    onSigninClicked();
                    return false;
                }
                return false;
            }
        });

        //setSignUpLink();
    }

    @OnCheckedChanged(R.id.cbRememberMe)
    void onGenderSelected(CompoundButton button, boolean checked) {
        if (checked) {
            mPresenter.saveCredentials(etUserName.getText().toString(), etPassword.getText().toString());
        }
    }

    private void checkOldCredentials() {
        mPresenter.checkOldCredentials(etUserName, etPassword);
    }

    private void initComponent() {

        this.signinComponent = DaggerSigninComponent.builder()
                .applicationComponent(getApplicationComponent())
                .signinModule(new SigninModule(this))
                .build();
        signinComponent.inject(this);
    }

    @OnClick(R.id.tvForgetUNamePswd)
    public void onNavigateToForgetPasswordActivity() {
        startNewActivity(SigninActivity.this, ForgetCredentialsActivity.class);
    }

    @OnClick(R.id.btnLogin)
    public void onSigninClicked() {
        doSignin();
    }

    @OnClick(R.id.llSignup)
    public void signupClicked() {
        Intent mIntent=new Intent(SigninActivity.this, RegistrationActivity.class);
        startNewActivity(mIntent);

    }

    private void doSignin() {
        mPresenter.validateUserLogin(etUserName.getText().toString(), etPassword.getText().toString());
    }

    @Override
    public void setError(int resID, String message) {
        EditText editText = (EditText) findViewById(resID);
        editText.setError(message);
    }
    @Override
    public void openDeviceRegistrationDialog(SigninResponse response) {
     /* Intent intent=new Intent(SigninActivity.this, DashboardActivity.class);
      startNewActivity(intent);*/
        AppDialogManager.showDeviceTypeDialog(SigninActivity.this, SigninActivity.this);
    }

    @Override
    public void navigateToDeviceRegistration() {
        mIntent = new Intent(SigninActivity.this, PersonalDeviceActivity.class);
        mIntent.putExtra(KEY_DEVICE_TYPE, deviceType);
        startNewActivity(mIntent);
    }

    @Override
    public void navigateToKeyRegistration() {
        mIntent = new Intent(SigninActivity.this, RegistrationKeyActivity.class);
        mIntent.putExtra(SCREEN_TYPE, REG_KEY_SCREEN);
        startNewActivity(mIntent);
        finish();
    }

    @Override
    public void navigateToDashBoard(String message,String userID) {
        showErrorMessage(message);
        //showErrorDialog(message);
        mIntent = new Intent(SigninActivity.this, DashboardActivity.class);
        mIntent.putExtra(KEY_USERID, Integer.parseInt(userID));
        startNewActivity(mIntent);
        finish();

    }


    @Override
    public void personalDeviceSelected() {
        if (refreshedFCMToken == null) {
            refreshedFCMToken = FirebaseInstanceId.getInstance().getToken();
        }
        deviceType = 0;
        mPresenter.updateDeviceInfo(refreshedFCMToken, String.valueOf(deviceType));
    }

    @Override
    public void shardDeviceSelected() {
        if (refreshedFCMToken == null) {
            refreshedFCMToken = FirebaseInstanceId.getInstance().getToken();
        }
        deviceType = 1;
        mPresenter.updateDeviceInfo(refreshedFCMToken,String.valueOf(deviceType));
    }
}
