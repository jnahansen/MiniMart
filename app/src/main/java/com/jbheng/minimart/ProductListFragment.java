
package com.jbheng.minimart;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jbheng.minimart.json.Product;

import java.util.Vector;

/**
 * Product List Fragment/View
 */
public class ProductListFragment extends Fragment implements LoadMoreProductsInterface {

    private static final String TAG = ProductListFragment.class.getName();

    protected RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    private LinearLayoutManager mLayoutManager;

    private LoadMoreProductsTask mLoadProductsTask;

    // Scroll to end detection
    private EndlessRecyclerViewScrollListener mScrollHitBottomListener;

    /**
     * @return A new instance of fragment.
     */
    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);

        // Don't create a new fragment when Activity is re-created e.g. during rotation
        fragment.setRetainInstance(true);

        return fragment;
    }

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        // Get some products to show
        getMoreProducts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.product_list_layout, container, false);
        rootView.setTag(TAG);

        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView.
        mRecyclerView.setHasFixedSize(true);

        // Specify a linear layout manager.
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.scrollToPosition(0);        // todo: option - goto arg position

        // Create adapter on Activity lifecycle and set into RecyclerView
        mAdapter = new RecyclerViewAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        // For handling scroll to bottom loads more products
        // Retain an instance so that you can call `resetState()` for fresh searches
        mScrollHitBottomListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i(TAG,"onLoadMore: page: " + String.valueOf(page) + " totalItemsCount: " + String.valueOf(totalItemsCount));
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                loadNextDataFromApi(page);
                getMoreProducts();
            }
        };
        // Adds scroll listener to RecyclerView
        mRecyclerView.addOnScrollListener(mScrollHitBottomListener);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AdapterInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mLoadProductsTask != null) {
            mLoadProductsTask.cancel(false);    // let thread finish
            mAdapter = null;
        }
    }

    // Get an AsyncTask for loading products, if no task is already running.
    // Only one task should run at a time
    private boolean setProductLoadingTask() {
        if (mLoadProductsTask == null) {
            mLoadProductsTask = new LoadMoreProductsTask(this);
            return true;
        }
        Log.e(TAG,"setProductLoadingTask: not allowed, a task is already running");
        return false;
    }

    private void clearProductLoadingTask() {
        Log.i(TAG,"clearProductLoadingTask");
        mLoadProductsTask = null;
    }

    // Uses Retrofit for networking and react for thread management
    public void getMoreProducts() {
        Log.i(TAG, "getMoreProducts");

        // Check network
        if(! Utils.hasNetwork(getActivity())) {
            Toast.makeText(getContext(),getString(R.string.no_network),Toast.LENGTH_LONG).show();
            return;
        }

        // Start a task (if one is not already running)
        if(setProductLoadingTask())
            mLoadProductsTask.execute();
    }

    @Override
    public void OnLoadProductsFinished(Vector<Product> list) {
        Log.i(TAG, "OnLoadProductsFinished");
        if(list == null || list.isEmpty()) {
            Log.e(TAG, "OnLoadProductsFinished: returned list was null or empty");
            clearProductLoadingTask();
            return;
        }
        // Add products to Products data
        Products.getInstance().append(list);
        // Update adapter if there was data
        if(mAdapter != null && ! mLoadProductsTask.isCancelled()) {
            // update mAdapter here
            boolean hadNoProducts = (mAdapter.getItemCount() == 0);
            if(hadNoProducts)
                mAdapter.notifyDataSetChanged();
            else
                mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), list.size());
        }
        clearProductLoadingTask();
        // Reset state for scroll to bottom listener
        mScrollHitBottomListener.resetState();
    }

}
