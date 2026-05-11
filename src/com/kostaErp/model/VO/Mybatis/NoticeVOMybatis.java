package com.kostaErp.model.VO.Mybatis;

public class NoticeVOMybatis {
	private String noticeId;
	private String disposalId;
	private String noticeDate;
	private String readYN;
	
	
	public NoticeVOMybatis(String noticeId, String disposalId ) {
		this.noticeId = noticeId;
		this.disposalId = disposalId;
	}
	
}
