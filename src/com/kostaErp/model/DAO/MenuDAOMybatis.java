package com.kostaErp.model.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kostaErp.model.DBCPMybatis;
import com.kostaErp.model.VO.MenuCategoryVOMybatis;
import com.kostaErp.model.VO.MenuVOMybatis;
import com.kostaErp.model.VO.UsedVOMybatis;
import com.kostaErp.model.VO.menuCategoryVO;
import com.kostaErp.model.VO.menuVO;

public class MenuDAOMybatis implements MenuDAOInterface{

	// 1. 메뉴 추가
	@Override
	public String addMenu(String menuName, int menuPrice, String menuCategoryId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		MenuVOMybatis vo = new MenuVOMybatis(menuName, menuPrice, menuCategoryId);
		int result = session.insert("menuMapper.addMenu", vo);
		if(result > 0) session.commit();
		String menuId = session.selectOne("menuMapper.getNewMenuId", vo);

		session.close();
		return menuId;
	}

	// 2. 메뉴카테고리 추가
	@Override
	public int addMenuCategory(String menuCategory, String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		MenuCategoryVOMybatis vo = new MenuCategoryVOMybatis(menuCategory, bId);
		int count = session.selectOne("menuMapper.checkMenuCategoryExists", vo);
		
		if (count > 0) return 0;
		int result = session.insert("menuMapper.addMenuCategory", vo);
		if (result > 0) session.commit();
		
		session.close();
		return result;
	}

	// 3. 메뉴 카테고리 삭제
	@Override
	public int deleteMenuCategory(String menuCategory) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		int result = session.delete("menuMapper.deleteMenuCategory", menuCategory);
		
		if(result > 0) session.commit();
		
		session.close();
		return result;
	}

	// 4. 사용 식자재 추가
	@Override
	public int addUsedMaterial(int usedCount, String foodMaterialId, String menuId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		int result = session.insert("menuMapper.addUsedMaterial", new UsedVOMybatis(usedCount, foodMaterialId, menuId));
		
		if(result > 0) session.commit();
		
		session.close();
		return result;
	}

	// 5. 사용 식자재 삭제
	@Override
	public int deleteUsedMaterial(String usedMaterialId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		int result = session.delete("menuMapper.deleteUsedMaterial", usedMaterialId);
		
		if(result > 0) session.commit();
		
		session.close();
		return result;
	}

	// 6. 메뉴리스트 전체 가져오기
	@Override
	public List<MenuVOMybatis> getMenuList(String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<MenuVOMybatis> result = new ArrayList<MenuVOMybatis>();
		
		result = session.selectList("menuMapper.getMenuList", bId);

		session.close();
		return result;
	}
	
	// 7. 메뉴 사용 식자재 가져오기
	@Override
	public List<MenuVOMybatis> getMenuDetail(String menuId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<MenuVOMybatis> result = new ArrayList<MenuVOMybatis>();
		
		result = session.selectList("menuMapper.getMenuDetail", menuId);
		
		session.close();
		return result;
	}

	// 8
	@Override
	public boolean updateFoodMaterialAfterSale(String menuId, int saleCount, String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		Map<String, Object> map = new HashMap<>();
		map.put("menuId", menuId);
		map.put("saleCount", saleCount);
		map.put("bId", bId);
		int result = session.update("menuMapper.updateFoodMaterialAfterSale", map);
		
		if(result > 0) session.commit();
		
		session.close();
		return result > 0;
	}

	// 9
	@Override
	public List<MenuCategoryVOMybatis> getMenuCategoryList(String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<MenuCategoryVOMybatis> result = new ArrayList<MenuCategoryVOMybatis>();
		
		result = session.selectList("menuMapper.getMenuCategoryList", bId);
		
		session.close();
		return result;
	}

	// 10
	@Override
	public boolean hasMenuByCategory(String menuCategory) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		boolean result = (session.selectOne("menuMapper.hasMenuByCategory", menuCategory) != null);
		
		session.close();
		return result;
	}

	// 11
	@Override
	public boolean hasMenuCheck(String menuName) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		boolean result = (session.selectOne("menuMapper.hasMenuCheck", menuName) != null);
		
		session.close();
		
		return result;
	}
	
	// 12
	@Override
	public String getCategoryId(String menuCategory) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		
		String result = session.selectOne("menuMapper.getCategoryId", menuCategory);
		session.close();
		
		return result;
	}
}