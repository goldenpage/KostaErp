package com.kostaErp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class noticeDAO {
	// 알림 추가
	public boolean insertNotice(String noticeId, String disposalId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = DBCP.getConnection();
            String sql = "INSERT INTO DISPOSAL_NOTICE (notice_id, disposal_id, notice_date, read_yn) VALUES (?, ?, SYSDATE, 'N')";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, noticeId);
            pstmt.setString(2, disposalId);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { 
            	if (pstmt != null) 
            		pstmt.close(); 
            	} catch (Exception e) {}
            try { 
            	if (conn != null) 
            		conn.close(); 
            	} catch (Exception e) {}
        }
        return false;
    }

    // 알림 목록 조회
	public ArrayList<noticeVO> getNoticeList(String bId) {

	    ArrayList<noticeVO> list = new ArrayList<>();

	    String sql =
	    	    "SELECT " +
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


	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	    ) {
	    	pstmt.setString(1, bId);

            ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {

	            noticeVO vo = new noticeVO();

	            vo.setFoodMaterialName(rs.getString("foodMaterialName"));
	            vo.setFoodCategory(rs.getString("foodCategory"));
	            vo.setFoodMaterialType(rs.getString("foodMaterialType"));
	            vo.setDisposalCountAll(rs.getInt("disposalCountAll"));
	            vo.setExpireDate(rs.getDate("expirationDate"));

	            vo.setDisposalId(rs.getString("disposal_Id"));

	            list.add(vo);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

 // 전체 삭제 (롤백 포함)
    public boolean deleteAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = DBCP.getConnection();
            conn.setAutoCommit(false);
            String sql = "DELETE FROM DISPOSAL_NOTICE";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            conn.commit();
            return true;
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {}
            e.printStackTrace();
        } finally {
            try { 
            	if (pstmt != null) 
            		pstmt.close(); 
            	} catch (Exception e) {}
            try { 
            	if (conn != null) 
            		conn.close(); 
            	} catch (Exception e) {}
        }
        return false;
    }
    
 // 읽음 처리
    public boolean updateReadYn(String noticeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = DBCP.getConnection();
            String sql = "UPDATE DISPOSAL_NOTICE SET read_yn = 'Y' WHERE notice_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, noticeId);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { 
            	if (pstmt != null) 
            		pstmt.close(); 
            	} catch (Exception e) {}
            try { 
            	if (conn != null) 
            		conn.close(); 
            	} catch (Exception e) {}
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

    public int getExpiredCount(String bId) {
        String sql =
        		"SELECT COUNT(*) AS cnt " +
        		        "FROM FOODM f " +
        		        "LEFT JOIN DISPOSALS d ON f.foodMaterial_Id = d.foodMaterial_Id " +
        		        "WHERE f.bId = ? " +
        		        "AND f.expirationDate < SYSDATE " +
        		        "AND NVL(d.disposalCountAll, 0) > 0";
        
        try (
            Connection conn = DBCP.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, bId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSolidTotal(String bId) {
        String sql =
            "SELECT NVL(SUM(d.disposalCountAll),0) total " +
            "FROM DISPOSALS d " +
            "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
            "WHERE f.bId = ? " +
            "AND f.foodMaterialType = '고체'" + 
            "AND f.expirationDate < SYSDATE";
        try (
            Connection conn = DBCP.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, bId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getLiquidTotal(String bId) {
        String sql =
            "SELECT NVL(SUM(d.disposalCountAll),0) total " +
            "FROM DISPOSALS d " +
            "JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id " +
            "WHERE f.bId = ? " +
            "AND f.foodMaterialType = '액체'" +
            "AND f.expirationDate < SYSDATE";
        try (
            Connection conn = DBCP.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, bId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getMaxOverDay(String bId) {
        String sql =
        		"SELECT NVL(MAX(TRUNC(SYSDATE - expirationDate)),0) AS max_day " +
        		"FROM FOODM " +
        		"WHERE bId = ? " +
        		"AND expirationDate < SYSDATE";
        try (
            Connection conn = DBCP.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, bId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("max_day");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
