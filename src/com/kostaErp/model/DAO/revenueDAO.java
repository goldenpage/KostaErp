package com.kostaErp.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kostaErp.model.DBCP;
import com.kostaErp.model.VO.revenueVO;
import com.kostaErp.model.VO.userInfoVO;

public class revenueDAO {

	public revenueDAO(){}
	
	// 1. ���� �޴� �Ǹ� ��ŷ ��ȸ
	public List<revenueVO> getMonthlyMenuSalesRank(String bId, String startDate, String endDate) {
	    String sql =
	        "SELECT " +
	        "    RANK() OVER (ORDER BY SUM(s.saleMenuCount) DESC) AS ranking, " +
	        "    s.menu_Id, " +
	        "    m.menuName, " +
	        "    m.menuPrice, " +
	        "    SUM(s.saleMenuCount) AS totalSaleCount, " +
	        "    SUM(s.saleMenuCount * m.menuPrice) AS totalSalesAmount " +
	        "FROM SALES s " +
	        "JOIN MENUS m ON s.menu_Id = m.menu_Id " +
	        "JOIN MENUC mc ON m.menuCategory_Id = mc.menuCategory_Id " +
	        "JOIN REVENUE r ON s.revenue_Id = r.revenue_Id " +
	        "WHERE mc.bId = ? " +
	        "AND r.revenueDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND r.revenueDate < TO_DATE(?, 'YYYY-MM-DD') " +
	        "GROUP BY s.menu_Id, m.menuName, m.menuPrice " +
	        "ORDER BY ranking";

	    List<revenueVO> list = new ArrayList<>();

	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {
	        stmt.setString(1, bId);
	        stmt.setString(2, startDate);
	        stmt.setString(3, endDate);

	        try (ResultSet rs = stmt.executeQuery()) {
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
	        }

	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	public int getRevenue(String bId, String startDate, String endDate) {
		String sql = "SELECT SUM(menuprice * salemenucount) FROM SALES, MENUS";
		
		Connection conn;
		try {
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bId);
	        stmt.setString(2, startDate);
	        stmt.setString(3, endDate);
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
        
		return 0;
	}
	
	
	// ���� �� ����
	public int getMonthlyRevenue(String bId, String startDate, String endDate) {
	    String sql =
	        "SELECT NVL(SUM(s.saleMenuCount * m.menuPrice), 0) AS totalRevenue " +
	        "FROM SALES s " +
	        "JOIN MENUS m ON s.menu_Id = m.menu_Id " +
	        "JOIN MENUC mc ON m.menuCategory_Id = mc.menuCategory_Id " +
	        "JOIN REVENUE r ON s.revenue_Id = r.revenue_Id " +
	        "WHERE mc.bId = ? " +
	        "AND r.revenueDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND r.revenueDate < TO_DATE(?, 'YYYY-MM-DD')";

	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {
	        stmt.setString(1, bId);
	        stmt.setString(2, startDate);
	        stmt.setString(3, endDate);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("totalRevenue");
	            }
	        }

	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return 0;
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
			System.err.println("�α��� üũ �� DB ����: " + e.getMessage());
		}
		return null;
	}






}
