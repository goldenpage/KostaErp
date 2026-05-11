package com.kostaErp.model.Interface;

import java.util.List;
import java.util.Map;

import com.kostaErp.model.VO.userInfoVO;

public interface UserInfoDAOInterface {
	int register(String bId, String name, String phone, 
			String email, String storeName, String storeType, String storeCategory, 
			String pw, String signDate, String agreementDate, String marketingDate);

	userInfoVO checkMemberByVO(String bId, String name, String pw);
	int setPw(String pw, String bId, String name, String phone);
	boolean checkPwFindUser(String bId, String name, String phone);
	List<userInfoVO> getMarketingMembers();
	userInfoVO login(String bId, String pw);
	boolean getPhoneCheck(String phone);
	boolean getBid(String bId);
}
