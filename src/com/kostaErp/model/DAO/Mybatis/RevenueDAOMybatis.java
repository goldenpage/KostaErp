package com.kostaErp.model.DAO.Mybatis;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kostaErp.model.DBCPMybatis;
import com.kostaErp.model.Interface.RevenueDAOInterface;
import com.kostaErp.model.VO.revenueVO;

public class RevenueDAOMybatis implements RevenueDAOInterface {

	@Override
	public List<revenueVO> getMonthlyMenuSalesRank(String bId, String startDate, String endDate) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, String> param = new HashMap<>();
		param.put("bId", bId);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		
		List<revenueVO> list = session.selectList("revenueMapper.getMonthlyMenuSalesRank", param);
		return list;
	}

	@Override
	public int getRevenue(String bId, String startDate, String endDate) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int result = session.selectOne("revenueMapper.getRevenue");
		
		return result;
	}

	@Override
	public int getMonthlyRevenue(String bId, String startDate, String endDate) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, String> param = new HashMap<>();
		param.put("bId", endDate);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		int result = session.selectOne("revenueMapper.getMonthlyRevenue", param);
		return result;
	}

	
	
}
