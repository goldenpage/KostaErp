package com.kostaErp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class noticeDAO {
	String uri = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "hr";
	String password = "hr";
	
	public noticeDAO() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private Connection getConnection() throws Exception{
		return DriverManager.getConnection(uri, user, password);
	}
	
	public boolean insertNotice(String noticeId, String disposalId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
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

    //알림 목록 조회
    public ArrayList<noticeVO> getNoticeList() {
        ArrayList<noticeVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT notice_id, disposal_id, notice_date, read_yn FROM DISPOSAL_NOTICE ORDER BY notice_date DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                noticeVO vo = new noticeVO();
                vo.setNoticeId(rs.getString("notice_id"));
                vo.setDisposalId(rs.getString("disposal_id"));
                vo.setNoticeDate(rs.getDate("notice_date"));
                vo.setReadYn(rs.getString("read_yn"));
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { 
            	if (rs != null) 
            		rs.close(); 
            	} catch (Exception e) {}
            try { 
            	if (pstmt != null) 
            		pstmt.close(); 
            	} catch (Exception e) {}
            try { 
            	if (conn != null) 
            		conn.close(); 
            	} catch (Exception e) {}
        }
        return list;
    }

    //전체 삭제 (롤백 포함)
    public boolean deleteAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
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
    
    //읽음 처리
    public boolean updateReadYn(String noticeId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
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
}
