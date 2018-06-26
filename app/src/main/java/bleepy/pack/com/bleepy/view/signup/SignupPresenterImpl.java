package bleepy.pack.com.bleepy.view.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.interactor.ApiInteractor;
import bleepy.pack.com.bleepy.models.common.ImageUploadResponse;
import bleepy.pack.com.bleepy.models.common.UserImageUploadRequest;
import bleepy.pack.com.bleepy.models.common.WelcomeScreenRequest;
import bleepy.pack.com.bleepy.models.common.WelcomeScreenResponse;
import bleepy.pack.com.bleepy.models.signin.SigninResponse;
import bleepy.pack.com.bleepy.models.signup.RegisterUserRequest;
import bleepy.pack.com.bleepy.models.signup.SignupResponse;
import bleepy.pack.com.bleepy.models.signup.UpdateDeviceInfoReq;
import bleepy.pack.com.bleepy.models.signup.UpdateDeviceInfoResponse;
import bleepy.pack.com.bleepy.models.signup.UpdateRegKeyRequest;
import bleepy.pack.com.bleepy.models.signup.UpdateRegKeyResponse;
import bleepy.pack.com.bleepy.utils.Constants;
import bleepy.pack.com.bleepy.utils.Validation;
import bleepy.pack.com.bleepy.utils.preferences.PrefsManager;
import bleepy.pack.com.bleepy.view.base.BaseView;
import bleepy.pack.com.bleepy.view.base.LoadListener;

import static bleepy.pack.com.bleepy.utils.Constants.DEBUG_MODE;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_IMAGE_BYTE;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_IS_VALID_USER;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_USERID;
import static bleepy.pack.com.bleepy.utils.Constants.STATUS_FAILURE;
import static bleepy.pack.com.bleepy.utils.Constants.STATUS_SUCCESS;

/**
 * Created by siranjeevi on 9/7/17.
 */
public class SignupPresenterImpl implements SignupContract.Presenter {

    private PrefsManager mPrefsManager;
    private ApiInteractor mApiInteractor;
    private SignupContract.RegisterationView mRegistrationView;
    private SignupContract.UpdateRegKeyView mUpdateRegKeyView;
    private Context mContext;
    private PackageInfoInteractor mPackageInfoInteractor;
    private SignupContract.SplashScreenView mSplashScreenView;
    private String deviceType;


    public SignupPresenterImpl(Context context, ApiInteractor apiInteractor, PrefsManager prefsManager, PackageInfoInteractor mPackageInfoInteractor) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
        this.mContext = context;
        this.mPackageInfoInteractor = mPackageInfoInteractor;

    }


    @Override
    public void setRegistrationView(SignupContract.RegisterationView registrationView) {
        this.mRegistrationView = registrationView;
    }

    @Override
    public void setUpdateRegKeyView(SignupContract.UpdateRegKeyView updateRegKeyView) {
        this.mUpdateRegKeyView=updateRegKeyView;
    }

    @Override
    public void setSplashScreenView(SignupContract.SplashScreenView splashScreenView) {
        this.mSplashScreenView=splashScreenView;
    }

    @Override
    public void registerUser() {
        if (validateUserInput()) {
                RegisterUserRequest registerUserRequest = new RegisterUserRequest();
                registerUserRequest.setFirstname(mRegistrationView.getFirstName());
                registerUserRequest.setLastname(mRegistrationView.getLastName());
                registerUserRequest.setEmailid(mRegistrationView.getEmailID());
                registerUserRequest.setBleepId("0");
                registerUserRequest.setDesignation(mRegistrationView.getDesingnation());
                registerUserRequest.setStaffId(mRegistrationView.getStaffID());
                registerUserRequest.setMobile(mRegistrationView.getMobile());
                registerUserRequest.setRtype("1");
                registerUserRequest.setFcmToken(mRegistrationView.fcmToken());
                registerUserRequest.setPassword(mRegistrationView.getPassword());
                registerUserRequest.setUserProfilePic(mRegistrationView.getUserProfilePicUrl());
                Log.e("Request:",new Gson().toJson(registerUserRequest).toString());
                mApiInteractor.registerUser(mRegistrationView, registerUserRequest,mUserSignUpListener,true );

        }

    }

    public boolean validatePassword(String pass) {
        if (pass.length() == 0) {
            mRegistrationView.showErrorDialog(R.string.error_enter_valid_password_1);
            return false;
        } else if (pass.length() < 8) {
            mRegistrationView.showErrorDialog(R.string.enter_min_character_password);
            return false;
        } else if (pass.length() > 12) {
            mRegistrationView.showErrorDialog(R.string.enter_min_character_maxpassword);
            return false;
        }
        return true;
    }


    @Override
    public void snapPhotoClick() {
        if (mPackageInfoInteractor.checkForCameraPermission()) {
            capturePhotoFromCamera();
        } else {
            mPackageInfoInteractor.getPermissions(cameraPermissionGrantedListener);
        }
    }

    @Override
    public void pickFromGalleryClick() {
        if (mPackageInfoInteractor.checkForCameraPermission()) {
            selectPictureFromGallery();
        } else {
            mPackageInfoInteractor.getPermissions(galleryPermissionGrantedListener);
        }
    }

    @Override
    public void uploadUserImage(String base64) {
        if (validateUserInput()) {
                if (!(base64.length()==0)) {
                    UserImageUploadRequest userImageUploadRequest = new UserImageUploadRequest();
                    userImageUploadRequest.setProfileImage(base64);
                    if(DEBUG_MODE)Log.e("Request:",new Gson().toJson(userImageUploadRequest).toString());
                    mApiInteractor.uploadProfilePicture(mRegistrationView, userImageUploadRequest, mImageUploadListener, true);
                }else{
                    mRegistrationView.showErrorDialog(mContext.getString(R.string.select_profile_picture));
                }

        }
    }

    @Override
    public void updateDeviceInfo(String deviceType, String fcmToken) {
        this.deviceType=deviceType;
        UpdateDeviceInfoReq updateDeviceInfoReq=new UpdateDeviceInfoReq();
        updateDeviceInfoReq.setFCMToken(fcmToken);
        updateDeviceInfoReq.setDevicetype(deviceType);
        if(DEBUG_MODE)Log.e("Request:",new Gson().toJson(updateDeviceInfoReq).toString());
        mApiInteractor.updateDeviceInfo(mRegistrationView,updateDeviceInfoReq,mUpdateDeviceInfoResponseLoadListener,true);
    }

    private void capturePhotoFromCamera() {
        mRegistrationView.openActivityForResult(mPackageInfoInteractor.getPhotoCaptureIntent(), Constants.REQUEST_CAPTURE_PHOTO);
    }

    private void selectPictureFromGallery() {
        mRegistrationView.openActivityForResult(Intent.createChooser(mPackageInfoInteractor.getPhotoGalleryIntent(), "Choose photo"), Constants.REQUEST_GALLERY_PHOTO);
    }


    @Override
    public void setView(BaseView view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.REQUEST_GALLERY_PHOTO) {
                mPackageInfoInteractor.handleGalleryPhoto(data.getData(), imageProcessingListener);
            } else if (requestCode == Constants.REQUEST_CAPTURE_PHOTO) {
                mPackageInfoInteractor.handleCapturedPhoto(imageProcessingListener);
            } else if (requestCode == Constants.REQUEST_CODE_CLOSE) {
                closeActivityOnResult(data);
            }
        } else if (requestCode == Constants.CROPING_CODE) {
            if (data != null)
                mRegistrationView.setImageOnImageView(data.getByteArrayExtra(KEY_IMAGE_BYTE));
        }

    }

    @Override
    public void haveEmailID(String userRegType) {
        UpdateRegKeyRequest updateRegKeyRequest=new UpdateRegKeyRequest();
        updateRegKeyRequest.setUserRegType("0");
        mApiInteractor.updateRegKey(mUpdateRegKeyView,updateRegKeyRequest,mHaveEmailIDResponseLoadListener,true);
    }

    @Override
    public void hasRegistrationKey() {
      if(validateRegKey()){
          UpdateRegKeyRequest updateRegKeyRequest=new UpdateRegKeyRequest();
          updateRegKeyRequest.setServername(mUpdateRegKeyView.getServerName());
          updateRegKeyRequest.setRegistrationkey(mUpdateRegKeyView.getRegKey());
          updateRegKeyRequest.setUserRegType("1");
          Log.e("Request:",new Gson().toJson(updateRegKeyRequest).toString());
          mApiInteractor.updateRegKey(mUpdateRegKeyView,updateRegKeyRequest,mUpdateRegKeyResponseLoadListener,true);
      }
    }
    public boolean validateRegKey(){
        if(mUpdateRegKeyView.getRegKey().isEmpty()){
            mUpdateRegKeyView.showErrorDialog(mContext.getString(R.string.enter_reg_key));
            return false;
        }else if(mUpdateRegKeyView.getServerName().isEmpty()){
            mUpdateRegKeyView.showErrorDialog(mContext.getString(R.string.enter_server_name));
            return false;
        }
        return true;
    }

    @Override
    public void hasUnamePswd() {
        if(validateUNamePswd()){
            UpdateRegKeyRequest updateRegKeyRequest=new UpdateRegKeyRequest();
            updateRegKeyRequest.setServername(mUpdateRegKeyView.getServerName());
            updateRegKeyRequest.setUsername(mUpdateRegKeyView.userName());
            updateRegKeyRequest.setPassword(mUpdateRegKeyView.password());
            updateRegKeyRequest.setUserRegType("2");
            mApiInteractor.updateRegKey(mUpdateRegKeyView,updateRegKeyRequest,mUpdateRegKeyResponseLoadListener,true);

        }

    }

    @Override
    public void getWelcomeScreens() {
        WelcomeScreenRequest welcomeScreenRequest=new WelcomeScreenRequest();
        welcomeScreenRequest.setAppid(mSplashScreenView.getFCMToken());
        welcomeScreenRequest.setLocationID(mSplashScreenView.locationID());
        mApiInteractor.getWelcomeScreens(mSplashScreenView,welcomeScreenRequest,mWelcomeScreenResponseLoadListener,true);
    }

    public boolean validateUNamePswd(){
         if(mUpdateRegKeyView.getServerName().isEmpty()){
            mUpdateRegKeyView.showErrorDialog(mContext.getString(R.string.enter_server_name));
            return false;
        }else if(mUpdateRegKeyView.userName().isEmpty()){
            mUpdateRegKeyView.showErrorDialog(mContext.getString(R.string.error_enter_username));
            return false;
        }else if(mUpdateRegKeyView.password().isEmpty()){
             mUpdateRegKeyView.showErrorDialog(mContext.getString(R.string.error_enter_valid_password_1));
             return false;
         }
        return true;
    }

    private void closeActivityOnResult(Intent data) {
        mRegistrationView.setResultAndCloseActivity(data);
    }
    boolean validateUserInput() {
        if (mRegistrationView.getFirstName().length() == 0) {
            mRegistrationView.showErrorDialog(R.string.enter_first_name);
            return false;
        } else if (mRegistrationView.getFirstName().length() <= 2) {
            mRegistrationView.showErrorDialog(R.string.enter_min_character_first_name);
            return false;
        } else if (mRegistrationView.getLastName().length() == 0) {
            mRegistrationView.showErrorDialog(R.string.enter_last_name);
            return false;
        } else if (mRegistrationView.getLastName().length() <= 2) {
            mRegistrationView.showErrorDialog(R.string.enter_min_character_last_name);
            return false;
        } else if (mRegistrationView.getEmailID().length() == 0) {
            mRegistrationView.showErrorDialog(R.string.error_enter_email);
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mRegistrationView.getEmailID()).matches()) {
            mRegistrationView.showErrorDialog(R.string.error_enter_valid_email);
            return false;
        } else if (mRegistrationView.getDesingnation().isEmpty()) {
            mRegistrationView.showErrorDialog(R.string.error_enter_designation);
            return false;
        } else if (mRegistrationView.getStaffID().isEmpty()) {
            mRegistrationView.showErrorDialog(R.string.error_enter_staffid);
            return false;
        } /*else if (mRegistrationView.getPassword().length() == 0) {
            mRegistrationView.showErrorDialog(R.string.error_enter_valid_password_1);
            return false;
        }*/
        return true;
    }


    private PermissionGrantedListener cameraPermissionGrantedListener = new PermissionGrantedListener() {
        @Override
        public void onPermissionDenied() {

        }

        @Override
        public void onPermissionGranted() {
            capturePhotoFromCamera();
        }
    };
    private ImageProcessingListener imageProcessingListener = new ImageProcessingListener() {
        @Override
        public void onProcessingStart() {
            // mRegistrationView.showProgress();
        }

        @Override
        public void onProcessingComplete(String imageUri) {
            mRegistrationView.navigateToImageCropper(imageUri);
        }
    };
    private PermissionGrantedListener galleryPermissionGrantedListener =
            new PermissionGrantedListener() {
                @Override
                public void onPermissionDenied() {

                }

                @Override
                public void onPermissionGranted() {
                    selectPictureFromGallery();
                }
            };

    LoadListener<SignupResponse> mUserSignUpListener = new LoadListener<SignupResponse>() {
        @Override
        public void onSuccess(SignupResponse responseBody) {

            if(responseBody!=null){
                if(responseBody!=null){
                    switch (responseBody.getMeta().getStatusType()){
                        case STATUS_SUCCESS:

                            mRegistrationView.showSelectDeviceTypeDialog(responseBody);
                            break;
                        case STATUS_FAILURE:
                            mRegistrationView.showErrorDialog(responseBody.getMeta().getMessage());
                            break;
                    }

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
    LoadListener<ImageUploadResponse> mImageUploadListener = new LoadListener<ImageUploadResponse>() {
        @Override
        public void onSuccess(ImageUploadResponse responseBody) {

            if(responseBody!=null){
                switch (responseBody.getMeta().getStatusType()){
                    case STATUS_SUCCESS:
                        mRegistrationView.setUserProfilePicUrl(responseBody.getData().getProfileImage());
                        registerUser();
                        break;
                    case STATUS_FAILURE:
                        mRegistrationView.showErrorDialog(responseBody.getMeta().getMessage());
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
    LoadListener<UpdateDeviceInfoResponse> mUpdateDeviceInfoResponseLoadListener = new LoadListener<UpdateDeviceInfoResponse>() {
        @Override
        public void onSuccess(UpdateDeviceInfoResponse responseBody) {

            if(responseBody!=null){
                switch (responseBody.getMeta().getStatusType()){
                    case STATUS_SUCCESS:
                        if(deviceType.equalsIgnoreCase("0")){
                            mRegistrationView.navigateToDeviceRegistration();
                        }else if(deviceType.equalsIgnoreCase("1")){
                            mRegistrationView.navigateToKeyRegistration();
                        }

                        break;
                    case STATUS_FAILURE:
                        mRegistrationView.showErrorDialog(responseBody.getMeta().getMessage());
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

    LoadListener<UpdateRegKeyResponse> mUpdateRegKeyResponseLoadListener = new LoadListener<UpdateRegKeyResponse>() {
        @Override
        public void onSuccess(UpdateRegKeyResponse responseBody) {
            Log.e("Response:",new Gson().toJson(responseBody).toString());
            if(responseBody!=null){
                switch (responseBody.getMeta().getStatusType()){
                    case STATUS_SUCCESS:
                        if(responseBody.getData()!=null) {
                            mPrefsManager.saveBooleanKeyValueToPrefs(KEY_IS_VALID_USER, true);
                            mPrefsManager.saveKeyValuePairToPrefs(KEY_USERID, responseBody.getData().getUserid());
                            mUpdateRegKeyView.navigateEmailRegistration(responseBody);
                        }
                        break;
                    case STATUS_FAILURE:
                        mUpdateRegKeyView.showErrorDialog(responseBody.getMeta().getMessage());
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
    LoadListener<UpdateRegKeyResponse> mHaveEmailIDResponseLoadListener = new LoadListener<UpdateRegKeyResponse>() {
        @Override
        public void onSuccess(UpdateRegKeyResponse responseBody) {
            Log.e("Response:",new Gson().toJson(responseBody).toString());
            if(responseBody!=null){
                switch (responseBody.getMeta().getStatusType()){
                    case STATUS_SUCCESS:
                        mUpdateRegKeyView.navigateEmailRegistration( responseBody);
                        //mPrefsManager.saveBooleanKeyValueToPrefs(KEY_IS_VALID_USER,true);
                        //mPrefsManager.saveKeyValuePairToPrefs(KEY_USERID,responseBody.getData().getUserid());
                        break;
                    case STATUS_FAILURE:
                        mUpdateRegKeyView.showErrorDialog(responseBody.getMeta().getMessage());
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
    LoadListener<WelcomeScreenResponse> mWelcomeScreenResponseLoadListener = new LoadListener<WelcomeScreenResponse>() {
        @Override
        public void onSuccess(WelcomeScreenResponse responseBody) {

            if(responseBody!=null){
                switch (responseBody.getMeta().getStatusType()){
                    case STATUS_SUCCESS:
                        mSplashScreenView.navigateToWelcomeScreen(responseBody);
                        break;
                    case STATUS_FAILURE:
                        mSplashScreenView.showErrorDialog(responseBody.getMeta().getMessage());
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
