package test.com.kostaErp.model;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kostaErp.model.DBCPMybatis;
import com.kostaErp.model.VO.revenueVO;

public class RevenueDAOMybatisTest {
	private static SqlSessionFactory factory;
	private SqlSession session;
	
	@BeforeClass
	public static void beforeClass() {
		factory = DBCPMybatis.getSqlSessionFactory();
	}
	
	@Before
	public void setUp() {
		session = factory.openSession();
	}
	
	@After
	public void tearDown() {
		if(session != null) {
			session.rollback();
			
		}
		
		session.close();
	}
	
	@Test
	public void getMonthlyMenuSalesRankTest() {
		Map<String, String> param = new HashMap<>();
		param.put("bId", "0000000000");
		param.put("startDate", "2026-04-01");
		param.put("endDate", "2026-05-01");
		
		List<revenueVO> list = session.selectList("revenueMapper.getMonthlyMenuSalesRank", param);
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void getRevenueTest() {
		int result = session.selectOne("revenueMapper.getRevenue");
		assertTrue(result == 4128129);
	}
	
	@Test
	public void getMonthlyRevenueTest() {
		Map<String, String> param = new HashMap<>();
		param.put("bId", "0000000000");
		param.put("startDate", "2026-04-01");
		param.put("endDate", "2026-05-01");
		int result = session.selectOne("revenueMapper.getMonthlyRevenue", param);
		assertTrue(result == 326000);
	}
	

}
