package bleepy.pack.com.bleepy.view.Dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bvapp.arcmenulibrary.ArcMenu;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.DaggerDashboardComponent;
import bleepy.pack.com.bleepy.di.component.DashboardComponent;
import bleepy.pack.com.bleepy.di.module.DashboardModule;
import bleepy.pack.com.bleepy.models.common.EmergencyCode;
import bleepy.pack.com.bleepy.models.common.MenuItem;
import bleepy.pack.com.bleepy.models.dashboard.DashboardInfoResponse;
import bleepy.pack.com.bleepy.utils.customdialog.DialogListener;
import bleepy.pack.com.bleepy.view.adapter.NavDrawerAdapter;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import bleepy.pack.com.bleepy.view.callforhelp.CallForHelpActivity;
import bleepy.pack.com.bleepy.view.signin.SigninActivity;
import bleepy.pack.com.bleepy.view.team.GroupMembersActivity;
import butterknife.BindView;
import butterknife.OnClick;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

import static bleepy.pack.com.bleepy.utils.Constants.FCM_BUNDLE;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_CODE_CREATED;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_CODE_ID;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_DESCRIPTION;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_LOCATION;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_NOTI_TYPE;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_RESPONDERS;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_USERID;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_VOICE_DATA;
import static bleepy.pack.com.bleepy.utils.customdialog.AppDialogManager.showWaitingAcceptanceDialog;


public class DashboardActivity extends BaseActivity
        implements DuoMenuView.OnMenuClickListener,DashboardContract.DashboardView,DialogListener.DashBoardListener {
    @Inject
    DashboardContract.Presenter mPresenter;
    DashboardComponent mDashboardComponent;
    private NavDrawerAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    private ArrayList<String> mTitles = new ArrayList<>();
    private TypedArray mTypedImageArray;
    @BindView(R.id.toolbar)Toolbar mToolBar;
    @BindView(R.id.toolbar_title)TextView toolbar_title;
    @BindView(R.id.drawer)DuoDrawerLayout drawerLayout;
    @BindView(R.id.arcMenu)ArcMenu mArcMenu;
    @BindView(R.id.llArcMenu)LinearLayout llArcMenu;
    @BindView(R.id.ivProfileImage)ImageView ivProfileImage;
    @BindView(R.id.duo_view_header_text_title)TextView tvUserName;
    @BindView(R.id.duo_view_header_text_sub_title)TextView tvDesingnation;
    @BindView(R.id.tvSignOut)TextView tvSignOut;
    @BindView(R.id.ivArcMenu)ImageView ivArcMenu;
    Menu mMenu;
    //int userID;
    Intent mIntent;
    DashboardInfoResponse dashboardInfo;
    DuoDrawerToggle duoDrawerToggle;
    @BindView(R.id.containerShadow)FrameLayout containerShadow;
    Bundle mFcmBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        if(getIntent()!=null&&getIntent().getExtras()!=null){
                //showWaitingAcceptanceDialog1(DashboardActivity.this,DashboardActivity.this);
                Bundle bundle=getIntent().getBundleExtra(FCM_BUNDLE);
                if(bundle!=null) {
                    EmergencyCode emergencyCode = new EmergencyCode();
                    emergencyCode.setCodeID(bundle.getString(KEY_CODE_ID));
                    emergencyCode.setCodeCreatedDate(bundle.getString(KEY_CODE_CREATED));
                    emergencyCode.setDescription(bundle.getString(KEY_DESCRIPTION));
                    emergencyCode.setVoiceData(bundle.getString(KEY_VOICE_DATA));
                    emergencyCode.setLocation(bundle.getString(KEY_LOCATION));
                    emergencyCode.setLocation(bundle.getString(KEY_RESPONDERS));
                    openWaitingAccptanceDialog(emergencyCode);
                }

        }
        init();
    }
    /*@Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(getIntent().getExtras()!=null){
            if(getIntent().getExtras().getBoolean(KEY_NOTI_TYPE)){
                //showWaitingAcceptanceDialog1(DashboardActivity.this,DashboardActivity.this);
                Bundle bundle=intent.getExtras();
                EmergencyCode emergencyCode=new EmergencyCode();
                emergencyCode.setCodeID(bundle.getString(KEY_CODE_ID));
                emergencyCode.setCodeCreatedDate(bundle.getString(KEY_CODE_CREATED));
                emergencyCode.setDescription(bundle.getString(KEY_DESCRIPTION));
                emergencyCode.setVoiceData(bundle.getString(KEY_VOICE_DATA));
                emergencyCode.setLocation(bundle.getString(KEY_LOCATION));
                emergencyCode.setLocation(bundle.getString(KEY_RESPONDERS));
                openWaitingAccptanceDialog(emergencyCode);
            }
        }
    }*/
    @SuppressLint("SetTextI18n")
    private void init(){
        initComponent();
        setSupportActionBar(mToolBar);
        setDisplayHomeUp();
        toolbar_title.setText("Profile Name");
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));
        mTypedImageArray = getResources().obtainTypedArray(R.array.menuIcons);
        mViewHolder = new ViewHolder();
        handleArcMenu();
        handleMenu();
        handleDrawer();
        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));
        mPresenter.getUserDashboardInfo();
    }

    private void initComponent() {
        this.mDashboardComponent = DaggerDashboardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .dashboardModule(new DashboardModule(this))
                .build();
        mDashboardComponent.inject(this);
        mPresenter.setDashboardView(this);
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu=menu;
        getMenuInflater().inflate(R.menu.menu_more_options, menu);//Menu Resource, Menu
        return true;
    }*/


    private void handleArcMenu(){
        mArcMenu.showTooltip(true);
        mArcMenu.setToolTipBackColor(Color.TRANSPARENT);
        mArcMenu.setToolTipCorner(2);
        mArcMenu.setToolTipPadding(1);
        mArcMenu.setToolTipTextSize(12);
        mArcMenu.setToolTipTextColor(ContextCompat.getColor(DashboardActivity.this,R.color.white));

        final int itemCount = MenuItem.ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(MenuItem.ITEM_DRAWABLES[i]);
            final int position = i;
            mArcMenu.addItem(item, MenuItem.STR[i], new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onBottomMenuItemClicked(position);
                }
            });
        }
    }
    private void onBottomMenuItemClicked(int position){
        containerShadow.setVisibility(View.GONE);
        switch (position){
            case 0:
                mIntent=new Intent(DashboardActivity.this,CallForHelpActivity.class);
                startNewActivity(mIntent);
                break;
            case 1:
                mIntent=new Intent(DashboardActivity.this,GroupMembersActivity.class);
                startNewActivity(mIntent);
                break;
            case 2:
                break;
            case 3:
                mIntent=new Intent(DashboardActivity.this,MyScheduleActivity.class);
                startNewActivity(mIntent);
                break;
            case 4:
                break;
        }
    }
    @OnClick(R.id.llArcMenu)
    public void onArcMenuClicked(){


        if(mArcMenu.performClick()){
            containerShadow.setVisibility(View.VISIBLE);
        }else{
            containerShadow.setVisibility(View.GONE);
        }

    }
    @OnClick(R.id.tvSignOut)
    public void onSignoutClicked(){
        onFooterClicked();
    }

    @OnClick(R.id.ivHome)
    public void onHomeClicked(){
       /* toolbar_title.setText(dashboardInfo.getData().getUsername());
        mMenuAdapter.setViewSelected(0, true);
        containerShadow.setVisibility(View.GONE);
        replaceFragment(R.id.container, HomeFragment.newInstance(new Gson().toJson(dashboardInfo).toString()));
        if(mArcMenu.isOpen()) {
            mArcMenu.performClick();
        }*/
        onOptionClicked(0,this);
    }


    private void handleDrawer() {
         duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        duoDrawerToggle.setDrawerIndicatorEnabled(false);
        duoDrawerToggle.setHomeAsUpIndicator(R.drawable.menu_icon);
        duoDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DuoDrawerLayout drawer = (DuoDrawerLayout) findViewById(R.id.drawer);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu() {
        mMenuAdapter = new NavDrawerAdapter(mTitles,mTypedImageArray);
        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {
       mPresenter.signOutClicked();
    }

    @Override
    public void onHeaderClicked() {

    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.add(R.id.container, fragment).commit();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {

        setTitle(mTitles.get(position));
        mMenuAdapter.setViewSelected(position, true);
        containerShadow.setVisibility(View.GONE);
        if(mArcMenu.isOpen()) {
            mArcMenu.performClick();
        }

        switch (position) {
            case 0:
                toolbar_title.setText(dashboardInfo.getData().getUsername());
                whiteTheme();
                replaceFragment(R.id.container,HomeFragment.newInstance(new Gson().toJson(dashboardInfo).toString()));
                break;
            case 1:
                toolbar_title.setText(dashboardInfo.getData().getUsername());
                redTheme();
                replaceFragment(R.id.container,new EditProfileFragment());
                break;
            case 2:
                toolbar_title.setText(getString(R.string.settings));
                redTheme();
                replaceFragment(R.id.container,new SettingsFragment());

                break;
            default:

                break;
        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }
    private void whiteTheme(){
        mToolBar.setBackgroundColor(ContextCompat.getColor(DashboardActivity.this,R.color.white));
        toolbar_title.setTextColor(ContextCompat.getColor(DashboardActivity.this,R.color.black));
        duoDrawerToggle.setHomeAsUpIndicator(R.drawable.menu_icon);
    }
    private void redTheme(){
        mToolBar.setBackgroundColor(ContextCompat.getColor(DashboardActivity.this,R.color.colorPrimary));
        toolbar_title.setTextColor(ContextCompat.getColor(DashboardActivity.this,R.color.white));
        duoDrawerToggle.setHomeAsUpIndicator(R.drawable.menu_icon_white);
    }
    @Override
    public void setDashboardInfo(DashboardInfoResponse dashboardInfo) {
        this.dashboardInfo=dashboardInfo;
        setProfileInfo(dashboardInfo);
        replaceFragment(R.id.container, HomeFragment.newInstance(new Gson().toJson(dashboardInfo).toString()));

    }
    private void setProfileInfo(DashboardInfoResponse dashboardInfoResponse){
        if(dashboardInfoResponse.getData()!=null&&dashboardInfoResponse.getData().getProfileImage()!=null) {
            String imageUrl = dashboardInfoResponse.getData().getProfileImage();
            toolbar_title.setText(dashboardInfoResponse.getData().getUsername());
            tvUserName.setText(dashboardInfoResponse.getData().getUsername());
            tvDesingnation.setText(dashboardInfoResponse.getData().getDesignation());

            if (imageUrl != null) {
                Log.e("ImageURl:",imageUrl);
                Glide.with(DashboardActivity.this).load(imageUrl)
                        .asBitmap()
                        .placeholder(R.drawable.profile_placeholder)
                        .centerCrop()
                        .into(new BitmapImageViewTarget(ivProfileImage) {
                            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                ivProfileImage.setImageDrawable(circularBitmapDrawable);
                                ivProfileImage.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }
        }
    }

    @Override
    public void navigateToHome() {

        Intent intent=new Intent(DashboardActivity.this, SigninActivity.class);
        startNewActivity(intent);
        finish();
    }




    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
        }
    }
}
