package com.kostaErp.model;

import java.sql.Date;

public class noticeVO {
	private String noticeId;
	private String disposalId;
    private Date noticeDate;
    private String readYn;
    
    public noticeVO(){}
    
    public noticeVO(String noticeId, String disposalId, Date noticeDate, String readYn){
    	this.noticeId = noticeId;
    	this.disposalId = disposalId;
    	this.noticeDate = noticeDate;
    	this.readYn = readYn;
    }
    
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	public String getDisposalId() {
		return disposalId;
	}
	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}
	public Date getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	public String getReadYn() {
		return readYn;
	}
	public void setReadYn(String readYn) {
		this.readYn = readYn;
	}
    
}

