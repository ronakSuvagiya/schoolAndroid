package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetDetector {
    private Context mcontext;

    public InternetDetector(Context context) {
        this.mcontext = context;
    }

    public boolean checkMobileInternetConn() {
        NetworkInfo activeNetwork = ((ConnectivityManager) this.mcontext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
            return false;
        }
        int networkType = activeNetwork.getType();
        if (networkType == 1 || networkType == 0) {
            return true;
        }
        return false;
    }
}
