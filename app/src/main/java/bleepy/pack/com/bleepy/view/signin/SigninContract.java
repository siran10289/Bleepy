package bleepy.pack.com.bleepy.view.signin;

import android.content.Intent;
import android.widget.EditText;

import bleepy.pack.com.bleepy.models.signin.SigninResponse;
import bleepy.pack.com.bleepy.view.base.BasePresenter;
import bleepy.pack.com.bleepy.view.base.BaseView;


/**
 * Created by siranjeevi on 09/06/17.
 */
public interface SigninContract {

    interface SigninView extends BaseView {


        void openDeviceRegistrationDialog(SigninResponse response);
        void navigateToDeviceRegistration();
        void navigateToKeyRegistration();
        void navigateToDashBoard(String userID);

    }
    interface ForgotPasswordView extends BaseView{
        String getEmailID();
       /* void setErrorMessage(String message);
        void setForgetUnamePswdStatus(SignupResponse signupResponse);
        void setValidateOTPStatus(SignupResponse signupResponse);*/
    }


    interface Presenter extends BasePresenter {
        void setSigninView(SigninView signinView);
        void setForgetPasswordView(ForgotPasswordView forgetPasswordView);
        void validateUserLogin(String userName, String passWord);
        void forgotUserNamePassword(String dialCode, String mobileNumber, String emailID, String otp);
        void checkOldCredentials(EditText etUserName, EditText etPassword);
        void saveCredentials(String userName, String password);
        void onActivityResult(int requestCode, int resultCode, Intent intent);
        void onForgetCredentials(int type);
        void updateDeviceInfo(String fcmToken, String deviceType);

    }

}
