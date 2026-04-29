package test.com.kostaErp.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

import com.kostaErp.model.foodMaterialDAO;
import com.kostaErp.model.foodMaterialVO;
import com.kostaErp.model.revenueDAO;

public class foodMaterialDAOTest {
	
	private foodMaterialDAO dao;
	@Before
	public void setUp() {
		 dao = new foodMaterialDAO();
		System.out.println("dao 등록");
	}

	
	@After
	public void tearDown() {
		 dao = null;
		System.out.println("dao 삭제");
	}
	
	
	//월별 총 지출액
	@Test
	public void getFoodMaterialTotalAmount() {
		   int result = dao.getFoodMaterialTotalAmount("0000000000","2026-04-01","2026-05-01");

			    System.out.println(result);

			    assertTrue(result > 0);
	}
	//월별 지출랭킹
	@Test
	public void getFoodMaterialSpendingRank() {
		  List<foodMaterialVO> list = dao.getFoodMaterialSpendingRank("0000000000","2026-04-01","2026-05-01"	    );
		
		  assertNotNull(list);
		    assertTrue(list.size() > 0);

		    for (foodMaterialVO vo : list) {
		        System.out.println(
		            vo.getRanking() + "\t" +
		            vo.getFoodMaterialId() + "\t" +
		            vo.getFoodMaterialName() + "\t" +
		            vo.getFoodMaterialPrice() + "\t" +
		            vo.getFoodMaterialCount() + "\t" +
		            vo.getTotalExpense() + "\t" +
		            vo.getIncomeDate() + "\t" +
		            vo.getbId()
		        );

		        assertEquals("0000000000", vo.getbId());
		        assertTrue(vo.getTotalExpense() > 0);
		        assertNotNull(vo.getIncomeDate());
		    }
	}

}
