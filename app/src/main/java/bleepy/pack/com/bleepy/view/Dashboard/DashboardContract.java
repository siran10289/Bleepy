package bleepy.pack.com.bleepy.view.Dashboard;

import android.content.Intent;
import android.widget.EditText;

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
       int getUserID();
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
        int getUserID();
        void setProfileInfo(UserProfileResponse userProfileResponse);
        String mobileNumber();
    }
    interface SettingsView extends BaseView{
        String getCurrentPassword();
        String getNewPassword();
        String getConfirmPassword();
    }
    interface Presenter extends BasePresenter {

        void setDashboardView(DashboardView dashboardView);
        void setMyScheduleView(MyScheduleView myScheduleView);
        void setGroupMembersView(GroupMembersView groupMembersView);
        void setProfileView(ProfileView profileView);
        void setSettingsView(SettingsView settingsView);
        void getUserDashboardInfo();
        void signOutClicked();
        void getMySchedules();
        void getTeamMembers();
        void getProfileInfo();
        void updateProfileInfo();
        void changePassword();


    }

}
