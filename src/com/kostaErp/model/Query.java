package com.kostaErp.model;

public interface Query {
	
	String GET_DISPOSALS = "SELECT " +
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
	
	
	
	
	
	
	
	
	String GET_Monthly_MENU_SALE_RANK = "SELECT " +
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
	
	String GET_REVENU = "SELECT SUM(menuprice * salemenucount) FROM SALES, MENUS";
	
	String GET_MONTHLY_REVENUE =    "SELECT NVL(SUM(s.saleMenuCount * m.menuPrice), 0) AS totalRevenue " +
	        "FROM SALES s " +
	        "JOIN MENUS m ON s.menu_Id = m.menu_Id " +
	        "JOIN MENUC mc ON m.menuCategory_Id = mc.menuCategory_Id " +
	        "JOIN REVENUE r ON s.revenue_Id = r.revenue_Id " +
	        "WHERE mc.bId = ? " +
	        "AND r.revenueDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND r.revenueDate < TO_DATE(?, 'YYYY-MM-DD')";
	
	String CHECK_MEMBER_BY_VO ="SELECT bId, name, storeName, storeType, pw FROM USERINFO " +
			"WHERE bId = ? AND name = ? AND pw = ?";
	
	String REGISTER = "INSERT INTO USERINFO (bid,name,phone,email,storeName, "
			+ "storeType,storeCategory,pw,signDate,agreementDate,marketingDate) " 
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	String SET_PW = "UPDATE USERINFO SET pw = ? WHERE bId = ? AND NAME = ? AND PHONE = ?";
	String CHECK_PW_FIND_USER ="SELECT bId FROM USERINFO WHERE bId = ? AND name = ? AND phone = ?";
	String GET_MARKETING_MEMBERS ="SELECT bid, name, phone, email, marketingDate FROM USERINFO WHERE marketingDate IS NOT NULL";
	
	String LOGIN = "SELECT  bId, name FROM USERINFO" 
			+ " WHERE bId = ?  AND pw = ? ";
	
	String GET_PHONE_CHECK ="SELECT bId, name, phone FROM USERINFO" 
			+ " WHERE phone = ? ";
	
	String GET_BID ="SELECT bId FROM USERINFO WHERE bId = ?";
}
