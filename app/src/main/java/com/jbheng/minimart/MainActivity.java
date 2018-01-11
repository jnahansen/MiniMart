package com.jbheng.minimart;

import android.os.Bundle;
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

//        if (savedInstanceState == null) {     // todo: why needed?
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.sample_content_fragment, ProductListFragment.newInstance());
            transaction.commit();
//        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
        // Check network connectivity
        Utils.hasNetwork(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
