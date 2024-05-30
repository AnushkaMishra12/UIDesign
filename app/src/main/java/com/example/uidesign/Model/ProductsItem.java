package com.example.uidesign.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductsItem{

	@SerializedName("images")
	private List<String> images;

	@SerializedName("thumbnail")
	private String thumbnail;

	@SerializedName("minimumOrderQuantity")
	private int minimumOrderQuantity;

	@SerializedName("rating")
	private Object rating;

	@SerializedName("returnPolicy")
	private String returnPolicy;

	@SerializedName("description")
	private String description;

	@SerializedName("weight")
	private int weight;

	@SerializedName("warrantyInformation")
	private String warrantyInformation;

	@SerializedName("title")
	private String title;

	@SerializedName("tags")
	private List<String> tags;

	@SerializedName("discountPercentage")
	private Object discountPercentage;

	@SerializedName("reviews")
	private List<ReviewsItem> reviews;

	@SerializedName("price")
	private Object price;

	@SerializedName("meta")
	private Meta meta;

	@SerializedName("shippingInformation")
	private String shippingInformation;

	@SerializedName("id")
	private int id;

	@SerializedName("availabilityStatus")
	private String availabilityStatus;

	@SerializedName("category")
	private String category;

	@SerializedName("stock")
	private int stock;

	@SerializedName("sku")
	private String sku;

	@SerializedName("dimensions")
	private Dimensions dimensions;

	@SerializedName("brand")
	private String brand;

	public List<String> getImages(){
		return images;
	}

	public String getThumbnail(){
		return thumbnail;
	}

	public int getMinimumOrderQuantity(){
		return minimumOrderQuantity;
	}

	public Object getRating(){
		return rating;
	}

	public String getReturnPolicy(){
		return returnPolicy;
	}

	public String getDescription(){
		return description;
	}

	public int getWeight(){
		return weight;
	}

	public String getWarrantyInformation(){
		return warrantyInformation;
	}

	public String getTitle(){
		return title;
	}

	public List<String> getTags(){
		return tags;
	}

	public Object getDiscountPercentage(){
		return discountPercentage;
	}

	public List<ReviewsItem> getReviews(){
		return reviews;
	}

	public Object getPrice(){
		return price;
	}

	public Meta getMeta(){
		return meta;
	}

	public String getShippingInformation(){
		return shippingInformation;
	}

	public int getId(){
		return id;
	}

	public String getAvailabilityStatus(){
		return availabilityStatus;
	}

	public String getCategory(){
		return category;
	}

	public int getStock(){
		return stock;
	}

	public String getSku(){
		return sku;
	}

	public Dimensions getDimensions(){
		return dimensions;
	}

	public String getBrand(){
		return brand;
	}
}