package com.kostaErp.model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userDAO {

	public userDAO(){}

	public int register(String bId, String name, String phone, 
			String email, String storeName, String storeType, String storeCategory, 
			String pw, String signDate, String agreementDate, String marketingDate){

		int result = 0;

		String sql = "INSERT INTO USERINFO (bid,name,phone,email,storeName, "
				+ "storeType,storeCategory,pw,signDate,agreementDate,marketingDate) " 
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, bId);
			stmt.setString(2, name);
			stmt.setString(3, phone);
			stmt.setString(4, email);
			stmt.setString(5, storeName);
			stmt.setString(6, storeType);
			stmt.setString(7, storeCategory);
			stmt.setString(8, pw);
			stmt.setDate(9, Date.valueOf(signDate));
			stmt.setDate(10, Date.valueOf(agreementDate));
			if(marketingDate != null) {
				stmt.setDate(11, Date.valueOf(marketingDate));
			} else {
				stmt.setDate(11, null);
			}
			
			result = stmt.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	// 2. �α���
	public userInfoVO checkMemberByVO(String bId, String name, String pw) throws ClassNotFoundException {
		String sql = "SELECT bId, name, pw FROM USERINFO " +
				"WHERE bId = ? AND name = ? AND pw = ?";

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
					return member;
				}
			}
		} catch (SQLException e) {
			System.err.println("�α��� üũ �� DB ����: " + e.getMessage());
		}
		return null;
	}

	// 3. ��й�ȣ ���� 
	public int setPw(String pw, String bId, String name, String phone){

		int result = 0;

		String sql = "UPDATE USERINFO SET pw = ? WHERE bId = ? AND NAME = ? AND PHONE = ?";
		Connection conn;

		try{
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, pw);
			stmt.setString(2, bId);
			stmt.setString(3, name);
			stmt.setString(4, phone);

			result = stmt.executeUpdate();

			
			
			stmt.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		} 
		return result;
	}

	public boolean checkPwFindUser(String bId, String name, String phone) {
		boolean flag = false;
		String sql = "SELECT bId FROM USERINFO WHERE bId = ? AND name = ? AND phone = ?";
		Connection conn;
		

		try {
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bId);
			stmt.setString(2, name);
			stmt.setString(3, phone);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				flag = true;
			}
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return flag;
	}

	// 4. ������ ����
	public List<userInfoVO> getMarketingMembers() throws ClassNotFoundException {
		String sql = "SELECT bid, name, phone, email, marketingDate FROM USERINFO WHERE marketingDate IS NOT NULL";
		List<userInfoVO> list = new ArrayList<>();

		try (Connection conn = DBCP.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				userInfoVO member = new userInfoVO();
				member.setbId(rs.getString("bid"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setEmail(rs.getString("email"));
				member.setMarketingDate(rs.getString("marketingDate")); 

				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean addUser(String parameter, String parameter2, String parameter3) {
		// TODO Auto-generated method stub
		return false;
	}


	public Map<String,Object> login(String bId, String pw) {
		Map<String, Object> tmp = new HashMap<>();
		
		String sql = "SELECT  bId, name FROM USERINFO" 
				+ " WHERE bId = ?  AND pw = ? ";
	
		Connection conn;
		try {
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bId );
			stmt.setString(2, pw);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				tmp.put("bId", rs.getString(1));
				tmp.put("name", rs.getString(2));
				
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return tmp;
	}
	
	//휴대폰인증
	public boolean getPhoneCheck(String phone) {
		boolean flag = true;
		String sql = "SELECT bId, name, phone FROM USERINFO" 
				+ " WHERE phone = ? ";
		
		Connection conn;
		try {
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, phone );
		
			ResultSet rs = stmt.executeQuery();
			boolean exists = rs.next();
			System.out.println("DAO phone=[" + phone + "]");
			System.out.println("DAO exists=" + exists);

			if (!exists) {
			    flag = true;
			} else {
			    System.out.println("DB phone=" + rs.getString("phone"));
			    flag = false;
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	public boolean getBid(String bId) {
		boolean flag = false;
		String sql = "SELECT bId FROM USERINFO WHERE bId = ?";
		
		Connection conn;
		try {
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bId );
		
			ResultSet rs = stmt.executeQuery();

			if(!rs.next()){
				flag = true;
			}
		
			rs.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return flag;
		
	}

}
