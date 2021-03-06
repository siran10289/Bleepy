package bleepy.pack.com.bleepy.utils.customdialog;


import java.util.List;

import bleepy.pack.com.bleepy.models.callforhelp.TeamsResponse;
import bleepy.pack.com.bleepy.models.common.EmergencyCode;
import bleepy.pack.com.bleepy.models.signin.SigninResponse;

/**
 * Created by Siranjeevi on 7/6/18.
 */
public interface DialogListener {

    public interface AlertDialogListener{
        void onOKPressed(String status);
        void onCancelPressed();
    }
    public interface NetworkDialogListener{
        void onOKPressed();
        void onSettingsPressed();
    }
    public interface SelectDeviceLisenter{
        void personalDeviceSelected();
        void shardDeviceSelected();
    }
    public interface BaseListener{
        void onAcceptClicked(EmergencyCode emergencyCode);
        void onRejectClicked(EmergencyCode emergencyCode);
        void onRejectReasonOKClicked(String reason);
        void confirmClicked();
        void CancelClicked();
    }
    public interface DashBoardListener{

    }
    public interface CallForHelpListener{
        void onTeamsSelected(List<TeamsResponse.Datum> selectedTeams);

    }
    public interface EmergencyCodeLogListener{

    }



}
