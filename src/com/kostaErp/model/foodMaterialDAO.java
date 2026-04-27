package com.kostaErp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class foodMaterialDAO {

    public int getFoodMaterialCount(String bId) {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM FOODM WHERE bId = ?";

        try {
            Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, bId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public List<foodMaterialVO> getFoodMaterialList(String bId, String sortType, int page, int pageSize) {
        List<foodMaterialVO> list = new ArrayList<foodMaterialVO>();

        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        String sql = getFoodListSql(sortType);

        try {
            Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, bId);
            stmt.setInt(2, startRow);
            stmt.setInt(3, endRow);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                foodMaterialVO vo = new foodMaterialVO();

                vo.setFoodMaterialId(rs.getString("foodMaterial_Id"));
                vo.setFoodMaterialName(rs.getString("foodMaterialName"));
                vo.setFoodCategory(rs.getString("foodCategory"));
                vo.setFoodMaterialCount(rs.getInt("foodMaterialCount"));
                vo.setFoodMaterialCountAll(rs.getInt("foodMaterialCountAll"));
                vo.setFoodMaterialPrice(rs.getInt("foodMaterialPrice"));
                vo.setVender(rs.getString("vender"));
                vo.setIncomeDate(rs.getDate("incomeDate"));
                vo.setExpirationDate(rs.getDate("expirationDate"));
                vo.setFoodMaterialType(rs.getString("foodMaterialType"));

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

    public foodMaterialVO getFoodMaterialDetail(String foodMaterialId) {
        foodMaterialVO vo = null;

        String sql =
            "SELECT f.foodMaterial_Id, f.foodMaterialName, c.foodCategory, " +
            " f.foodMaterialCount, f.foodMaterialCountAll, f.foodMaterialPrice, " +
            " f.foodMaterialType, f.vender, f.incomeDate, f.expirationDate, f.bId " +
            "FROM FOODM f " +
            "JOIN FOODC c ON f.foodCategory_Id = c.foodCategory_Id " +
            "WHERE f.foodMaterial_Id = ?";

        try {
            Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, foodMaterialId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                vo = new foodMaterialVO();

                vo.setFoodMaterialId(rs.getString("foodMaterial_Id"));
                vo.setFoodMaterialName(rs.getString("foodMaterialName"));
                vo.setFoodCategory(rs.getString("foodCategory"));
                vo.setFoodMaterialCount(rs.getInt("foodMaterialCount"));
                vo.setFoodMaterialCountAll(rs.getInt("foodMaterialCountAll"));
                vo.setFoodMaterialPrice(rs.getInt("foodMaterialPrice"));
                vo.setFoodMaterialType(rs.getString("foodMaterialType"));
                vo.setVender(rs.getString("vender"));
                vo.setIncomeDate(rs.getDate("incomeDate"));
                vo.setExpirationDate(rs.getDate("expirationDate"));
                vo.setbId(rs.getString("bId"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vo;
    }

    private String getFoodListSql(String sortType) {
        String orderBy = "f.foodMaterial_Id DESC";

        if ("idAsc".equals(sortType)) {
            orderBy = "f.foodMaterial_Id ASC";
        } else if ("idDesc".equals(sortType)) {
            orderBy = "f.foodMaterial_Id DESC";
        } else if ("expAsc".equals(sortType)) {
            orderBy = "f.expirationDate ASC";
        } else if ("expDesc".equals(sortType)) {
            orderBy = "f.expirationDate DESC";
        }

        return
            "SELECT * " +
            "FROM ( " +
            " SELECT ROW_NUMBER() OVER (ORDER BY " + orderBy + ") AS rn, " +
            " f.foodMaterial_Id, f.foodMaterialName, c.foodCategory, " +
            " f.foodMaterialCount, f.foodMaterialCountAll, f.foodMaterialPrice, " +
            " f.vender, f.incomeDate, f.expirationDate, f.foodMaterialType " +
            " FROM FOODM f " +
            " JOIN FOODC c ON f.foodCategory_Id = c.foodCategory_Id " +
            " WHERE f.bId = ? " +
            ") " +
            "WHERE rn BETWEEN ? AND ?";
    }
}