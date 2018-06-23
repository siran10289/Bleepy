package bleepy.pack.com.bleepy.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.interactor.ApiInteractor;
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
import bleepy.pack.com.bleepy.models.dashboard.ChangePasswordRequest;
import bleepy.pack.com.bleepy.models.dashboard.DashboardInfoRequest;
import bleepy.pack.com.bleepy.models.dashboard.DashboardInfoResponse;
import bleepy.pack.com.bleepy.models.dashboard.UpdateProfileRequest;
import bleepy.pack.com.bleepy.models.dashboard.UserProfileResponse;
import bleepy.pack.com.bleepy.models.myschedule.MyScheduleListResponse;
import bleepy.pack.com.bleepy.models.team.GetTeamMembersRequest;
import bleepy.pack.com.bleepy.models.team.GroupMembersResponse;
import bleepy.pack.com.bleepy.utils.Validation;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardContract;
import bleepy.pack.com.bleepy.view.signup.PackageInfoInteractor;

import static bleepy.pack.com.bleepy.utils.Constants.DEBUG_MODE;
import static bleepy.pack.com.bleepy.utils.Constants.DELAY_MILLISECONDS;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_USERID;
import static bleepy.pack.com.bleepy.utils.Constants.STATUS_FAILURE;
import static bleepy.pack.com.bleepy.utils.Constants.STATUS_SUCCESS;

/**
 * Created by Siranjeevi Rengarajan on 6/13/2018.
 */
public class BasePresenterImpl implements BaseContract.Presenter {
    DashboardContract.DashboardView mDashboardView;
    private PrefsManager mPrefsManager;
    private ApiInteractor mApiInteractor;
    private Activity mActivity;
    private PackageInfoInteractor mPackageInfoInteractor;
    private BaseContract.EmergencyAlertView mEmergencyAlertView;


    public BasePresenterImpl( Activity context, ApiInteractor apiInteractor, PrefsManager prefsManager, PackageInfoInteractor mPackageInfoInteractor) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
        this.mActivity = context;
        this.mPackageInfoInteractor = mPackageInfoInteractor;


    }
    @Override
    public void setEmergencyAlertView(BaseContract.EmergencyAlertView emergencyAlertView) {
        this.mEmergencyAlertView=emergencyAlertView;
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

    @Override
    public void codeConfirmation(String codeID,String status) {
        CodeConfirmationRequest codeConfirmationRequest=new CodeConfirmationRequest();
        codeConfirmationRequest.setCodeId(codeID);
        codeConfirmationRequest.setCodeStatus(status);
        codeConfirmationRequest.setTimeToAttend(getTime());
        codeConfirmationRequest.setUserId(String.valueOf(mPrefsManager.getIntKeyValueFromPrefsByKey(KEY_USERID)));
        mApiInteractor.getCodeConformation(mActivity,mEmergencyAlertView,codeConfirmationRequest,mCodeConfirmationResponseLoadListener,true);
    }

    @Override
    public void getCodeInformation(String codeID) {
        CodeInfoRequest codeInfoRequest=new CodeInfoRequest();
        codeInfoRequest.setCodeId(codeID);
        mApiInteractor.getCodeInformation(mActivity,mEmergencyAlertView,codeInfoRequest,mCodeInformationResponseLoadListener,true);
    }

    private String getDate(){
        Date cDate = Calendar.getInstance().getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(cDate);
    }
    private String getTime(){
        Date cDate = Calendar.getInstance().getTime();
        return new SimpleDateFormat("hh:mm:ss").format(cDate);
    }


    @Override
    public void setView(BaseView view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


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
    LoadListener<CodeInformationResponse> mCodeInformationResponseLoadListener = new LoadListener<CodeInformationResponse>() {
        @Override
        public void onSuccess(CodeInformationResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mEmergencyAlertView.showConfirmationDialog(responseBody);
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

    LoadListener<CodeConfirmationResponse> mCodeConfirmationResponseLoadListener = new LoadListener<CodeConfirmationResponse>() {
        @Override
        public void onSuccess(CodeConfirmationResponse responseBody) {

            if (responseBody != null) {
                switch (responseBody.getMeta().getStatusType()) {
                    case STATUS_SUCCESS:
                        mEmergencyAlertView.setConfirmationResonse(responseBody);
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
