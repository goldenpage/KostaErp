package com.kostaErp.model.Interface;

import java.util.ArrayList;
import java.util.List;

import com.kostaErp.model.VO.noticeVO;

public interface NoticeDAOInterface {
	boolean insertNotice(String noticeId, String disposalId);
	ArrayList<noticeVO> getNoticeList(String bId);
	boolean deleteAll();
	boolean updateReadYn(String noticeId);
	ArrayList<noticeVO> getExpiredDisposalIds(String bId);
	int getExpiredCount(String bId);
	int getSolidTotal(String bId);
	int getLiquidTotal(String bId);
	int getMaxOverDay(String bId);
}
