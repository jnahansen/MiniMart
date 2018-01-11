package com.jbheng.minimart;

import com.jbheng.minimart.json.ProductsQuery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jhansen on 1/10/18.
 */

public interface ProductsInterface {

    // NOTE: "Call" is the normal retrofit method of running synchronous or asynchronous fetch
    // NOTE: "Single" says there will be a single object as the response and result can be converted to reactive methods like observable
    @GET("/_ah/api/walmart/v1/walmartproducts/{apiKey}/{pageNumber}/{pageSize}")
    Call<ProductsQuery> productsQuery(
            @Path("apiKey") String apiKey,
            @Path("pageNumber") int pageNumber,
            @Path("pageSize") int pageSize);
}
