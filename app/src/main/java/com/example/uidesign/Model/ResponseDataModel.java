package com.example.uidesign.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDataModel {

    @SerializedName("total")
    private int total;

    @SerializedName("limit")
    private int limit;

    @SerializedName("skip")
    private int skip;

    @SerializedName("products")
    private List<ProductsItem> products;

    public int getTotal() {
        return total;
    }

    public int getLimit() {
        return limit;
    }

    public int getSkip() {
        return skip;
    }

    public List<ProductsItem> getProducts() {
        return products;
    }
}