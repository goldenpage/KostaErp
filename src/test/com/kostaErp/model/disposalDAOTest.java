package test.com.kostaErp.model;


import com.kostaErp.model.disposalDAO;
import com.kostaErp.model.disposalVO;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class disposalDAOTest {
	private disposalDAO dao;

	//테스트 전에 DAO 생성
	@Before
	public void setUp() {
		dao = new disposalDAO();
		System.out.println("테스트 시작");
	}

	//테스트 종료 후 실행
	@After
	public void tearDown() {
		dao = null;
		System.out.println("테스트 종료");
	}

	//1. 폐기 품목 조회 테스트
	//@Test
	public void getDisposalsTest() {
		List<disposalVO> list = dao.getDisposals();
		assertNotNull(list);
		assertTrue(list.size() > 0);
		for (disposalVO vo : list) {
			System.out.println(vo.getDisposalId() + " / " + vo.getReasonId());
		}
	}

	//2. 폐기 식자재명 조회 테스트
	//@Test
	public void getFoodMaterialNamesTest() { 
		List<String> list = dao.getFoodMaterialNames(); 
		assertNotNull(list); assertTrue(list.size() > 0); 
		for (String name : list) { 
			System.out.println("식자재명 : " + name); 
		} 
	}

	//3. 카테고리 조회 테스트
	//@Test 
	public void getCategoriesTest() { 
		List<String> list = dao.getCategories(); 
		assertNotNull(list); 
		assertTrue(list.size() > 0); 
		for (String category : list) { 
			System.out.println("카占쌓곤옙占쏙옙: " + category); 
		} 
	}

	//4. 폐기사유 조회 테스트
	//@Test 
	public void getReasonsTest() { 
		List<String> list = dao.getReasons(); 
		assertNotNull(list); 
		assertTrue(list.size() > 0); 
		for (String reason : list) { 
			System.out.println("사유: " + reason); 
		}
	}

	//5. 폐기 품목 페이징 테스트
	//@Test 
	public void getDisposalsPagingTest() { 
		List<disposalVO> list = dao.getDisposalsPaging(1, 4); 
		assertNotNull(list); 
		assertTrue(list.size() > 0); 
		for (disposalVO vo : list) { 
			System.out.println("ID: " + vo.getDisposalId()); 
		} 
	}
	//6. 폐기사유 수정
	//@Test 
	public void updateReasonTest() { 
		String disposalId = "DIS001"; 
		String before = "D"; 
		String after = "B"; 
		
		boolean result = dao.updateReason(disposalId, after); 
		assertTrue(result); 
		System.out.println("占쏙옙占쏙옙 占쏙옙占쏙옙"); 
		//삭제 롤백
		dao.updateReason(disposalId, before); 
	}
	
	//7. 월별 폐기율
	//@Test
	public void getDisoposalRateTest() throws ClassNotFoundException {

		double result = dao.getDisposalRate("0000000000", "2026-04-01","2026-05-01");
		assertNotNull(result);
		System.out.println(result);
		assertEquals(176.47, result, 0.01);


	}
	//8. 월별 폐기금액
	//@Test
	public void getTotalDisposalPrice() throws ClassNotFoundException {
		int result = dao.getTotalDisposalPrice("0000000000", "2026-04-01","2026-05-01");
		assertNotNull(result);
		assertEquals(100000, result);

	}

	//9.월별 폐기품목 Top3
	//@Test
	public void getTop3DisposalItems() throws ClassNotFoundException {
		List<disposalVO> list = dao.getTop3DisposalItems("0000000000", "2026-04-01","2026-05-01");
		assertNotNull(list); 
		assertTrue(list.size() > 0);

		boolean hasChamgireum = false;
		boolean hasCheese = false;

		for (disposalVO vo : list) {
			System.out.println(
					vo.getFoodMaterialId() + " / " +
							vo.getFoodMaterialName() + " / " +
							vo.getTotalDisposalPrice() + " / " +
							vo.getDisposalCount()
					);

			if ("참기름".equals(vo.getFoodMaterialName())) {
				hasChamgireum = true;
				assertEquals(50000, vo.getTotalDisposalPrice());
				assertEquals(1, vo.getDisposalCount());
			}

			if ("치즈".equals(vo.getFoodMaterialName())) {
				hasCheese = true;
				assertEquals(50000, vo.getTotalDisposalPrice());
				assertEquals(1, vo.getDisposalCount());
			}
		}
		assertTrue(hasChamgireum);
		assertTrue(hasCheese);
	}

	//10. 폐기사유비율
	//@Test
	public void getDisposalReasonRatio() throws ClassNotFoundException {
		List<disposalVO> list = dao.getTop3DisposalItems("0000000000", "2026-04-01","2026-05-01");
		assertNotNull(list); 
		assertTrue(list.size() > 0);
		boolean hasBroken = false;
		boolean hasExpiration = false;

		for (disposalVO vo : list) {
			System.out.println(
					vo.getReasonId() + "\t" +
							vo.getReason() + "\t" +
							vo.getDisposalCount() + "\t" +
							vo.getReasonRatio()
					);
			

			if ("B".equals(vo.getReasonId())) {
				hasBroken = true;
				assertEquals("파손", vo.getReason());
				assertEquals(1, vo.getDisposalCount());
				assertEquals(50.0, vo.getReasonRatio(), 0.01);
			}

			if ("E".equals(vo.getReasonId())) {
				hasExpiration = true;
				assertEquals("유통기한만료", vo.getReason());
				assertEquals(1, vo.getDisposalCount());
				assertEquals(50.0, vo.getReasonRatio(), 0.01);
			}
		}

		assertTrue(hasBroken);
		assertTrue(hasExpiration);



	}

	//12. 날짜별 폐기 유형
	@Test
	public void selectDailyDisposalByTypeTest() throws ClassNotFoundException {

		List<disposalVO> list = dao.getTop3DisposalItems("0000000000", "2026-04-01","2026-05-01");

	    assertNotNull(list);
	    assertEquals(2, list.size());

	    boolean hasSolid = false;
	    boolean hasLiquid = false;

	    for (disposalVO vo : list) {
	        System.out.println(
	            vo.getDisposalDay() + "\t" +
	            vo.getFoodMaterialType() + "\t" +
	            vo.getDisposalCount() + "\t" +
	            vo.getTotalDisposalPrice()
	        );

	        assertEquals(java.sql.Date.valueOf("2026-04-22"), vo.getDisposalDay());

	        if ("고체".equals(vo.getFoodMaterialType())) {
	            hasSolid = true;
	            assertEquals(1, vo.getDisposalCount());
	            assertEquals(50000, vo.getTotalDisposalPrice());
	        }

	        if ("액체".equals(vo.getFoodMaterialType())) {
	            hasLiquid = true;
	            assertEquals(1, vo.getDisposalCount());
	            assertEquals(50000, vo.getTotalDisposalPrice());
	        }
	    }

	    assertTrue(hasSolid);
	    assertTrue(hasLiquid);
	}
}


