package com.kostaErp.model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kostaErp.model.DBCP;
import com.kostaErp.model.Query;
import com.kostaErp.model.VO.noticeVO;

public class noticeDAO {
	// 알림 추가
	public boolean insertNotice(String noticeId, String disposalId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
        	conn = DBCP.getConnection();
            pstmt = conn.prepareStatement(Query.INSERT_NOTICE);
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
	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(Query.GET_NOTICELIST);
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
            pstmt = conn.prepareStatement(Query.DELETE_ALL);
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
            pstmt = conn.prepareStatement(Query.UPDATE_READYN);
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

    public int getExpiredCount(String bId) {
        try (
            Connection conn = DBCP.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(Query.GET_EXPIRED_COUNT)
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
        try (
            Connection conn = DBCP.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(Query.GET_SOLID_TOTAL)
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
        try (
            Connection conn = DBCP.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(Query.GET_LIQUID_TOTAL)
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
        try (
            Connection conn = DBCP.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(Query.GET_MAX_OVERDAY)
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
