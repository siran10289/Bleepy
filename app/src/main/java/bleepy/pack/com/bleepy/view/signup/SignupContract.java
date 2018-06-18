package bleepy.pack.com.bleepy.view.signup;

import android.content.Intent;
import android.widget.EditText;

import bleepy.pack.com.bleepy.models.common.WelcomeScreenResponse;
import bleepy.pack.com.bleepy.models.signup.UpdateRegKeyResponse;
import bleepy.pack.com.bleepy.view.base.BasePresenter;
import bleepy.pack.com.bleepy.view.base.BaseView;


/**
 * Created by siranjeevi on 09/06/17.
 */
public interface SignupContract {

    interface RegisterationView extends BaseView {
        void navigateToImageCropper(String imageURI);
        void setImageOnImageView(byte[] imageByteArray);
        void showSelectDeviceTypeDialog();
        void setError(EditText editText, String message);
        String fcmToken();
        String getFirstName();
        String getLastName();
        String getEmailID();
        String getDesingnation();
        String getMobile();
        String getPassword();
        String getStaffID();
        void setUserProfilePicUrl(String url);
        String getUserProfilePicUrl();
        void navigateToDeviceRegistration();
        void navigateToKeyRegistration();

    }
    interface UpdateRegKeyView extends BaseView {

        void navigateEmailRegistration(UpdateRegKeyResponse response);
        String getServerName();
        String getRegKey();
        String userName();
        String password();
    }
    interface SplashScreenView extends BaseView {

        void navigateToWelcomeScreen(WelcomeScreenResponse welcomeScreenResponse);
        String getFCMToken();
        String locationID();

    }
    interface Presenter extends BasePresenter {
        void setRegistrationView(RegisterationView registrationView);
        void setUpdateRegKeyView(UpdateRegKeyView updateRegKeyView);
        void setSplashScreenView(SplashScreenView splashScreenView);
        void registerUser();
        void snapPhotoClick();
        void pickFromGalleryClick();
        void uploadUserImage(String base64);
        void updateDeviceInfo(String deviceType,String fcmToken);
        void onActivityResult(int requestCode, int resultCode, Intent intent);
        void haveEmailID(String userRegType);
        void hasRegistrationKey();
        void hasUnamePswd();
        void getWelcomeScreens();


    }

}
