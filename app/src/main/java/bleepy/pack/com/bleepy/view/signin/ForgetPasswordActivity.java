package bleepy.pack.com.bleepy.view.signin;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.TextView;


import javax.inject.Inject;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.SigninComponent;
import bleepy.pack.com.bleepy.di.module.SigninModule;
import bleepy.pack.com.bleepy.utils.AppUtils;
import bleepy.pack.com.bleepy.utils.customdialog.DialogListener;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;



public class ForgetPasswordActivity extends BaseActivity /*implements SigninContract.ForgotPasswordView,DialogListener.AlertDialogListener*/{


   /* @BindView(R.id.toolbar)Toolbar mToolBar;
    @BindView(R.id.toolbar_title)TextView toolbar_title;
    @BindView(R.id.coordinatorLayout)CoordinatorLayout coordinatorLayout;
    @BindView(R.id.etCountryCode)EditText etCountryCode;
    @BindView(R.id.etPhoneNumber)EditText etPhoneNumber;
    @BindView(R.id.etEmailAddress)EditText etEmailAddress;
    SigninComponent signinComponent;
    @Inject
    SigninContract.Presenter mPresenter;
    private SmsReceiver smsReceiver = new SmsReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        init();
    }
    private void init(){
        setSupportActionBar(mToolBar);
        setDisplayHomeUp();
        initComponent();
        checkSMSPermission();
        mPresenter.setForgetPasswordView(this);
        toolbar_title.setText(R.string.title_forgot_up);
        initCountryCode();
    }
    private void initComponent() {
        this.signinComponent = DaggerSigninComponent.builder()
                .applicationComponent(getApplicationComponent())
                .signinModule(new SigninModule(this))
                .build();
        signinComponent.inject(this);
    }
    private void initCountryCode(){
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        etCountryCode.setText(PhoneUtils.getDialCode(telephonyManager.getSimCountryIso()));
        etCountryCode.setSelection(etCountryCode.getText().length());
    }
    private void checkSMSPermission() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }
    @OnClick(R.id.btnResetPassword)
    public void onResetPswdBtnClicked(){
        mPresenter.forgotUserNamePassword(etCountryCode.getText().toString(),etPhoneNumber.getText().toString(),etEmailAddress.getText().toString(),null);
    }


    @Override
    public void setErrorMessage(String message) {
        AppUtils.showSnackBarWithAction(ForgetPasswordActivity.this,message,coordinatorLayout);

    }

    @Override
    public void setForgetUnamePswdStatus(SignupResponse signupResponse) {
        if (signupResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
            AppDialogManager.showPinValidationDialog(ForgetPasswordActivity.this,getString(R.string.lbl_enter_otp),getString(R.string.msg_otp_validation), this);

        } else if (signupResponse.getStatus().equalsIgnoreCase(STATUS_FAILURE)) {
            showSnackBar(signupResponse.getDescription(), coordinatorLayout);
        }
    }

    @Override
    public void setValidateOTPStatus(SignupResponse signupResponse) {
        if (signupResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
            AppDialogManager.showStatusDialog(ForgetPasswordActivity.this, this,getString(R.string.thank_you),signupResponse.getDescription(),R.drawable.ic_sent,null);
        } else if (signupResponse.getStatus().equalsIgnoreCase(STATUS_FAILURE)) {
            showSnackBar(signupResponse.getDescription(), coordinatorLayout);
            AppDialogManager.showPinValidationDialog(ForgetPasswordActivity.this,getString(R.string.lbl_enter_otp),getString(R.string.msg_otp_validation), this);
        }

    }

    private void initLocalBrodCast() {
        IntentFilter filter = new IntentFilter(ACTION_SMS_RECIEVED);
        registerReceiver(smsReceiver, filter);
    }

    private void unRegisterLocalBroadCast() {
        unregisterReceiver(smsReceiver); 
    }


    @Override
    public void onConfirm(String otp) {
        mPresenter.forgotUserNamePassword(etCountryCode.getText().toString(),etPhoneNumber.getText().toString(),etEmailAddress.getText().toString(),otp);

    }

    @Override
    public void onResendClicked() {
        mPresenter.forgotUserNamePassword(etCountryCode.getText().toString(),etPhoneNumber.getText().toString(),etEmailAddress.getText().toString(),null);

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onOKPressed(String status) {
        onBackPressed();
    }

    @Override
    public void onCancelPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        initLocalBrodCast();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unRegisterLocalBroadCast();
    }*/
   @Override
   public void setError(int resID, String message) {
       EditText editText=(EditText)findViewById(resID);
       editText.setError(message);

   }


}
