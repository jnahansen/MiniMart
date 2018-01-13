package com.jbheng.minimart.loading;

import android.os.AsyncTask;
import android.util.Log;

import com.jbheng.minimart.Constants;
import com.jbheng.minimart.Products;
import com.jbheng.minimart.json.Product;
import com.jbheng.minimart.json.ProductsQuery;
import com.jbheng.minimart.retrofit.ProductsInterface;
import com.jbheng.minimart.retrofit.ProductsInterfaceFactoryForRetrofit;

import java.util.Vector;

import retrofit2.Call;

/**
 * Created by jhansen on 1/11/18.
 */

public class LoadMoreProductsTask extends AsyncTask<Void,Void,Object> {

    private static final String TAG = LoadMoreProductsTask.class.getName();

    private LoadMoreProductsInterface mLoadProductsListener;

    public LoadMoreProductsTask(LoadMoreProductsInterface listener) {
        this.mLoadProductsListener = listener;
    }

    @Override
    protected Object doInBackground(Void... unused) {
        Log.i(TAG, "doInBackground");
        // Get more products in background
        Vector<Product> products = getMoreProductsUsingRetrofit();
        return products;
    }

    @Override
    protected void onPostExecute(Object obj) {
        Log.i(TAG, "onPostExecute");
        handleResult(obj);
    }

    @Override
    protected void onCancelled(Object obj) {
        Log.i(TAG, "onCancelled");
        // If task got cancelled, we can still add items to our data store
        handleResult(obj);
    }

    private void handleResult(Object obj) {
        Log.i(TAG, "handleResult");
        if(obj == null) {
            if(mLoadProductsListener != null) mLoadProductsListener.OnLoadProductsFinished(null);
            return;
        }
        // Get list from argument
        Vector<Product> list = null;
        try {
            list = (Vector<Product>) obj;
        } catch (Exception e) {
            Log.e(TAG, "handleResult: exception: ",e);
        }
        // Call back to listener
        if(mLoadProductsListener != null)
            mLoadProductsListener.OnLoadProductsFinished(list);
    }

    // Should be run on a background thread
    public Vector<Product> getMoreProductsUsingRetrofit() {
        // Get page to load (based on constant page size) and current Products store
        int page = Products.getInstance().getNextPageForProducts();
        Log.i(TAG, "getMoreProductsUsingRetrofit: page: " + String.valueOf(page));
        // Create interface call
        ProductsInterface queryIntf = new ProductsInterfaceFactoryForRetrofit().create();
        // We want a synchronous call here for IO operation
        Call<ProductsQuery> query = queryIntf.productsQuery(Constants.APIKEY,page,Constants.PAGESIZE);
        try {
            ProductsQuery pq = query.execute().body();
            // Add new products to singleton list
            //Products.getInstance().append(pq.getProducts());
            return pq.getProducts();
        } catch (Throwable t) {
            Log.e(TAG,"getMoreProductsUsingRetrofit: exception: ",t);
        }
        return null;
    }

}