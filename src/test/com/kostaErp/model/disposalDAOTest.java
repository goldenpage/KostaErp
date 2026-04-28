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
    @Test
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
    		System.out.println("카테고리: " + category); 
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
    
    //5. 페이징 테스트 
    //@Test 
    public void getDisposalsPagingTest() { 
    	List<disposalVO> list = dao.getDisposalsPaging(1, 4); 
    	assertNotNull(list); 
    	assertTrue(list.size() > 0); 
    	for (disposalVO vo : list) { 
    		System.out.println("ID: " + vo.getDisposalId()); 
    		} 
   	}
    //6. 폐기사유 수정 테스트
    	//@Test 
    	public void updateReasonTest() { 
    		String disposalId = "DIS001"; 
    		String before = "D"; 
    		String after = "B"; 
    		//변경 
    		boolean result = dao.updateReason(disposalId, after); 
    		assertTrue(result); 
    		System.out.println("수정 성공"); 
    		//다시 원복 (테스트 데이터 유지) 
    		dao.updateReason(disposalId, before); 
    		}
}

