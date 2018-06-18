package bleepy.pack.com.bleepy.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.view.base.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
