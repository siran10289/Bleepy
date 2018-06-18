package bleepy.pack.com.bleepy.view.activation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.DaggerSignupComponent;
import bleepy.pack.com.bleepy.di.component.SignupComponent;
import bleepy.pack.com.bleepy.di.module.SignupModule;
import bleepy.pack.com.bleepy.models.signup.UpdateRegKeyResponse;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardActivity;
import bleepy.pack.com.bleepy.view.HomeActivity;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import bleepy.pack.com.bleepy.view.signup.SignupContract;
import butterknife.BindView;
import butterknife.OnClick;

import static bleepy.pack.com.bleepy.utils.Constants.KEY_USERID;
import static bleepy.pack.com.bleepy.utils.Constants.REG_KEY_SCREEN;
import static bleepy.pack.com.bleepy.utils.Constants.SCREEN_TYPE;
import static bleepy.pack.com.bleepy.utils.Constants.UNAME_PSWD_SCREEN;

public class RegistrationKeyActivity extends BaseActivity implements SignupContract.UpdateRegKeyView {
    @Inject
    SignupContract.Presenter mPresenter;
    @BindView(R.id.toolbar)Toolbar mToolBar;
    @BindView(R.id.toolbar_title)TextView toolbar_title;
    @BindView(R.id.etServerName)EditText etServerName;
    @BindView(R.id.etRegkey)EditText etRegkey;
    @BindView(R.id.etUserName)EditText etUserName;
    @BindView(R.id.etPassword)EditText etPassword;
    private SignupComponent signupComponent;
    String screenType;
    Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_key);
        init();
    }
    @SuppressLint("SetTextI18n")
    private void init() {
        initComponent();
        setSupportActionBar(mToolBar);
        setDisplayHomeUp();

        if(getIntent().getExtras()!=null){
            screenType=getIntent().getExtras().getString(SCREEN_TYPE);
           switch (screenType){
               case REG_KEY_SCREEN:
                   toolbar_title.setText(R.string.have_reg_key_title);
                   etUserName.setVisibility(View.GONE);
                   etPassword.setVisibility(View.GONE);
                   break;
               case UNAME_PSWD_SCREEN:
                   toolbar_title.setText(R.string.title_uname_password);
                   etRegkey.setVisibility(View.GONE);
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
    @OnClick(R.id.btnRegister)
    public void onRegBtnClicked(){
        switch (screenType){
            case REG_KEY_SCREEN:
               mPresenter.hasRegistrationKey();
                break;
            case UNAME_PSWD_SCREEN:
                mPresenter.hasUnamePswd();
                break;
        }
    }

    @Override
    public void navigateEmailRegistration(UpdateRegKeyResponse response) {
        mIntent=new Intent(RegistrationKeyActivity.this,DashboardActivity.class);
        mIntent.putExtra(KEY_USERID,response.getData().getUserid());
        startNewActivity(mIntent);
        this.finish();
    }

    @Override
    public String getServerName() {
        return etServerName.getText().toString().trim();
    }

    @Override
    public String getRegKey() {
        return etRegkey.getText().toString().trim();
    }

    @Override
    public String userName() {
        return etUserName.getText().toString().trim();
    }

    @Override
    public String password() {
        return etPassword.getText().toString().trim();
    }


}
