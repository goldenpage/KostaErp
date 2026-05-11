package com.kostaErp.model.DAO;

import java.util.List;

import com.kostaErp.model.VO.foodMaterialCategoryVO;
import com.kostaErp.model.VO.foodMaterialVO;

public interface FoodMaterialDAOInterface {
	int getFoodMaterialCount(String bId);
	int addFoodMaterial(List<foodMaterialVO> list, String bId);
	int addFoodCategory(String foodCategory);
	int deleteFoodCategory(String foodCategory);
	List<foodMaterialVO> getFoodMaterialByName(String foodMaterialName, String bId);
	List<foodMaterialVO> getFoodMaterialList(String bId, String sortType, int page, int pageSize);
	foodMaterialVO getFoodMaterialDetail(String foodMaterialId);
	int deleteFoodMaterial(String foodMaterialId, String bId);
	int getFoodMaterialTotalAmount(String bId, String startDate, String endDate);
	List<foodMaterialVO> getFoodMaterialSpendingRank(String bId, String startDate, String endDate);
	List<foodMaterialCategoryVO> getFoodCategoryList();
	boolean hasFoodMaterialByCategory(String foodCategory);
	List<foodMaterialVO> getFoodMaterialListAll(String bId);
	String getCategoryId(String foodCategory);
}