package bleepy.pack.com.bleepy.view.Dashboard;


import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import javax.inject.Inject;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.di.component.DaggerDashboardComponent;
import bleepy.pack.com.bleepy.di.component.DashboardComponent;
import bleepy.pack.com.bleepy.di.module.DashboardModule;
import bleepy.pack.com.bleepy.models.dashboard.DashboardInfoResponse;
import bleepy.pack.com.bleepy.models.dashboard.UpdateProfileRequest;
import bleepy.pack.com.bleepy.models.dashboard.UserProfileResponse;
import bleepy.pack.com.bleepy.view.base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class EditProfileFragment extends BaseFragment implements DashboardContract.ProfileView  {

    @Inject
    DashboardContract.Presenter mPresenter;
    DashboardComponent mDashboardComponent;
    View mRootView;
    @BindView(R.id.etFirstName)EditText etFirstName;
    @BindView(R.id.etSecondName)EditText etSecondName;
    @BindView(R.id.etEmail)EditText etEmail;
    @BindView(R.id.etDesignation)EditText etDesignation;
    @BindView(R.id.etStaffId)EditText etStaffId;
    @BindView(R.id.etMobile)EditText etMobile;
    @BindView(R.id.etTeamID)EditText etTeamID;
    @BindView(R.id.etUserName)EditText etUserName;
    @BindView(R.id.rgDeviceType)RadioGroup rgDeviceType;
    @BindView(R.id.rbSingleDevice)AppCompatRadioButton  rbSingleDevice;
    @BindView(R.id.rbSharedDevice)AppCompatRadioButton rbSharedDevice;
    @BindView(R.id.etBleepID)EditText etBleepID;
    @BindView(R.id.etRegkey)EditText etRegkey;
    @BindView(R.id.ivProfileImage)ImageView ivProfileImage;
    UserProfileResponse mUserProfileResponse;
    Menu mMenu;


    public EditProfileFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.fragment_edit_profile, container, false);
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
       mPresenter.getProfileInfo();
    }
    private void initComponent() {
        this.mDashboardComponent = DaggerDashboardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .dashboardModule(new DashboardModule(getActivity()))
                .build();
        mDashboardComponent.inject(this);
        mPresenter.setProfileView(this);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.mMenu=menu;
        inflater.inflate(R.menu.menu_more_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menuEditProfile:
                etMobile.setEnabled(true);
                etMobile.setFocusable(true);
                etMobile.setFocusableInTouchMode(true);
                item.setVisible(false);
                MenuItem update=mMenu.findItem(R.id.menuUpdate);
                update.setVisible(true);

                return true;
            case R.id.menuUpdate:
                onUpdateClicked();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @OnClick(R.id.btnSave)
    public void onUpdateClicked(){
        mPresenter.updateProfileInfo();
    }

    @Override
    public int getUserID() {
        return  ((DashboardActivity)getActivity()).getUserID();

    }

    @Override
    public void setProfileInfo(UserProfileResponse userProfileResponse) {
        this.mUserProfileResponse=userProfileResponse;
        UserProfileResponse.Data data=userProfileResponse.getData();
        if(data!=null){
            if(data.getFirstname()!=null)etFirstName.setText(data.getFirstname());
            if(data.getLastname()!=null)etSecondName.setText(data.getLastname());
            if(data.getEmail()!=null)etEmail.setText(data.getEmail());
            if(data.getDesignation()!=null)etDesignation.setText(data.getDesignation());
            if(data.getStaffid()!=null)etStaffId.setText(data.getStaffid());
            if(data.getMobile()!=null)etMobile.setText(data.getMobile());
            if(data.getTeamName()!=null)etTeamID.setText(data.getTeamName());
            if(data.getBleepyid()!=null)etBleepID.setText(data.getBleepyid());
            if(data.getUsername()!=null)etUserName.setText(data.getUsername());
            if(data.getUserRegKey()!=null)etRegkey.setText(data.getUserRegKey());
            if(data.getDevicetype()!=null){
                switch (data.getDevicetype()){
                    case "0":
                        rbSingleDevice.setChecked(true);
                        break;
                    case "1":
                        rbSharedDevice.setChecked(true);
                        break;
                }
            }
            setProfilePicture(userProfileResponse);
        }

    }

    @Override
    public String mobileNumber() {
        if(etMobile.getText().toString().isEmpty()){
            showErrorDialog(getString(R.string.error_enter_mobile_number));
            return null;
        }
        return etMobile.getText().toString().trim();
    }

    private void setProfilePicture(UserProfileResponse userProfileResponse){
        if(userProfileResponse.getData()!=null&&userProfileResponse.getData().getUserProfilePic()!=null) {
            String imageUrl = userProfileResponse.getData().getUserProfilePic();

            if (imageUrl != null) {
                Log.e("ImageURl:",imageUrl);
                Glide.with(getActivity()).load(imageUrl)
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

                            }
                        });
            }
        }
    }
}
