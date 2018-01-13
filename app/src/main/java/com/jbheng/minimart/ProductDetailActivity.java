package com.jbheng.minimart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
            showProductDetail(getSupportFragmentManager(),position);
        }
    }

    private void showProductDetail(FragmentManager fm,int position) {
        Log.i(TAG,"showProductDetail: position: " + String.valueOf(position));
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.sample_content_fragment, ProductDetailFragment.newInstance(position), ProductDetailFragment.TAG);
        transaction.commit();
    }

    public static void startProductDetailActivity(int position) {
        Log.i(TAG,"startProductDetailActivity");
        // show the product
        Intent i = new Intent();
        i.putExtra(Constants.REQUESTED_POSITION,position);
        i.setClass(App.getMyAppContext(),ProductDetailActivity.class);
        App.getMyAppContext().startActivity(i);
    }

}
