package com.jbheng.minimart;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jhansen on 10/2/17.
 */

public class Utils {

    public static final String TAG = Utils.class.getName();

    // See if we have network connectivity e.g. Wifi or LTE
    // WARNING: this method does not guarantee any URL is reachable!
    public static boolean isNetworkConnected(Context c) {
        boolean result = false;
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isAvailable())
                result = netInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            Log.e(TAG, "isNetworkConnected: exception: " + e.getMessage());
            e.printStackTrace();
        }
        Log.i(TAG, "isNetworkConnected: result: " + String.valueOf(result));
        return result;
    }


}
