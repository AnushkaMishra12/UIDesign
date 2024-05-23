package com.example.uidesign;

import com.example.uidesign.Model.ResponseDataItem;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    String Base_Url="https://jsonplaceholder.typicode.com/";

    @GET("photos")
    Call<List<ResponseDataItem>> getData();

}
