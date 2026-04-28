package com.kostaErp.model;

public class menuCategoryVO {
	private String menuCategoryId;
	private String menuCategory;
	private String bid;

	public String getMenuCategoryId() {
		return menuCategoryId;
	}
	public String getMenuCategory() {
		return menuCategory;
	}
	public String getBid() {
		return bid;
	}


	private String menuCategoryId;
	private String menuCategory;
	private String bId;

	public menuCategoryVO(){}

	public menuCategoryVO(String menuCategory, String bId) {
		this.menuCategory = menuCategory;
		this.bId = bId;
	}

	public String getMenuCategoryId(){ return menuCategoryId; }
	public String getMenuCategory(){ return menuCategory; }
	public String getBId(){ return bId; }

	public void setMenuCategoryId(String menuCategoryId){
		this.menuCategoryId = menuCategoryId;
	}
	public void setMenuCategory(String menuCategory){
		this.menuCategory = menuCategory;
	}
	public void setBId(String bId){
		this.bId = bId;
	}


	public String getMenuCategoryId() {
		return menuCategoryId;
	}

	public void setMenuCategoryId(String menuCategoryId) {
		this.menuCategoryId = menuCategoryId;
	}

	public String getMenuCategory() {
		return menuCategory;
	}

	public void setMenuCategory(String menuCategory) {
		this.menuCategory = menuCategory;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}
}

