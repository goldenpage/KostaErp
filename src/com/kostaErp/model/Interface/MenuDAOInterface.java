package com.kostaErp.model.Interface;

import java.util.List;

import com.kostaErp.model.VO.MenuVOMybatis;
import com.kostaErp.model.VO.menuCategoryVO;
import com.kostaErp.model.VO.MenuCategoryVOMybatis;
import com.kostaErp.model.VO.menuVO;

public interface MenuDAOInterface {
	String addMenu(String menuName, int menuPrice, String menuCategoryId);
	int addMenuCategory(String menuCategory, String bId);
	int deleteMenuCategory(String menuCategory);
	int addUsedMaterial(int usedCount, String foodMaterialId, String menuId);
	int deleteUsedMaterial(String usedMaterialId);
	List<MenuVOMybatis> getMenuList(String bId);
	List<MenuVOMybatis> getMenuDetail(String menuId);
	
	boolean updateFoodMaterialAfterSale(String menuId, int saleCount, String bId);
	
	List<MenuCategoryVOMybatis> getMenuCategoryList(String bId);
	boolean hasMenuByCategory(String menuCategory);
	boolean hasMenuCheck(String menuName);
	String getCategoryId(String menuCategory);
}
