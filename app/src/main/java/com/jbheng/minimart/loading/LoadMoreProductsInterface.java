package com.jbheng.minimart.loading;

import com.jbheng.minimart.json.Product;

import java.util.Vector;

/**
 * Created by jhansen on 1/11/18.
 */

public interface LoadMoreProductsInterface {

    public void OnLoadProductsFinished(Vector<Product> list);

}
