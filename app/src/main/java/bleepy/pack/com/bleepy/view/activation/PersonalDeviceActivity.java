package bleepy.pack.com.bleepy.view.activation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.DaggerSignupComponent;
import bleepy.pack.com.bleepy.di.component.SignupComponent;
import bleepy.pack.com.bleepy.di.module.SignupModule;
import bleepy.pack.com.bleepy.models.signup.UpdateRegKeyResponse;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import bleepy.pack.com.bleepy.view.signup.RegistrationActivity;
import bleepy.pack.com.bleepy.view.signup.SignupContract;
import butterknife.BindView;
import butterknife.OnClick;

import static bleepy.pack.com.bleepy.utils.Constants.KEY_DEVICE_TYPE;
import static bleepy.pack.com.bleepy.utils.Constants.REG_KEY_SCREEN;
import static bleepy.pack.com.bleepy.utils.Constants.SCREEN_TYPE;
import static bleepy.pack.com.bleepy.utils.Constants.UNAME_PSWD_SCREEN;

public class PersonalDeviceActivity extends BaseActivity implements SignupContract.UpdateRegKeyView {
    @Inject
    SignupContract.Presenter mPresenter;
    @BindView(R.id.toolbar)Toolbar mToolBar;
    @BindView(R.id.toolbar_title)TextView toolbar_title;
    private SignupComponent signupComponent;
    Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_device);
        init();
    }
    @SuppressLint("SetTextI18n")
    private void init() {
        initComponent();
        setSupportActionBar(mToolBar);
        setDisplayHomeUp();
        if (getIntent().getExtras() != null) {
            int deviceType = getIntent().getExtras().getInt(KEY_DEVICE_TYPE);
            switch (deviceType) {
                case 0:
                    toolbar_title.setText(R.string.title_personal_device);
                    break;
                case 1:
                    toolbar_title.setText(R.string.shared_device);
                    break;
            }
        }

    }
    private void initComponent() {
        this.signupComponent = DaggerSignupComponent.builder()
                .applicationComponent(getApplicationComponent())
                .signupModule(new SignupModule(this))
                .build();
        signupComponent.inject(this);
        mPresenter.setUpdateRegKeyView(this);
    }
    @OnClick({R.id.cvHaveRegKey,R.id.cvHaveUNamePswd,R.id.cvHaveEmail})
    public void onViewClicked(View view) {
         switch (view.getId()){
             case R.id.cvHaveEmail:
                 //mPresenter.haveEmailID("0");
                 mIntent=new Intent(PersonalDeviceActivity.this,EmailRegistrationActivity.class);
                 startNewActivity(mIntent);
                 break;
             case R.id.cvHaveRegKey:
                 mIntent=new Intent(PersonalDeviceActivity.this,RegistrationKeyActivity.class);
                 mIntent.putExtra(SCREEN_TYPE,REG_KEY_SCREEN);
                 startNewActivity(mIntent);
                 break;
             case R.id.cvHaveUNamePswd:
                 mIntent=new Intent(PersonalDeviceActivity.this,RegistrationKeyActivity.class);
                 mIntent.putExtra(SCREEN_TYPE,UNAME_PSWD_SCREEN);
                 startNewActivity(mIntent);
                 break;
         }

    }


    @Override
    public void navigateEmailRegistration(UpdateRegKeyResponse response) {
        mIntent=new Intent(PersonalDeviceActivity.this,EmailRegistrationActivity.class);
        startNewActivity(mIntent);
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public String getRegKey() {
        return null;
    }

    @Override
    public String userName() {
        return null;
    }

    @Override
    public String password() {
        return null;
    }


}
