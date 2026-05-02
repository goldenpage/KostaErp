package test.com.kostaErp.model;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kostaErp.model.DAO.disposalDAO;
import com.kostaErp.model.DAO.revenueDAO;
import com.kostaErp.model.VO.disposalVO;
import com.kostaErp.model.VO.revenueVO;

public class revenueDAOTest {
	private revenueDAO dao;
	@Before
	public void setUp() {
		 dao = new revenueDAO();
		System.out.println("dao ���");
	}

	
	@After
	public void tearDown() {
		 dao = null;
		System.out.println("dao ����");
	}
	
	//���� �޴� �Ǹ� ��ŷ ��ȸ
	
	@Test
	public void getMonthlyMenuSalesRankTest() {
		List<revenueVO> list = dao.getMonthlyMenuSalesRank("0000000000","2026-04-01","2026-05-01");

	    assertNotNull(list);
	    assertEquals(4, list.size());

	    revenueVO first = list.get(0);

	    assertEquals(1, first.getRanking());
	    assertEquals("참치김밥", first.getMenuName());
	    assertEquals(21, first.getTotalSaleCount());
	    assertEquals(84000, first.getTotalSalesAmount());
	}
	
	//���� �� ����
	@Test
	public void getMonthlyRevenue() {
	    int result = dao.getMonthlyRevenue("0000000000","2026-04-01","2026-05-01");
	    
	    
	    System.out.println(result);

	    assertEquals(161000, result);

	}

}
