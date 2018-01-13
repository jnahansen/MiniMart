package com.jbheng.minimart;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

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

    public static boolean hasNetwork(Context c) {
        if (!Utils.isNetworkConnected(c)) {
            Toast.makeText(c, c.getString(R.string.no_network), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    // For Picasso image rendering
    public static void setIconUsingPicasso(String iconUrl, ImageView iv) {
        int defaultDrawableId = R.drawable.null_icon;
        if (TextUtils.isEmpty(iconUrl))
            Log.e(TAG, "setIconUsingPicasso: iconUrl is null or empty, using default");
        if (iv == null) {
            Log.e(TAG, "setIconUsingPicasso: imageView is null or empty, leaving");
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

    public static boolean isFragmentVisible(FragmentManager fm,String tag) {
        Fragment frag = fm.findFragmentByTag(tag);
        return frag != null && frag.isVisible();
    }

}
