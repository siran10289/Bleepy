package bleepy.pack.com.bleepy.interactor;
import android.app.Activity;
import android.speech.tts.Voice;

import bleepy.pack.com.bleepy.apiservice.Api;
import bleepy.pack.com.bleepy.models.callforhelp.CodeConfirmationRequest;
import bleepy.pack.com.bleepy.models.callforhelp.CodeConfirmationResponse;
import bleepy.pack.com.bleepy.models.callforhelp.CodeCreationRequest;
import bleepy.pack.com.bleepy.models.callforhelp.CodeCreationResponse;
import bleepy.pack.com.bleepy.models.callforhelp.CodeInfoRequest;
import bleepy.pack.com.bleepy.models.callforhelp.CodeInformationResponse;
import bleepy.pack.com.bleepy.models.callforhelp.EmergencyAlertAcceptRequest;
import bleepy.pack.com.bleepy.models.callforhelp.EmergencyAlertResponse;
import bleepy.pack.com.bleepy.models.callforhelp.LocationsResponse;
import bleepy.pack.com.bleepy.models.callforhelp.PushVoiceRequest;
import bleepy.pack.com.bleepy.models.callforhelp.TeamsResponse;
import bleepy.pack.com.bleepy.models.callforhelp.VoiceUpdateResponse;
import bleepy.pack.com.bleepy.models.common.CommonRequest;
import bleepy.pack.com.bleepy.models.common.CommonResponse;
import bleepy.pack.com.bleepy.models.common.ImageUploadResponse;
import bleepy.pack.com.bleepy.models.common.UserImageUploadRequest;
import bleepy.pack.com.bleepy.models.common.WelcomeScreenRequest;
import bleepy.pack.com.bleepy.models.common.WelcomeScreenResponse;
import bleepy.pack.com.bleepy.models.dashboard.ChangePasswordRequest;
import bleepy.pack.com.bleepy.models.dashboard.DashboardInfoRequest;
import bleepy.pack.com.bleepy.models.dashboard.DashboardInfoResponse;
import bleepy.pack.com.bleepy.models.dashboard.UpdateProfileRequest;
import bleepy.pack.com.bleepy.models.dashboard.UserProfileResponse;
import bleepy.pack.com.bleepy.models.myschedule.MyScheduleListResponse;
import bleepy.pack.com.bleepy.models.signin.ForgetCredentialsRequest;
import bleepy.pack.com.bleepy.models.signin.SigninRequest;
import bleepy.pack.com.bleepy.models.signin.SigninResponse;
import bleepy.pack.com.bleepy.models.signup.RegisterUserRequest;
import bleepy.pack.com.bleepy.models.signup.SignupResponse;
import bleepy.pack.com.bleepy.models.signup.UpdateDeviceInfoReq;
import bleepy.pack.com.bleepy.models.signup.UpdateDeviceInfoResponse;
import bleepy.pack.com.bleepy.models.signup.UpdateRegKeyRequest;
import bleepy.pack.com.bleepy.models.signup.UpdateRegKeyResponse;
import bleepy.pack.com.bleepy.models.team.GetTeamMembersRequest;
import bleepy.pack.com.bleepy.models.team.GroupMembersResponse;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.base.BaseView;
import bleepy.pack.com.bleepy.view.base.LoadListener;
import bleepy.pack.com.bleepy.view.base.UiCallback;
import retrofit2.Call;

/**
 * Created by siranjeevi on 5/6/17.
 */
public class ApiInteractorImpl implements ApiInteractor {

    PrefsManager mPrefsManager;
    Api mApi;

    public ApiInteractorImpl(Api remoteService, PrefsManager mPrefsManager) {
        this.mApi = remoteService;
        this.mPrefsManager = mPrefsManager;
    }

    @Override
    public void validateUser(BaseView mView, SigninRequest request, LoadListener<SigninResponse> mLoginListener, boolean state) {
        UiCallback<SigninResponse> callback = new UiCallback(mView, mLoginListener,state);
        Call<SigninResponse> call = mApi.validateUser(request);
        callback.start(call);
    }


    @Override
    public void registerUser(BaseView mView, RegisterUserRequest request, LoadListener<SignupResponse> mRegisterUserListener, boolean state) {
        UiCallback<SignupResponse> callback = new UiCallback(mView, mRegisterUserListener,state);
        Call<SignupResponse> call = mApi.registerUser(request);
        callback.start(call);
    }

    @Override
    public void uploadProfilePicture(BaseView mView, UserImageUploadRequest userImageUploadRequest, LoadListener<ImageUploadResponse> mSignupListener, boolean state) {
        UiCallback<ImageUploadResponse> callback = new UiCallback(mView, mSignupListener,state);
        Call<ImageUploadResponse> call = mApi.uploadUserImage(userImageUploadRequest);
        callback.start(call);
    }

    @Override
    public void updateDeviceInfo(BaseView mView, UpdateDeviceInfoReq updateDeviceInfoReq, LoadListener<UpdateDeviceInfoResponse> mSignupListener, boolean state) {
        UiCallback<UpdateDeviceInfoResponse> callback = new UiCallback(mView, mSignupListener,state);
        Call<UpdateDeviceInfoResponse> call = mApi.updateDeviceInfo(updateDeviceInfoReq);
        callback.start(call);
    }

    @Override
    public void updateRegKey(BaseView mView, UpdateRegKeyRequest updateRegKeyRequest, LoadListener<UpdateRegKeyResponse> listener, boolean state) {
        UiCallback<UpdateRegKeyResponse> callback = new UiCallback(mView, listener,state);
        Call<UpdateRegKeyResponse> call = mApi.updateRegKey(updateRegKeyRequest);
        callback.start(call);
    }

    @Override
    public void forgetCredentials(BaseView mView, ForgetCredentialsRequest forgetCredentialsRequest, LoadListener<CommonResponse> listener, int type, boolean state) {
        UiCallback<CommonResponse> callback = new UiCallback(mView, listener,state);
        Call<CommonResponse> call = null;
        switch (type){
            case 0:
               call = mApi.forgetUn(forgetCredentialsRequest);
                break;
            case 1:
                call = mApi.forgetPswd(forgetCredentialsRequest);

                break;
        }
        callback.start(call);

    }



    @Override
    public void getWelcomeScreens(BaseView mView, WelcomeScreenRequest welcomeScreenRequest, LoadListener<WelcomeScreenResponse> listener, boolean state) {
        UiCallback<WelcomeScreenResponse> callback = new UiCallback(mView, listener,state);
        Call<WelcomeScreenResponse> call = mApi.welcomeScreen(welcomeScreenRequest);
        callback.start(call);
    }

    @Override
    public void getDashboardInfo(BaseView mView, DashboardInfoRequest dashboardInfoRequest, LoadListener<DashboardInfoResponse> listener, boolean state) {
        UiCallback<DashboardInfoResponse> callback = new UiCallback(mView, listener,state);
        Call<DashboardInfoResponse> call = mApi.getDashBoardInfo(dashboardInfoRequest);
        callback.start(call);
    }

    @Override
    public void getMySchedules(BaseView mView,int userID,String date, LoadListener<MyScheduleListResponse> listener, boolean state) {
        UiCallback<MyScheduleListResponse> callback = new UiCallback(mView, listener,state);
        Call<MyScheduleListResponse> call = mApi.getMyScheduleDetails(userID,date);
        callback.start(call);
    }

    @Override
    public void getTeamMembers(BaseView mView, GetTeamMembersRequest getTeamMembersRequest, LoadListener<GroupMembersResponse> listener, boolean state) {
        UiCallback<GroupMembersResponse> callback = new UiCallback(mView, listener,state);
        Call<GroupMembersResponse> call = mApi.getTeamMembers(getTeamMembersRequest);
        callback.start(call);
    }

    @Override
    public void getPrfoileInfo(Activity activity,BaseView mView, CommonRequest commonRequest, LoadListener<UserProfileResponse> listener, boolean state) {
        UiCallback<UserProfileResponse> callback = new UiCallback(activity,mView, listener,state);
        Call<UserProfileResponse> call = mApi.getUserProfileInfo(commonRequest);
        callback.start(call);
    }

    @Override
    public void updatePrfoileInfo(Activity activity, BaseView mView, UpdateProfileRequest commonRequest, LoadListener<UserProfileResponse> listener, boolean state) {
        UiCallback<UserProfileResponse> callback = new UiCallback(activity,mView, listener,state);
        Call<UserProfileResponse> call = mApi.updateUserProfileInfo(commonRequest);
        callback.start(call);
    }

    @Override
    public void changePassword(Activity activity, BaseView mView, ChangePasswordRequest commonRequest, LoadListener<CommonResponse> listener, boolean state) {
        UiCallback<CommonResponse> callback = new UiCallback(activity,mView, listener,state);
        Call<CommonResponse> call = mApi.changePassword(commonRequest);
        callback.start(call);
    }

    @Override
    public void getLocations(Activity activity, BaseView mView, LoadListener<LocationsResponse> listener, boolean state) {
        UiCallback<LocationsResponse> callback = new UiCallback(activity,mView, listener,state);
        Call<LocationsResponse> call = mApi.getLocations();
        callback.start(call);
    }

    @Override
    public void getTeams(Activity activity, BaseView mView, CommonRequest commonRequest, LoadListener<TeamsResponse> listener, boolean state) {
        UiCallback<TeamsResponse> callback = new UiCallback(activity,mView, listener,state);
        Call<TeamsResponse> call = mApi.getTeams(commonRequest);
        callback.start(call);
    }

    @Override
    public void pushVoiceData(Activity activity, BaseView mView, PushVoiceRequest pushVoiceRequest, LoadListener<VoiceUpdateResponse> listener, boolean state) {
        UiCallback<VoiceUpdateResponse> callback = new UiCallback(activity,mView, listener,state);
        Call<VoiceUpdateResponse> call = mApi.pushVoiceData(pushVoiceRequest);
        callback.start(call);
    }

    @Override
    public void generateCode(Activity activity, BaseView mView, CodeCreationRequest codeCreationRequest, LoadListener<CodeCreationResponse> listener, boolean state) {
        UiCallback<CodeCreationResponse> callback = new UiCallback(activity,mView, listener,state);
        Call<CodeCreationResponse> call = mApi.createCode(codeCreationRequest);
        callback.start(call);
    }

    @Override
    public void acceptRejectEmergency(Activity activity, BaseView mView, EmergencyAlertAcceptRequest emergencyAlertAcceptRequest, LoadListener<EmergencyAlertResponse> listener, boolean state) {
        UiCallback<EmergencyAlertResponse> callback = new UiCallback(activity,mView, listener,state);
        Call<EmergencyAlertResponse> call = mApi.emergencyAlert(emergencyAlertAcceptRequest);
        callback.start(call);
    }

    @Override
    public void getCodeInformation(Activity activity, BaseView mView, CodeInfoRequest codeInfoRequest, LoadListener<CodeInformationResponse> listener, boolean state) {
        UiCallback<CodeInformationResponse> callback = new UiCallback(activity,mView, listener,state);
        Call<CodeInformationResponse> call = mApi.getCodeInfo(codeInfoRequest);
        callback.start(call);
    }

    @Override
    public void getCodeConformation(Activity activity, BaseView mView, CodeConfirmationRequest emergencyAlertAcceptRequest, LoadListener<CodeConfirmationResponse> listener, boolean state) {
        UiCallback<CodeConfirmationResponse> callback = new UiCallback(activity,mView, listener,state);
        Call<CodeConfirmationResponse> call = mApi.getCodeConformation(emergencyAlertAcceptRequest);
        callback.start(call);
    }

   /* @Override
    public void validateUser(BaseView mView, SigninRequest request, LoadListener<ValidateOTPResponse> mLoginListener, boolean state) {
        UiCallback<ValidateOTPResponse> callback = new UiCallback(mView, mLoginListener,state);
        Call<ValidateOTPResponse> call = mApi.validateUser(request);
        callback.start(call);
    }



    @Override
    public void forgetUnamePswd(BaseView mView, ForgetUPRequest forgetUPRequest, LoadListener<SignupResponse> mForgetUPListeber, boolean state) {
        UiCallback<SignupResponse> callback = new UiCallback(mView, mForgetUPListeber,state);
        Call<SignupResponse> call = mApi.forgetUnaamePswd(forgetUPRequest);
        callback.start(call);
    }*/


}
