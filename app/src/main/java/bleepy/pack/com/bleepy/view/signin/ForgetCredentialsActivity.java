package bleepy.pack.com.bleepy.view.signin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import javax.inject.Inject;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.DaggerSigninComponent;
import bleepy.pack.com.bleepy.di.component.DaggerSignupComponent;
import bleepy.pack.com.bleepy.di.component.SigninComponent;
import bleepy.pack.com.bleepy.di.component.SignupComponent;
import bleepy.pack.com.bleepy.di.module.SigninModule;
import bleepy.pack.com.bleepy.di.module.SignupModule;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import bleepy.pack.com.bleepy.view.signup.SignupContract;
import butterknife.BindView;
import butterknife.OnClick;

public class ForgetCredentialsActivity extends BaseActivity implements SigninContract.ForgotPasswordView {
    @Inject
    SigninContract.Presenter mPresenter;
    private SigninComponent signinComponent;
    @BindView(R.id.rbForgetUN)RadioButton rbForgetUN;
    @BindView(R.id.rbForgetPswd)RadioButton rbForgetPswd;
    @BindView(R.id.etEmail)EditText etEmail;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_credentials);
        initComponent();
    }
    private void initComponent() {

        this.signinComponent = DaggerSigninComponent.builder()
                .applicationComponent(getApplicationComponent())
                .signinModule(new SigninModule(this))
                .build();
        signinComponent.inject(this);
        mPresenter.setForgetPasswordView(this);
    }
    @OnClick({R.id.rbForgetUN, R.id.rbForgetPswd,R.id.btnSubmit})
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?


        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbForgetUN:
                if (rbForgetUN.isChecked()) {
                    rbForgetUN.setTextColor(getColor(R.color.white));
                    rbForgetPswd.setTextColor(getColor(R.color.light_lavendor_color));
                    type=0;

                }
                break;
            case R.id.rbForgetPswd:
                if (rbForgetPswd.isChecked()) {
                    rbForgetUN.setTextColor(getColor(R.color.light_lavendor_color));
                    rbForgetPswd.setTextColor(getColor(R.color.white));
                    type=1;

                }
                break;
            case R.id.btnSubmit:
                mPresenter.onForgetCredentials(type);
                break;
        }
    }


    @Override
    public String getEmailID() {
        return etEmail.getText().toString().trim();
    }
}
