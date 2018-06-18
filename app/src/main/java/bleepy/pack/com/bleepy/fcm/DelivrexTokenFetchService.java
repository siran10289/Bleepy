package bleepy.pack.com.bleepy.fcm;

/**
 * Created by siranjeevi on 07/06/17.
 */

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


import javax.inject.Inject;


public class DelivrexTokenFetchService extends FirebaseInstanceIdService {


    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onCreate() {
        super.onCreate();

    }
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
    }

    private void sendRegistrationToServer(String token) {

        // TODO: Implement this method to send token to your app server.
    }
}