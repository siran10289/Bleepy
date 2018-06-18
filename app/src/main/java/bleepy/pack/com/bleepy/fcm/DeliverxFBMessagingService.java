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
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import bleepy.pack.com.bleepy.R;

public class DeliverxFBMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MessagingService";
    private int notificationID;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        /*try {
            Log.d(TAG, "From: " + remoteMessage.getFrom());


            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            }

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                notificationID++;
                Log.e("Notification:",remoteMessage.getNotification().getBody().toString());
                Log.d(TAG, "Activity -----> " + Application.isActivityVisible());
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
                Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
                Log.d(TAG, "Message Notification modid: " + remoteMessage.getData().get("modid"));
                Log.d(TAG, "Message Notification articleid: " + remoteMessage.getData().get("articleid"));

                for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    // do stuff
                    Log.d(TAG, "Message Notification Tag: " + key + " --- " + value);
                }

                sendNotification(remoteMessage);

            }
        }catch (Exception e){
            Log.e("Exception:",e.toString());
        }*/

    }

   /* private void sendNotification(RemoteMessage remoteMessage) {
        Intent intents = new Intent(this, SplashActivity.class);
        intents.putExtra(Constants.TAG_Notification, "Noti");
        intents.putExtra(Constants.TAG_Module_ID, remoteMessage.getData().get(Constants.TAG_Module_ID));
        intents.putExtra(Constants.TAG_Module_Name, remoteMessage.getData().get(Constants.TAG_Module_Name));
        intents.putExtra(Constants.TAG_Article_ID, remoteMessage.getData().get(Constants.TAG_Article_ID));
        intents.putExtra("type", "home");

        intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intents,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(remoteMessage.getNotification().getTitle())
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);



        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationID *//* ID of notification *//*, notificationBuilder.build());
    }*/


}
