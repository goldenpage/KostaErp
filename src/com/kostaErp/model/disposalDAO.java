package com.kostaErp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class disposalDAO {
	//String uri = "jdbc:oracle:thin:@192.168.0.234:1521:xe";
	//String user = "kosta";
	//String password = "0707";
	String uri = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "hr";
	String password = "hr";
	
	public disposalDAO() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private Connection getConnection() throws Exception{
		return DriverManager.getConnection(uri, user, password);
	}
	
	//1. 폐기 품목 조회
	public List<disposalVO> getDisposals() {
        List<disposalVO> list = new ArrayList<>();
        String sql = "SELECT disposal_Id, disposalCountAll, disposalPrice, disposalDate, reason_Id, foodMaterial_id FROM DISPOSALS";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

        	while (rs.next()) {
        	    disposalVO vo = new disposalVO();
        	    vo.setDisposalId(rs.getString("disposal_Id"));
        	    vo.setDisposalCountAll(rs.getInt("disposalCountAll"));
        	    vo.setDisposalPrice(rs.getInt("disposalPrice"));
        	    vo.setDisposalDate(rs.getDate("disposalDate"));
        	    vo.setReasonId(rs.getString("reason_Id"));
        	    vo.setFoodMaterialId(rs.getString("foodMaterial_id"));
        	    list.add(vo);
        	}
        	rs.close();
        	pstmt.close();
        	conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	
	//2. 폐기 식자재명 조회
	public List<String> getFoodMaterialNames() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT f.foodMaterialName FROM DISPOSALS d JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id";
        try (Connection conn = getConnection();
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

        try (Connection conn = getConnection();
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
        try (Connection conn = getConnection();
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

    //5. 폐기품목 페이지 이동 (페이징)
    public List<disposalVO> getDisposalsPaging(int start, int end) {
        List<disposalVO> list = new ArrayList<>();
        String sql = "SELECT disposal_Id, disposalCountAll, disposalPrice, disposalDate, reason_Id, foodMaterial_id FROM (" +
                " SELECT d.disposal_Id, d.disposalCountAll, d.disposalPrice, d.disposalDate, d.reason_Id, d.foodMaterial_id," +
                " ROW_NUMBER() OVER (ORDER BY d.disposal_Id DESC) AS rn" +
                " FROM DISPOSALS d" +
                ") WHERE rn BETWEEN ? AND ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, start);
            pstmt.setInt(2, end);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                disposalVO vo = new disposalVO();
                vo.setDisposalId(rs.getString("disposal_Id"));
                vo.setDisposalCountAll(rs.getInt("disposalCountAll"));
                vo.setDisposalPrice(rs.getInt("disposalPrice"));
                vo.setDisposalDate(rs.getDate("disposalDate"));
                vo.setReasonId(rs.getString("reason_Id"));
                vo.setFoodMaterialId(rs.getString("foodMaterial_id"));
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
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reasonId);
            pstmt.setString(2, disposalId);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

