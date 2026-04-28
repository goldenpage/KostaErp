package com.kostaErp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class foodMaterialDAO {

	public int getFoodMaterialTotalAmount(String bId) {
		String sql = "SELECT SUM(foodMaterialPrice) FROM FOODM WHERE bId = ?;";
		int totalAmount = 0;

		Connection conn;
		try {
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bId);

			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return totalAmount;

	}

	public List<foodMaterialVO> getFoodMaterialSpendingRank(String bId, String startDate, String endDate) {
		String sql = "SELECT foodMaterial_Id, foodMaterialName, foodMaterialPrice * foodMaterialCount AS totalExpense FROM FOODM WHERE bId = ? AND incomeDate >= TO_DATE(?, 'YYYY-MM-DD') AND incomeDate < TO_DATE(?, 'YYYY-MM-DD') ORDER BY totalExpense DESC;";
		List<foodMaterialVO> list = new ArrayList<>();
		Connection conn;
		try {
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bId);
			stmt.setString(2, startDate);
			stmt.setString(3, endDate);

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				foodMaterialVO vo = new foodMaterialVO();
				vo.setRanking(rs.getInt("ranking"));
				vo.setFoodMaterialId(rs.getString("foodMaterial_Id"));
				vo.setFoodMaterialName(rs.getString("foodMaterialName"));
				vo.setFoodMaterialPrice(rs.getInt("foodMaterialPrice"));
				vo.setFoodMaterialCount(rs.getInt("foodMaterialCount"));
				vo.setTotalExpense(rs.getInt("totalExpense"));
				vo.setIncomeDate(rs.getDate("incomeDate"));
				vo.setbId(rs.getString("bId"));

				list.add(vo);

			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}


		 return list;
	}

}
