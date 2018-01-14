package com.jbheng.minimart;

import android.app.Application;
import android.content.Context;

/**
 * Created by jhansen on 1/10/18.
 */

    public class App extends Application {

        private static final String TAG = App.class.getName();

        // Let's cache app context for later use
        private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }

    // Sometimes App context is convenient to use.
    public static Context getMyAppContext() {
        return mContext;
    }


}
