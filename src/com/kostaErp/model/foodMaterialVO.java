package com.kostaErp.model;

import java.sql.Date;

public class foodMaterialVO {
	private String foodMaterialId;
	private String foodMaterialName;
	private String foodCategory;
	private int foodMaterialCount;
	private int foodMaterialCountAll;
	private int foodMaterialPrice;
	private String vender;
	private String foodMaterialType;
	private Date incomeDate;
	private Date expirationDate;
	private String bid;
	
	public foodMaterialVO(String foodMaterialName, String foodCategory, int foodMaterialCount, 
			int foodMaterialCountAll, int foodMaterialPrice, String vender, String foodMaterialType, 
			Date incomeDate, Date expirationDate, String bid) {
		this.foodMaterialName = foodMaterialName;
		this.foodCategory = foodCategory;
		this.foodMaterialCount = foodMaterialCount;
		this.foodMaterialCountAll = foodMaterialCountAll;
		this.foodMaterialPrice = foodMaterialPrice;
		this.vender = vender;
		this.foodMaterialType = foodMaterialType;
		this.incomeDate = incomeDate;
		this.expirationDate = expirationDate;
		this.bid = bid;
	}
	
	public foodMaterialVO() {}
	
	public String getFoodMaterialId(){ return foodMaterialId; }
	public String getFoodMaterialName(){ return foodMaterialName;}
	public String getFoodCategory(){ return foodCategory; }
	public int getFoodMaterialCount(){ return foodMaterialCount; }
	public int getFoodMaterialCountAll(){ return foodMaterialCountAll; }
	public int getFoodMaterialPrice(){ return foodMaterialPrice; }
	public String getVender(){ return vender; }
	public Date getIncomeDate(){ return incomeDate; }
	public Date getExpirationDate(){ return expirationDate; }
	public String getFoodMaterialType(){ return foodMaterialType; }
	public String getBid(){ return bid; }
	
	public void setFoodMaterialName(String foodMaterialName) {
	    this.foodMaterialName = foodMaterialName;
	}
	public void setFoodCategory(String foodCategory) {
	    this.foodCategory = foodCategory;
	}
	public void setVender(String vender) {
	    this.vender = vender;
	}
}
