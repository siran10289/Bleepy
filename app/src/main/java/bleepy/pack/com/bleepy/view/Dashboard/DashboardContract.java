package bleepy.pack.com.bleepy.view.Dashboard;

import android.content.Intent;
import android.widget.EditText;

import bleepy.pack.com.bleepy.models.callforhelp.CodeCreationResponse;
import bleepy.pack.com.bleepy.models.callforhelp.EmergencyAlertResponse;
import bleepy.pack.com.bleepy.models.callforhelp.LocationsResponse;
import bleepy.pack.com.bleepy.models.callforhelp.TeamsResponse;
import bleepy.pack.com.bleepy.models.callforhelp.VoiceUpdateResponse;
import bleepy.pack.com.bleepy.models.common.WelcomeScreenResponse;
import bleepy.pack.com.bleepy.models.dashboard.DashboardInfoResponse;
import bleepy.pack.com.bleepy.models.dashboard.UserProfileResponse;
import bleepy.pack.com.bleepy.models.myschedule.MyScheduleListResponse;
import bleepy.pack.com.bleepy.models.signup.UpdateRegKeyResponse;
import bleepy.pack.com.bleepy.models.team.GroupMembersResponse;
import bleepy.pack.com.bleepy.view.base.BasePresenter;
import bleepy.pack.com.bleepy.view.base.BaseView;


/**
 * Created by siranjeevi on 09/06/17.
 */
public interface DashboardContract {


    interface DashboardView extends BaseView {

       void setDashboardInfo(DashboardInfoResponse dashboardInfo);
       void navigateToHome();
    }
    interface MyScheduleView extends BaseView {
        String getDate();
        void setMyScheduleResponse(MyScheduleListResponse myScheduleResponse);
    }
    interface GroupMembersView extends BaseView {
        //String getTeamID();
        void setTeamMembers(GroupMembersResponse groupMembersResponse);
    }
    interface ProfileView extends BaseView{
        void setProfileInfo(UserProfileResponse userProfileResponse);
        String mobileNumber();
    }
    interface SettingsView extends BaseView{
        String getCurrentPassword();
        String getNewPassword();
        String getConfirmPassword();
    }
    interface CallForHelpView extends BaseView{
        void setLocations(LocationsResponse locationsResponse);
        void setTeams(TeamsResponse teamsResponse);
        void setVoiceDataUrl(VoiceUpdateResponse updateResponse);
        void setCodeCreationResponse(CodeCreationResponse response);
        String getDescription();
        String getVoiceDataUrl();
        String getLocationID();
        String getTeamID();
    }
    interface EmergencyAlertView extends BaseView{
        String getCodeID();
        String getUserStatus();
        void setEmergencyAlertResponse(EmergencyAlertResponse emergencyAlertResponse);

    }
    interface Presenter extends BasePresenter {

        void setDashboardView(DashboardView dashboardView);
        void setMyScheduleView(MyScheduleView myScheduleView);
        void setGroupMembersView(GroupMembersView groupMembersView);
        void setProfileView(ProfileView profileView);
        void setSettingsView(SettingsView settingsView);
        void setCallForHelpView(CallForHelpView callForHelpView);
        void setEmergencyAlertView(EmergencyAlertView emergencyAlertView);
        void pushVoiceData(String voiceBase64);
        void getUserDashboardInfo();
        void signOutClicked();
        void getMySchedules();
        void getTeamMembers();
        void getProfileInfo();
        void updateProfileInfo();
        void changePassword();
        void getLocations();
        void getTeams();
        void generateCode();
        void emergencyAccept();
        void emergencyReject(String reason);



    }

}
