package com.kostaErp.model.DAO.Mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kostaErp.model.DBCPMybatis;
import com.kostaErp.model.Interface.UserInfoDAOInterface;
import com.kostaErp.model.VO.userInfoVO;

public class UserInfoDAOMybatis implements UserInfoDAOInterface{
	private static SqlSessionFactory factory;
	private SqlSession session;

	@Override
	public int register(String bId, String name, String phone, String email, String storeName, String storeType,
			String storeCategory, String pw, String signDate, String agreementDate, String marketingDate) {
		factory = DBCPMybatis.getSqlSessionFactory();
		factory.openSession();
		Map<String, String> param = new HashMap<>();
		param.put("bId", bId);
		param.put("name", name);
		param.put("phone", phone);
		param.put("email", email);
		param.put("storeName", storeName);
		param.put("storeType", storeType);
		param.put("storeCategory", storeCategory);
		param.put("pw", pw);
		param.put("signDate", signDate);
		param.put("agreementDate", agreementDate);

		if(marketingDate != null) {
			param.put("marketingDate", marketingDate);
		} else {
			param.put("marketingDate", null);
		}

		return  session.insert("userInfoMapper.register", param);

	}

	@Override
	public userInfoVO checkMemberByVO(String bId, String name, String pw) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();

		Map<String, String> param = new HashMap<>();
		param.put("bId", bId);
		param.put("name", name);
		param.put("pw", pw);
		userInfoVO result = session.selectOne("userInfoMapper.checkMemberByVO", param);
		return result;
	}

	@Override
	public int setPw(String pw, String bId, String name, String phone) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, String> param = new HashMap<>();
		param.put("pw",pw);
		param.put("bId", bId);
		param.put("name",name);
		param.put("phone",phone);

		return session.update("userInfoMapper.setPw", param); 

	}

	@Override
	public boolean checkPwFindUser(String bId, String name, String phone) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, String> param = new HashMap<>();
		param.put("bId", bId);
		param.put("name", name);
		param.put("phone", phone);
		boolean result = session.selectOne("userInfoMapper.checkPwFindUser", param);
		return result;
	}

	@Override
	public List<userInfoVO> getMarketingMembers() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<userInfoVO> list = session.selectList("userInfoMapper.getMarketingMembers");
		return list;
	}

	@Override
	public Map<String, Object> login(String bId, String pw) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, String> param = new HashMap<>();
		param.put("bId", bId);
		param.put("pw", pw);
		Map<String, Object> result = session.selectOne("userInfoMapper.login",param);
		return result;
	}

	@Override
	public boolean getPhoneCheck(String phone) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int result = session.selectOne("userInfoMapper.getPhoneCheck", phone);
		System.out.println(result);
		return result > 0;
	}

	@Override
	public boolean getBid(String bId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int result = session.selectOne("userInfoMapper.getBidCheck", bId);
		System.out.println(result);
		return result > 0;
	}

}