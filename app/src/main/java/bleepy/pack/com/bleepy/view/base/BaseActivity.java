package bleepy.pack.com.bleepy.view.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;


import bleepy.pack.com.bleepy.BleepyApplication;
import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.ApplicationComponent;
import bleepy.pack.com.bleepy.di.module.ActivityModule;
import bleepy.pack.com.bleepy.utils.AppUtils;
import bleepy.pack.com.bleepy.utils.customdialog.AppDialogManager;
import bleepy.pack.com.bleepy.utils.customdialog.CustomProgressDialog;
import bleepy.pack.com.bleepy.utils.customdialog.DialogListener;
import butterknife.ButterKnife;


/**
 * Base {@link Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView,DialogListener.NetworkDialogListener {
  ProgressDialog progressDialog;
  public FragmentManager supportFragmentManager;
  FragmentTransaction fragmentTransaction;
  public long lastPressedTime;
  public final int _period = 2000;
  boolean doubleBackToExitPressedOnce = false;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
    supportFragmentManager=this.getSupportFragmentManager();
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
    AppDialogManager.showAlertDialog(BaseActivity.this,errorMsg,"ERROR",false);
  }

  @Override
  public void showErrorDialog(int resID) {
    AppDialogManager.showAlertDialog(BaseActivity.this,getString(resID),"ERROR",false);

  }

  @Override
  public void showNoNetworkDialog() {
    AppDialogManager.showNetWorkStatusAlertDialog(BaseActivity.this,this);
  }

  @Override
  public void showListenerDialog(String errorMsg) {

  }

  @Override
  public void showErrorMessage(String message) {
    Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
  }
  public static void showSnackBar(String message, CoordinatorLayout coordinatorLayout){
    Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);
    snackbar.show();
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

  @Override
  protected void onResume() {
    super.onResume();
  }
}
