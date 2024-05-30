package com.example.uidesign.Model;

import com.google.gson.annotations.SerializedName;

public class ReviewsItem{

	@SerializedName("date")
	private String date;

	@SerializedName("reviewerName")
	private String reviewerName;

	@SerializedName("reviewerEmail")
	private String reviewerEmail;

	@SerializedName("rating")
	private int rating;

	@SerializedName("comment")
	private String comment;

	public String getDate(){
		return date;
	}

	public String getReviewerName(){
		return reviewerName;
	}

	public String getReviewerEmail(){
		return reviewerEmail;
	}

	public int getRating(){
		return rating;
	}

	public String getComment(){
		return comment;
	}
}