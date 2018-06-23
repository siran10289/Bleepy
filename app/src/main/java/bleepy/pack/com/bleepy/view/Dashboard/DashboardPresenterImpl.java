package bleepy.pack.com.bleepy.view.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.interactor.ApiInteractor;
import bleepy.pack.com.bleepy.models.callforhelp.CodeCreationRequest;
import bleepy.pack.com.bleepy.models.callforhelp.CodeCreationResponse;
import bleepy.pack.com.bleepy.models.callforhelp.EmergencyAlertAcceptRequest;
import bleepy.pack.com.bleepy.models.callforhelp.EmergencyAlertResponse;
import bleepy.pack.com.bleepy.models.callforhelp.LocationsResponse;
import bleepy.pack.com.bleepy.models.callforhelp.PushVoiceRequest;
import bleepy.pack.com.bleepy.models.callforhelp.TeamsResponse;
import bleepy.pack.com.bleepy.models.callforhelp.VoiceUpdateResponse;
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
import bleepy.pack.com.bleepy.utils.Validation;
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
    private DashboardContract.CallForHelpView mCallForHelpView;
    private DashboardContract.EmergencyAlertView mEmergencyAlertView;
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
    public void setCallForHelpView(DashboardContract.CallForHelpView callForHelpView) {
        this.mCallForHelpView=callForHelpView;
    }

    @Override
    public void setEmergencyAlertView(DashboardContract.EmergencyAlertView emergencyAlertView) {
        this.mEmergencyAlertView=emergencyAlertView;
    }

    @Override
    public void pushVoiceData(String voiceBase64) {
        PushVoiceRequest pushVoiceRequest=new PushVoiceRequest();
        pushVoiceRequest.setVoiceRecord(voiceBase64);
        mApiInteractor.pushVoiceData(mActivity,mCallForHelpView,pushVoiceRequest,mVoiceUpdateResponseLoadListener,true);
    }

    @Override
    public void getUserDashboardInfo() {
        DashboardInfoRequest dashboardInfoRequest = new DashboardInfoRequest();
        dashboardInfoRequest.setUserid(String.valueOf(mPrefsManager.getIntKeyValueFromPrefsByKey(KEY_USERID)));
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
        commonRequest.setUserid(mPrefsManager.getIntKeyValueFromPrefsByKey(KEY_USERID));
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
    public void getLocations() {
        mApiInteractor.getLocations(mActivity,mCallForHelpView,mLocationsResponseLoadListener,true);
    }

    @Override
    public void getTeams() {
      CommonRequest commonRequest=new CommonRequest();
      commonRequest.setUserid(mPrefsManager.getIntKeyValueFromPrefsByKey(KEY_USERID));
      mApiInteractor.getTeams(mActivity,mCallForHelpView,commonRequest,mTeamsResponseLoadListener,true);

    }

    @Override
    public void generateCode() {
        if(isInputsValidatedWithNoErrors()) {
            CodeCreationRequest codeCreationRequest = new CodeCreationRequest();
            codeCreationRequest.setUserid(String.valueOf(mPrefsManager.getIntKeyValueFromPrefsByKey(KEY_USERID)));
            codeCreationRequest.setDescription(mCallForHelpView.getDescription());
            if(mCallForHelpView.getVoiceDataUrl()!=null){
                codeCreationRequest.setCodeVoicePath(mCallForHelpView.getVoiceDataUrl());
            }
            codeCreationRequest.setLocationID(mCallForHelpView.getLocationID());
            codeCreationRequest.setTeamID(mCallForHelpView.getTeamID());
            codeCreationRequest.setCodeCreatedOnDate(getDate());
            codeCreationRequest.setCodeCreatedOnTime(getTime());
            Log.e("Request:",new Gson().toJson(codeCreationRequest).toString());
            mApiInteractor.generateCode(mActivity,mCallForHelpView,codeCreationRequest,mCodeCreationResponseLoadListener,true);


        }
    }

    @Override
    public void emergencyAccept() {
        EmergencyAlertAcceptRequest emergencyAlertAcceptRequest=new EmergencyAlertAcceptRequest();
        emergencyAlertAcceptRequest.setCodeId(mEmergencyAlertView.getCodeID());
        emergencyAlertAcceptRequest.setUserId(String.valueOf(mPrefsManager.getIntKeyValueFromPrefsByKey(KEY_USERID)));
        emergencyAlertAcceptRequest.setCodeRespondTime(getTime());
        emergencyAlertAcceptRequest.setUserStatus(mEmergencyAlertView.getUserStatus());
        mApiInteractor.acceptRejectEmergency(mActivity,mEmergencyAlertView,emergencyAlertAcceptRequest,mEmergencyAlertResponseLoadListener,false);
    }

    @Override
    public void emergencyReject(String reason) {

    }

    private String getDate(){
        Date cDate = Calendar.getInstance().getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(cDate);
    }
    private String getTime(){
        Date cDate = Calendar.getInstance().getTime();
        return new SimpleDateFormat("hh:mm:ss").format(cDate);
    }

    private Boolean isInputsValidatedWithNoErrors() {
        Boolean isError = false;
        if (Validation.isEmpty(mCallForHelpView.getDescription())) {
            mCallForHelpView.showErrorDialog(mContext.getString(R.string.please_enter_description));
            isError = true;
        } else if (Validation.isEmpty(mCallForHelpView.getLocationID())) {
            isError = true;
            mCallForHelpView.showErrorDialog(mContext.getString(R.string.choose_your_location));
        }else if (Validation.isEmpty(mCallForHelpView.getTeamID())) {
            isError = true;
            mCallForHelpView.showErrorDialog(mContext.getString(R.string.select_teams));
        }
        return !isError;
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

    LoadListener<LocationsResponse> mLocationsResponseLoadListener = new LoadListener<LocationsResponse>() {
        @Override
        public void onSuccess(LocationsResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mCallForHelpView.setLocations(responseBody);
                        break;
                    case STATUS_FAILURE:
                        mCallForHelpView.showErrorDialog(responseBody.getMeta().getMessage());
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
    LoadListener<TeamsResponse> mTeamsResponseLoadListener = new LoadListener<TeamsResponse>() {
        @Override
        public void onSuccess(TeamsResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mCallForHelpView.setTeams(responseBody);
                        break;
                    case STATUS_FAILURE:
                        mCallForHelpView.showErrorDialog(responseBody.getMeta().getMessage());
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

    LoadListener<VoiceUpdateResponse> mVoiceUpdateResponseLoadListener = new LoadListener<VoiceUpdateResponse>() {
        @Override
        public void onSuccess(VoiceUpdateResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mCallForHelpView.setVoiceDataUrl(responseBody);
                        break;
                    case STATUS_FAILURE:
                        mCallForHelpView.showErrorDialog(responseBody.getMeta().getMessage());
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
    LoadListener<CodeCreationResponse> mCodeCreationResponseLoadListener = new LoadListener<CodeCreationResponse>() {
        @Override
        public void onSuccess(CodeCreationResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mCallForHelpView.setCodeCreationResponse(responseBody);
                        break;
                    case STATUS_FAILURE:
                        mCallForHelpView.showErrorDialog(responseBody.getMeta().getMessage());
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
    LoadListener<EmergencyAlertResponse> mEmergencyAlertResponseLoadListener = new LoadListener<EmergencyAlertResponse>() {
        @Override
        public void onSuccess(EmergencyAlertResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mEmergencyAlertView.setEmergencyAlertResponse(responseBody);
                        break;
                    case STATUS_FAILURE:
                        mEmergencyAlertView.showErrorDialog(responseBody.getMeta().getMessage());
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
