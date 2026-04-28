package com.kostaErp.model;

public class menuVO {
    private String menuId;
    private String menuName;
    private String menuCategory;
    private int menuPrice;
    private String foodMaterialName;
    private int usedCount;
    private int foodMaterialPrice;
    private int foodMaterialCountAll;
    private int usedPrice;

	private String menuId;
	private String menuName;
	private int menuPrice;
	private String menuCategoryId;

	public menuVO() {}

	public menuVO(String menuName, int menuPrice, String menuCategoryId) {
		this.menuName = menuName;
		this.menuPrice = menuPrice;
		this.menuCategoryId = menuCategoryId;
	}

	public String getMenuId(){ return menuId; }
	public String getMenuName(){ return menuName; }
	public int getMenuPrice(){ return menuPrice; }
	public String getMenuCategoryId(){ return menuCategoryId; }

	public void setMenuId(String menuId){ 
		this.menuId = menuId;
	}
	public void setMenuName(String menuName){
		this.menuName = menuName;
	}
	public void setMenuPrice(int menuPrice){
		this.menuPrice = menuPrice;
	}
	public void setMenuCategory_Id(String menuCategoryId){
		this.menuCategoryId = menuCategoryId;
	}
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(String menuCategory) {
        this.menuCategory = menuCategory;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getFoodMaterialName() {
        return foodMaterialName;
    }

    public void setFoodMaterialName(String foodMaterialName) {
        this.foodMaterialName = foodMaterialName;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public int getFoodMaterialPrice() {
        return foodMaterialPrice;
    }

    public void setFoodMaterialPrice(int foodMaterialPrice) {
        this.foodMaterialPrice = foodMaterialPrice;
    }

    public int getFoodMaterialCountAll() {
        return foodMaterialCountAll;
    }

    public void setFoodMaterialCountAll(int foodMaterialCountAll) {
        this.foodMaterialCountAll = foodMaterialCountAll;
    }

    public int getUsedPrice() {
        return usedPrice;
    }

    public void setUsedPrice(int usedPrice) {
        this.usedPrice = usedPrice;
    }
}