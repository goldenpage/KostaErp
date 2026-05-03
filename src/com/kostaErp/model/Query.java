package com.kostaErp.model;

public interface Query {

	
	public static final String GET_DISPOSALS = "SELECT " +
	        " d.disposal_Id, " +
	        " f.foodMaterialName, " +
	        " fc.foodCategory, " +
	        " d.disposalCountAll, " +
	        " d.disposalPrice, " +
	        " d.disposalDate, " +
	        " r.reason_Id, " +
	        " r.reason " +
	        "FROM DISPOSALS d " +
	        "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "JOIN FOODC fc ON f.foodCategory_Id = fc.foodCategory_Id " +
	        "JOIN REASON r ON d.reason_Id = r.reason_Id " +
	        "ORDER BY d.disposal_Id DESC";
	
	public static final String GET_Monthly_MENU_SALE_RANK = "SELECT " +
	        "    RANK() OVER (ORDER BY SUM(s.saleMenuCount) DESC) AS ranking, " +
	        "    s.menu_Id, " +
	        "    m.menuName, " +
	        "    m.menuPrice, " +
	        "    SUM(s.saleMenuCount) AS totalSaleCount, " +
	        "    SUM(s.saleMenuCount * m.menuPrice) AS totalSalesAmount " +
	        "FROM SALES s " +
	        "JOIN MENUS m ON s.menu_Id = m.menu_Id " +
	        "JOIN MENUC mc ON m.menuCategory_Id = mc.menuCategory_Id " +
	        "JOIN REVENUE r ON s.revenue_Id = r.revenue_Id " +
	        "WHERE mc.bId = ? " +
	        "AND r.revenueDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND r.revenueDate < TO_DATE(?, 'YYYY-MM-DD') " +
	        "GROUP BY s.menu_Id, m.menuName, m.menuPrice " +
	        "ORDER BY ranking";
	
	public static final String GET_REVENU = "SELECT SUM(menuprice * salemenucount) FROM SALES, MENUS";
	
	public static final String GET_MONTHLY_REVENUE =    "SELECT NVL(SUM(s.saleMenuCount * m.menuPrice), 0) AS totalRevenue " +
	        "FROM SALES s " +
	        "JOIN MENUS m ON s.menu_Id = m.menu_Id " +
	        "JOIN MENUC mc ON m.menuCategory_Id = mc.menuCategory_Id " +
	        "JOIN REVENUE r ON s.revenue_Id = r.revenue_Id " +
	        "WHERE mc.bId = ? " +
	        "AND r.revenueDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND r.revenueDate < TO_DATE(?, 'YYYY-MM-DD')";
	
	public static final String CHECK_MEMBER_BY_VO ="SELECT bId, name, storeName, storeType, pw FROM USERINFO " +
			"WHERE bId = ? AND name = ? AND pw = ?";
	
	public static final String REGISTER = "INSERT INTO USERINFO (bid,name,phone,email,storeName, "
			+ "storeType,storeCategory,pw,signDate,agreementDate,marketingDate) " 
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String SET_PW = "UPDATE USERINFO SET pw = ? WHERE bId = ? AND NAME = ? AND PHONE = ?";
	public static final String CHECK_PW_FIND_USER ="SELECT bId FROM USERINFO WHERE bId = ? AND name = ? AND phone = ?";
	
	public static final String LOGIN = "SELECT  bId, name FROM USERINFO" 
			+ " WHERE bId = ?  AND pw = ? ";
	
	public static final String GET_PHONE_CHECK ="SELECT bId, name, phone FROM USERINFO" 
			+ " WHERE phone = ? ";
	
	public static final String GET_BID ="SELECT bId FROM USERINFO WHERE bId = ?";


	public static final String ADD_FOOD_MATERIAL =
			"INSERT INTO FOODM(foodMaterialName, foodCategory_Id, foodMaterialCount, foodMaterialCountAll, "
			+ "foodMaterialPrice, foodMaterialType, vender, incomeDate, expirationDate, bId) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String CHECK_FOOD_CATEGORY_EXISTS =
			"SELECT COUNT(*) FROM FOODC WHERE foodCategory = ?";

	public static final String ADD_FOOD_CATEGORY =
			"INSERT INTO FOODC(foodCategory) VALUES(?)";

	public static final String DELETE_FOOD_CATEGORY =
			"DELETE FROM FOODC WHERE foodCategory = ?";

	public static final String GET_FOOD_MATERIAL_BY_NAME =
			"SELECT f.foodMaterialName, c.foodCategory, f.vender, f.foodMaterialType "
			+ "FROM FOODM f JOIN FOODC c ON f.foodCategory_Id = c.foodCategory_Id "
			+ "WHERE f.foodMaterialName LIKE ? AND f.bId = ?";

	public static final String GET_MARKETING_MEMBERS =
			"SELECT bid, name, phone, email, marketingDate "
			+ "FROM USERINFO "
			+ "WHERE marketingDate IS NOT NULL";

	public static final String GET_FOOD_MATERIAL_COUNT =
			"SELECT COUNT(*) FROM FOODM WHERE bId = ?";

	public static final String GET_FOOD_MATERIAL_LIST =
			"SELECT * "
			+ "FROM ( "
			+ " SELECT ROW_NUMBER() OVER (ORDER BY %s) AS rn, "
			+ " f.foodMaterial_Id, f.foodMaterialName, c.foodCategory, "
			+ " f.foodMaterialCount, f.foodMaterialCountAll, f.foodMaterialPrice, "
			+ " f.vender, f.incomeDate, f.expirationDate, f.foodMaterialType "
			+ " FROM FOODM f "
			+ " JOIN FOODC c ON f.foodCategory_Id = c.foodCategory_Id "
			+ " WHERE f.bId = ? "
			+ ") "
			+ "WHERE rn BETWEEN ? AND ?";

	public static final String GET_FOOD_MATERIAL_DETAIL =
			"SELECT f.foodMaterial_Id, f.foodMaterialName, c.foodCategory, "
			+ " f.foodMaterialCount, f.foodMaterialCountAll, f.foodMaterialPrice, "
			+ " f.foodMaterialType, f.vender, f.incomeDate, f.expirationDate, f.bId "
			+ "FROM FOODM f "
			+ "JOIN FOODC c ON f.foodCategory_Id = c.foodCategory_Id "
			+ "WHERE f.foodMaterial_Id = ?";

	public static final String DELETE_USED_BY_FOOD_MATERIAL =
			"DELETE FROM USED "
			+ "WHERE foodMaterial_Id = ?";

	public static final String DELETE_DISPOSALS_BY_FOOD_MATERIAL =
			"DELETE FROM DISPOSALS "
			+ "WHERE foodMaterial_Id = ?";

	public static final String DELETE_FOOD_MATERIAL =
			"DELETE FROM FOODM "
			+ "WHERE foodMaterial_Id = ? "
			+ "AND bId = ?";

	public static final String GET_FOOD_MATERIAL_TOTAL_AMOUNT =
			"SELECT NVL(SUM(foodMaterialPrice), 0) AS totalAmount "
			+ "FROM FOODM "
			+ "WHERE bId = ? "
			+ "AND incomeDate >= TO_DATE(?, 'YYYY-MM-DD') "
			+ "AND incomeDate < TO_DATE(?, 'YYYY-MM-DD')";

	public static final String GET_FOOD_MATERIAL_SPENDING_RANK =
			"SELECT "
			+ "    RANK() OVER (ORDER BY foodMaterialPrice * foodMaterialCount DESC) AS ranking, "
			+ "    foodMaterial_Id, "
			+ "    foodMaterialName, "
			+ "    foodMaterialPrice, "
			+ "    foodMaterialCount, "
			+ "    foodMaterialPrice * foodMaterialCount AS totalExpense, "
			+ "    incomeDate, "
			+ "    bId "
			+ "FROM FOODM "
			+ "WHERE bId = ? "
			+ "AND incomeDate >= TO_DATE(?, 'YYYY-MM-DD') "
			+ "AND incomeDate < TO_DATE(?, 'YYYY-MM-DD') "
			+ "ORDER BY totalExpense DESC";

	public static final String GET_FOOD_CATEGORY_LIST =
			"SELECT foodCategory_Id, foodCategory FROM FOODC";

	public static final String HAS_FOOD_MATERIAL_BY_CATEGORY =
			"SELECT COUNT(*) "
			+ "FROM FOODM f JOIN FOODC c ON f.foodCategory_Id = c.foodCategory_Id "
			+ "WHERE c.foodCategory = ?";

	public static final String GET_FOOD_MATERIAL_LIST_ALL =
			"SELECT f.foodMaterial_Id, f.foodMaterialName, c.foodCategory "
			+ "FROM FOODM f JOIN FOODC c ON f.foodCategory_Id = c.foodCategory_Id "
			+ "WHERE f.bId = ? ORDER BY f.foodMaterialName ASC";

	public static final String GET_CATEGORY_ID =
			"SELECT foodCategory_Id FROM FOODC "
			+ "WHERE foodCategory = ?";
}

