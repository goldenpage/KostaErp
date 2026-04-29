package com.kostaErp.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class foodMaterialDAO {
	
	public foodMaterialDAO(){}

	public int addFoodMaterial(String foodMaterialName, String foodCategory_Id, int foodMaterialCount, 
			int foodMaterialCountAll, int foodMaterialPrice, String vender, String foodMaterialType, 
			String incomeDate, String expirationDate, String bId){

		int result = 0;

		String sql = "INSERT INTO FOODM(foodMaterialName, foodCategory_Id, foodMaterialCount, foodMaterialCountAll, "
				+ "foodMaterialPrice, foodMaterialType, vender, incomeDate, expirationDate, bId) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, foodMaterialName);
			stmt.setString(2, foodCategory_Id);
			stmt.setInt(3, foodMaterialCount);
			stmt.setInt(4, foodMaterialCountAll);
			stmt.setInt(5, foodMaterialPrice);
			stmt.setString(6, foodMaterialType);
			stmt.setString(7, vender);
			stmt.setDate(8, Date.valueOf(incomeDate));
			stmt.setDate(9, Date.valueOf(expirationDate));
			stmt.setString(10, bId);

			result = stmt.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	 // 2. 카테고리 추가
    public int addFoodCategory(String foodCategoryId, String foodCategory){
        int result = 0;
        String sql = "INSERT INTO FOODC(foodCategory_Id, foodCategory) VALUES(?, ?)";

        try{
        	Connection conn = DBCP.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, foodCategoryId);
            stmt.setString(2, foodCategory);
            
            result = stmt.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
	public int deleteFoodCategory(String foodCategory) {
		int result = 0;
		String sql = "DELETE FROM FOODC WHERE foodCategory = ?";

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, foodCategory);

			result = stmt.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public List<foodMaterialVO> getFoodMaterialByName(String foodMaterialName) {
		List<foodMaterialVO> list = new ArrayList<>();
		String sql = "SELECT foodMaterialName, foodCategory_Id, vender FROM FOODM "
				+ "WHERE foodMaterialName = ?";

		try{
			Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, foodMaterialName);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				foodMaterialVO foodMaterial = new foodMaterialVO();
				foodMaterial.setFoodMaterialName(rs.getString("foodMaterialName"));
				foodMaterial.setFoodCategory(rs.getString("foodCategory_Id"));
				foodMaterial.setVender(rs.getString("vender"));
				list.add(foodMaterial);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}


	//	public userInfoVO checkMemberByVO(String bId, String name, String pw) throws ClassNotFoundException {
	//	    String sql = "SELECT bId, name, storeName, storeType, pw FROM USERINFO " +
	//	                 "WHERE bId = ? AND name = ? AND pw = ?";
	//	    
	//	    try (Connection conn = DBCP.getConnection();
	//	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	//	        
	//	        stmt.setString(1, bId);
	//	        stmt.setString(2, name);
	//	        stmt.setString(3, pw);
	//
	//	        try (ResultSet rs = stmt.executeQuery()) {
	//	            if (rs.next()) {
	//	                userInfoVO member = new userInfoVO();
	//	                member.setbId(rs.getString("bId"));
	//	                member.setName(rs.getString("name"));
	//	                member.setPw(rs.getString("pw"));
	//	                member.setStoreName(rs.getString("storeName"));
	//	                return member;
	//	            }
	//	        }
	//	    } catch (SQLException e) {
	//	        System.err.println("쨌횓짹횞�횓 횄쩌횇짤 횁횩 DB 쩔징쨌짱: " + e.getMessage());
	//	    }
	//	    return null;
	//	}
	//
	public List<userInfoVO> getMarketingMembers() throws ClassNotFoundException {
		String sql = "SELECT bid, name, phone, email, marketingDate FROM USERINFO WHERE marketingDate IS NOT NULL";
		List<userInfoVO> list = new ArrayList<>();

		try (Connection conn = DBCP.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				userInfoVO member = new userInfoVO();
				
				member.setName(rs.getString("name"));
				
				member.setEmail(rs.getString("email"));
				

				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


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
	
	

	// 월별 총지출액
	public int getFoodMaterialTotalAmount(String bId, String startDate, String endDate) {
	    String sql =
	        "SELECT NVL(SUM(foodMaterialPrice), 0) AS totalAmount " +
	        "FROM FOODM " +
	        "WHERE bId = ? " +
	        "AND incomeDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND incomeDate < TO_DATE(?, 'YYYY-MM-DD')";

	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {
	        stmt.setString(1, bId);
	        stmt.setString(2, startDate);
	        stmt.setString(3, endDate);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("totalAmount");
	            }
	        }

	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return 0;
	}
	
	// 월별 지출 식자재 순위
	public List<foodMaterialVO> getFoodMaterialSpendingRank(String bId, String startDate, String endDate) {
	    String sql =
	        "SELECT " +
	        "    RANK() OVER (ORDER BY foodMaterialPrice * foodMaterialCount DESC) AS ranking, " +
	        "    foodMaterial_Id, " +
	        "    foodMaterialName, " +
	        "    foodMaterialPrice, " +
	        "    foodMaterialCount, " +
	        "    foodMaterialPrice * foodMaterialCount AS totalExpense, " +
	        "    incomeDate, " +
	        "    bId " +
	        "FROM FOODM " +
	        "WHERE bId = ? " +
	        "AND incomeDate >= TO_DATE(?, 'YYYY-MM-DD') " +
	        "AND incomeDate < TO_DATE(?, 'YYYY-MM-DD') " +
	        "ORDER BY totalExpense DESC";

	    List<foodMaterialVO> list = new ArrayList<>();

	    try (
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {
	        stmt.setString(1, bId);
	        stmt.setString(2, startDate);
	        stmt.setString(3, endDate);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
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
	        }

	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

}

