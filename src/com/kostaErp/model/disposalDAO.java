package com.kostaErp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class disposalDAO {

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
		String sql = "SELECTTRUNC(d.disposalDate) AS disposalDay,SUM(CASE WHEN f.foodMaterialType = '고체' THEN 1 ELSE 0 END) AS solidCount,SUM(CASE WHEN f.foodMaterialType = '액체' THEN 1 ELSE 0 END) AS liquidCountFROM DISPOSALS d JOIN FOODM fON d.foodMaterial_Id = f.foodMaterial_IdWHERE d.bId = ?AND d.disposalDate >= TO_DATE(?, 'YYYY-MM-DD')AND d.disposalDate < TO_DATE(?, 'YYYY-MM-DD')GROUP BY TRUNC(d.disposalDate)ORDER BY disposalDay";

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


