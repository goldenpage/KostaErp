package com.kostaErp.model.DAO.Mybatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kostaErp.model.DBCPMybatis;
import com.kostaErp.model.Interface.NoticeDAOInterface;
import com.kostaErp.model.VO.noticeVO;
import com.kostaErp.model.VO.Mybatis.NoticeVOMybatis;

public class NoticeDAOMybatis implements NoticeDAOInterface {

	@Override
	public boolean insertNotice(String noticeId, String disposalId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		boolean result = session.insert("noticeMapper.insertNotice", new noticeVO(noticeId,disposalId))== 1;
		session.commit();
		session.close();
		return result;
	}

	@Override
	public ArrayList<noticeVO> getNoticeList(String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<noticeVO> noticeList = session.selectList("noticeMapper.getNoticeList", bId);
		session.commit();
		session.close();
		return new ArrayList<>(noticeList);
	}

	@Override
	public boolean deleteAll() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		boolean result = session.delete("noticeMapper.deleteNoticeAll")== 0;
		session.commit();
		session.close();
		return result;
	}

	@Override
	public boolean updateReadYn(String noticeId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		boolean result = session.delete("noticeMapper.updateReadYn", noticeId)== 1;
		session.commit();
		session.close();
		return result;
	}

	@Override
	public ArrayList<noticeVO> getExpiredDisposalIds(String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<noticeVO> noticeList = session.selectList("noticeMapper.getExpiredIdList", bId);
		session.commit();
		session.close();
		return new ArrayList<>(noticeList);
	}

	@Override
	public int getExpiredCount(String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int result = session.selectOne("noticeMapper.getExpiredCount", bId);
		session.commit();
		session.close();
		return result;
	}

	@Override
	public int getSolidTotal(String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int result = session.selectOne("noticeMapper.getSolidTotal", bId);
		session.commit();
		session.close();
		return result;
	}

	@Override
	public int getLiquidTotal(String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int result = session.selectOne("noticeMapper.getLiquidTotal", bId);
		session.commit();
		session.close();
		return result;
	}

	@Override
	public int getMaxOverDay(String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int result = session.selectOne("noticeMapper.getMaxOverDay", bId);
		session.commit();
		session.close();
		return result;
	}

	
	
}
