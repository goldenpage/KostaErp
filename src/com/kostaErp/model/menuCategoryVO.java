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



	private String bId;

	public menuCategoryVO(){}

	public menuCategoryVO(String menuCategory, String bId) {
<<<<<<< HEAD
        this.menuCategory = menuCategory;
        this.bId = bId;
    }
=======
		this.menuCategory = menuCategory;
		this.bId = bId;
	}


	public String getBId(){ return bId; }

	public void setBId(String bId){
		this.bId = bId;
	}

>>>>>>> refs/remotes/origin/master

	public void setMenuCategoryId(String menuCategoryId) {
		this.menuCategoryId = menuCategoryId;
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

