package com.kostaErp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class menuDAO {
	public menuDAO(){}

	// 1. 메뉴 추가
	public String addMenu(String menuName, int menuPrice, String menuCategoryId){
		String menuId = null;
		String sql = "INSERT INTO MENUS(menuName, menuPrice, menuCategory_Id) VALUES(?, ?, ?)";
		String sql2 = "SELECT menu_Id FROM MENUS WHERE menuName = ? AND menuCategory_Id = ? "
				+ "ORDER BY menu_Id DESC";
		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, menuName);
			stmt.setInt(2, menuPrice);
			stmt.setString(3, menuCategoryId);

			stmt.executeUpdate();
			stmt.close();
			
			
			PreparedStatement Stmt2 = conn.prepareStatement(sql2);
				Stmt2.setString(1, menuName);
				Stmt2.setString(2, menuCategoryId);
				ResultSet rs = Stmt2.executeQuery();
				if (rs.next()) {
					menuId = rs.getString("menu_Id");
				}
				rs.close();
				Stmt2.close();
				conn.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		return menuId;
	}

	// 2. 메뉴 카테고리 추가
	public int addMenuCategory(String menuCategory, String bId){
		String checkSql = "SELECT COUNT(*) FROM MENUC WHERE menuCategory = ? AND bId = ?";
		String insertSql = "INSERT INTO MENUC(menuCategory, bId) VALUES(?, ?)";

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement checkStmt = conn.prepareStatement(checkSql);

			checkStmt.setString(1, menuCategory);
			checkStmt.setString(2, bId);

			ResultSet rs = checkStmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				rs.close();
				checkStmt.close();
				conn.close();
				return 0;
			}
			rs.close();
			checkStmt.close();

			PreparedStatement insertStmt = conn.prepareStatement(insertSql);
			insertStmt.setString(1, menuCategory);
			insertStmt.setString(2, bId);
			int result = insertStmt.executeUpdate();
			insertStmt.close();
			conn.close();
			return result;

		}catch(Exception e){
			e.printStackTrace();
		}
		return -1;
	}

	// 3. 메뉴 카테고리 삭제
	public int deleteMenuCategory(String menuCategory){
		int result = 0;
		String sql = "DELETE FROM MENUC WHERE menuCategory = ?";

		try{
			Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            		
			stmt.setString(1, menuCategory);
            
			result = stmt.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	// 4) 식자재 사용량 추가
	public int addUsedMaterial(int usedCount, String foodMaterialId, String menuId){
		int result = 0;
		String sql = "INSERT INTO USED(usedCount, foodMaterial_Id, menu_Id) VALUES(?, ?, ?)";

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, usedCount);
			stmt.setString(2, foodMaterialId);
			stmt.setString(3, menuId);
			
			result = stmt.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	// 5. 식자재 사용량 삭제
	public int deleteUsedMaterial(String usedMaterialId){
		int result = 0;
		String sql = "DELETE FROM USED WHERE usedMaterial_Id = ?";

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, usedMaterialId);
			
			result = stmt.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
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
            "    SELECT SUM(u.usedCount) * ? " +
            "    FROM USED u " +
            "    WHERE u.foodMaterial_Id = f.foodMaterial_Id " +
            "      AND u.menu_Id = ? " +
            ") " +
            "WHERE f.bId = ? " +
            "AND EXISTS ( " +
            "    SELECT 1 " +
            "    FROM USED u " +
            "    WHERE u.foodMaterial_Id = f.foodMaterial_Id " +
            "      AND u.menu_Id = ? " +
            ") " +
            "AND NOT EXISTS ( " +
            "    SELECT 1 " +
            "    FROM FOODM f2, ( " +
            "        SELECT foodMaterial_Id, SUM(usedCount) * ? AS needCount " +
            "        FROM USED " +
            "        WHERE menu_Id = ? " +
            "        GROUP BY foodMaterial_Id " +
            "    ) need " +
            "    WHERE f2.foodMaterial_Id = need.foodMaterial_Id " +
            "      AND f2.bId = ? " +
            "      AND f2.foodMaterialCountAll < need.needCount " +
            ")";

        try {
            Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, saleCount);
            stmt.setString(2, menuId);

            stmt.setString(3, bId);
            stmt.setString(4, menuId);

            stmt.setInt(5, saleCount);
            stmt.setString(6, menuId);
            stmt.setString(7, bId);

            flag = stmt.executeUpdate() > 0;

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

	//사용자의 메뉴 카테고리 조회(추가) - 메뉴 카테고리에 bId가 있음
	public List<menuCategoryVO> getMenuCategoryList(String bId){
		List<menuCategoryVO> list = new ArrayList<>();
		String sql = "SELECT menuCategory_Id, menuCategory FROM MENUC WHERE bId = ?";
		try {
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, bId);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
				menuCategoryVO vo = new menuCategoryVO();
				vo.setMenuCategoryId(rs.getString("menuCategory_Id"));
				vo.setMenuCategory(rs.getString("menuCategory"));
				list.add(vo);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	//카테고리 삭제 전에 사용하는 메뉴가 있는지 확인하는 작업 -> null값 들어가면 안됨(추가)
	public boolean hasMenuByCategory(String menuCategory){
		int count = 0;
		String sql = "SELECT COUNT(*) FROM MENUS m "
				+ "JOIN MENUC mc ON m.menuCategory_Id = mc.menuCategory_Id "
				+ "WHERE mc.menuCategory = ?";
		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, menuCategory);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				count = rs.getInt(1);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return count > 0;
	}
	
	//메뉴 중복체크(추가)
	public boolean hasMenuCheck(String menuName){
		boolean result = false;
		String sql = "SELECT menuName FROM MENUS WHERE menuName = ?";
		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, menuName);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				result = true;
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
