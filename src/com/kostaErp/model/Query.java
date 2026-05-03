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
	String GET_FOODMATERIALNAMES = "SELECT f.foodMaterialName FROM DISPOSALS d JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id";
	String GET_CATEGORIES = "SELECT fc.foodCategory FROM DISPOSALS d JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id JOIN FOODC fc ON f.foodCategory_Id = fc.foodCategory_Id"; 
	String GET_DISPOSALS_FILTERED_PAGING =
	        "SELECT * FROM ( " +
	        "   SELECT d.disposal_Id, " +
	        "          f.foodMaterialName, " +
	        "          fc.foodCategory, " +
	        "          d.disposalCountAll, " +
	        "          f.foodMaterialType, " +
	        "          d.disposalPrice, " +
	        "          d.disposalDate, " +
	        "          r.reason, " +
	        "          ROW_NUMBER() OVER (ORDER BY d.disposal_Id DESC) rn " +
	        "   FROM DISPOSALS d " +
	        "   JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "   JOIN FOODC fc ON f.foodCategory_Id = fc.foodCategory_Id " +
	        "   JOIN REASON r ON d.reason_Id = r.reason_Id " +
	        "   WHERE f.BID = ? ";
	String GET_DISPOSALS_PAGING = "SELECT * FROM ( " +
	        " SELECT d.disposal_Id, f.foodMaterialName, fc.foodCategory, d.disposalCountAll, " +
	        "        f.foodMaterialType, d.disposalPrice, d.disposalDate, r.reason, " +
	        "        ROW_NUMBER() OVER (ORDER BY d.disposal_Id DESC) AS rn " +
	        " FROM DISPOSALS d " +
	        " JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        " JOIN FOODC fc ON f.foodCategory_Id = fc.foodCategory_Id " +
	        " JOIN REASON r ON d.reason_Id = r.reason_Id " +
	        " WHERE f.BID = ? ";
	String GET_REASONS = "SELECT reason FROM REASON";
	String GET_DISPOSALS_BY_CATEGORY_AND_BID = "SELECT d.disposal_Id, f.foodMaterialName, fc.foodCategory, " +
	        "d.disposalCountAll, f.foodMaterialType, d.disposalPrice, " +
	        "d.disposalDate, r.reason_Id, r.reason " +
	        "FROM DISPOSALS d " +
	        "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "JOIN FOODC fc ON f.foodCategory_Id = fc.foodCategory_Id " +
	        "JOIN REASON r ON d.reason_Id = r.reason_Id " +
	        "WHERE fc.foodCategory = ? AND f.bId = ? " +
	        "ORDER BY d.disposal_Id DESC";
	String GET_DISPOSALS_PAGING1 = "SELECT * FROM ( " +
	        "    SELECT " +
	        "        d.disposal_Id, " +
	        "        f.foodMaterialName, " +
	        "        fc.foodCategory, " +
	        "        d.disposalCountAll, " +
	        "        f.foodMaterialType, " +
	        "        d.disposalPrice, " +
	        "        d.disposalDate, " +
	        "        r.reason_Id, " +
	        "        r.reason, " +
	        "        ROW_NUMBER() OVER (ORDER BY d.disposal_Id DESC) AS rn " +
	        "    FROM DISPOSALS d " +
	        "    JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "    JOIN FOODC fc ON f.foodCategory_Id = fc.foodCategory_Id " +
	        "    JOIN REASON r ON d.reason_Id = r.reason_Id " +
	        "    WHERE f.bId = ? " +
	        ") WHERE rn BETWEEN ? AND ?";
	String GET_DISPOSAL_COUNT = 
	        "SELECT COUNT(*) " +
	        "FROM DISPOSALS d " +
	        "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "JOIN FOODC fc ON f.foodCategory_Id = fc.foodCategory_Id " +
	        "JOIN REASON r ON d.reason_Id = r.reason_Id " +
	        "WHERE f.BID = ? ";
	String GET_TOTAL_COUNT =
	        "SELECT COUNT(*) " +
	                "FROM DISPOSALS d " +
	                "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	                "JOIN FOODC fc ON f.foodCategory_Id = fc.foodCategory_Id " +
	                "JOIN REASON r ON d.reason_Id = r.reason_Id " +
	                "WHERE f.BID = ? ";
	String UPDATE_REASON = "UPDATE DISPOSALS SET reason_Id = ? WHERE disposal_Id = ?";
	String GET_EXPIRED_DISPOSALSIDS = "SELECT d.disposal_Id " +
	        "FROM DISPOSALS d " +
	        "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "WHERE f.bId = ? " +
	        "AND f.expirationDate < SYSDATE";
	String GET_DISPOSAL_RATE = "SELECT NVL(ROUND(SUM(d.disposalCountAll) * 100 / NULLIF(SUM(f.foodMaterialCountAll), 0), 2), 0) AS disposalRate " +
	        "FROM DISPOSALS d " +
	        "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "WHERE f.bId = ? " +
	        "AND d.disposalDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND d.disposalDate < TO_DATE(?, 'YYYY-MM-DD')";
	String GET_TOTAL_DISPOSALPRICE = "SELECT NVL(SUM(d.disposalPrice), 0) AS totalDisposalPrice " +
	        "FROM DISPOSALS d " +
	        "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "WHERE f.bId = ? " +
	        "AND d.disposalDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND d.disposalDate < TO_DATE(?, 'YYYY-MM-DD')";
	String GET_TOP3_DISPOSALITEMS = "SELECT * " +
	        "FROM ( " +
	        "    SELECT " +
	        "        f.foodMaterial_Id, " +
	        "        f.foodMaterialName, " +
	        "        SUM(d.disposalPrice) AS totalDisposalPrice, " +
	        "        COUNT(d.disposal_Id) AS disposalCount " +
	        "    FROM DISPOSALS d " +
	        "    JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "    WHERE f.bId = ? " +
	        "    AND d.disposalDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "    AND d.disposalDate < TO_DATE(?, 'YYYY-MM-DD') " +
	        "    GROUP BY f.foodMaterial_Id, f.foodMaterialName " +
	        "    ORDER BY totalDisposalPrice DESC, f.foodMaterial_Id ASC " +
	        ") " +
	        "WHERE ROWNUM <= 3";
	String GET_DISPOSAL_REASON_RATIO = "SELECT " +
	        "    r.reason_Id, " +
	        "    r.reason, " +
	        "    COUNT(d.disposal_Id) AS disposalCount, " +
	        "    ROUND( " +
	        "        COUNT(d.disposal_Id) * 100 / SUM(COUNT(d.disposal_Id)) OVER (), " +
	        "        2 " +
	        "    ) AS reasonRatio " +
	        "FROM DISPOSALS d " +
	        "JOIN REASON r ON d.reason_Id = r.reason_Id " +
	        "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "WHERE f.bId = ? " +
	        "AND d.disposalDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND d.disposalDate < TO_DATE(?, 'YYYY-MM-DD') " +
	        "GROUP BY r.reason_Id, r.reason " +
	        "ORDER BY reasonRatio DESC";
	String SELECT_DAILY_DISPOSAL_AMOUNT = "SELECT TRUNC(disposalDate) AS disposalDay,COUNT(disposal_Id) AS disposalCount,NVL(SUM(disposalPrice), 0) AS totalDisposalPriceFROM DISPOSALSWHERE bId = ?AND disposalDate >= TO_DATE(?, 'YYYY-MM-DD')AND disposalDate < TO_DATE(?, 'YYYY-MM-DD')GROUP BY TRUNC(disposalDate)ORDER BY disposalDay";
	String SELECT_DAILY_DISPOSAL_BY_TYPE = "SELECT " +
	        "    TRUNC(d.disposalDate) AS disposalDay, " +
	        "    f.foodMaterialType, " +
	        "    COUNT(d.disposal_Id) AS disposalCount, " +
	        "    SUM(d.disposalPrice) AS totalDisposalPrice " +
	        "FROM DISPOSALS d " +
	        "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "WHERE f.bId = ? " +
	        "AND d.disposalDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND d.disposalDate < TO_DATE(?, 'YYYY-MM-DD') " +
	        "GROUP BY TRUNC(d.disposalDate), f.foodMaterialType " +
	        "ORDER BY disposalDay, f.foodMaterialType";
	
	String INSERT_NOTICE = "INSERT INTO DISPOSAL_NOTICE (notice_id, disposal_id, notice_date, read_yn) VALUES (?, ?, SYSDATE, 'N')";
	String GET_NOTICELIST = "SELECT " +
	    	    " f.foodMaterialName, " +
	    	    " fc.foodCategory, " +
	    	    " NVL(d.disposalCountAll, 0) AS disposalCountAll, " +
	    	    " f.foodMaterialType, " +
	    	    " f.expirationDate, " +
	    	    " d.disposal_Id " +
	    	    "FROM FOODM f " +
	    	    "JOIN FOODC fc ON f.foodCategory_Id = fc.foodCategory_Id " +
	    	    "LEFT JOIN DISPOSALS d ON f.foodMaterial_Id = d.foodMaterial_Id " +
	    	    "WHERE f.bId = ? " +
	    	    "AND f.expirationDate < SYSDATE " +
	    	    "AND NVL(d.disposalCountAll, 0) > 0 " + 
	    	    "ORDER BY f.expirationDate DESC";
	String DELETE_ALL = "DELETE FROM DISPOSAL_NOTICE";
	String UPDATE_READYN = "UPDATE DISPOSAL_NOTICE SET read_yn = 'Y' WHERE notice_id = ?";
	String GET_EXPIRED_DISPOSALIDS = "SELECT d.disposal_Id " +
            "FROM DISPOSALS d " +
            "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
            "WHERE f.bId = ? " +
            "AND f.expirationDate < SYSDATE";
	String GET_EXPIRED_COUNT = "SELECT COUNT(*) AS cnt " +
	        "FROM FOODM f " +
	        "LEFT JOIN DISPOSALS d ON f.foodMaterial_Id = d.foodMaterial_Id " +
	        "WHERE f.bId = ? " +
	        "AND f.expirationDate < SYSDATE " +
	        "AND NVL(d.disposalCountAll, 0) > 0";
	String GET_SOLID_TOTAL = "SELECT NVL(SUM(d.disposalCountAll),0) total " +
            "FROM DISPOSALS d " +
            "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
            "WHERE f.bId = ? " +
            "AND f.foodMaterialType = '고체'" + 
            "AND f.expirationDate < SYSDATE";
	String GET_LIQUID_TOTAL = "SELECT NVL(SUM(d.disposalCountAll),0) total " +
            "FROM DISPOSALS d " +
            "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
            "WHERE f.bId = ? " +
            "AND f.foodMaterialType = '액체'" +
            "AND f.expirationDate < SYSDATE";
	String GET_MAX_OVERDAY = "SELECT NVL(MAX(TRUNC(SYSDATE - expirationDate)),0) AS max_day " +
    		"FROM FOODM " +
    		"WHERE bId = ? " +
    		"AND expirationDate < SYSDATE";
}
