package com.kostaErp.model;

public class foodMaterialCategoryVO {
	private String foodCategoryId;
	private String foodCategory;
	
	public foodMaterialCategoryVO() {}

    public foodMaterialCategoryVO(String foodCategoryId, String foodCategory) {
        this.foodCategoryId = foodCategoryId;
        this.foodCategory = foodCategory;
    }
    
	public String getFoodCategoryId(){ return foodCategoryId; }
	public String getFoodCategory(){ return foodCategory; }
}
