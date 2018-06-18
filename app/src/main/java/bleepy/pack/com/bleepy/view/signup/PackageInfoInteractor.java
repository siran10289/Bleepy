package bleepy.pack.com.bleepy.view.signup;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by Siranjeevi on 19/07/17.
 */
public interface PackageInfoInteractor {

    Intent getPhotoCaptureIntent();

    Intent getPhotoGalleryIntent();

    boolean checkForCameraPermission();

    void getPermissions(PermissionGrantedListener permissionGrantedListener);

    void removeTempFiles();

    void handleCapturedPhoto(ImageProcessingListener processingListener);

    void handleGalleryPhoto(Uri uri, ImageProcessingListener processingListener);

    File getPhotoFile();

    String getBitmapImg();

}
