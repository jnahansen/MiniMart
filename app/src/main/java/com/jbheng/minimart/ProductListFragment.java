
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

import com.jbheng.minimart.json.Product;
import com.jbheng.minimart.json.ProductsQuery;
import com.jbheng.minimart.retrofit.ProductsInterface;
import com.jbheng.minimart.retrofit.ProductsInterfaceFactoryForRetrofit;

import java.util.Vector;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 * Product List Fragment/View
 */
public class ProductListFragment extends Fragment {

    private static final String TAG = ProductListFragment.class.getName();

    protected RecyclerView mRecyclerView;

    // List of Products that populate RecyclerView.
    private Vector<Product> mNewProducts = new Vector<>();
    private int mPage;

    private AdapterInterface mAdapterIntf;
    private LinearLayoutManager mLayoutManager;

    // State for scroll to end detection
    // I know this is ugly...
    private int previousTotal = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private int firstVisibleItem = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        // todo: add initial products to list
        fetchMoreProducts(1);
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

        // Update the RecyclerView item's list with menu items and Native Express ads.
//        addMenuItemsFromJson();

        // Create adapter on Activity lifecycle and set into RecyclerView
        mAdapterIntf.setAdapter(new RecyclerViewAdapter(getContext(), mNewProducts));
        mRecyclerView.setAdapter(mAdapterIntf.getAdapter());


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    Log.i(TAG, "YAYYYYY! end called");

                    // todo: fetchMoreProducts here and then set loading back to true

                    loading = true;
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Initialize dataset on a background thread
//        new LoadData().execute("");       // todo: remove when done w/ AsyncTask; can call fetchMoreProducts here
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mAdapterIntf = (AdapterInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AdapterInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAdapterIntf = null;
    }

    // Uses Retrofit for networking and react for thread management
    public void fetchMoreProducts(int page) {
        Log.i(TAG, "fetchMoreProducts: page: " + String.valueOf(page));
        mNewProducts.clear();

        // NOTE: no gui treatment here as its usually called by StartupActivity
        // This should be done on a background thread and syncd up w/ main thread when needed
        io.reactivex.Observable.just("placeHolder")
                .subscribeOn(Schedulers.io())
                .doOnNext((item) -> {
                    setNewProducts(page);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stockUpdate -> {
                    // Append new page data to Adapter
                    if(! mNewProducts.isEmpty()) {
                        mAdapterIntf.getAdapter().appendItems(mNewProducts);
                        mAdapterIntf.getAdapter().notifyDataSetChanged();
                    }
                    // todo: loading = false here????
                });
    }

    private void setNewProducts(int page) {
        Log.i(TAG, "setNewProducts: page: " + String.valueOf(page));
        try {
            mNewProducts = getProductsUsingRetrofit(page);
            if(! mNewProducts.isEmpty()) mPage = page;
        } catch (Throwable t) {
            Log.e(TAG, "setNewProducts: exception",t);
        }
    }

    public Vector<Product> getProductsUsingRetrofit(int page) {
        Log.i(TAG, "getProductsUsingRetrofit: page: " + String.valueOf(page));
        // Create interface call
        ProductsInterface productsInterface = new ProductsInterfaceFactoryForRetrofit().create();
        // We want a synchronous call here for IO operation
        Call<ProductsQuery> query = productsInterface.productsQuery(Constants.APIKEY,page,Constants.PAGESIZE);
        try {
            ProductsQuery pq = query.execute().body();
            return pq.getProducts();
        } catch (Throwable t) {
            Log.e(TAG,"getProductsUsingRetrofit: exception: ",t);
        }
        return null;
    }


}
