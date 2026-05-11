package com.kostaErp.model.DAO.Mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kostaErp.model.DBCPMybatis;
import com.kostaErp.model.Interface.FoodMaterialDAOInterface;
import com.kostaErp.model.VO.foodMaterialCategoryVO;
import com.kostaErp.model.VO.foodMaterialVO;

public class FoodMaterialDAOMybatis implements FoodMaterialDAOInterface {

	@Override
	public int getFoodMaterialCount(String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int count = session.selectOne("foodMaterialMapper.getFoodMaterialCount", bId);
		session.close();
		return count;
	}
	
	@Override
	public int addFoodMaterial(List<foodMaterialVO> list, String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int successCount = 0;

		for (foodMaterialVO vo : list) {
		    vo.setbId(bId);
		    successCount += session.insert("foodMaterialMapper.addFoodMaterial", vo);
		}

		if (successCount == list.size()) {
			session.commit();
		} else {
			session.rollback();
		}

		session.close();
		return successCount;
	}

	@Override
	public int addFoodCategory(String foodCategory) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

		int result1 = session.selectOne("foodMaterialMapper.addFoodCategory", foodCategory);

		if (result1 > 0) {
			session.close();
			return 0;
		}

		int result2 = session.insert("foodMaterialMapper.addFoodCategory1", foodCategory);

		if (result2 > 0) {
			session.commit();
		} else {
			session.rollback();
		}

		session.close();
		return result2;
	}

	@Override
	public int deleteFoodCategory(String foodCategory) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

		int result1 = session.delete("foodMaterialMapper.deleteFoodCategory", foodCategory);
		int result2 = session.delete("foodMaterialMapper.deleteFoodCategory1", foodCategory);

		if (result2 > 0) {
			session.commit();
		} else {
			session.rollback();
		}

		session.close();
		return result2;
	}

	@Override
	public List<foodMaterialVO> getFoodMaterialByName(String foodMaterialName, String bId) {
	    SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

	    Map<String, Object> param = new HashMap<String, Object>();
	    param.put("foodMaterialName", foodMaterialName);
	    param.put("bId", bId);

	    List<foodMaterialVO> list =
	    		session.selectList("foodMaterialMapper.getFoodMaterialByName", param);

	    session.close();
	    return list;
	}

	@Override
	public List<foodMaterialVO> getFoodMaterialList(String bId, String sortType, int page, int pageSize) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

		int startRow = (page - 1) * pageSize + 1;
		int endRow = page * pageSize;

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bId", bId);
		param.put("orderBy", getOrderBy(sortType));
		param.put("startRow", startRow);
		param.put("endRow", endRow);

		List<foodMaterialVO> list = session.selectList("foodMaterialMapper.getFoodMaterialList", param);

		session.close();
		return list;
	}
	
	private String getOrderBy(String sortType) {
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

		return orderBy;
	}

	@Override
	public foodMaterialVO getFoodMaterialDetail(String foodMaterialId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

		foodMaterialVO vo = session.selectOne("foodMaterialMapper.getFoodMaterialDetail", foodMaterialId);

		session.close();
		return vo;
	}

	@Override
	public int deleteFoodMaterial(String foodMaterialId, String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

		session.delete("foodMaterialMapper.deleteUsedByFoodMaterial", foodMaterialId);
		session.delete("foodMaterialMapper.deleteDisposalsByFoodMaterial", foodMaterialId);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("foodMaterialId", foodMaterialId);
		param.put("bId", bId);

		int result = session.delete("foodMaterialMapper.deleteFoodMaterial", param);

		if (result > 0) {
			session.commit();
		} else {
			session.rollback();
		}

		session.close();
		return result;
	}

	@Override
	public int getFoodMaterialTotalAmount(String bId, String startDate, String endDate) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bId", bId);
		param.put("startDate", startDate);
		param.put("endDate", endDate);

		Integer result = session.selectOne("foodMaterialMapper.getFoodMaterialTotalAmount", param);

		session.close();

		if (result == null) {
			return 0;
		}

		return result;
	}

	@Override
	public List<foodMaterialVO> getFoodMaterialSpendingRank(String bId, String startDate, String endDate) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bId", bId);
		param.put("startDate", startDate);
		param.put("endDate", endDate);

		List<foodMaterialVO> list = session.selectList("foodMaterialMapper.getFoodMaterialSpendingRank", param);

		session.close();
		return list;
	}

	@Override
	public List<foodMaterialCategoryVO> getFoodCategoryList() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

		List<foodMaterialCategoryVO> list = session.selectList("foodMaterialMapper.getFoodCategoryList");

		session.close();
		return list;
	}

	@Override
	public boolean hasFoodMaterialByCategory(String foodCategory) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

		int count = session.selectOne("foodMaterialMapper.hasFoodMaterialByCategory", foodCategory);

		session.close();
		return count > 0;
	}

	@Override
	public List<foodMaterialVO> getFoodMaterialListAll(String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

		List<foodMaterialVO> list = session.selectList("foodMaterialMapper.getFoodMaterialListAll", bId);

		session.close();
		return list;
	}

	@Override
	public String getCategoryId(String foodCategory) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

		String categoryId = session.selectOne("foodMaterialMapper.getCategoryId", foodCategory);

		session.close();
		return categoryId;
	}
}