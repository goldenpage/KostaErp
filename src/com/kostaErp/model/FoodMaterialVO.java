package com.kostaErp.model;

import java.sql.Date;

public class FoodMaterialVO {
	private String foodMaterialId;
	private String foodMaterialName;
	private String foodCategory;
	private int foodMaterialCount;
	private int foodMaterialCountAll;
	private int foodMaterialPrice;
	private String vender;
	private Date incomeDate;
	private Date expirationDate;
	private String foodMaterialType;
	private String bid;
	
	
	public String getFoodMaterialId() {
		return foodMaterialId;
	}
	public String getFoodMaterialName() {
		return foodMaterialName;
	}
	public String getFoodCategory() {
		return foodCategory;
	}
	public int getFoodMaterialCount() {
		return foodMaterialCount;
	}
	public int getFoodMaterialCountAll() {
		return foodMaterialCountAll;
	}
	public int getFoodMaterialPrice() {
		return foodMaterialPrice;
	}
	public String getVender() {
		return vender;
	}
	public Date getIncomeDate() {
		return incomeDate;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	public String getFoodMaterialType() {
		return foodMaterialType;
	}
	
	public String getBid() {
		return bid;
	}

	
	
	
}
