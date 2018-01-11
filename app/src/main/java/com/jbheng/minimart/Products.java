package com.jbheng.minimart;

import android.util.Log;

import com.jbheng.minimart.json.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by jhansen on 1/11/18.
 *
 * Singleton for managing in-memory list of products.
 *
 * Methods should only be called on main thread.
 */

public class Products {
    private static final Products ourInstance = new Products();

    static Products getInstance() {
        return ourInstance;
    }

    private List<Product> products;

    private static final String TAG = Products.class.getName();

    private Products() {
        products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void clear() {
        products.clear();
    }

    public void append(Vector<Product> newProducts) {
        Log.i(TAG,"append");
        products.addAll(newProducts);
    }

    public int getNextPageForProducts() {
        if(products.isEmpty()) return 1;
        return products.size() / Constants.PAGESIZE + 1;
    }







}
