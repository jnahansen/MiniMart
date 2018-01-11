
package com.jbheng.minimart.picasso;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by jhansen on 1/11/18
 */
public class PicassoTrustAllSSL {
    private static final String TAG = PicassoTrustAllSSL.class.getName();

    private static Picasso mInstance = null;

    private PicassoTrustAllSSL(Context context) {
        mInstance = new Picasso.Builder(context)
                // Set logging for picasso on only is debug build
                //.loggingEnabled(Constants.DEBUG_ENABLED)
                .downloader(new UrlConnectionDownloader(context) {
                    @Override
                    protected HttpURLConnection openConnection(final Uri uri)
                            throws IOException {
                        HttpURLConnection connection = super.openConnection(uri);

                        if (connection instanceof HttpsURLConnection) {
                            ((HttpsURLConnection) connection)
                                    .setSSLSocketFactory(CustomSSLSocketFactory.getSocketFactory());
                            ((HttpsURLConnection) connection)
                                    .setHostnameVerifier(CustomSSLSocketFactory.getHostNameVerifier());
                        }
                        return connection;
                    }

                })
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.e(TAG, "onImageLoadFailed: exception: " + String.valueOf(exception));
                    }
                }).build();
    }

    public static Picasso getInstance(Context context) {
        if (mInstance == null) {
            new PicassoTrustAllSSL(context);
        }
        return mInstance;
    }

}