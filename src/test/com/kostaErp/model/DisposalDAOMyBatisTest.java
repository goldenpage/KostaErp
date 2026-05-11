package test.com.kostaErp.model;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.kostaErp.model.DBCPMybatis;
import com.kostaErp.model.VO.disposalVO;

public class DisposalDAOMyBatisTest {
	//1
	@Test
	public void getDisposalsTest(){
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		try {
			List<disposalVO> list = session.selectList("DisposalMapper.getDisposals");
			assertNotNull("세션으로부터 가져온 리스트가 null입니다.", list);
			assertTrue("조회된 데이터가 없습니다.", list.size() > 0);
			System.out.println("조회된 데이터 개수: " + list.size());
			for(disposalVO vo : list) {
				System.out.println(vo.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	//2
	@Test
	public void getFoodMaterialNamesTest() {
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		try {
			List<String> nameList = session.selectList("DisposalMapper.getFoodMaterialNames");
			assertNotNull("식재료명 리스트가 null입니다.", nameList);
			assertFalse("식재료명 데이터가 하나도 없습니다.", nameList.isEmpty());
			System.out.println("=== 조회된 식재료명 목록 (총 " + nameList.size() + "건) ===");
			for (String name : nameList) {
				System.out.println("식재료명 : " + name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	//3
	@Test
	public void getCategoriesTest(){
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		try{
			List<String> categoriesList = session.selectList("DisposalMapper.getCategories");
			assertNotNull("카테고리 리스트가 null 입니다.", categoriesList);
			assertFalse("카테고리 리스트가 하나도 없습니다.", categoriesList.isEmpty());
			System.out.println("조회된 카테곻리 목록 (총 " + categoriesList.size() + "건)");
			for (String categories : categoriesList){
				System.out.println("카테고리명 : " + categories);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	//4
	@Test
	public void getDisposalsFilteredPagingTest() {
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();

		try {
			String testBId = "0000000000";     
			String testCategory = "전체"; 
			String testReason = "전체";  
			int startRow = 1;
			int endRow = 3;

			Map<String, Object> map = new HashMap<>();
			map.put("bId", testBId);
			map.put("category", testCategory);
			map.put("reason", testReason);
			map.put("start", startRow);
			map.put("end", endRow);

			List<disposalVO> list = session.selectList("DisposalMapper.getDisposalsFilteredPaging", map);
			assertNotNull("페이징 리스트가 null입니다.", list);
			System.out.println("=== 필터링 및 페이징 결과 (범위 : " + startRow + " ~ " + endRow + ") ===");
			System.out.println("조회된 데이터 수: " + list.size());

			for (disposalVO vo : list) {
				System.out.println(vo); 
			}
			assertTrue("요청 범위를 초과", list.size() <= (endRow - startRow + 1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	//5
	@Test
	public void getDisposalCountTest() {
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();

		try {
			String testbId = "0000000000";
			String testCategory = "채소";
			String testReason = "전체";

			Map<String, Object> map = new HashMap<>();
			map.put("bId", testbId);
			map.put("category", testCategory);
			map.put("reason", testReason);

			int count = session.selectOne("DisposalMapper.getDisposalCount", map);
			assertTrue("0보다 작을 수 없음", count >= 0);

			System.out.println("=== 조회 조건 ===");
			System.out.println("BID : " + testbId + ", 카테고리 : " + testCategory + ", 사유 : " + testReason);
			System.out.println("총 폐기 데이터 수 : " + count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	//6
	@Test
	public void getTotalCountTest(){
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		try{
			String testbId = "0000000000";
			String testCategory = "전체";
			String testReason = "전체";
			
			Map<String, Object> map = new HashMap<>();
			map.put("bId", testbId);
			map.put("category", testCategory);
			map.put("reason", testReason);
			
			int count = session.selectOne("DisposalMapper.getTotalCount", map);
			assertTrue("0보다 작을 수 없음", count >= 0);
			System.out.println("조회조건");
			System.out.println("BID : " + testbId + ", 카테고리 : " + testCategory + ", 사유 : " + testReason);
			System.out.println("총 폐기 데이터 수 : " + count);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	//7
	@Test
	public void getReasonsTest(){
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		try {
	        List<String> reasonList = session.selectList("DisposalMapper.getReason");

	        assertNotNull("사유 목록 리스트가 null입니다.", reasonList);
	        assertFalse("조회된 폐기 사유가 없습니다. DB를 확인하세요.", reasonList.isEmpty());

	        System.out.println("=== 조회된 폐기 사유 목록 (총 " + reasonList.size() + "건) ===");
	        for (String reason : reasonList) {
	            System.out.println("사유 : " + reason);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	//8
	@Test
	public void getDisposalsByCategoryAndBIdTest(){
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
	    SqlSession session = factory.openSession();

	    try {
	        String testCategory = "채소"; 
	        String testBId = "0000000000";

	        Map<String, Object> map = new HashMap<>();
	        map.put("category", testCategory);
	        map.put("bId", testBId);

	        List<disposalVO> list = session.selectList("DisposalMapper.getDisposalsByCategoryAndBId", map);
	        assertNotNull("결과 리스트가 null입니다.", list);
	        
	        System.out.println("---------- 조회 결과 ----------");
	        System.out.println("검색 조건 -> 카테고리: " + testCategory + ", bId: " + testBId);
	        System.out.println("조회된 데이터 수: " + list.size());
	        
	        for (disposalVO vo : list) {
	            System.out.println(vo); 
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	//9
	@Test
	public void getDisposalsPagingTest(){
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
	    SqlSession session = factory.openSession();

	    try {
	        String testBId = "0000000000"; 
	        int startRow = 1;
	        int endRow = 3;

	        Map<String, Object> map = new HashMap<>();
	        map.put("bId", testBId);
	        map.put("start", startRow);
	        map.put("end", endRow);

	        List<disposalVO> list = session.selectList("DisposalMapper.getDisposalsPaging", map);

	        assertNotNull("페이징 리스트가 null입니다.", list);
	        
	        System.out.println("조회 결과");
	        System.out.println("bId : " + testBId + " | 범위: " + startRow + " ~ " + endRow);
	        System.out.println("조회된 행 수: " + list.size());

	        for (disposalVO vo : list) {
	            System.out.println(vo);
	        }
	        
	        int expectedSize = endRow - startRow + 1;
	        assertEquals("요청한 사이즈만큼 데이터가 조회되지 않음", expectedSize, list.size());

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	//10
	@Test
	public void updateReasonTest(){
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		try{
			String testDisposalId = "DIS001"; 
	        String testReasonId = "D";
	        Map<String, Object> map = new HashMap<>();
	        
	        map.put("disposalId", testDisposalId);
	        map.put("reasonId", testReasonId);

	        int result = session.update("DisposalMapper.updateReason", map);

	        assertEquals("데이터 수정에 실패했습니다 (영향을 받은 행이 1이 아님).", 1, result);

	        if (result == 1) {
	            session.commit(); 
	            System.out.println("성공: 폐기번호 " + testDisposalId + "의 사유가 " + testReasonId + "(으)로 변경되었습니다.");
	        } else {
	            session.rollback(); 
	            System.out.println("실패: 해당 번호의 데이터를 찾을 수 없거나 수정되지 않았습니다.");
	        }
	    } catch (Exception e) {
	        session.rollback(); 
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	//11
	@Test
	public void getExpiredDisposalIdsTest() {
	    SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
	    SqlSession session = factory.openSession();

	    try {
	        String testBId = "0000000000";

	        List<String> expiredIds = session.selectList("DisposalMapper.getExpiredDisposalIds", testBId);

	        assertNotNull("결과 리스트 객체가 생성되지 않음", expiredIds);

	        System.out.println("유통기한 경과 ID 조회");
	        System.out.println("bId: " + testBId);
	        
	        if (expiredIds.isEmpty()) {
	            System.out.println("현재 유통기한이 지난 폐기 항목이 없습니다.");
	        } else {
	            System.out.println("조회된 경과 항목 수: " + expiredIds.size());
	            for (String id : expiredIds) {
	                System.out.println("경과된 폐기 ID: " + id);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	//12
	@Test
	public void getDisposalRateTest() {
	    SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
	    SqlSession session = factory.openSession();

	    try {
	        String testBId = "0000000000";
	        String testStartDate = "2024-01-01";
	        String testEndDate = "2026-12-31";

	        Map<String, Object> map = new HashMap<>();
	        map.put("bId", testBId);
	        map.put("startDate", testStartDate);
	        map.put("endDate", testEndDate);

	        Double rate = session.selectOne("DisposalMapper.getDisposalRate", map);

	        assertNotNull("폐기율 결과가 null", rate);
	        
	        assertTrue("폐기율은 0% 이상이어야함", rate >= 0.0);
	        
	        System.out.println("지점별 폐기율 조회");
	        System.out.println("지점ID: " + testBId);
	        System.out.println("조회기간: " + testStartDate + " ~ " + testEndDate);
	        System.out.printf("계산된 폐기율: %.2f%%\n", rate);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	//13
	@Test
	public void getTotalDisposalPriceTest() {
	    SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
	    SqlSession session = factory.openSession();

	    try {
	        String testBId = "0000000000";
	        String testStartDate = "2024-01-01";
	        String testEndDate = "2026-12-31";

	        Map<String, Object> map = new HashMap<>();
	        map.put("bId", testBId);
	        map.put("startDate", testStartDate);
	        map.put("endDate", testEndDate);

	        Integer total = session.selectOne("DisposalMapper.getTotalDisposalPrice", map);

	        assertNotNull("결과값 : null", total);
	        assertTrue("총 금액은 0원 이상이어야 합니다.", total >= 0);

	        System.out.println("총 폐기 금액 조회");
	        System.out.println("지점ID: " + testBId);
	        System.out.println("조회기간: " + testStartDate + " ~ " + testEndDate);
	        System.out.println("총 폐기 금액: " + String.format("%, d", total) + "원");

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	//14
	@Test
	public void getTop3DisposalItemsTest() {
	    SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
	    SqlSession session = factory.openSession();

	    try {
	        String testBId = "0000000000";
	        String testStartDate = "2024-01-01";
	        String testEndDate = "2026-12-31";

	        Map<String, String> map = new HashMap<>();
	        map.put("bId", testBId);
	        map.put("startDate", testStartDate);
	        map.put("endDate", testEndDate);

	        List<disposalVO> list = session.selectList("DisposalMapper.getTop3DisposalItems", map);

	        assertNotNull("결과 리스트가 null", list);
	        assertTrue("데이터가 3개를 초과하여 조회됨", list.size() <= 3);

	        System.out.println("---------- 폐기 품목 TOP 3 ----------");
	        System.out.println("지점ID: " + testBId + " | 기간: " + testStartDate + " ~ " + testEndDate);
	        
	        if (list.isEmpty()) {
	            System.out.println("해당 기간에 폐기된 품목이 없음");
	        } else {
	            for (int i = 0; i < list.size(); i++) {
	                System.out.println((i + 1) + "위: " + list.get(i));
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	//15
	@Test
	public void getDisposalReasonRatioTest() {
	    SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
	    SqlSession session = factory.openSession();

	    try {
	        String testBId = "0000000000";
	        String testStartDate = "2024-01-01";
	        String testEndDate = "2026-12-31";

	        Map<String, String> map = new HashMap<>();
	        map.put("bId", testBId);
	        map.put("startDate", testStartDate);
	        map.put("endDate", testEndDate);

	        List<disposalVO> list = session.selectList("DisposalMapper.getDisposalReasonRatio", map);

	        assertNotNull("결과 리스트 객체가 생성되지 않음", list);

	        System.out.println("---------- 폐기 사유별 비율  ----------");
	        System.out.println("지점ID: " + testBId + " | 기간: " + testStartDate + " ~ " + testEndDate);
	        
	        if (list.isEmpty()) {
	            System.out.println("조회된 사유 데이터가 없습니다.");
	        } else {
	            for (disposalVO vo : list) {
	                System.out.println(vo); 
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	//16
	@Test
	public void selectDailyDisposalAmountTest() {
	    SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
	    SqlSession session = factory.openSession();

	    try {
	        String testBId = "0000000000";
	        String testStartDate = "2024-01-01";
	        String testEndDate = "2026-12-31";

	        Map<String, String> map = new HashMap<>();
	        map.put("bId", testBId);
	        map.put("startDate", testStartDate);
	        map.put("endDate", testEndDate);

	        List<disposalVO> list = session.selectList("DisposalMapper.selectDailyDisposalAmount", map);

	        assertNotNull("조회된 리스트가 null", list);

	        System.out.println("---------- 일별 폐기 현황 ----------");
	        System.out.println("지점: " + testBId + " | 기간: " + testStartDate + " ~ " + testEndDate);
	        
	        if (list.isEmpty()) {
	            System.out.println("해당 기간의 일별 폐기 데이터가 없음");
	        } else {
	            for (disposalVO vo : list) {
	                System.out.println(vo);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
	//17
	@Test
	public void selectDailyDisposalByTypeTest() {
	    SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
	    SqlSession session = factory.openSession();

	    try {
	        String testBId = "0000000000";
	        String testStartDate = "2024-01-01";
	        String testEndDate = "2026-12-31";

	        Map<String, String> map = new HashMap<>();
	        map.put("bId", testBId);
	        map.put("startDate", testStartDate);
	        map.put("endDate", testEndDate);

	        List<disposalVO> list = session.selectList("DisposalMapper.selectDailyDisposalByType", map);

	        assertNotNull("결과 리스트가 null입니다.", list);

	        System.out.println("---------- 일별/유형별 폐기 상세 현황 ----------");
	        System.out.println("지점: " + testBId + " | 기간: " + testStartDate + " ~ " + testEndDate);
	        
	        if (list.isEmpty()) {
	            System.out.println("조회된 유형별 폐기 데이터가 없음");
	        } else {
	            for (disposalVO vo : list) {
	                System.out.println(vo);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}
}
