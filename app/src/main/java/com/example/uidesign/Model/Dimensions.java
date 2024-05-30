package com.example.uidesign.Model;

import com.google.gson.annotations.SerializedName;

public class Dimensions{

	@SerializedName("depth")
	private Object depth;

	@SerializedName("width")
	private Object width;

	@SerializedName("height")
	private Object height;

	public Object getDepth(){
		return depth;
	}

	public Object getWidth(){
		return width;
	}

	public Object getHeight(){
		return height;
	}
}