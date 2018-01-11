package com.jbheng.minimart;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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

        if(hasNoNetwork()) return;

//        if (savedInstanceState == null) {     // todo: why needed?
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.sample_content_fragment, ProductListFragment.newInstance());
            transaction.commit();
//        }

    }

    private boolean hasNoNetwork() {
        if(! Utils.isNetworkConnected(this)) {
            Toast.makeText(this,getString(R.string.no_network),Toast.LENGTH_LONG).show();
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
        // Check network
        hasNoNetwork();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
