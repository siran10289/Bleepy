package bleepy.pack.com.bleepy.view.signup;

/**
 * Created by Siranjeevi on 19/7/17.
 */
public interface PermissionGrantedListener {

    void onPermissionDenied();

    void onPermissionGranted();
}
