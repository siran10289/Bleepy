package bleepy.pack.com.bleepy.view.welcome;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.models.common.WelcomeScreenResponse;
import bleepy.pack.com.bleepy.view.adapter.WelcomeScreenPagerAdapter;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import bleepy.pack.com.bleepy.view.signin.SigninActivity;
import bleepy.pack.com.bleepy.view.signup.RegistrationActivity;
import butterknife.BindView;
import butterknife.OnClick;

import static bleepy.pack.com.bleepy.utils.Constants.KEY_MY_OBJ;

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.view_pager)ViewPager viewPager;
    @BindView(R.id.layoutDots)TabLayout layoutDots;
    WelcomeScreenPagerAdapter welcomeScreensPagerAdapter;
    List<WelcomeScreenResponse.Screen> mScreenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initLayoutFields();
    }
    private void initLayoutFields(){
        if(getIntent().getExtras()!=null) {
            String json=getIntent().getExtras().getString(KEY_MY_OBJ);
            WelcomeScreenResponse response=new Gson().fromJson(json, WelcomeScreenResponse.class);
            mScreenList=response.getData();
            welcomeScreensPagerAdapter = new WelcomeScreenPagerAdapter(WelcomeActivity.this, mScreenList);
            viewPager.setAdapter(welcomeScreensPagerAdapter);
            layoutDots.setupWithViewPager(viewPager, true);
        }

    }
    @OnClick(R.id.btnGetStarted)
    public void onbtnGetStartedButtonClicked(){
        navigateToRegistration();
    }
    // Making notification bar transparent

    public void navigateToRegistration() {
        startNewActivity(this,SigninActivity.class);
        this.finish();
    }

    @Override
    public void setError(int resID, String message) {

    }
}
