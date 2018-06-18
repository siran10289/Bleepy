package bleepy.pack.com.bleepy.view.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.interactor.ApiInteractor;
import bleepy.pack.com.bleepy.models.common.CommonRequest;
import bleepy.pack.com.bleepy.models.common.CommonResponse;
import bleepy.pack.com.bleepy.models.dashboard.ChangePasswordRequest;
import bleepy.pack.com.bleepy.models.dashboard.DashboardInfoRequest;
import bleepy.pack.com.bleepy.models.dashboard.DashboardInfoResponse;
import bleepy.pack.com.bleepy.models.dashboard.UpdateProfileRequest;
import bleepy.pack.com.bleepy.models.dashboard.UserProfileResponse;
import bleepy.pack.com.bleepy.models.myschedule.MyScheduleListResponse;
import bleepy.pack.com.bleepy.models.signup.UpdateDeviceInfoResponse;
import bleepy.pack.com.bleepy.models.team.GetTeamMembersRequest;
import bleepy.pack.com.bleepy.models.team.GroupMembersResponse;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.base.BaseView;
import bleepy.pack.com.bleepy.view.base.LoadListener;
import bleepy.pack.com.bleepy.view.signup.PackageInfoInteractor;

import static bleepy.pack.com.bleepy.utils.Constants.DEBUG_MODE;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_USERID;
import static bleepy.pack.com.bleepy.utils.Constants.STATUS_FAILURE;
import static bleepy.pack.com.bleepy.utils.Constants.STATUS_SUCCESS;

/**
 * Created by Siranjeevi Rengarajan on 6/13/2018.
 */
public class DashboardPresenterImpl implements DashboardContract.Presenter {
    DashboardContract.DashboardView mDashboardView;
    private PrefsManager mPrefsManager;
    private ApiInteractor mApiInteractor;
    private Context mContext;
    private PackageInfoInteractor mPackageInfoInteractor;
    private DashboardContract.MyScheduleView mMyScheduleView;
    private DashboardContract.GroupMembersView mGroupMembersView;
    private DashboardContract.ProfileView mProfileView;
    private DashboardContract.SettingsView mSettingsView;
    private Activity mActivity;

    public DashboardPresenterImpl(Activity activity,Context context, ApiInteractor apiInteractor, PrefsManager prefsManager, PackageInfoInteractor mPackageInfoInteractor) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
        this.mContext = context;
        this.mPackageInfoInteractor = mPackageInfoInteractor;
        this.mActivity=activity;

    }

    @Override
    public void setDashboardView(DashboardContract.DashboardView dashboardView) {
        this.mDashboardView = dashboardView;

    }

    @Override
    public void setMyScheduleView(DashboardContract.MyScheduleView myScheduleView) {
        this.mMyScheduleView=myScheduleView;

    }

    @Override
    public void setGroupMembersView(DashboardContract.GroupMembersView groupMembersView) {
        this.mGroupMembersView=groupMembersView;
    }

    @Override
    public void setProfileView(DashboardContract.ProfileView profileView) {
        this.mProfileView=profileView;
    }

    @Override
    public void setSettingsView(DashboardContract.SettingsView settingsView) {
         this.mSettingsView=settingsView;
    }

    @Override
    public void getUserDashboardInfo() {
        DashboardInfoRequest dashboardInfoRequest = new DashboardInfoRequest();
        dashboardInfoRequest.setUserid(String.valueOf(mDashboardView.getUserID()));
        if(DEBUG_MODE)Log.e("Req:",new Gson().toJson(dashboardInfoRequest).toString());
        mApiInteractor.getDashboardInfo(mDashboardView, dashboardInfoRequest, mDashboardInfoResponseLoadListener, true);

    }

    @Override
    public void signOutClicked() {
        mPrefsManager.clearPreferences();
        mDashboardView.navigateToHome();

    }

    @Override
    public void getMySchedules() {
        //mApiInteractor.getMySchedules(mMyScheduleView,mPrefsManager.getIntKeyValueFromPrefsByKey(KEY_USERID), mMyScheduleView.getDate(), mMyScheduleListResponseLoadListener, true);

        mApiInteractor.getMySchedules(mMyScheduleView,mPrefsManager.getIntKeyValueFromPrefsByKey(KEY_USERID), mMyScheduleView.getDate(), mMyScheduleListResponseLoadListener, true);

    }

    @Override
    public void getTeamMembers() {
        GetTeamMembersRequest getTeamMembersRequest=new GetTeamMembersRequest();
        getTeamMembersRequest.setUserId(String.valueOf(mPrefsManager.getIntKeyValueFromPrefsByKey(KEY_USERID)));
        mApiInteractor.getTeamMembers(mGroupMembersView,getTeamMembersRequest,mTeamMemberResLisener,true);
    }

    @Override
    public void getProfileInfo() {
        CommonRequest commonRequest=new CommonRequest();
        commonRequest.setUserid(mProfileView.getUserID());
        mApiInteractor.getPrfoileInfo(mActivity,mProfileView,commonRequest,mUserProfileResponseLoadListener,true);

    }

    @Override
    public void updateProfileInfo() {
        UpdateProfileRequest updateProfileRequest=new UpdateProfileRequest();
        updateProfileRequest.setUserid(String.valueOf(mPrefsManager.getIntKeyValueFromPrefsByKey(KEY_USERID)));
        updateProfileRequest.setMobile(mProfileView.mobileNumber());
        Log.e("Input:",new Gson().toJson(updateProfileRequest).toString());
        mApiInteractor.updatePrfoileInfo(mActivity,mProfileView,updateProfileRequest,mUpdateProfileListener,true);
    }

    @Override
    public void changePassword() {
        if (validateoldPassword(mSettingsView.getCurrentPassword())){
            if (validatenewPassword(mSettingsView.getNewPassword())){
                if (validateconPassword(mSettingsView.getConfirmPassword())) {
                    if (passWordMatch(mSettingsView.getNewPassword(), mSettingsView.getConfirmPassword())) {
                        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
                        changePasswordRequest.setUserid(mPrefsManager.getIntKeyValueFromPrefsByKey(KEY_USERID));
                        changePasswordRequest.setCupassword(mSettingsView.getCurrentPassword());
                        changePasswordRequest.setNewpassword(mSettingsView.getNewPassword());
                        mApiInteractor.changePassword(mActivity, mSettingsView, changePasswordRequest, mChangePasswordListener, true);
                    }
                }
            }

        }


    }

    @Override
    public void setView(BaseView view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private boolean validateoldPassword(String oldpass) {
        if (oldpass.length() == 0) {
            mSettingsView.showErrorDialog("Please enter old password.");
            return false;
        } else if (oldpass.length() < 8) {
            mSettingsView.showErrorDialog("Sorry, your old password must be at least 8 characters long.");
            return false;
        } else if (oldpass.length() > 12) {
            mSettingsView.showErrorDialog("Sorry, your old password must be max 12 characters long.");
            return false;
        }
        return true;
    }

    private boolean validatenewPassword(String newpass) {
        if (newpass.length() == 0) {
            mSettingsView.showErrorDialog("Please enter new password.");
            return false;
        } else if (newpass.length() < 8) {
            mSettingsView.showErrorDialog("Sorry, your new password must be at least 8 characters long.");
            return false;
        } else if (newpass.length() > 12) {
            mSettingsView.showErrorDialog("Sorry, your confirm password must be max 12 characters long.");
            return false;
        }
        return true;
    }

    private boolean validateconPassword(String conpass) {
        if (conpass.length() == 0) {
            mSettingsView.showErrorDialog("Please enter confirm password.");
            return false;
        } else if (conpass.length() < 8) {
            mSettingsView.showErrorDialog("Sorry, your new password must be at least 8 characters long.");
            return false;
        } else if (conpass.length() > 12) {
            mSettingsView.showErrorDialog("Sorry, your confirm password must be max 12 characters long.");
            return false;
        }
        return true;
    }
    private boolean passWordMatch(String newPassword,String confirmPassword){
        if(newPassword.equalsIgnoreCase(confirmPassword)){
            return true;
        }
        mSettingsView.showErrorDialog(mActivity.getString(R.string.error_password_not_match));
        return false;
    }

    LoadListener<DashboardInfoResponse> mDashboardInfoResponseLoadListener = new LoadListener<DashboardInfoResponse>() {
        @Override
        public void onSuccess(DashboardInfoResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mDashboardView.setDashboardInfo(responseBody);
                        break;
                    case STATUS_FAILURE:
                        mDashboardView.showErrorDialog(responseBody.getMeta().getMessage());
                        break;
                }

            }

        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<MyScheduleListResponse> mMyScheduleListResponseLoadListener = new LoadListener<MyScheduleListResponse>() {
        @Override
        public void onSuccess(MyScheduleListResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mMyScheduleView.setMyScheduleResponse(responseBody);
                        break;
                    case STATUS_FAILURE:
                        mDashboardView.showErrorDialog(responseBody.getMeta().getMessage());
                        break;
                }

            }

        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    LoadListener<GroupMembersResponse> mTeamMemberResLisener = new LoadListener<GroupMembersResponse>() {
        @Override
        public void onSuccess(GroupMembersResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mGroupMembersView.setTeamMembers(responseBody);
                        break;
                    case STATUS_FAILURE:
                        mGroupMembersView.showErrorDialog(responseBody.getMeta().getMessage());
                        break;
                }

            }

        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    LoadListener<UserProfileResponse> mUserProfileResponseLoadListener = new LoadListener<UserProfileResponse>() {
        @Override
        public void onSuccess(UserProfileResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mProfileView.setProfileInfo(responseBody);
                        break;
                    case STATUS_FAILURE:
                        mProfileView.showErrorDialog(responseBody.getMeta().getMessage());
                        break;
                }

            }

        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    LoadListener<UserProfileResponse> mUpdateProfileListener = new LoadListener<UserProfileResponse>() {
        @Override
        public void onSuccess(UserProfileResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mProfileView.showErrorDialog(responseBody.getMeta().getMessage());
                        break;
                    case STATUS_FAILURE:
                        mProfileView.showErrorDialog(responseBody.getMeta().getMessage());
                        break;
                }

            }

        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    LoadListener<CommonResponse> mChangePasswordListener = new LoadListener<CommonResponse>() {
        @Override
        public void onSuccess(CommonResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mSettingsView.showErrorDialog(responseBody.getMeta().getMessage());
                        break;
                    case STATUS_FAILURE:
                        mSettingsView.showErrorDialog(responseBody.getMeta().getMessage());
                        break;
                }

            }

        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

}
