package com.kostaErp.model.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;

import com.kostaErp.model.DBCPMybatis;
import com.kostaErp.model.DAO.Interface.DisposalDAOInterface;
import com.kostaErp.model.VO.disposalVO;

public class DisposalDAOMyBatis implements DisposalDAOInterface{

	//1
	@Override
	public List<disposalVO> getDisposals() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<disposalVO> list = session.selectList("DisposalMapper.getDisposals");
		session.close();
		return list;
	}

	//2
	@Override
	public List<String> getFoodMaterialNames() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<String> list = session.selectList("DisposalMapper.getFoodMaterialNames");
		session.close();
		return list;
	}

	//3
	@Override
	public List<String> getCategories() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<String> list = session.selectList("DisposalMapper.getCategories");
		session.close();
		return list;
	}
	
	//4
	@Override
	public List<disposalVO> getDisposalsFilteredPaging(String bId, String category, String reason, int start, int end) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> map = new HashMap<>();
		map.put("bId", bId);
		map.put("category", category);
		map.put("reason", reason);
		map.put("start", start);
		map.put("end", end);
		
		List<disposalVO> list = session.selectList("DisposalMapper.getDisposalsFilteredPaging", map);
		session.close();
		return list;
	}

	//5
	@Override
	public int getDisposalCount(String bId, String category, String reason) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> map = new HashMap<>();
		map.put("bId", bId);
		map.put("category", category);
		map.put("reason", reason);
		
		int count = session.selectOne("DisposalMapper.getDisposalCount", map);
		session.close();
		return count;
	}

	//6
	@Override
	public int getTotalCount(String bId, String category, String reason) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		 Map<String, Object> map = new HashMap<>();
		 map.put("bId", bId);
		 map.put("category", category);
		 map.put("reason", reason);
		 
		 int count = session.selectOne("DisposalMapper.getTotalCount", map);
		 session.close();
		 return count;
	}

	//7
	@Override
	public List<String> getReasons() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<String> list = session.selectList("DisposalMapper.getReason");
		session.close();
		return list;
	}

	//8
	@Override
	public List<disposalVO> getDisposalsByCategoryAndBId(String category, String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> map = new HashMap<>();
		map.put("category", category);
		map.put("bId", bId);
		
		List<disposalVO> list = session.selectList("DisposalMapper.getDisposalsByCategoryAndBId", map);
		session.close();
		return list;
	}

	//9
	@Override
	public List<disposalVO> getDisposalsPaging(String bId, int start, int end) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> map = new HashMap<>();
		map.put("bId", bId);
		map.put("start", start);
		map.put("end", end);
		
		List<disposalVO> list = session.selectList("DisposalMapper.getDisposalsPaging", map);
		session.close();
		return list;
	}

	//10
	@Override
	public boolean updateReason(String disposalId, String reasonId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> map = new HashMap<>();
		map.put("disposalId",  disposalId);
		map.put("reasonId",  reasonId);
		
		int result = session.update("DisposalMapper.updateReason", map);
		if(result == 1){
			session.commit();
			session.close();
			return true;
		}
		session.close();
		return false;
	}

	//11
	@Override
	public List<String> getExpiredDisposalIds(String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<String> list = session.selectList("DisposalMapper.getExpiredDisposalIds", bId);
		session.close();
		return list;
	}

	//12
	@Override
	public double getDisposalRate(String bId, String startDate, String endDate) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		Map<String, Object> map = new HashMap<>();
		map.put("bId", bId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		double rate = session.selectOne("DisposalMapper.getDisposalRate", map);
		session.close();
		return rate;
	}

	//13
	@Override
	public int getTotalDisposalPrice(String bId, String startDate, String endDate) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		Map<String, String> map = new HashMap<>();
		map.put("bId", bId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		int total = session.selectOne("DisposalMapper.getTotalDisposalPrice", map);
		session.close();
		return total;
	}

	//14
	@Override
	public List<disposalVO> getTop3DisposalItems(String bId, String startDate, String endDate) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		Map<String, String> map = new HashMap<>();
		map.put("bId", bId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		List<disposalVO> list = session.selectList("DisposalMapper.getTop3DisposalItems", map);
		session.close();
		return list;
	}

	//15
	@Override
	public List<disposalVO> getDisposalReasonRatio(String bId, String startDate, String endDate) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		Map<String, String> map = new HashMap<>();
		map.put("bId", bId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		List<disposalVO> list = session.selectList("DisposalMapper.getDisposalReasonRatio", map);
		session.close();
		return list;
	}

	//16
	@Override
	public List<disposalVO> selectDailyDisposalAmount(String bId, String startDate, String endDate) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		Map<String, String> map = new HashMap<>();
		map.put("bId", bId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		List<disposalVO> list = session.selectList("DisposalMapper.selectDailyDisposalAmount", map);
		session.close();
		return list;
	}

	//17
	@Override
	public List<disposalVO> selectDailyDisposalByType(String bId, String startDate, String endDate) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		Map<String, String> map = new HashMap<>();
		map.put("bId", bId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		List<disposalVO> list = session.selectList("DisposalMapper.selectDailyDisposalByType", map);
		session.close();
		return list;
	}
}
