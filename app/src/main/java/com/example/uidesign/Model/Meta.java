package com.example.uidesign.Model;

import com.google.gson.annotations.SerializedName;

public class Meta{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("qrCode")
	private String qrCode;

	@SerializedName("barcode")
	private String barcode;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public String getQrCode(){
		return qrCode;
	}

	public String getBarcode(){
		return barcode;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}