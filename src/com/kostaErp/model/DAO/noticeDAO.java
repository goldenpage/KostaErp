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
	
	//1. 새로운 알림 추가 (폐기 ID와 매칭하여 등록)
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

	//2. 사업자별 알림 목록 조회 (식자재명, 카테고리, 유통기한 등 포함)
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

	//3. 전체 삭제 (롤백 포함)
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
    
    //4. 특정 알림의 읽음 상태 업데이트 (읽음 처리)
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
    
    //5. 유통기한이 만료된 폐기 항목의 ID 목록 조회
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

    //6. 유통기한 만료 항목의 총 개수 조회
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

    //7. 고체 형태 식자재의 총 폐기 수량 조회
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
    //8. 액체 형태 식자재의 총 폐기 수량 조회
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

    //9. 유통기한 경과일 중 가장 오래된 경과일수(최대 경과일) 조회
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
