package com.jbheng.minimart;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jhansen on 1/10/18.
 */


// Factory class to build Retrofit Interface for Walmart Products.
public class ProductsInterfaceFactoryForRetrofit {

    // For okClient logging
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.WALMART_BASE_URL)
            .build();

    public ProductsInterface create() {
        return retrofit.create(ProductsInterface.class);
    }

}

