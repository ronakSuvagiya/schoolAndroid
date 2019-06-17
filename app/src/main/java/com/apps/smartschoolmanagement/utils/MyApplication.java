package com.apps.smartschoolmanagement.utils;

import android.app.Application;
import android.content.Context;
//import com.splunk.mint.Mint;

public class MyApplication extends Application {
    private static boolean activityVisible;

    public void onCreate() {
        super.onCreate();
        Context mContext = getApplicationContext();
//        Mint.initAndStartSession(this, "aea11b3a");
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }
}
