package bleepy.pack.com.bleepy.interactor;


import android.app.Activity;

import bleepy.pack.com.bleepy.models.callforhelp.CodeCreationRequest;
import bleepy.pack.com.bleepy.models.callforhelp.CodeCreationResponse;
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
import bleepy.pack.com.bleepy.view.base.BaseView;
import bleepy.pack.com.bleepy.view.base.LoadListener;


/**
 * Created by siranjeevi on 5/6/17.
 */
public interface ApiInteractor {



    void validateUser(BaseView mView, SigninRequest request, LoadListener<SigninResponse> mLoginListener, boolean state);
    void registerUser(BaseView mView, RegisterUserRequest request, LoadListener<SignupResponse> mSignupListener, boolean state);
    void uploadProfilePicture(BaseView mView, UserImageUploadRequest userImageUploadRequest, LoadListener<ImageUploadResponse> mSignupListener, boolean state);
    void updateDeviceInfo(BaseView mView, UpdateDeviceInfoReq updateDeviceInfoReq, LoadListener<UpdateDeviceInfoResponse> mSignupListener, boolean state);
    void updateRegKey(BaseView mView, UpdateRegKeyRequest updateRegKeyRequest, LoadListener<UpdateRegKeyResponse> mUpdateRegKeyResponse, boolean state);
    void forgetCredentials(BaseView mView, ForgetCredentialsRequest forgetCredentialsRequest, LoadListener<CommonResponse> listener, int type, boolean state);
    void getWelcomeScreens(BaseView mView, WelcomeScreenRequest welcomeScreenRequest, LoadListener<WelcomeScreenResponse> mUpdateRegKeyResponse, boolean state);
    void getDashboardInfo(BaseView mView, DashboardInfoRequest dashboardInfoRequest, LoadListener<DashboardInfoResponse> listener, boolean state);
    void getMySchedules(BaseView mView, int userID, String date, LoadListener<MyScheduleListResponse> listener, boolean state);
    void getTeamMembers(BaseView mView, GetTeamMembersRequest getTeamMembersRequest, LoadListener<GroupMembersResponse> listener, boolean state);
    void getPrfoileInfo(Activity activity, BaseView mView, CommonRequest commonRequest, LoadListener<UserProfileResponse> listener, boolean state);
    void updatePrfoileInfo(Activity activity, BaseView mView, UpdateProfileRequest commonRequest, LoadListener<UserProfileResponse> listener, boolean state);
    void changePassword(Activity activity, BaseView mView, ChangePasswordRequest commonRequest, LoadListener<CommonResponse> listener, boolean state);
    void getLocations(Activity activity, BaseView mView, LoadListener<LocationsResponse> listener, boolean state);
    void getTeams(Activity activity, BaseView mView, CommonRequest commonRequest, LoadListener<TeamsResponse> listener, boolean state);
    void pushVoiceData(Activity activity, BaseView mView, PushVoiceRequest pushVoiceRequest, LoadListener<VoiceUpdateResponse> listener, boolean state);
    void generateCode(Activity activity, BaseView mView, CodeCreationRequest codeCreationRequest, LoadListener<CodeCreationResponse> listener, boolean state);

}

