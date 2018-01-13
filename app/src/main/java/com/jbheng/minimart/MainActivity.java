package com.jbheng.minimart;

import android.os.Bundle;
import android.preference.PreferenceManager;
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

//    private static boolean productDetailShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_main);
        // Show the product list
        showProductList(true);
        // If we rotated and product detail was showing, redraw it also
//        if(productDetailShowing) {
//            int pos = PreferenceManager.getDefaultSharedPreferences(App.getMyAppContext()).getInt(Constants.LAST_PRODUCT_DETAIL_INDEX,0);
//            ProductDetailFragment.show(getSupportFragmentManager(),pos);
//        }
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

    @Override
    public void onPause() {
        // See if Product Detail Fragment is showing
      /*  if (isFragmentVisible(ProductDetailFragment.TAG)) {
            Log.e(TAG, "onPause: DETAIL FRAGMENT SHOWING, REDRAW DETAIL FRAGMENT");
            productDetailShowing = true;
        }*/

        super.onPause();
    }

    // Hitting back button clears data for starting over
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.i(TAG,"onBackPressed");
        // If List fragment is showing, do a normal BACK press and finish the activity
        if(isFragmentVisible(ProductListFragment.TAG)) {
            Log.i(TAG,"onBackPressed: found list frag, was visible");
            // destroy Products model data
            Products.getInstance().destroy();
            finish();
        } else {
            Log.i(TAG,"onBackPressed: found list frag, was hidden, showing again");
            // UnHide List fragment here
            // todo: probably not needed when using ProductDetailActivity
            try {
                Fragment listFragment = getSupportFragmentManager().findFragmentByTag(ProductListFragment.TAG);
                getSupportFragmentManager().beginTransaction()
                        .show(listFragment)
                        .commit();
            } catch (Exception e) {
                Log.e(TAG,"onBackPressedException ",e);
            }
        }
    }

    private boolean isFragmentVisible(String tag) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(tag);
        return frag != null && frag.isVisible();
    }

}
