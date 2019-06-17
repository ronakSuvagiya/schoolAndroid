package com.apps.smartschoolmanagement.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat.Builder;
import android.util.Log;
import com.google.common.primitives.Ints;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.HomeActivity;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {
            try {
                sendNotification(remoteMessage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendNotification(RemoteMessage remoteMessage) throws JSONException {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, Ints.MAX_POWER_OF_TWO);
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(2);
        JSONObject jsonObject1 = new JSONObject(remoteMessage.getData().toString()).getJSONObject("data");
        Builder notificationBuilder = new Builder(this, channelId).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(jsonObject1.getString("title")).setContentText(jsonObject1.getString("message")).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
        notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationBuilder.setLights(getResources().getColor(17170443), 1000, 1000);
        notificationBuilder.addAction(R.mipmap.ic_launcher, "", pendingIntent);
        notificationBuilder.setColor(getResources().getColor(17170443));
        ((NotificationManager) getSystemService("notification")).notify((int) Calendar.getInstance().getTimeInMillis(), notificationBuilder.build());
    }
}
