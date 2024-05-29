package com.example.uidesign.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsItem {

    @SerializedName("discountPercentage")
    private Object discountPercentage;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("images")
    private List<String> images;

    @SerializedName("price")
    private int price;

    @SerializedName("rating")
    private Object rating;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("stock")
    private int stock;

    @SerializedName("category")
    private String category;

    @SerializedName("brand")
    private String brand;


    public Object getDiscountPercentage() {
        return discountPercentage;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public List<String> getImages() {
        return images;
    }

    public int getPrice() {
        return price;
    }

    public Object getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }
}