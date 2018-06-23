package bleepy.pack.com.bleepy.view.base;

import bleepy.pack.com.bleepy.models.callforhelp.CodeConfirmationResponse;
import bleepy.pack.com.bleepy.models.callforhelp.CodeCreationResponse;
import bleepy.pack.com.bleepy.models.callforhelp.CodeInformationResponse;
import bleepy.pack.com.bleepy.models.callforhelp.EmergencyAlertResponse;
import bleepy.pack.com.bleepy.models.callforhelp.LocationsResponse;
import bleepy.pack.com.bleepy.models.callforhelp.TeamsResponse;
import bleepy.pack.com.bleepy.models.callforhelp.VoiceUpdateResponse;
import bleepy.pack.com.bleepy.models.dashboard.DashboardInfoResponse;
import bleepy.pack.com.bleepy.models.dashboard.UserProfileResponse;
import bleepy.pack.com.bleepy.models.myschedule.MyScheduleListResponse;
import bleepy.pack.com.bleepy.models.team.GroupMembersResponse;


/**
 * Created by siranjeevi on 09/06/17.
 */
public interface BaseContract {



    interface EmergencyAlertView extends BaseView{
        String getCodeID();
        String getUserStatus();
        void showConfirmationDialog(CodeInformationResponse responseBody);
        void setEmergencyAlertResponse(EmergencyAlertResponse emergencyAlertResponse);
        void setConfirmationResonse(CodeConfirmationResponse codeConfirmationResponse);

    }
    interface Presenter extends BasePresenter {
        void setEmergencyAlertView(EmergencyAlertView emergencyAlertView);
        void emergencyAccept();
        void emergencyReject(String reason);
        void codeConfirmation(String codeID,String status);
        void getCodeInformation(String codeID);
    }

}
