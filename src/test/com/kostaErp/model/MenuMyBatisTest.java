package test.com.kostaErp.model;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.kostaErp.model.DBCPMybatis;
import com.kostaErp.model.DAO.Mybatis.MenuDAOMybatis;
import com.kostaErp.model.Interface.MenuDAOInterface;
import com.kostaErp.model.VO.Mybatis.MenuCategoryVOMybatis;
import com.kostaErp.model.VO.Mybatis.MenuVOMybatis;

public class MenuMyBatisTest {

	// 1. 메뉴 추가
//	@Test
	public void addMenu() {
		MenuDAOInterface dao = new MenuDAOMybatis();
		
		System.out.println(dao.addMenu("충무김밥", 10000, "M001"));
	}
	
	// 2. 메뉴카테고리 추가
//	@Test
	public void addMenuCategory() {
		MenuDAOInterface dao = new MenuDAOMybatis();
		
		System.out.println(dao.addMenuCategory("돈까스", "0000000000"));
	}
	
	// 3. 메뉴 카테고리 삭제
//	@Test
	public void deleteMenuCategory() {
		MenuDAOInterface dao = new MenuDAOMybatis();
		
		System.out.println(dao.deleteMenuCategory("123123"));
	}

	// 4. 사용 식자재 추가
//	@Test
	public void addUsedMaterial() {
		MenuDAOInterface dao = new MenuDAOMybatis();
		
		System.out.println(dao.addUsedMaterial(30, "FM001", "MI001"));
	}
	
	// 5. 사용 식자재 삭제
//	@Test
	public void deleteUsedMaterial() {
		MenuDAOInterface dao = new MenuDAOMybatis();
		System.out.println(dao.deleteUsedMaterial("U024"));
	}

	// 6. 메뉴리스트 전체 가져오기
//	@Test
	public void getMenuList() {
		MenuDAOInterface dao = new MenuDAOMybatis();
		List<MenuVOMybatis> list = dao.getMenuList("0000000000");
		
		for (MenuVOMybatis vo : list) {
			System.out.println(vo.getMenuId() + vo.getMenuName()
			+ vo.getMenuCategory() + vo.getMenuPrice());
		}
	}
	
	// 7. 메뉴 사용 식자재 가져오기
//	@Test
	public void getMenuDetail() {
		MenuDAOInterface dao = new MenuDAOMybatis();
		List<MenuVOMybatis> list = dao.getMenuDetail("MI002");
		
		if (list != null) {
			for (MenuVOMybatis vo : list) {
				System.out.println(vo.getMenuId() + vo.getMenuName()
				 + vo.getMenuPrice() + vo.getFoodMaterialName()
				 + vo.getUsedCount() + vo.getFoodMaterialPrice()
				 + vo.getFoodMaterialCountAll() + vo.getUsedPrice());
			}
		}
	}
	
	// 8. 식자재 차감
//	@Test
	public void updateFoodMaterialAfterSale() {
		MenuDAOInterface dao = new MenuDAOMybatis();
		
		boolean result = dao.updateFoodMaterialAfterSale("MI001", 10, "0000000000");
		System.out.println("판매 처리 결과: " + result);
	}
	
	// 9. 메뉴 카테고리 전체 가져오기
//	@Test
	public void getMenuCategoryList() {
		MenuDAOInterface dao = new MenuDAOMybatis();
		List<MenuCategoryVOMybatis> list = dao.getMenuCategoryList("0000000000");
		
		if (list != null) {
			for (MenuCategoryVOMybatis vo : list) {
				System.out.println(vo.getMenuCategoryId() + vo.getMenuCategory());
			}
		}
	}
	
	// 10. 메뉴 카테고리 중복체크
//	@Test
	public void hasMenuByCategory(){
		MenuDAOInterface dao = new MenuDAOMybatis();
		System.out.println(dao.hasMenuByCategory("김밥류"));
	}
	
	// 11. 메뉴 중복체크
//	@Test
	public void hasMenuCheck(){
		MenuDAOInterface dao = new MenuDAOMybatis();
		System.out.println(dao.hasMenuCheck("치즈김밥"));
	}
	
	// 12. 메뉴 카테고리id가져오기
//	@Test
	public void getCategoryId(){
		MenuDAOInterface dao = new MenuDAOMybatis();
		System.out.println(dao.getCategoryId("1"));
	}
}
