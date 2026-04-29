package com.kostaErp.model;

public class reasonVO {
	private String reasonId;
	private String reason;
	
	public reasonVO(String reasonId, String reason){
		this.reasonId = reasonId;
		this.reason = reason;
	}
	public String getReasonId() {
		return reasonId;
	}
	public String getReason() {
		return reason;
}
}
