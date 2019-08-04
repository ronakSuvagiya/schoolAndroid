package com.apps.smartschoolmanagement.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;

import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.utils.URLs;
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
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private NotificationManager mNotificationManager;
    private Context mContext;

    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {
            try {
//                JSONObject jsonObject1 = new JSONObject(remoteMessage.getData().toString()).getJSONObject("data");
//                Toast.makeText(this,"send notification",Toast.LENGTH_LONG).show();
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

//        Intent resultIntent = new Intent(this ,HomeActivity.class);
//        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,
//                0 /* Request code */, resultIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(2);
        JSONObject jsonObject1 = new JSONObject(remoteMessage.getData().toString()).getJSONObject("data");
        Builder notificationBuilder = new Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher).
                        setContentTitle(jsonObject1.getString("title"))
                .setContentText(jsonObject1.getString("message"))
                .setAutoCancel(true).setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationBuilder.setLights(getResources().getColor(17170443), 1000, 1000);
        notificationBuilder.addAction(R.mipmap.ic_launcher, "", pendingIntent);
        notificationBuilder.setColor(getResources().getColor(17170443));
        ((NotificationManager) getSystemService("notification")).notify((int) Calendar.getInstance().getTimeInMillis(), notificationBuilder.build());


//        mBuilder = new NotificationCompat.Builder(mContext);
//        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        mBuilder.setContentTitle(jsonObject1.getString("title"))
//                .setContentText(jsonObject1.getString("message"))
//                .setAutoCancel(false)
//                .setSound(defaultSoundUri)
//                .setContentIntent(resultPendingIntent);
//
//        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
//        {
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.enableVibration(true);
//            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            assert mNotificationManager != null;
//            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
//            mNotificationManager.createNotificationChannel(notificationChannel);
//        }
//        assert mNotificationManager != null;
//        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
    }

    @Override
    public void onNewToken(String s) {
        SharedPreferences sp;
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String channel = (sp.getString("schoolid", ""));
        String stdid = (sp.getString("stdId", ""));
        String DeviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("tokan", s);
            jsonBody.put("deviceID", DeviceID);
            jsonBody.put("school", channel);
            jsonBody.put("std", stdid);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLs.updateToken, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int code = Integer.parseInt(String.valueOf(response.get("code")));
                        if(code == 200)
                        {
                            Toast.makeText(MyFirebaseMessagingService.this,"token Update Successfully!",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MyFirebaseMessagingService.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MyFirebaseMessagingService.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

            MyRequestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
