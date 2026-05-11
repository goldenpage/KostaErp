package com.kostaErp.model.VO;

public class MenuCategoryVOMybatis {
	private String menuCategoryId;
	private String menuCategory;
	private String bId;
 
	public MenuCategoryVOMybatis() {}
 
	public MenuCategoryVOMybatis(String menuCategory, String bId) {
		this.menuCategory = menuCategory;
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
 
	public String getBId() {
		return bId;
	}
 
	public void setBId(String bId) {
		this.bId = bId;
	}
}
