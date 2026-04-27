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
}