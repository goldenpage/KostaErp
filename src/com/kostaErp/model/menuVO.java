package com.kostaErp.model;

public class menuVO {

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
}