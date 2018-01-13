package com.jbheng.minimart;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by jhansen on 1/13/18.
 */

public class ProductDetailActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);     // reuse same layout
        // Show the product
        if(getIntent() != null) {
            int position = getIntent().getIntExtra(Constants.REQUESTED_POSITION,0);
            showProductDetail(position);
        }
    }

    private void showProductDetail(int position) {
        Log.i(TAG,"showProductDetail: position: " + String.valueOf(position));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.sample_content_fragment, ProductDetailFragment.newInstance(position), ProductDetailFragment.TAG);
        transaction.commit();
    }

}
