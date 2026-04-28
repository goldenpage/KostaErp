package com.kostaErp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class disposalDAO {	
	//1. 횈처짹창 횉째쨍챰 횁쨋횊쨍
	public List<disposalVO> getDisposals() {
		List<disposalVO> list = new ArrayList<>();
		String sql = "SELECT disposal_Id, disposalCountAll, disposalPrice, disposalDate, reason_Id, foodMaterial_id FROM DISPOSALS";
		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				disposalVO vo = new disposalVO();
				vo.setDisposalId(rs.getString("disposal_Id"));
				vo.setDisposalCountAll(rs.getInt("disposalCountAll"));
				vo.setDisposalPrice(rs.getInt("disposalPrice"));
				vo.setDisposalDate(rs.getString("disposalDate"));
				vo.setReasonId(rs.getString("reason_Id"));
				vo.setFoodMaterialId(rs.getString("foodMaterial_id"));
				list.add(vo);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//2. 횈처짹창 쩍횆�횣�챌쨍챠 횁쨋횊쨍
	public List<String> getFoodMaterialNames() {
		List<String> list = new ArrayList<>();
		String sql = "SELECT f.foodMaterialName FROM DISPOSALS d JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id";
		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(rs.getString("foodMaterialName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 3. 횈처짹창 쩍횆�횣�챌 횆짬횇횞째챠쨍짰 횁쨋횊쨍
	public List<String> getCategories() {
		List<String> list = new ArrayList<>();
		String sql = "SELECT fc.foodCategory FROM DISPOSALS d JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id JOIN FOODC fc ON f.foodCategory_Id = fc.foodCategory_Id";

		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(rs.getString("foodCategory"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//4. 횈처짹창쨩챌�짱 횁쨋횊쨍
	public List<String> getReasons() {
		List<String> list = new ArrayList<>();
		String sql = "SELECT reason FROM REASON";
		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(rs.getString("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//5. 횈처짹창횉째쨍챰 횈채�횑횁철 �횑쨉쩔 (횈채�횑횂징)
	public List<disposalVO> getDisposalsPaging(int start, int end) {
		List<disposalVO> list = new ArrayList<>();
		String sql = "SELECT disposal_Id, disposalCountAll, disposalPrice, disposalDate, reason_Id, foodMaterial_id FROM (" +
				" SELECT d.disposal_Id, d.disposalCountAll, d.disposalPrice, d.disposalDate, d.reason_Id, d.foodMaterial_id," +
				" ROW_NUMBER() OVER (ORDER BY d.disposal_Id DESC) AS rn" +
				" FROM DISPOSALS d" +
				") WHERE rn BETWEEN ? AND ?";
		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				disposalVO vo = new disposalVO();
				vo.setDisposalId(rs.getString("disposal_Id"));
				vo.setDisposalCountAll(rs.getInt("disposalCountAll"));
				vo.setDisposalPrice(rs.getInt("disposalPrice"));
				vo.setDisposalDate(rs.getString("disposalDate"));
				vo.setReasonId(rs.getString("reason_Id"));
				vo.setFoodMaterialId(rs.getString("foodMaterial_id"));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//6. 횈처짹창쨩챌�짱 쩌철횁짚
	public boolean updateReason(String disposalId, String reasonId) {
		String sql = "UPDATE DISPOSALS SET reason_Id = ? WHERE disposal_Id = ?";
		try (Connection conn = DBCP.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, reasonId);
			pstmt.setString(2, disposalId);
			return pstmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public double selectDisposalRate(String bId, String startDate, String endDate) throws ClassNotFoundException{
		String sql = "ROUND((SELECT NVL(SUM(d.disposalPrice), 0)FROM DISPOSALS dWHERE d.bId = ?AND d.disposalDate >= TO_DATE(?, 'YYYY-MM-DD')AND d.disposalDate < TO_DATE(?, 'YYYY-MM-DD'))* 100/NULLIF((SELECT NVL(SUM(f.foodMaterialPrice * f.foodMaterialCount), 0)FROM FOODM fWHERE f.bId = ?AND f.incomeDate >= TO_DATE(?, 'YYYY-MM-DD')AND f.incomeDate < TO_DATE(?, 'YYYY-MM-DD')),0),2) AS disposalRateFROM dual";

		try (
				Connection conn = DBCP.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)
				) {
			stmt.setString(1, bId);
			stmt.setString(2, startDate);
			stmt.setString(3, endDate);

			stmt.setString(4, bId);
			stmt.setString(5, startDate);
			stmt.setString(6, endDate);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getDouble("disposalRate");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public int selectTotalDisposalPrice(String bId, String startDate, String endDate) throws ClassNotFoundException{
		String sql = "SELECT NVL(SUM(disposalPrice), 0) AS totalDisposalPrice FROM DISPOSALS WHERE bId = ? AND disposalDate >= TO_DATE(?, 'YYYY-MM-DD') AND disposalDate < TO_DATE(?, 'YYYY-MM-DD')";

		try (
				Connection conn = DBCP.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)
				) {
			stmt.setString(1, bId);
			stmt.setString(2, startDate);
			stmt.setString(3, endDate);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("totalDisposalPrice");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public List<disposalVO> selectTop3DisposalItems(String bId, String startDate, String endDate) throws ClassNotFoundException{
		String sql = "SELECT *FROM (SELECT f.foodMaterial_Id, f.foodMaterialName, SUM(d.disposalPrice) AS totalDisposalPrice, COUNT(d.disposal_Id) AS disposalCount FROM DISPOSALS d JOIN FOODM f ON d.foodMaterial_Id = f.foodMaterial_Id WHERE d.bId = ? AND d.disposalDate >= TO_DATE(?, 'YYYY-MM-DD') AND d.disposalDate < TO_DATE(?, 'YYYY-MM-DD') GROUP BY f.foodMaterial_Id, f.foodMaterialName ORDER BY totalDisposalPrice DESC) WHERE ROWNUM <= 3";

		List<disposalVO> list = new ArrayList<>();

		try (
				Connection conn = DBCP.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)
				) {
			stmt.setString(1, bId);
			stmt.setString(2, startDate);
			stmt.setString(3, endDate);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					disposalVO vo = new disposalVO();

					vo.setFoodMaterialId(rs.getString("foodMaterial_Id"));
					vo.setFoodMaterialName(rs.getString("foodMaterialName"));
					vo.setTotalDisposalPrice(rs.getInt("totalDisposalPrice"));
					vo.setDisposalCount(rs.getInt("disposalCount"));

					list.add(vo);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<disposalVO> selectDisposalReasonRatio(String bId, String startDate, String endDate) throws ClassNotFoundException{
		String sql = "SELECT r.reason_Id, r.reason, COUNT(d.disposal_Id) AS disposalCount, ROUND( COUNT(d.disposal_Id) * 100 / SUM(COUNT(d.disposal_Id)) OVER (),2) AS reasonRatio FROM DISPOSALS d JOIN REASON r ON d.reason_Id = r.reason_Id WHERE d.bId = ? AND d.disposalDate >= TO_DATE(?, 'YYYY-MM-DD') AND d.disposalDate < TO_DATE(?, 'YYYY-MM-DD') GROUP BY r.reason_Id, r.reason ORDER BY reasonRatio DESC";

		List<disposalVO> list = new ArrayList<>();

		try (
				Connection conn = DBCP.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)
				) {
			stmt.setString(1, bId);
			stmt.setString(2, startDate);
			stmt.setString(3, endDate);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					disposalVO vo = new disposalVO();

					vo.setReasonId(rs.getString("reason_Id"));
					vo.setReason(rs.getString("reason"));
					vo.setDisposalCount(rs.getInt("disposalCount"));
					vo.setReasonRatio(rs.getDouble("reasonRatio"));

					list.add(vo);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<disposalVO> selectDailyDisposalAmount(String bId, String startDate, String endDate) throws ClassNotFoundException{
		String sql = "SELECTTRUNC(disposalDate) AS disposalDay,COUNT(disposal_Id) AS disposalCount,NVL(SUM(disposalPrice), 0) AS totalDisposalPriceFROM DISPOSALSWHERE bId = ?AND disposalDate >= TO_DATE(?, 'YYYY-MM-DD')AND disposalDate < TO_DATE(?, 'YYYY-MM-DD')GROUP BY TRUNC(disposalDate)ORDER BY disposalDay";

		List<disposalVO> list = new ArrayList<>();

		try (
				Connection conn = DBCP.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)
				) {
			stmt.setString(1, bId);
			stmt.setString(2, startDate);
			stmt.setString(3, endDate);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					disposalVO vo = new disposalVO();

					vo.setDisposalDay(rs.getDate("disposalDay"));
					vo.setDisposalCount(rs.getInt("disposalCount"));
					vo.setTotalDisposalPrice(rs.getInt("totalDisposalPrice"));

					list.add(vo);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	public List<disposalVO> selectDailyDisposalByType(String bId, String startDate, String endDate) throws ClassNotFoundException{
		String sql = "SELECTTRUNC(d.disposalDate) AS disposalDay,SUM(CASE WHEN f.foodMaterialType = '째챠횄쩌' THEN 1 ELSE 0 END) AS solidCount,SUM(CASE WHEN f.foodMaterialType = '쩐횞횄쩌' THEN 1 ELSE 0 END) AS liquidCountFROM DISPOSALS d JOIN FOODM fON d.foodMaterial_Id = f.foodMaterial_IdWHERE d.bId = ?AND d.disposalDate >= TO_DATE(?, 'YYYY-MM-DD')AND d.disposalDate < TO_DATE(?, 'YYYY-MM-DD')GROUP BY TRUNC(d.disposalDate)ORDER BY disposalDay";

		List<disposalVO> list = new ArrayList<>();

		try (
				Connection conn = DBCP.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)
				) {
			stmt.setString(1, bId);
			stmt.setString(2, startDate);
			stmt.setString(3, endDate);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					disposalVO vo = new disposalVO();

					vo.setDisposalDay(rs.getDate("disposalDay"));
					vo.setSolidCount(rs.getInt("solidCount"));
					vo.setLiquidCount(rs.getInt("liquidCount"));

					list.add(vo);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
}



