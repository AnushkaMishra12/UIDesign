package com.example.uidesign;

import com.example.uidesign.Model.ProductsItem;
import com.example.uidesign.Model.ResponseDataItem;
import com.example.uidesign.Model.ResponseDataModel;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    String Base_Url = "https://jsonplaceholder.typicode.com/";
    String Base_Url_product = "https://dummyjson.com/";

    @GET("photos")
    Call<List<ResponseDataItem>> getData();

    @GET("products")
    Call<List<ProductsItem>> getDataProduct();

}
