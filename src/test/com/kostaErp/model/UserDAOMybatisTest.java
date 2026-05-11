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
import com.kostaErp.model.VO.userInfoVO;

public class UserDAOMybatisTest {
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
	public void registerTest() {
		Map<String, String> param = new HashMap<>();
		param.put("bId", "123456789");
		param.put("name", "김밥왕");
		param.put("phone", "01012345678");
		param.put("email", "test@test.com");
		param.put("storeName", "김밥집");
		param.put("storeType", "일반음식점");
		param.put("storeCategory", "중식");
		param.put("pw", "test1234");
		param.put("signDate", "2026-05-03");
		param.put("agreementDate", "2026-05-03");
		param.put("marketingDate", "2026-05-03");
		
		
		boolean result = session.insert("userMapper.register", param) == 0;
		assertFalse(result);
	}
	
	@Test
	public void checkMemberByVOTest() {
		Map<String, String> param = new HashMap<>();
		param.put("bId", "0000000000");
		param.put("name", "김사장");
		param.put("pw", "test123");
		userInfoVO result = session.selectOne("userMapper.checkMemberByVO", param);
		assertNotNull(result);
		
	}
	
	@Test
	public void setPwTest() {
		Map<String, String> param = new HashMap<>();
		param.put("","");
		param.put("","");
		param.put("","");
		param.put("","");
		
		boolean result = session.update("userMapper.setPw", param) ==1;
		assertTrue(result);
	}
	
	@Test
	public void checkPwFindUserTest() {
		
	}
	
}
