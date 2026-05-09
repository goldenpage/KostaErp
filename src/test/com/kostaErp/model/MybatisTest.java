package test.com.kostaErp.model;

import static org.junit.Assert.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.kostaErp.model.DBCPMybatis;

public class MybatisTest {

	@Test
	public void getFoodMaterialCountTest() {
		
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		System.out.println(factory);
		
		SqlSession session = factory.openSession();
		System.out.println(session);
		
		int count = session.selectOne("foodMaterialMapper.getFoodMaterialCount","0000000000");
		System.out.println("식자재 개수 : " + count);
		
	}
}
