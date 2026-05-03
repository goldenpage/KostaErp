package com.kostaErp.model;

public interface Query {


	    String addMenu1 =
	        "INSERT INTO MENUS(menuName, menuPrice, menuCategory_Id) VALUES(?, ?, ?)";

	    String addMenu2 =
	        "SELECT menu_Id FROM MENUS WHERE menuName = ? AND menuCategory_Id = ? " +
	        "ORDER BY menu_Id DESC";

	    String addMenuCategory1 =
	        "SELECT COUNT(*) FROM MENUC WHERE menuCategory = ? AND bId = ?";
	    
	    String addMenuCategory2 = "INSERT INTO MENUC(menuCategory, bId) VALUES(?, ?)";

	    String deleteMenuCategory =
	        "DELETE FROM MENUC WHERE menuCategory = ?";

	    String addUsedMaterial =
	        "INSERT INTO USED(usedCount, foodMaterial_Id, menu_Id) VALUES(?, ?, ?)";

	    String deleteUsedMaterial =
	        "DELETE FROM USED WHERE usedMaterial_Id = ?";

	    String getMenuList =
	        "SELECT m.menu_Id, m.menuName, mc.menuCategory, m.menuPrice " +
	        "FROM MENUS m " +
	        "JOIN MENUC mc ON m.menuCategory_Id = mc.menuCategory_Id " +
	        "WHERE mc.bId = ? " +
	        "ORDER BY m.menuName ASC";

	    String getMenuDetail =
	        "SELECT m.menu_Id, m.menuName, m.menuPrice, " +
	        " f.foodMaterialName, u.usedCount, " +
	        " f.foodMaterialPrice, f.foodMaterialCountAll, " +
	        " ROUND((u.usedCount / f.foodMaterialCountAll) * f.foodMaterialPrice, 0) AS usedPrice " +
	        "FROM MENUS m " +
	        "JOIN USED u ON m.menu_Id = u.menu_Id " +
	        "JOIN FOODM f ON u.foodMaterial_Id = f.foodMaterial_Id " +
	        "WHERE m.menu_Id = ? " +
	        "ORDER BY f.foodMaterialName ASC";

	    String updateFoodMaterialAfterSale =
	        "UPDATE FOODM f " +
	        "SET f.foodMaterialCountAll = f.foodMaterialCountAll - ( " +
	        "    SELECT SUM(u.usedCount) * ? " +
	        "    FROM USED u " +
	        "    WHERE u.foodMaterial_Id = f.foodMaterial_Id " +
	        "      AND u.menu_Id = ? " +
	        ") " +
	        "WHERE f.bId = ? " +
	        "AND EXISTS ( " +
	        "    SELECT 1 " +
	        "    FROM USED u " +
	        "    WHERE u.foodMaterial_Id = f.foodMaterial_Id " +
	        "      AND u.menu_Id = ? " +
	        ") " +
	        "AND NOT EXISTS ( " +
	        "    SELECT 1 " +
	        "    FROM FOODM f2, ( " +
	        "        SELECT foodMaterial_Id, SUM(usedCount) * ? AS needCount " +
	        "        FROM USED " +
	        "        WHERE menu_Id = ? " +
	        "        GROUP BY foodMaterial_Id " +
	        "    ) need " +
	        "    WHERE f2.foodMaterial_Id = need.foodMaterial_Id " +
	        "      AND f2.bId = ? " +
	        "      AND f2.foodMaterialCountAll < need.needCount " +
	        ")";

	    String getMenuCategoryList =
	        "SELECT menuCategory_Id, menuCategory FROM MENUC WHERE bId = ?";

	    String hasMenuByCategory =
	        "SELECT COUNT(*) FROM MENUS m " +
	        "JOIN MENUC mc ON m.menuCategory_Id = mc.menuCategory_Id " +
	        "WHERE mc.menuCategory = ?";

	    String hasMenuCheck =
	        "SELECT menuName FROM MENUS WHERE menuName = ?";

	    String getCategoryId =
	        "SELECT menuCategory_Id FROM MENUC WHERE menuCategory = ?";
}
