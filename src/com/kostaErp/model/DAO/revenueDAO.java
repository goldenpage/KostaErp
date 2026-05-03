package com.kostaErp.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kostaErp.model.DBCP;
import com.kostaErp.model.Query;
import com.kostaErp.model.VO.revenueVO;
import com.kostaErp.model.VO.userInfoVO;

public class revenueDAO {

	public revenueDAO(){}
	
	// 1. 
	public List<revenueVO> getMonthlyMenuSalesRank(String bId, String startDate, String endDate) {
		String sql = Query.GET_Monthly_MENU_SALE_RANK;

	    List<revenueVO> list = new ArrayList<>();

	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
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
		
		Connection conn;
		try {
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(Query.GET_REVENU);
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
	   String sql = Query.GET_MONTHLY_REVENUE;
	     

	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
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
		String sql = Query.CHECK_MEMBER_BY_VO;
		
		List<userInfoVO> list = new ArrayList<>();

		
		
		try (Connection conn = DBCP.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {

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
