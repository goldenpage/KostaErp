package com.kostaErp.model;

public class foodMaterialCategoryVO {

	private String foodCategoryId;
	private String foodCategory;
	
	public foodMaterialCategoryVO() {}

    public foodMaterialCategoryVO(String foodCategoryId, String foodCategory) {
        this.foodCategoryId = foodCategoryId;
        this.foodCategory = foodCategory;
    }
    

    private String foodCategoryId;
    private String foodCategory;

    public String getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(String foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }
}

