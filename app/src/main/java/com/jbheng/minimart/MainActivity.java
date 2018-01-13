package com.jbheng.minimart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/*
    MiniMart Exercise
    todo: put the exercise notes here

*/

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_main);

        showProductList(true);
    }

    private void showProductList(boolean fetchMoreProducts) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.sample_content_fragment, ProductListFragment.newInstance(fetchMoreProducts,0),ProductListFragment.TAG);
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
        // Check network connectivity
        Utils.hasNetwork(this);
    }

    // Hitting back button clears data for starting over
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.i(TAG,"onBackPressed");
        Fragment listFragment = getSupportFragmentManager().findFragmentByTag(ProductListFragment.TAG);
        if(listFragment == null) {
            Log.e(TAG,"onBackPressed: listFragment was null, not found, unexpected");
            return;
        }
        // If List fragment is showing, do a normal BACK press and finish the activity
        if(listFragment.isVisible()) {
            Log.i(TAG,"onBackPressed: found list frag, was visible");
            // destroy Products model data
            Products.getInstance().destroy();
            finish();
        } else {
            Log.i(TAG,"onBackPressed: found list frag, was hidden, showing again");

            // UnHide List fragment here
            getSupportFragmentManager().beginTransaction()
                    .show(listFragment)
                    .commit();
        }
    }

}
