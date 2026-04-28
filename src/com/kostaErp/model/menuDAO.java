package com.kostaErp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class menuDAO {

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