package com.kostaErp.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kostaErp.model.DBCP;
import com.kostaErp.model.VO.menuCategoryVO;
import com.kostaErp.model.VO.menuVO;
import com.kostaErp.model.Query;

public class menuDAO {
	public menuDAO(){}

	// 1. 메뉴 추가
	public String addMenu(String menuName, int menuPrice, String menuCategoryId){
		String menuId = null;
		
		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(Query.addMenu1);

			stmt.setString(1, menuName);
			stmt.setInt(2, menuPrice);
			stmt.setString(3, menuCategoryId);

			stmt.executeUpdate();
			stmt.close();
			
			
			PreparedStatement Stmt2 = conn.prepareStatement(Query.addMenu2);
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

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement checkStmt = conn.prepareStatement(Query.addMenuCategory1);

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

			PreparedStatement insertStmt = conn.prepareStatement(Query.addMenuCategory2);
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

		try{
			Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.deleteMenuCategory);
            		
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

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(Query.addUsedMaterial);

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

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(Query.deleteUsedMaterial);

			stmt.setString(1, usedMaterialId);
			
			result = stmt.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

    public List<menuVO> getMenuList(String bId) {
        List<menuVO> list = new ArrayList<menuVO>();


        try {
            Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.getMenuList);

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

        try {
            Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.getMenuDetail);

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


        try {
            Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.updateFoodMaterialAfterSale);

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
		try {
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(Query.getMenuCategoryList);
			
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
		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(Query.hasMenuByCategory);
			
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
		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(Query.hasMenuCheck);
			
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
	
	//카테고리 id 불러오기
	public String getCategoryId(String menuCategory){

        try{
        	Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.getCategoryId);
            stmt.setString(1, menuCategory);
            ResultSet rs = stmt.executeQuery();
            String categoryId = null;
            
            if (rs.next()) 
            	categoryId = rs.getString("menuCategory_Id");
            
            rs.close();
            stmt.close();
            conn.close();

            return categoryId;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
