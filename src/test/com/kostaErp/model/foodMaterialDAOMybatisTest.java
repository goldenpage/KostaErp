package test.com.kostaErp.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kostaErp.model.DBCPMybatis;
import com.kostaErp.model.DAO.Mybatis.FoodMaterialDAOMybatis;
import com.kostaErp.model.Interface.FoodMaterialDAOInterface;
import com.kostaErp.model.VO.foodMaterialVO;
import com.kostaErp.model.VO.foodMaterialCategoryVO;

public class foodMaterialDAOMybatisTest {

	private SqlSession session;
	
	@Before
	public void setUp() throws Exception {
		session = DBCPMybatis.getSqlSessionFactory().openSession(false);
	}

	@After
	public void tearDown() throws Exception {
		session.rollback();
		session.close();
	}
	
	@Test
	public void getFoodMaterialCount_테스트(){
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		int count = dao.getFoodMaterialCount("0000000000");

		System.out.println(count);

		assertTrue(count >= 0);
	}
	
	@Test
	public void addFoodMaterial() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		List<foodMaterialVO> list = new ArrayList<>();

		foodMaterialVO vo = new foodMaterialVO();
		vo.setFoodMaterialName("단무지");
		vo.setFoodCategory("PP");
		vo.setFoodMaterialCount(5);
		vo.setFoodMaterialCountAll(1500);
		vo.setFoodMaterialPrice(15000);
		vo.setVender("하나로마트");
		vo.setFoodMaterialType("고체");
		vo.setIncomeDate(java.sql.Date.valueOf("2026-04-27"));
		vo.setExpirationDate(java.sql.Date.valueOf("2026-04-30"));
		list.add(vo);

		int successCount = dao.addFoodMaterial(list, "0000000000");

		assertTrue(successCount == list.size());
	}
	
	@Test
	public void addFoodCategory(){
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		int result = dao.addFoodCategory("가공식품");

		assertTrue(result >= 0);

		System.out.println(result);
	}
	
	@Test
	public void deleteFoodCategory() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		int result = dao.deleteFoodCategory("가공");

		assertTrue("삭제성공", result >= 0);

		System.out.println(result);
	}
	 
	@Test
	public void getFoodMaterial(){
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		List<foodMaterialVO> list = dao.getFoodMaterialByName("단무지", "0000000000");

		assertTrue("검색성공", list.size() > 0);
		}
	
	@Test
	public void getFoodMaterialListTest() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();
	
		List<foodMaterialVO> list = dao.getFoodMaterialList("0000000000", "idDesc", 1, 5);
		
		foodMaterialVO test = new foodMaterialVO();
		System.out.println(test.getFoodMaterialId());
		for (foodMaterialVO vo : list) {
			System.out.println(vo.getFoodMaterialId() + " / " + vo.getFoodMaterialName() + " / " + vo.getFoodCategory()
					+ " / " + vo.getExpirationDate());
		}

		assertNotNull(list);
		assertTrue(list.size() <= 5);
	}
	
	@Test
	public void getFoodMaterialTotalAmountTest() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		int result = dao.getFoodMaterialTotalAmount("0000000000", "2026-04-01", "2026-05-01");

		System.out.println(result);

		assertTrue(result >= 0);
	}
	
	@Test
	public void getFoodCategoryListTest() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		List<foodMaterialCategoryVO> list = dao.getFoodCategoryList();

		System.out.println("카테고리 수 : " + list.size());

		assertNotNull(list);
	}
	
	@Test
	public void getFoodMaterialDetailTest() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		List<foodMaterialVO> list = dao.getFoodMaterialList("0000000000", "idDesc", 1, 5);

		if (list.size() > 0) {
			String foodMaterialId = list.get(0).getFoodMaterialId();

			foodMaterialVO vo = dao.getFoodMaterialDetail(foodMaterialId);

			System.out.println("상세 식자재 : " + vo.getFoodMaterialName());

			assertNotNull(vo);
			assertEquals(foodMaterialId, vo.getFoodMaterialId());
		}
	}
	
	@Test
	public void getFoodMaterialPageTest() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		List<foodMaterialVO> page1 = dao.getFoodMaterialList("0000000000", "idDesc", 1, 5);
		List<foodMaterialVO> page2 = dao.getFoodMaterialList("0000000000", "idDesc", 2, 5);

		System.out.println("1페이지 개수 : " + page1.size());
		System.out.println("2페이지 개수 : " + page2.size());

		assertNotNull(page1);
		assertNotNull(page2);
		assertTrue(page1.size() <= 5);
		assertTrue(page2.size() <= 5);
	}
	
	@Test
	public void getFoodMaterialListSortTest() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		List<foodMaterialVO> idDescList = dao.getFoodMaterialList("0000000000", "idDesc", 1, 5);
		List<foodMaterialVO> idAscList = dao.getFoodMaterialList("0000000000", "idAsc", 1, 5);
		List<foodMaterialVO> expAscList = dao.getFoodMaterialList("0000000000", "expAsc", 1, 5);
		List<foodMaterialVO> expDescList = dao.getFoodMaterialList("0000000000", "expDesc", 1, 5);

		System.out.println("idDesc : " + idDescList.size());
		System.out.println("idAsc : " + idAscList.size());
		System.out.println("expAsc : " + expAscList.size());
		System.out.println("expDesc : " + expDescList.size());

		assertNotNull(idDescList);
		assertNotNull(idAscList);
		assertNotNull(expAscList);
		assertNotNull(expDescList);
	}
	
	@Test
	public void getFoodMaterialListAllTest() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		List<foodMaterialVO> list = dao.getFoodMaterialListAll("0000000000");

		System.out.println("전체 식자재 수 : " + list.size());

		for (foodMaterialVO vo : list) {
			System.out.println(
				vo.getFoodMaterialId() + " / " +
				vo.getFoodMaterialName() + " / " +
				vo.getFoodCategory()
			);
		}

		assertNotNull(list);
	}
	
	@Test
	public void getFoodMaterialSpendingRankTest() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		List<foodMaterialVO> list = dao.getFoodMaterialSpendingRank(
			"0000000000",
			"2026-04-01",
			"2026-05-01"
		);

		assertNotNull(list);

		for (foodMaterialVO vo : list) {
			System.out.println(
				vo.getRanking() + " / " +
				vo.getFoodMaterialId() + " / " +
				vo.getFoodMaterialName() + " / " +
				vo.getTotalExpense()
			);
		}
	}
	
	@Test
	public void hasFoodMaterialByCategoryTest() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		boolean result = dao.hasFoodMaterialByCategory("가공식품");

		System.out.println("카테고리 사용 여부 : " + result);
	}
	
	@Test
	public void getCategoryIdTest() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		String result = dao.getCategoryId("가공식품");

		System.out.println("카테고리 ID : " + result);
	}
	
	@Test
	public void addAndDeleteFoodCategoryTest() {
		FoodMaterialDAOInterface dao = new FoodMaterialDAOMybatis();

		String foodCategory = "테스트";

		int addResult = dao.addFoodCategory(foodCategory);
		System.out.println("카테고리 추가 결과 : " + addResult);

		int deleteResult = dao.deleteFoodCategory(foodCategory);
		System.out.println("카테고리 삭제 결과 : " + deleteResult);

		assertTrue(addResult >= 0);
		assertTrue(deleteResult >= 0);
	}
	
}