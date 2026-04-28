package com.kostaErp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class menuDAO {
	public menuDAO(){}

	// 1. 메뉴 추가
	public int addMenu(String menuName, int menuPrice, String menuCategoryId){
		int result = 0;
		String sql = "INSERT INTO MENUS(menuName, menuPrice, menuCategory_Id) VALUES(?, ?, ?)";

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, menuName);
			stmt.setInt(2, menuPrice);
			stmt.setString(3, menuCategoryId);

			result = stmt.executeUpdate();

		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	// 2. 메뉴 카테고리 추가
	public int addMenuCategory(String menuCategory, String bId){
		int result = 0;
		String sql = "INSERT INTO MENUC(menuCategory, bId) VALUES(?, ?)";

		try{
			Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
			stmt.setString(1, menuCategory);
			stmt.setString(2, bId);
			
			result = stmt.executeUpdate();

		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	// 3. 메뉴 카테고리 삭제
	public int deleteMenuCategory(String menuCategory) {
		int result = 0;
		String sql = "DELETE FROM MENUC WHERE menuCategory = ?";

		try{
			Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            		
			stmt.setString(1, menuCategory);
            
			result = stmt.executeUpdate();

		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	// 4) 식자재 사용량 추가
	public int addUsedMaterial(int usedCount, String foodMaterialId, String menuId) {
		int result = 0;
		String sql = "INSERT INTO USED(usedCount, foodMaterial_Id, menu_Id) VALUES(?, ?, ?)";

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, usedCount);
			stmt.setString(2, foodMaterialId);
			stmt.setString(3, menuId);
			
			result = stmt.executeUpdate();

		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	// 5. 식자재 사용량 삭제
	public int deleteUsedMaterial(String usedMaterialId) {
		int result = 0;
		String sql = "DELETE FROM USED WHERE usedMaterial_Id = ?";

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, usedMaterialId);
			
			result = stmt.executeUpdate();

		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
