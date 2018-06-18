package bleepy.pack.com.bleepy.view.signin;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.interactor.ApiInteractor;
import bleepy.pack.com.bleepy.models.common.CommonResponse;
import bleepy.pack.com.bleepy.models.signin.ForgetCredentialsRequest;
import bleepy.pack.com.bleepy.models.signin.SigninRequest;
import bleepy.pack.com.bleepy.models.signin.SigninResponse;
import bleepy.pack.com.bleepy.models.signup.UpdateDeviceInfoReq;
import bleepy.pack.com.bleepy.models.signup.UpdateDeviceInfoResponse;
import bleepy.pack.com.bleepy.utils.Validation;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.base.BaseView;
import bleepy.pack.com.bleepy.view.base.LoadListener;

import static bleepy.pack.com.bleepy.utils.Constants.DEBUG_MODE;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_IS_VALID_USER;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_PASSWORD;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_USERID;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_USERNAME;
import static bleepy.pack.com.bleepy.utils.Constants.STATUS_FAILURE;
import static bleepy.pack.com.bleepy.utils.Constants.STATUS_SUCCESS;


/**
 * Created by siranjeevi on 7/6/18.
 */
public class SigninPresenterImpl implements SigninContract.Presenter {

    private PrefsManager mPrefsManager;
    private ApiInteractor mApiInteractor;
    private SigninContract.SigninView mSigninView;
    private SigninContract.ForgotPasswordView mForgotPasswordView;
    private Context mContext;
    private String deviceType;


    public SigninPresenterImpl(Context context, ApiInteractor apiInteractor, PrefsManager prefsManager) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
        this.mContext=context;

    }
    @Override
    public void setSigninView(SigninContract.SigninView signinView) {
        this.mSigninView=signinView;
    }

    @Override
    public void setForgetPasswordView(SigninContract.ForgotPasswordView forgetPasswordView) {
        this.mForgotPasswordView=forgetPasswordView;
    }

    @Override
    public void validateUserLogin(String userName, String passWord) {
      if(isInputsValidatedWithNoErrors(userName,passWord)){
          SigninRequest signinRequest=new SigninRequest();
          signinRequest.setUsername(userName);
          signinRequest.setPassword(passWord);
          Log.e("Input:",new Gson().toJson(signinRequest).toString());
          mApiInteractor.validateUser(mSigninView, signinRequest, mUserSigninListener, true);
      }

    }

    @Override
    public void forgotUserNamePassword(String dialCode,String mobileNumber, String emailID,String otp) {
      /*if(isInputsValidatedForForgetCredentials(dialCode,mobileNumber,emailID)){
          ForgetUPRequest forgetUPRequest=new ForgetUPRequest();
          if(!TextUtils.isEmpty(mobileNumber)&&!TextUtils.isEmpty(dialCode)) {
              forgetUPRequest.setCountryCode(dialCode);
              forgetUPRequest.setMobileNo(mobileNumber);
          }else if(!TextUtils.isEmpty(emailID)) {
              forgetUPRequest.setEmail(emailID);
          }
          if(otp!=null){
              forgetUPRequest.setOtp(Integer.parseInt(otp));
              mApiInteractor.forgetUnamePswd(mForgotPasswordView, forgetUPRequest, mValidateOTPListener, true);
          }else{
              mApiInteractor.forgetUnamePswd(mForgotPasswordView, forgetUPRequest, mForgetUPListener, true);
          }
          Log.e("Request:",new Gson().toJson(forgetUPRequest).toString());

      }
*/
    }

    @Override
    public void checkOldCredentials(EditText etUsername,EditText etPassword) {
        String username=mPrefsManager.getKeyValueFromPrefsByKey(KEY_USERNAME);
        String password=mPrefsManager.getKeyValueFromPrefsByKey(KEY_PASSWORD);
        if(username!=null){
            etUsername.setText(username);
        }
        if(password!=null){
            etPassword.setText(password);
        }
    }

    @Override
    public void saveCredentials(String userName, String password) {
        if(userName!=null&&!userName.isEmpty()){
            mPrefsManager.saveKeyValuePairToPrefs(KEY_USERNAME,userName);
        }
        if(password!=null&&!password.isEmpty()){
            mPrefsManager.saveKeyValuePairToPrefs(KEY_PASSWORD,password);
        }
    }

    private Boolean isInputsValidatedWithNoErrors(String userName, String passWord) {
        Boolean isError = false;
        if (Validation.isEmpty(userName)) {
            mSigninView.setError(R.id.etUserName, mContext.getString(R.string.error_enter_username));
            isError = true;
        } else if (Validation.isEmpty(passWord)) {
            isError = true;
            mSigninView.setError(R.id.etPassword, mContext.getString(R.string.error_enter_valid_password_1));
        }
        return !isError;
    }
   /* private Boolean isInputsValidatedForForgetCredentials(String dialCode,String mobileNumber, String emailID) {
        Boolean isError;
        PhoneEditText phoneEditText=new PhoneEditText(mContext);
        if (!Validation.isEmpty(mobileNumber)) {
            if(!Validation.isEmpty(dialCode)){
                if (phoneEditText.isValid(dialCode+mobileNumber)) {
                    isError = false;
                }else{
                    isError = true;
                    mForgotPasswordView.setError(R.id.etPhoneNumber, mContext.getString(R.string.error_invalid_phone_number));
                }

            }else{
                isError = true;
                mForgotPasswordView.setError(R.id.etCountryCode, mContext.getString(R.string.error_enter_countrycode));
            }

        }else if(!Validation.isEmpty(emailID)) {
            if (Validation.validateEmail(emailID)) {
                isError = false;
            }else{
                isError = true;
                mForgotPasswordView.setError(R.id.etEmailAddress, mContext.getString(R.string.error_enter_valid_email));
            }

        }else{
            isError = true;
            mForgotPasswordView.setErrorMessage(mContext.getString(R.string.error_phonenumber_email));
        }
        return !isError;
    }*/


    @Override
    public void setView(BaseView view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }

    @Override
    public void onForgetCredentials(int type) {
        if(validateEmail()){
            ForgetCredentialsRequest forgetCredentialsRequest=new ForgetCredentialsRequest();
            forgetCredentialsRequest.setEmailid(mForgotPasswordView.getEmailID());
            mApiInteractor.forgetCredentials(mForgotPasswordView,forgetCredentialsRequest,mCommonResponseLoadListener,type,true);
        }
    }
    private boolean validateEmail(){
        if (mForgotPasswordView.getEmailID().length() == 0) {
            mForgotPasswordView.showErrorDialog(R.string.error_enter_email);
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mForgotPasswordView.getEmailID()).matches()) {
            mForgotPasswordView.showErrorDialog(R.string.error_enter_valid_email);
            return false;
        }
        return true;
    }
    @Override
    public void updateDeviceInfo(String fcmToken,String deviceType) {
        this.deviceType=deviceType;
        UpdateDeviceInfoReq updateDeviceInfoReq=new UpdateDeviceInfoReq();
        updateDeviceInfoReq.setFCMToken(fcmToken);
        updateDeviceInfoReq.setDevicetype(deviceType);
        if(DEBUG_MODE)Log.e("Request:",new Gson().toJson(updateDeviceInfoReq).toString());
        mApiInteractor.updateDeviceInfo(mSigninView,updateDeviceInfoReq,mUpdateDeviceInfoResponseLoadListener,true);
    }
    LoadListener<UpdateDeviceInfoResponse> mUpdateDeviceInfoResponseLoadListener = new LoadListener<UpdateDeviceInfoResponse>() {
        @Override
        public void onSuccess(UpdateDeviceInfoResponse responseBody) {

            if(responseBody!=null){
                switch (responseBody.getMeta().getStatusType()){
                    case STATUS_SUCCESS:
                        if(deviceType.equalsIgnoreCase("0")) {
                            mSigninView.navigateToDeviceRegistration();
                        }else if(deviceType.equalsIgnoreCase("1")){
                            mSigninView.navigateToKeyRegistration();
                        }

                        break;
                    case STATUS_FAILURE:
                        mSigninView.showErrorDialog(responseBody.getMeta().getMessage());
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

    LoadListener<SigninResponse> mUserSigninListener = new LoadListener<SigninResponse>() {
        @Override
        public void onSuccess(SigninResponse responseBody) {
            //mForgotPasswordView.setForgetUnamePswdStatus(responseBody);
            if(responseBody!=null){
                switch (responseBody.getMeta().getStatusType()){
                    case STATUS_SUCCESS:
                        if(responseBody.getData()!=null&&responseBody.getData()!=null){
                            if(responseBody.getData().getUserid()!=null){

                                if(responseBody.getData().getUserStatus()==1){
                                      mPrefsManager.saveBooleanKeyValueToPrefs(KEY_IS_VALID_USER,true);
                                      mPrefsManager.saveKeyValuePairToPrefs(KEY_USERID,Integer.parseInt(responseBody.getData().getUserid()));
                                    mSigninView.navigateToDashBoard(responseBody.getData().getUserid());
                                }else if(responseBody.getData().getUserStatus()==0){
                                    mSigninView.openDeviceRegistrationDialog(responseBody);
                                }

                            }

                        }
                        break;
                    case STATUS_FAILURE:
                        mSigninView.showErrorDialog(responseBody.getMeta().getMessage());
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
    LoadListener<CommonResponse> mCommonResponseLoadListener = new LoadListener<CommonResponse>() {
        @Override
        public void onSuccess(CommonResponse responseBody) {
            //mForgotPasswordView.setForgetUnamePswdStatus(responseBody);
            if(responseBody!=null){
                switch (responseBody.getMeta().getStatusType()){
                    case STATUS_SUCCESS:
                        if(responseBody.getMeta()!=null){
                            mForgotPasswordView.showErrorDialog(responseBody.getMeta().getMessage());
                        }
                        break;
                    case STATUS_FAILURE:
                        mForgotPasswordView.showErrorDialog(responseBody.getMeta().getMessage());
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
