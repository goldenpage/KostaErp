package com.kostaErp.model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class userDAO {
	
	public userDAO(){}

	// 1. 회원가입
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
			stmt.setDate(11, Date.valueOf(marketingDate));

			result = stmt.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	// 2. 로그인
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
			System.err.println("로그인 체크 중 DB 에러: " + e.getMessage());
		}
		return null;
	}

	// 3. 비밀번호 변경 
	public int setPw(String pw, String bId, String name, String phone){

		int result = 0;

		String sql = "UPDATE USERINFO SET pw = ? WHERE bId = ? AND NAME = ? AND PHONE = ?";

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, pw);
			stmt.setString(2, bId);
			stmt.setString(3, name);
			stmt.setString(4, phone);

			result = stmt.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	// 4. 마케팅 동의
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

}