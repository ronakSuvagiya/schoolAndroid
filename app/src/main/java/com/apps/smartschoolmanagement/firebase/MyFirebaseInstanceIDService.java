package com.apps.smartschoolmanagement.firebase;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService
//        extends FirebaseInstanceIdService
{
    private static final String TAG = "MyFirebaseIIDService";

    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token) {
    }
}
