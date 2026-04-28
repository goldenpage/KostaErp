package com.kostaErp.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class foodMaterialDAO {
	public foodMaterialDAO(){}
	
	// 1. 식자재 입력
	public int addFoodMaterial(String foodMaterialName, String foodCategory_Id, int foodMaterialCount, 
			int foodMaterialCountAll, int foodMaterialPrice, String vender, String foodMaterialType, 
			String incomeDate, String expirationDate, String bId){
		
		int result = 0;
		
		String sql = "INSERT INTO FOODM(foodMaterialName, foodCategory_Id, foodMaterialCount, foodMaterialCountAll, "
				+ "foodMaterialPrice, foodMaterialType, vender, incomeDate, expirationDate, bId) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, foodMaterialName);
            stmt.setString(2, foodCategory_Id);
            stmt.setInt(3, foodMaterialCount);
            stmt.setInt(4, foodMaterialCountAll);
            stmt.setInt(5, foodMaterialPrice);
            stmt.setString(6, foodMaterialType);
            stmt.setString(7, vender);
            stmt.setDate(8, Date.valueOf(incomeDate));
            stmt.setDate(9, Date.valueOf(expirationDate));
            stmt.setString(10, bId);

            result = stmt.executeUpdate();
            
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	

    // 2. 카테고리 추가
    public int addFoodCategory(String foodCategoryId, String foodCategory){
        int result = 0;
        String sql = "INSERT INTO FOODC(foodCategory_Id, foodCategory) VALUES(?, ?)";

        try{
        	Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, foodCategoryId);
            stmt.setString(2, foodCategory);
            
            result = stmt.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // 3. 카테고리 삭제
    public int deleteFoodCategory(String foodCategory) {
        int result = 0;
        String sql = "DELETE FROM FOODC WHERE foodCategory = ?";

        try{
        	Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, foodCategory);
            
            result = stmt.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // 4. 식자재명으로 검색
    public List<foodMaterialVO> getFoodMaterialByName(String foodMaterialName) {
        List<foodMaterialVO> list = new ArrayList<>();
        String sql = "SELECT foodMaterialName, foodCategory_Id, vender FROM FOODM "
        		+ "WHERE foodMaterialName = ?";

        try{
        	Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            		
            stmt.setString(1, foodMaterialName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                foodMaterialVO foodMaterial = new foodMaterialVO();
                foodMaterial.setFoodMaterialName(rs.getString("foodMaterialName"));
                foodMaterial.setFoodCategory(rs.getString("foodCategory_Id"));
                foodMaterial.setVender(rs.getString("vender"));
                list.add(foodMaterial);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
