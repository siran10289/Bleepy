package bleepy.pack.com.bleepy.view.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;


import javax.inject.Inject;

import bleepy.pack.com.bleepy.BleepyApplication;
import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.ApplicationComponent;
import bleepy.pack.com.bleepy.di.module.ActivityModule;
import bleepy.pack.com.bleepy.interactor.ApiInteractor;
import bleepy.pack.com.bleepy.models.callforhelp.CodeConfirmationResponse;
import bleepy.pack.com.bleepy.models.callforhelp.CodeCreationResponse;
import bleepy.pack.com.bleepy.models.callforhelp.CodeInformationResponse;
import bleepy.pack.com.bleepy.models.callforhelp.EmergencyAlertResponse;
import bleepy.pack.com.bleepy.models.common.EmergencyCode;
import bleepy.pack.com.bleepy.utils.AppUtils;
import bleepy.pack.com.bleepy.utils.customdialog.AppDialogManager;
import bleepy.pack.com.bleepy.utils.customdialog.CustomProgressDialog;
import bleepy.pack.com.bleepy.utils.customdialog.DialogListener;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.signup.PackageInfoInteractor;
import butterknife.ButterKnife;

import static bleepy.pack.com.bleepy.utils.Constants.ACTION_INTENT_FCM_RECIEVED;
import static bleepy.pack.com.bleepy.utils.Constants.DELAY_MILLISECONDS;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_CODE_CREATED;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_CODE_ID;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_DESCRIPTION;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_LOCATION;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_NOTIFICATION_TYPE;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_RESPONDERS;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_VOICE_DATA;
import static bleepy.pack.com.bleepy.utils.Constants.STATUS_FAILURE;
import static bleepy.pack.com.bleepy.utils.Constants.STATUS_SUCCESS;
import static bleepy.pack.com.bleepy.utils.customdialog.AppDialogManager.confirmationDialog;
import static bleepy.pack.com.bleepy.utils.customdialog.AppDialogManager.showCustomDialog;
import static bleepy.pack.com.bleepy.utils.customdialog.AppDialogManager.showWaitingAcceptanceDialog;


/**
 * Base {@link Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView,
        DialogListener.NetworkDialogListener,DialogListener.BaseListener,BaseContract.EmergencyAlertView {
  ProgressDialog progressDialog;
  public FragmentManager supportFragmentManager;
  FragmentTransaction fragmentTransaction;
  public long lastPressedTime;
  public final int _period = 2000;
  boolean doubleBackToExitPressedOnce = false;
  @Inject
  PrefsManager mPrefsManager;
  @Inject
  ApiInteractor mApiInteractor;
  @Inject
  PackageInfoInteractor mPackageInfoInteractor;
  BaseContract.Presenter mPresenter;
  EmergencyCode mEmergencyCode;
  String userStatus;
  public BleepyApplication mBleepyApplication;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBleepyApplication = (BleepyApplication) this.getApplicationContext();
    initLocalBrodCast();
    this.getApplicationComponent().inject(this);
    supportFragmentManager=this.getSupportFragmentManager();
    mPresenter=new BasePresenterImpl(BaseActivity.this,mApiInteractor,mPrefsManager,mPackageInfoInteractor);
    mPresenter.setEmergencyAlertView(BaseActivity.this);
    mPresenter.setView(this);


  }
  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
    progressDialog = CustomProgressDialog.getInstance(this);

  }



  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
  public void startNewActivity(Intent intent) {
    startActivity(intent);
    overridePendingTransition(R.anim.anim_right_left_enter, R.anim.anim_right_left_exit);
  }

  protected void replaceFragment(int containerViewId, Fragment fragment) {
    fragmentTransaction = supportFragmentManager.beginTransaction();
    fragmentTransaction.replace(containerViewId, fragment);
    fragmentTransaction.commit();
  }
  public void addFragment(Fragment fragment,int container) {
    fragmentTransaction= supportFragmentManager.beginTransaction();
    fragmentTransaction.setCustomAnimations(R.anim.anim_right_left_enter, R.anim.anim_right_left_exit,R.anim.animation_left_right_enter,R.anim.animation_left_right_exit);
    fragmentTransaction.add(container, fragment,fragment.getClass().getCanonicalName()).addToBackStack(fragment.getClass().getCanonicalName());
    fragmentTransaction.commit();
  }
  public void clearBackStack(){
    if(supportFragmentManager.getBackStackEntryCount()>0) {
      supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((BleepyApplication) getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(newBase);
    MultiDex.install(this);
  }
  public void startNewActivity(Context sourceClass,Class destinationClass){
      Intent intent=new Intent(sourceClass,destinationClass);
      startActivity(intent);
      overridePendingTransition(R.anim.anim_right_left_enter, R.anim.anim_right_left_exit);
  }

  public void clearAndStartNewActivity(Context sourceClass,Class destinationClass){
    Intent intent=new Intent(sourceClass,destinationClass);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    finish();
    overridePendingTransition(R.anim.anim_right_left_enter, R.anim.anim_right_left_exit);
  }
    public boolean shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canback = supportFragmentManager.getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
        return canback;
    }
    public void setDisplayHomeUp(){
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowTitleEnabled(false);
      //getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }
  @Override
  public void showProgress() {
    if (progressDialog != null) {
      progressDialog.show();
    }
  }

  @Override
  public void hideProgress() {

    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }
  public void showTempProgress(){
    if (progressDialog != null) {
      progressDialog.show();
    }
  }
  public void hideTempProgress(){
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }
  @Override
  public void showLoadErrorDialog() {

  }

  @Override
  public boolean isActivityActive() {
    return false;
  }

  @Override
  public void openActivityForResult(Intent intent, int resultCode) {
    startActivityForResult(intent, resultCode);
  }

  @Override
  public void setResultAndCloseActivity(Intent extraIntent) {

  }

  @Override
  public void showErrorDialog(String errorMsg) {
    if(!isFinishing()) {
      AppDialogManager.showAlertDialog(BaseActivity.this, errorMsg, "ERROR", false);
    }
  }

  @Override
  public void showErrorDialog(int resID) {
    AppDialogManager.showAlertDialog(BaseActivity.this,getString(resID),"ERROR",false);

  }
  public void showSuccessDialog(String title,String message){
    showCustomDialog(BaseActivity.this,title,message);
  }

  @Override
  public void showNoNetworkDialog() {
    AppDialogManager.showNetWorkStatusAlertDialog(BaseActivity.this,this);
  }

  @Override
  public void showListenerDialog(String errorMsg) {

  }

  @Override
  public void showErrorMessage(String message, CoordinatorLayout coordinatorLayout) {
    Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);
    snackbar.show();
  }
  @Override
  public void showErrorMessage(String message) {
    Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
  }
  public  void showSnackBar(String message, CoordinatorLayout coordinatorLayout){
    AppUtils.showSnackBar(BaseActivity.this,message,coordinatorLayout);
  }
  @Override
  public void onOKPressed() {

  }

  @Override
  public void onSettingsPressed() {
    Intent intent=new Intent(Settings.ACTION_SETTINGS);
    startNewActivity(intent);
  }
  @Override
  public void setError(int resID, String message) {

  }
  private void initLocalBrodCast() {
    IntentFilter filter = new IntentFilter();
    filter.addAction(ACTION_INTENT_FCM_RECIEVED);
    LocalBroadcastManager.getInstance(BaseActivity.this).registerReceiver(receiver, filter);

  }
  private void unRegisterLocalBroadCast() {
    LocalBroadcastManager.getInstance(BaseActivity.this).unregisterReceiver(receiver);
  }
  private final BroadcastReceiver receiver = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      if (ACTION_INTENT_FCM_RECIEVED.equals(intent.getAction())) {
        Bundle bundle=intent.getExtras();
        if(bundle.getString(KEY_NOTIFICATION_TYPE)!=null) {
          switch (bundle.getString(KEY_NOTIFICATION_TYPE)) {
            case "0":
              EmergencyCode emergencyCode = new EmergencyCode();
              emergencyCode.setCodeID(bundle.getString(KEY_CODE_ID));
              emergencyCode.setCodeCreatedDate(bundle.getString(KEY_CODE_CREATED));
              emergencyCode.setDescription(bundle.getString(KEY_DESCRIPTION));
              emergencyCode.setVoiceData(bundle.getString(KEY_VOICE_DATA));
              emergencyCode.setLocation(bundle.getString(KEY_LOCATION));
              emergencyCode.setResponseCount(bundle.getString(KEY_RESPONDERS));
              mEmergencyCode = emergencyCode;
              openWaitingAccptanceDialog(emergencyCode);
              break;
            case "4":
              openCodeRejectionDialog("Code " + bundle.getString(KEY_CODE_ID) + " has been returned check the agent's comments as emergency code log, Please send new code");
              break;
          }
        }

      }
    }
  };
  public void openWaitingAccptanceDialog(EmergencyCode emergencyCode){
      showWaitingAcceptanceDialog(BaseActivity.this,BaseActivity.this,emergencyCode);
  }
  public void openCodeRejectionDialog(String message){
      showCustomDialog(BaseActivity.this,"Verification failed",message);
  }
  public void showConfirmationDialog(CodeInformationResponse responseBody){

    CodeInformationResponse.Data data=responseBody.getData();
    if(data!=null) {
      if (data.getCodeStatus().equalsIgnoreCase("0") || data.getCodeStatus().equalsIgnoreCase("1")) {
        if(confirmationDialog!=null){
          confirmationDialog.cancel();
            AppDialogManager.showConfirmationDialog(mBleepyApplication.getCurrentActivity(), BaseActivity.this);

        }else {
          /*if(!isFinishing()) {
            AppDialogManager.showConfirmationDialog(BaseActivity.this, BaseActivity.this);
          }*/

            AppDialogManager.showConfirmationDialog(mBleepyApplication.getCurrentActivity(), BaseActivity.this);

        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            mPresenter.getCodeInformation(data.getCode());
          }
        }, DELAY_MILLISECONDS);
      }else{
        if(confirmationDialog!=null) {
          if (confirmationDialog.isShowing()) {
            confirmationDialog.dismiss();
          }
        }
      }
    }
  }
  @Override
  public void onAcceptClicked(EmergencyCode emergencyCode) {
    this.mEmergencyCode=emergencyCode;
    userStatus="1";
    mPresenter.emergencyAccept();
  }
  @Override
  public void onRejectClicked(EmergencyCode emergencyCode){
    this.mEmergencyCode=emergencyCode;
    userStatus="0";
    AppDialogManager.showRejectionReasonDialog(BaseActivity.this,this,mEmergencyCode);
  }
  public void onRejectReasonOKClicked(String reason){
    userStatus="0";
    mPresenter.emergencyReject(reason);
  }
  public void confirmClicked(){
    mPresenter.codeConfirmation(getCodeID(),"2");
  }
  public void CancelClicked(){
    //mPresenter.codeConfirmation(getCodeID(),"3");
  }
  @Override
  public String getCodeID() {
    return mEmergencyCode.getCodeID();
  }

  @Override
  public String getUserStatus() {
    return userStatus;
  }
  public void setEmergencyAlertResponse(EmergencyAlertResponse responseBody){
    if (responseBody != null) {
      switch (responseBody.getMeta().getStatusType()) {
        case STATUS_SUCCESS:
          final Handler handler = new Handler();
          handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              mPresenter.getCodeInformation(responseBody.getData().getCode());
            }
          }, DELAY_MILLISECONDS);
          break;
        case STATUS_FAILURE:
          showErrorDialog(responseBody.getMeta().getMessage());
          break;
      }

    }
  }
  public void setConfirmationResonse(CodeConfirmationResponse responseBody){
    switch (responseBody.getMeta().getStatusType()) {
      case STATUS_SUCCESS:
        if(confirmationDialog!=null) {
          if (confirmationDialog.isShowing()) {
            confirmationDialog.dismiss();
          }
        }
        break;
      case STATUS_FAILURE:
        showErrorDialog(responseBody.getMeta().getMessage());
        break;
    }
  }
  @Override
  protected void onDestroy() {
    super.onDestroy();
    unRegisterLocalBroadCast();
  }

  @Override
  protected void onStop() {
    super.onStop();
  }
}
