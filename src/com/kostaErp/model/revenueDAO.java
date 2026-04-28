package com.kostaErp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class revenueDAO {

	public revenueDAO(){}

	public List<revenueVO> selectMonthlyMenuSalesRank(String bId, String startDate, String endDate) {
		String sql = "SELECT RANK() OVER (ORDER BY SUM(s.saleMenuCount) DESC) AS ranking, s.menu_Id, m.menuName, m.menuPrice, SUM(s.saleMenuCount) AS totalSaleCount, SUM(s.saleMenuCount * m.menuPrice) AS totalSalesAmount FROM SALES s JOIN MENUS m ON s.menu_Id = m.menu_Id JOIN MENUC mc ON m.menuCategory_Id = mc.menuCategory_Id JOIN REVENUE r ON s.revenue_Id = r.revenue_Id WHERE mc.bId = ? AND r.revenueDate >= TO_DATE(?, 'YYYY-MM-DD') AND r.revenueDate < TO_DATE(?, 'YYYY-MM-DD') GROUP BY s.menu_Id, m.menuName, m.menuPrice ORDER BY totalSaleCount DESC";
		List<revenueVO> list = new ArrayList<>();

		Connection conn;

		try {
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, bId);
			stmt.setString(2, startDate);
			stmt.setString(3, endDate);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				revenueVO vo = new revenueVO();

				vo.setRanking(rs.getInt("ranking"));
				vo.setMenuId(rs.getString("menu_Id"));
				vo.setMenuName(rs.getString("menuName"));
				vo.setMenuPrice(rs.getInt("menuPrice"));
				vo.setTotalSaleCount(rs.getInt("totalSaleCount"));
				vo.setTotalSalesAmount(rs.getInt("totalSalesAmount"));

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

	public List<userInfoVO> checkMemberByVO(String bId, String name, String pw) throws ClassNotFoundException {
		String sql = "SELECT bId, name, storeName, storeType, pw FROM USERINFO " +
				"WHERE bId = ? AND name = ? AND pw = ?";
		List<userInfoVO> list = new ArrayList<>();

		try (Connection conn = DBCP.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, bId);
			stmt.setString(2, name);
			stmt.setString(3, pw);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					userInfoVO member = new userInfoVO();
					member.setbId(rs.getString("bId"));
					member.setName(rs.getString("name"));
					member.setPw(rs.getString("pw"));
					member.setStoreName(rs.getString("storeName"));

					list.add(member);
					return list;
				}
			}
		} catch (SQLException e) {
			System.err.println("로그인 체크 중 DB 에러: " + e.getMessage());
		}
		return null;
	}






}
