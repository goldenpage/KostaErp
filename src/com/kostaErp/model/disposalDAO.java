package com.kostaErp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class disposalDAO {	
	// 1. 폐기 품목 조회 
	public List<disposalVO> getDisposals() {
	    List<disposalVO> list = new ArrayList<>();
	    String sql =
	        "SELECT " +
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
	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	    		
	    ) {
	        while (rs.next()) {
	            disposalVO vo = new disposalVO();
	            vo.setDisposalId(rs.getString("disposal_Id"));
	            vo.setFoodMaterialName(rs.getString("foodMaterialName"));
	            vo.setFoodCategory(rs.getString("foodCategory"));
	            vo.setDisposalCountAll(rs.getInt("disposalCountAll"));
	            vo.setDisposalPrice(rs.getInt("disposalPrice"));
	            vo.setDisposalDate(rs.getDate("disposalDate"));
	            vo.setReasonId(rs.getString("reason_Id"));
	            vo.setReason(rs.getString("reason"));

	            list.add(vo);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	//2. 폐기 식자재명 조회
	public List<String> getFoodMaterialNames() {
		List<String> list = new ArrayList<>();
		String sql = "SELECT f.foodMaterialName FROM DISPOSALS d JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id";
		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(rs.getString("foodMaterialName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 3. 폐기 식자재 카테고리 조회
	public List<String> getCategories() {
		List<String> list = new ArrayList<>();
		String sql = "SELECT fc.foodCategory FROM DISPOSALS d JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id JOIN FOODC fc ON f.foodCategory_Id = fc.foodCategory_Id";

		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(rs.getString("foodCategory"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//4. 폐기사유 조회
	public List<String> getReasons() {
		List<String> list = new ArrayList<>();
		String sql = "SELECT reason FROM REASON";
		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(rs.getString("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<disposalVO> getDisposalsByCategoryAndBId(String category, String bId) {
	    List<disposalVO> list = new ArrayList<>();

	    String sql =
	        "SELECT d.disposal_Id, f.foodMaterialName, fc.foodCategory, " +
	        "d.disposalCountAll, f.foodMaterialType, d.disposalPrice, " +
	        "d.disposalDate, r.reason_Id, r.reason " +
	        "FROM DISPOSALS d " +
	        "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "JOIN FOODC fc ON f.foodCategory_Id = fc.foodCategory_Id " +
	        "JOIN REASON r ON d.reason_Id = r.reason_Id " +
	        "WHERE fc.foodCategory = ? AND f.bId = ? " +
	        "ORDER BY d.disposal_Id DESC";

	    try (Connection conn = DBCP.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, category);
	        pstmt.setString(2, bId);

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            disposalVO vo = new disposalVO();

	            vo.setDisposalId(rs.getString("disposal_Id"));
	            vo.setFoodMaterialName(rs.getString("foodMaterialName"));
	            vo.setFoodCategory(rs.getString("foodCategory"));
	            vo.setFoodMaterialType(rs.getString("foodMaterialType"));
	            vo.setDisposalCountAll(rs.getInt("disposalCountAll"));
	            vo.setDisposalPrice(rs.getInt("disposalPrice"));
	            vo.setDisposalDate(rs.getDate("disposalDate"));
	            vo.setReason(rs.getString("reason"));

	            list.add(vo);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	//5. 폐기품목 페이지 이동 (페이징)
	public List<disposalVO> getDisposalsPaging(String bId, int start, int end) {

	    List<disposalVO> list = new ArrayList<>();

	    String sql =
	        "SELECT * FROM ( " +
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

	    try (Connection conn = DBCP.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, bId);
	        pstmt.setInt(2, start);
	        pstmt.setInt(3, end);

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            disposalVO vo = new disposalVO();

	            vo.setDisposalId(rs.getString("disposal_Id"));
	            vo.setFoodMaterialName(rs.getString("foodMaterialName"));
	            vo.setFoodCategory(rs.getString("foodCategory"));
	            vo.setFoodMaterialType(rs.getString("foodMaterialType"));
	            vo.setDisposalCountAll(rs.getInt("disposalCountAll"));
	            vo.setDisposalPrice(rs.getInt("disposalPrice"));
	            vo.setDisposalDate(rs.getDate("disposalDate"));
	            vo.setReasonId(rs.getString("reason_Id"));
	            vo.setReason(rs.getString("reason"));

	            list.add(vo);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	//6. 폐기사유 수정
	public boolean updateReason(String disposalId, String reasonId) {
		String sql = "UPDATE DISPOSALS SET reason_Id = ? WHERE disposal_Id = ?";
		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, reasonId);
			pstmt.setString(2, disposalId);
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<String> getExpiredDisposalIds(String bId) {

	    List<String> list = new ArrayList<>();

	    String sql =
	        "SELECT d.disposal_Id " +
	        "FROM DISPOSALS d " +
	        "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "WHERE f.bId = ? " +
	        "AND f.expirationDate < SYSDATE";

	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql)
	    ) {
	        pstmt.setString(1, bId);

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            list.add(rs.getString("disposal_Id"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	// 7. 월별 폐기율
	public double getDisposalRate(String bId, String startDate, String endDate) throws ClassNotFoundException {
	    String sql =
	        "SELECT NVL(ROUND(SUM(d.disposalCountAll) * 100 / NULLIF(SUM(f.foodMaterialCountAll), 0), 2), 0) AS disposalRate " +
	        "FROM DISPOSALS d " +
	        "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "WHERE f.bId = ? " +
	        "AND d.disposalDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND d.disposalDate < TO_DATE(?, 'YYYY-MM-DD')";

	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {
	        stmt.setString(1, bId);
	        stmt.setString(2, startDate);
	        stmt.setString(3, endDate);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getDouble("disposalRate");
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return 0;
	}
	// 8. 월별 폐기금액
	public int getTotalDisposalPrice(String bId, String startDate, String endDate) throws ClassNotFoundException {
	    String sql =
	        "SELECT NVL(SUM(d.disposalPrice), 0) AS totalDisposalPrice " +
	        "FROM DISPOSALS d " +
	        "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
	        "WHERE f.bId = ? " +
	        "AND d.disposalDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND d.disposalDate < TO_DATE(?, 'YYYY-MM-DD')";

	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {
	        stmt.setString(1, bId);
	        stmt.setString(2, startDate);
	        stmt.setString(3, endDate);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("totalDisposalPrice");
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return 0;
	}

	// 9. 월별 폐기품목 Top3
	public List<disposalVO> getTop3DisposalItems(String bId, String startDate, String endDate)
	        throws ClassNotFoundException {

	    String sql =
	        "SELECT * " +
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

	    List<disposalVO> list = new ArrayList<>();

	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {
	        stmt.setString(1, bId);
	        stmt.setString(2, startDate);
	        stmt.setString(3, endDate);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                disposalVO vo = new disposalVO();

	                vo.setFoodMaterialId(rs.getString("foodMaterial_Id"));
	                vo.setFoodMaterialName(rs.getString("foodMaterialName"));
	                vo.setTotalDisposalPrice(rs.getInt("totalDisposalPrice"));
	                vo.setDisposalCount(rs.getInt("disposalCount"));

	                list.add(vo);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	// 10. 월별 폐기사유비율
	public List<disposalVO> getDisposalReasonRatio(String bId, String startDate, String endDate)
	        throws ClassNotFoundException {

	    String sql =
	        "SELECT " +
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

	    List<disposalVO> list = new ArrayList<>();

	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {
	        stmt.setString(1, bId);
	        stmt.setString(2, startDate);
	        stmt.setString(3, endDate);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                disposalVO vo = new disposalVO();

	                vo.setReasonId(rs.getString("reason_Id"));
	                vo.setReason(rs.getString("reason"));
	                vo.setDisposalCount(rs.getInt("disposalCount"));
	                vo.setReasonRatio(rs.getDouble("reasonRatio"));

	                list.add(vo);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	//11. 날짜별 폐기량
	public List<disposalVO> selectDailyDisposalAmount(String bId, String startDate, String endDate) throws ClassNotFoundException{
		String sql = "SELECT TRUNC(disposalDate) AS disposalDay,COUNT(disposal_Id) AS disposalCount,NVL(SUM(disposalPrice), 0) AS totalDisposalPriceFROM DISPOSALSWHERE bId = ?AND disposalDate >= TO_DATE(?, 'YYYY-MM-DD')AND disposalDate < TO_DATE(?, 'YYYY-MM-DD')GROUP BY TRUNC(disposalDate)ORDER BY disposalDay";

		List<disposalVO> list = new ArrayList<>();

		try (
				Connection conn = DBCP.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)
				) {
			stmt.setString(1, bId);
			stmt.setString(2, startDate);
			stmt.setString(3, endDate);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					disposalVO vo = new disposalVO();

					vo.setDisposalDay(rs.getDate("disposalDay"));
					vo.setDisposalCount(rs.getInt("disposalCount"));
					vo.setTotalDisposalPrice(rs.getInt("totalDisposalPrice"));

					list.add(vo);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	// 12. 날짜별 폐기량 유형
	public List<disposalVO> selectDailyDisposalByType(String bId, String startDate, String endDate)
	        throws ClassNotFoundException {

	    String sql =
	        "SELECT " +
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

	    List<disposalVO> list = new ArrayList<>();

	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {
	        stmt.setString(1, bId);
	        stmt.setString(2, startDate);
	        stmt.setString(3, endDate);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                disposalVO vo = new disposalVO();

	                vo.setDisposalDay(rs.getDate("disposalDay"));
	                vo.setFoodMaterialType(rs.getString("foodMaterialType"));
	                vo.setDisposalCount(rs.getInt("disposalCount"));
	                vo.setTotalDisposalPrice(rs.getInt("totalDisposalPrice"));

	                list.add(vo);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}
}



