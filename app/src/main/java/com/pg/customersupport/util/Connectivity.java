package com.pg.customersupport.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Check device's network connectivity and speed
 *
 * @author emil https://gist.github.com/emil2k/5130324
 */
public class Connectivity {

    /**
     * Get the network info
     *
     * @param context The current application context
     * @return current network information
     * @see ConnectivityManager
     * @see NetworkInfo
     */
    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     *
     * @param context The current application context
     * @return true if the device is connected to the network
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }
}
