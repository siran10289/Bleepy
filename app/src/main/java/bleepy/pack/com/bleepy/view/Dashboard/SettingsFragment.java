package bleepy.pack.com.bleepy.view.Dashboard;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import javax.inject.Inject;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.DaggerDashboardComponent;
import bleepy.pack.com.bleepy.di.component.DashboardComponent;
import bleepy.pack.com.bleepy.di.module.DashboardModule;
import bleepy.pack.com.bleepy.view.base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsFragment extends BaseFragment implements DashboardContract.SettingsView{
    @Inject
    DashboardContract.Presenter mPresenter;
    DashboardComponent mDashboardComponent;
    private View mRootView;
    @BindView(R.id.etCurrentPassword)EditText etCurrentPassword;
    @BindView(R.id.etNewPassword)EditText etNewPassword;
    @BindView(R.id.etConfirmPassword)EditText etConfirmPassword;
    public SettingsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }
    private void init(){
        initComponent();
    }
    private void initComponent() {
        this.mDashboardComponent = DaggerDashboardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .dashboardModule(new DashboardModule(getActivity()))
                .build();
        mDashboardComponent.inject(this);
        mPresenter.setSettingsView(this);
    }
    @OnClick(R.id.btnSave)
    public void onSaveClicked(){
        mPresenter.changePassword();
    }


    @Override
    public String getCurrentPassword() {
        return etCurrentPassword.getText().toString().trim();
    }

    @Override
    public String getNewPassword() {
        return etNewPassword.getText().toString().trim();
    }

    @Override
    public String getConfirmPassword() {
        return etConfirmPassword.getText().toString().trim();
    }


}
