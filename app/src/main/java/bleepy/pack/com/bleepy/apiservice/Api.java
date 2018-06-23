package bleepy.pack.com.bleepy.apiservice;




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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by siranjeevi on 5/6/17.
 */
public interface Api {
    @POST("loginauth")
    Call<SigninResponse> validateUser(@Body SigninRequest signinRequest);
    @POST("userreg")
    Call<SignupResponse> registerUser(@Body RegisterUserRequest registerUserRequest);
    @POST("updateprofile")
    Call<ImageUploadResponse> uploadUserImage(@Body UserImageUploadRequest userImageUploadRequest);
    @POST("updatedeviceinfo")
    Call<UpdateDeviceInfoResponse> updateDeviceInfo(@Body UpdateDeviceInfoReq userImageUploadRequest);
    @POST("updateregisterkey")
    Call<UpdateRegKeyResponse> updateRegKey(@Body UpdateRegKeyRequest userImageUploadRequest);
    @POST("splashscreens")
    Call<WelcomeScreenResponse> welcomeScreen(@Body WelcomeScreenRequest userImageUploadRequest);
    @POST("fusername")
    Call<CommonResponse> forgetUn(@Body ForgetCredentialsRequest forgetCredentialsRequest);
    @POST("fpassword")
    Call<CommonResponse> forgetPswd(@Body ForgetCredentialsRequest forgetCredentialsRequest);
    @POST("userdashboard")
    Call<DashboardInfoResponse> getDashBoardInfo(@Body DashboardInfoRequest dashboardInfoRequest);
    @GET("viewmyschedule")
    Call<MyScheduleListResponse> getMyScheduleDetails(@Query("uid") int userId, @Query("date") String date);
    @POST("viewgroups")
    Call<GroupMembersResponse> getTeamMembers(@Body GetTeamMembersRequest dashboardInfoRequest);
    @POST("viewprofile")
    Call<UserProfileResponse> getUserProfileInfo(@Body CommonRequest dashboardInfoRequest);
    @POST("editprofile")
    Call<UserProfileResponse> updateUserProfileInfo(@Body UpdateProfileRequest userProfileResponse);
    @POST("updatesettings")
    Call<CommonResponse> changePassword(@Body ChangePasswordRequest userProfileResponse);
    @POST("fetchlocationdetails")
    Call<LocationsResponse> getLocations();
    @POST("fetchteamdetails")
    Call<TeamsResponse> getTeams(@Body CommonRequest userProfileResponse);
    @POST("pushvoicedata")
    Call<VoiceUpdateResponse> pushVoiceData(@Body PushVoiceRequest pushVoiceRequest);
    @POST("generatecode")
    Call<CodeCreationResponse> createCode(@Body CodeCreationRequest pushVoiceRequest);
    @POST("checkcoderesponse")
    Call<EmergencyAlertResponse> emergencyAlert(@Body EmergencyAlertAcceptRequest pushVoiceRequest);
    @POST("codeinformation")
    Call<CodeInformationResponse> getCodeInfo(@Body CodeInfoRequest pushVoiceRequest);
    @POST("codeconfirmation")
    Call<CodeConfirmationResponse> getCodeConformation(@Body CodeConfirmationRequest pushVoiceRequest);


    /*@PUT("update-email")
    Call<SignupResponse> updateEmail(@Header("x-access-token") String accessToken, @Body UpdateEmailRequest updateEmailRequest);
    @POST("forgot-password")
    Call<SignupResponse> forgetUnaamePswd(@Body ForgetUPRequest forgetUPRequest);*/

}
