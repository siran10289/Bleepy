/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package bleepy.pack.com.bleepy.view.base;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bleepy.pack.com.bleepy.BleepyApplication;
import bleepy.pack.com.bleepy.MainActivity;
import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.HasComponent;
import bleepy.pack.com.bleepy.di.component.ApplicationComponent;
import bleepy.pack.com.bleepy.utils.AppUtils;
import bleepy.pack.com.bleepy.utils.customdialog.AppDialogManager;
import bleepy.pack.com.bleepy.utils.customdialog.CustomProgressDialog;
import bleepy.pack.com.bleepy.utils.customdialog.DialogListener;


/**
 * Base {@link Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment implements BaseView,DialogListener.NetworkDialogListener  {
    /**
     * Shows a {@link Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    ProgressDialog progressDialog;
    private boolean isActive = true;
    public TextView tvTitle;
    public CoordinatorLayout mCoordinatorLayout;
    public FragmentManager mFragmentManager;

    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.progressDialog = CustomProgressDialog.getInstance(getActivity());


    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
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
    @Override
    public void showLoadErrorDialog() {

    }

    @Override
    public void openActivityForResult(Intent intent, int resultCode) {

    }

    @Override
    public void setResultAndCloseActivity(Intent extraIntent) {

    }

    @Override
    public void showErrorDialog(String errorMsg) {
        AppDialogManager.showAlertDialog(getActivity(),errorMsg,"ERROR",false);
    }

    @Override
    public void showErrorDialog(int resID) {

    }

    @Override
    public void showListenerDialog(String errorMsg) {

    }
    @Override
    public void showErrorMessage(String message, CoordinatorLayout coordinatorLayout) {

    }
    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setError(int resID, String message) {

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
    public boolean isActivityActive() {
        try {
            return isActive && !getActivity().isFinishing();
        }catch (Exception e){
            return isActive;
        }
    }


    protected ApplicationComponent getApplicationComponent() {
        return ((BleepyApplication) getActivity().getApplication()).getApplicationComponent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void showNoNetworkDialog() {
        AppDialogManager.showNetWorkStatusAlertDialog(getActivity(),this);
    }
    public void startNewActivity(Intent intent){
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_right_left_enter, R.anim.anim_right_left_exit);
    }
    public void startActivityForResult(Intent intent,int requestCode){
        getActivity().startActivityForResult(intent,requestCode);
    }

}
