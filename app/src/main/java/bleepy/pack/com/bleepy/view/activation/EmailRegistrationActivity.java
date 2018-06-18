package bleepy.pack.com.bleepy.view.activation;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import bleepy.pack.com.bleepy.view.signup.RegistrationActivity;
import butterknife.BindView;

public class EmailRegistrationActivity extends BaseActivity {
    @BindView(R.id.toolbar)Toolbar mToolBar;
    @BindView(R.id.toolbar_title)TextView toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_registration);
        init();
    }
    @SuppressLint("SetTextI18n")
    private void init() {
        setSupportActionBar(mToolBar);
        setDisplayHomeUp();
        toolbar_title.setText(R.string.email_registration);
    }
}
