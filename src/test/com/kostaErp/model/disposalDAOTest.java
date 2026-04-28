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

    //�׽�Ʈ ���� DAO ����
    @Before
    public void setUp() {
        dao = new disposalDAO();
        System.out.println("�׽�Ʈ ����");
    }
    
    //�׽�Ʈ ���� �� ����
    @After
    public void tearDown() {
        dao = null;
        System.out.println("�׽�Ʈ ����");
    }

    //1. ��� ǰ�� ��ȸ �׽�Ʈ
    @Test
    public void getDisposalsTest() {
        List<disposalVO> list = dao.getDisposals();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        for (disposalVO vo : list) {
            System.out.println(vo.getDisposalId() + " / " + vo.getReasonId());
        }
    }
    
    //2. ��� ������� ��ȸ �׽�Ʈ
    //@Test
    public void getFoodMaterialNamesTest() { 
    	List<String> list = dao.getFoodMaterialNames(); 
    	assertNotNull(list); assertTrue(list.size() > 0); 
    	for (String name : list) { 
    		System.out.println("������� : " + name); 
    		} 
    }
    
    //3. ī�װ��� ��ȸ �׽�Ʈ
    //@Test 
    public void getCategoriesTest() { 
    	List<String> list = dao.getCategories(); 
    	assertNotNull(list); 
    	assertTrue(list.size() > 0); 
    	for (String category : list) { 
    		System.out.println("ī�װ���: " + category); 
    		} 
    }
    
    //4. ������ ��ȸ �׽�Ʈ 
    //@Test 
    public void getReasonsTest() { 
    	List<String> list = dao.getReasons(); 
    	assertNotNull(list); 
    	assertTrue(list.size() > 0); 
    	for (String reason : list) { 
    		System.out.println("����: " + reason); 
    		}
    }
    
    //5. ����¡ �׽�Ʈ 
    //@Test 
    public void getDisposalsPagingTest() { 
    	List<disposalVO> list = dao.getDisposalsPaging(1, 4); 
    	assertNotNull(list); 
    	assertTrue(list.size() > 0); 
    	for (disposalVO vo : list) { 
    		System.out.println("ID: " + vo.getDisposalId()); 
    		} 
   	}
    //6. ������ ���� �׽�Ʈ
    	//@Test 
    	public void updateReasonTest() { 
    		String disposalId = "DIS001"; 
    		String before = "D"; 
    		String after = "B"; 
    		//���� 
    		boolean result = dao.updateReason(disposalId, after); 
    		assertTrue(result); 
    		System.out.println("���� ����"); 
    		//�ٽ� ���� (�׽�Ʈ ������ ����) 
    		dao.updateReason(disposalId, before); 
    		}
}


