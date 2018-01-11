package com.jbheng.minimart;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.jbheng.minimart.picasso.PicassoTrustAllSSL;
import com.squareup.picasso.Picasso;

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

    // For Picasso image rendering
    public static void setIconUsingPicasso(String iconUrl, ImageView iv) {
        int defaultDrawableId = R.drawable.null_icon;
        try {
            if (TextUtils.isEmpty(iconUrl))
                Log.w(TAG, "setIconUsingPicasso: iconUrl is null or empty, using default");
        } catch (Throwable t) {
            Log.e(TAG, "setIconUsingPicasso: exception: ", t);
            return;
        }
        try {
            if (iconUrl == null || iconUrl.startsWith(Constants.HTTP)) {
                // NOTE: PicassoTrustAllSSL only works for http and https, not file:/// scheme
                // If server specified a url for retreive an icon, have Picasso fetch/cache it
                // Use Picasso to lazy load image from Web and attach to view
                if (defaultDrawableId != Constants.INVALID)
                    PicassoTrustAllSSL.getInstance(App.getMyAppContext())
                            .load(iconUrl)
                            .fit()
                            .placeholder(App.getMyAppContext().getResources().getDrawable(defaultDrawableId))     // null app icon
                            .into(iv);
                else    // no placeholder
                    PicassoTrustAllSSL.getInstance(App.getMyAppContext())
                            .load(iconUrl)
                            .fit()
                            .into(iv);
            } else { // if (iconUrl.startsWith(Constants.FILE_SCHEME)) {
                try {
                    // temporary: Invalidate cache for images from storage and other non-http* schemes
                    Picasso.with(App.getMyAppContext()).invalidate(iconUrl);
                    // load url
                    Picasso.with(App.getMyAppContext())
                            .load(iconUrl)
                            .fit()
                            .placeholder(App.getMyAppContext().getResources().getDrawable(defaultDrawableId))
                            .into(iv);
                } catch (Throwable t) {
                    Log.e(TAG, "setIconUsingPicasso: exception: ", t);
                }
            }
        } catch (Throwable t) {
            Log.e(TAG, "setIconUsingPicasso: exception: ", t);
        }
    }


}
