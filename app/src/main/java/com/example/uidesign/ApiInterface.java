package com.example.uidesign;

import com.example.uidesign.Model.ProductDataItem;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("products")
    Call<ProductDataItem> getDataProduct();
}
