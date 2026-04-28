package com.kostaErp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    public List<menuVO> getMenuList(String bId) {
        List<menuVO> list = new ArrayList<menuVO>();

        String sql =
            "SELECT m.menu_Id, m.menuName, mc.menuCategory, m.menuPrice " +
            "FROM MENUS m " +
            "JOIN MENUC mc ON m.menuCategory_Id = mc.menuCategory_Id " +
            "WHERE mc.bId = ? " +
            "ORDER BY m.menuName ASC";

        try {
            Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, bId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                menuVO vo = new menuVO();

                vo.setMenuId(rs.getString("menu_Id"));
                vo.setMenuName(rs.getString("menuName"));
                vo.setMenuCategory(rs.getString("menuCategory"));
                vo.setMenuPrice(rs.getInt("menuPrice"));

                list.add(vo);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<menuVO> getMenuDetail(String menuId) {
        List<menuVO> list = new ArrayList<menuVO>();

        String sql =
            "SELECT m.menu_Id, m.menuName, m.menuPrice, " +
            " f.foodMaterialName, u.usedCount, " +
            " f.foodMaterialPrice, f.foodMaterialCountAll, " +
            " ROUND((u.usedCount / f.foodMaterialCountAll) * f.foodMaterialPrice, 0) AS usedPrice " +
            "FROM MENUS m " +
            "JOIN USED u ON m.menu_Id = u.menu_Id " +
            "JOIN FOODM f ON u.foodMaterial_Id = f.foodMaterial_Id " +
            "WHERE m.menu_Id = ? " +
            "ORDER BY f.foodMaterialName ASC";

        try {
            Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, menuId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                menuVO vo = new menuVO();

                vo.setMenuId(rs.getString("menu_Id"));
                vo.setMenuName(rs.getString("menuName"));
                vo.setMenuPrice(rs.getInt("menuPrice"));
                vo.setFoodMaterialName(rs.getString("foodMaterialName"));
                vo.setUsedCount(rs.getInt("usedCount"));
                vo.setFoodMaterialPrice(rs.getInt("foodMaterialPrice"));
                vo.setFoodMaterialCountAll(rs.getInt("foodMaterialCountAll"));
                vo.setUsedPrice(rs.getInt("usedPrice"));

                list.add(vo);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public boolean updateFoodMaterialAfterSale(String menuId, int saleCount, String bId) {
        boolean flag = false;

        String sql =
            "UPDATE FOODM f " +
            "SET f.foodMaterialCountAll = f.foodMaterialCountAll - ( " +
            "    SELECT u.usedCount * ? " +
            "    FROM USED u " +
            "    WHERE u.foodMaterial_Id = f.foodMaterial_Id " +
            "      AND u.menu_Id = ? " +
            ") " +
            "WHERE EXISTS ( " +
            "    SELECT 1 " +
            "    FROM USED u " +
            "    WHERE u.foodMaterial_Id = f.foodMaterial_Id " +
            "      AND u.menu_Id = ? " +
            ") " +
            "AND f.bId = ?";

        try {
        	Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, saleCount);
            stmt.setString(2, menuId);
            stmt.setString(3, menuId);
            stmt.setString(4, bId);

            flag = stmt.executeUpdate() > 0;

            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }
}
