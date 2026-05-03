package com.kostaErp.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kostaErp.model.DBCP;
import com.kostaErp.model.Query;
import com.kostaErp.model.VO.disposalVO;

import java.sql.SQLException;

public class disposalDAO {	
	// 1. 폐기 품목 조회 
	public List<disposalVO> getDisposals() {
	    List<disposalVO> list = new ArrayList<>();
	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(Query.GET_DISPOSALS);
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
		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(Query.GET_FOODMATERIALNAMES);
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
		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(Query.GET_CATEGORIES);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(rs.getString("foodCategory"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<disposalVO> getDisposalsFilteredPaging(String bId, String category, String reason, int start, int end) {
		List<disposalVO> list = new ArrayList<>();
	    boolean isCatFiltered = category != null && !category.equals("전체") && !category.isEmpty();
	    boolean isReasFiltered = reason != null && !reason.equals("전체") && !reason.isEmpty();
	    
	    String sql = Query.GET_DISPOSALS_FILTERED_PAGING;
	    if (isCatFiltered) {
	        sql += " AND fc.foodCategory = ? ";
	    }
	    if (isReasFiltered) {
	        sql += " AND r.reason = ? ";
	    }
	    sql += ") WHERE rn BETWEEN ? AND ?";
	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql)
	    ) {
	        int idx = 1;
	        pstmt.setString(idx++, bId);
	        if (isCatFiltered) {
	            pstmt.setString(idx++, category);
	        }
	        if (isReasFiltered) {
	            pstmt.setString(idx++, reason);
	        }
	        pstmt.setInt(idx++, start);
	        pstmt.setInt(idx++, end);
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
	
	public List<disposalVO> getDisposalsPaging(String bId, String category, String reason, int start, int end) {
	    List<disposalVO> list = new ArrayList<>();
	    boolean isCategoryFiltered = category != null && !category.equals("전체") && !category.isEmpty();
	    boolean isReasonFiltered = reason != null && !reason.equals("전체") && !reason.isEmpty();
	    String sql = Query.GET_DISPOSALS_PAGING1;	 
	    if (isCategoryFiltered) {
	        sql += " AND fc.foodCategory = ? ";
	    }
	    if (isReasonFiltered) {
	        sql += " AND r.reason = ? ";
	    }
	    sql += " ) WHERE rn BETWEEN ? AND ? ";
	    try (Connection conn = DBCP.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        int idx = 1;
	        pstmt.setString(idx++, bId);
	        if (isCategoryFiltered) pstmt.setString(idx++, category);
	        if (isReasonFiltered) pstmt.setString(idx++, reason);
	        pstmt.setInt(idx++, start);
	        pstmt.setInt(idx++, end);

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
	
	public int getDisposalCount(String bId, String category, String reason) {
	    int count = 0;
	    boolean isCategoryFiltered = category != null && !category.equals("전체") && !category.isEmpty();
	    boolean isReasonFiltered = reason != null && !reason.equals("전체") && !reason.isEmpty();
	    String sql = Query.GET_DISPOSAL_COUNT;
	    if (isCategoryFiltered) {
	        sql += " AND fc.foodCategory = ? ";
	    }
	    if (isReasonFiltered) {
	        sql += " AND r.reason = ? ";
	    }
	    try (Connection conn = DBCP.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        int idx = 1;
	        pstmt.setString(idx++, bId);
	        if (isCategoryFiltered) pstmt.setString(idx++, category);
	        if (isReasonFiltered) pstmt.setString(idx++, reason);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) count = rs.getInt(1);
	    } catch (Exception e) { 
	        e.printStackTrace(); 
	    }
	    return count;
	}

	public int getTotalCount(String bId, String category, String reason) {
	    int count = 0;
	    boolean isCatFiltered = category != null && !category.equals("전체") && !category.isEmpty();
	    boolean isReasFiltered = reason != null && !reason.equals("전체") && !reason.isEmpty();
	    try (Connection conn = DBCP.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(Query.GET_TOTAL_COUNT)) {
	        int idx = 1;
	        pstmt.setString(idx++, bId);
	        if (isCatFiltered) pstmt.setString(idx++, category);
	        if (isReasFiltered) pstmt.setString(idx++, reason);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) count = rs.getInt(1);
	    } catch (Exception e) { e.printStackTrace(); }
	    return count;
	}
	
	//4. 폐기사유 조회
	public List<String> getReasons() {
		List<String> list = new ArrayList<>();
		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(Query.GET_REASONS);
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
	    try (Connection conn = DBCP.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(Query.GET_DISPOSALS_BY_CATEGORY_AND_BID)) {
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
	    try (Connection conn = DBCP.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(Query.GET_DISPOSALS_PAGING)) {
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
		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(Query.UPDATE_REASON)) {
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
	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(Query.GET_EXPIRED_DISPOSALIDS)
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
	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(Query.GET_DISPOSAL_RATE)
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
	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(Query.GET_TOTAL_DISPOSALPRICE)
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
	public List<disposalVO> getTop3DisposalItems(String bId, String startDate, String endDate) throws ClassNotFoundException {
	    List<disposalVO> list = new ArrayList<>();
	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(Query.GET_TOP3_DISPOSALITEMS)
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
	public List<disposalVO> getDisposalReasonRatio(String bId, String startDate, String endDate) throws ClassNotFoundException {
	    List<disposalVO> list = new ArrayList<>();
	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(Query.GET_DISPOSAL_REASON_RATIO)
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
		List<disposalVO> list = new ArrayList<>();
		try (
				Connection conn = DBCP.getConnection();
				PreparedStatement stmt = conn.prepareStatement(Query.SELECT_DAILY_DISPOSAL_AMOUNT)
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
	public List<disposalVO> selectDailyDisposalByType(String bId, String startDate, String endDate) throws ClassNotFoundException {
	    List<disposalVO> list = new ArrayList<>();
	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(Query.SELECT_DAILY_DISPOSAL_BY_TYPE)
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



