package test.com.kostaErp.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.kostaErp.model.DBCPMybatis;
import com.kostaErp.model.VO.NoticeVOMybatis;
import com.kostaErp.model.VO.noticeVO;

public class MybatisTest {

	
//
//	@Before
//	public void beforeach() {
//		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
//		factory.openSession();
//	}
	
	@Test
	public void getFoodMaterialCountTest() {
		
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		System.out.println(factory);
		
		SqlSession session = factory.openSession();
		System.out.println(session);
		
		int count = session.selectOne("foodMaterialMapper.getFoodMaterialCount","0000000000");
		System.out.println("식자재 개수 : " + count);
		
	}
	
	
	//알림 추가
	@Test
	public void setNoticeTest() {
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		boolean result = session.insert("noticeMapper.insertNotice", new noticeVO("N017","DIS008"))== 1;
		session.commit();
		System.out.println(result);
		assertTrue(result);
		session.close();
	}
	
	//사업자별 알림 목록 조회
	@Test
	public void getNoticeListTest() {
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		List<noticeVO> noticeList = session.selectList("noticeMapper.getNoticeList", "0000000000" );
		assertTrue(noticeList.size() > 0);
		session.close();
		
	}
	
	
	//알림 전체 삭제
	//@Test
	public void deleteNoticeAllTest() {
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		boolean result = session.delete("noticeMapper.deleteNoticeAll") == 0;
		session.rollback();
		System.out.println(result);
		assertTrue(result);
		session.close();
	}
	
	//특정 알림의 읽음 상태 업데이트 (읽음 처리)
	@Test
	public void updateReadYnTest() {
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		boolean result = session.update("noticeMapper.updateReadYn",  "N01") == 1;
		session.rollback();
		assertTrue(result);
		session.close();

	}
	//유통기한이 만료된 폐기 항목의 ID 목록 조회
	@Test
	public void getExpiredDisposalIdsTest() {
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		 List<noticeVO> list = session.selectList("noticeMapper.getExpiredIdList", "0000000000");
		 System.out.println(list.size());
		 assertTrue(list.size() > 0);
		 session.close();
		
	}
	//유통기한 만료 항목의 총 개수 조회
	@Test
	public void getExpiredCountTest() {
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		int result = session.selectOne("noticeMapper.getExpiredCount", "0000000000");
		System.out.println(result);
		assertTrue(result == 2);
		session.close();
	}
	
	
	
	//고체 개수
	@Test
	public void getSolidTotalTest() {
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		int result = session.selectOne("noticeMapper.getSolidTotal", "0000000000");
		session.close();
	}
	
	//액체 개수
	@Test
	public void getLiquidTotalTest() {
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		int result = session.selectOne("noticeMapper.getLiquidTotal", "0000000000");
		session.close();
	}
	
	//유통기한 경과일 중 가장 오래된 경과일수(최대 경과일) 조회
	@Test
	public void getMaxOverDayTest() {
		SqlSessionFactory factory = DBCPMybatis.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		int result = session.selectOne("noticeMapper.getLiquidTotal", "0000000000");
		session.close();
	}
	
	
	
}
