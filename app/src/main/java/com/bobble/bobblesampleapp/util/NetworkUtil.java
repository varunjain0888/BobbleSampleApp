package com.bobble.bobblesampleapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by varunjain on 26/06/17.
 */

public final class NetworkUtil {

    private NetworkUtil() {
        // This utility class is not publicly instantiable.
    }


    public static boolean isInternetConnected(Context context) {
        try {
            final ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo info = cm.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isWifiConnected(Context context) {
        final ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo mWifi = conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi != null && mWifi.isConnected();
    }
}
