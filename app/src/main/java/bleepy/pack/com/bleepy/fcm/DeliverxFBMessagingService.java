package bleepy.pack.com.bleepy.fcm;

/**
 * Created by kavi on 14/08/16.
 */


import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import bleepy.pack.com.bleepy.R;
import bleepy.pack.com.bleepy.utils.AppUtils;
import bleepy.pack.com.bleepy.utils.Constants;
import bleepy.pack.com.bleepy.view.Dashboard.DashboardActivity;
import bleepy.pack.com.bleepy.view.base.BaseActivity;
import bleepy.pack.com.bleepy.view.welcome.SplashScreenActivity;

import static bleepy.pack.com.bleepy.utils.Constants.ACTION_INTENT_FCM_RECIEVED;
import static bleepy.pack.com.bleepy.utils.Constants.KEY_NOTI_TYPE;

public class DeliverxFBMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MessagingService";
    private int notificationID=0;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            }

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                notificationID++;
                Log.e("Notification:",remoteMessage.getNotification().getBody().toString());

                for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    // do stuff
                    Log.d(TAG, "Message Notification Tag: " + key + " --- " + value);
                }

                if(AppUtils.isAppIsInBackground(getApplicationContext())) {
                    sendNotification(remoteMessage);

                }else {
                    Intent mIntent = new Intent(ACTION_INTENT_FCM_RECIEVED);
                    mIntent.putExtra(Constants.KEY_DESCRIPTION, remoteMessage.getData().get(Constants.KEY_DESCRIPTION));
                    mIntent.putExtra(Constants.KEY_CODE_CREATED, remoteMessage.getData().get(Constants.KEY_CODE_CREATED));
                    mIntent.putExtra(Constants.KEY_CODE_ID, remoteMessage.getData().get(Constants.KEY_CODE_ID));
                    mIntent.putExtra(Constants.KEY_VOICE_DATA, remoteMessage.getData().get(Constants.KEY_VOICE_DATA));
                    mIntent.putExtra(Constants.KEY_RESPONDERS, remoteMessage.getData().get(Constants.KEY_RESPONDERS));
                    mIntent.putExtra(Constants.KEY_LOCATION, remoteMessage.getData().get(Constants.KEY_LOCATION));
                    mIntent.putExtra(Constants.KEY_NOTIFICATION_TYPE,remoteMessage.getData().get(Constants.KEY_NOTIFICATION_TYPE));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(mIntent);
                }

            }
        }catch (Exception e){
            Log.e("Exception:",e.toString());
        }

    }

    private void sendNotification(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.putExtra(KEY_NOTI_TYPE,true);

        intent.putExtra(Constants.KEY_DESCRIPTION, remoteMessage.getData().get(Constants.KEY_DESCRIPTION));
        intent.putExtra(Constants.KEY_CODE_CREATED, remoteMessage.getData().get(Constants.KEY_CODE_CREATED));
        intent.putExtra(Constants.KEY_CODE_ID, remoteMessage.getData().get(Constants.KEY_CODE_ID));
        intent.putExtra(Constants.KEY_VOICE_DATA, remoteMessage.getData().get(Constants.KEY_VOICE_DATA));
        intent.putExtra(Constants.KEY_RESPONDERS, remoteMessage.getData().get(Constants.KEY_RESPONDERS));
        intent.putExtra(Constants.KEY_LOCATION, remoteMessage.getData().get(Constants.KEY_LOCATION));
        intent.putExtra(Constants.KEY_NOTIFICATION_TYPE,remoteMessage.getData().get(Constants.KEY_NOTIFICATION_TYPE));

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.mipmap.ic_launcher,1)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(remoteMessage.getNotification().getTitle())
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        /*TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(intent);
        notificationBuilder.setContentIntent(stackBuilder.getPendingIntent(notificationID,PendingIntent.FLAG_UPDATE_CURRENT));
*/
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationID,notificationBuilder.build());
    }


}
