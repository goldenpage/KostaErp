package com.kostaErp.model;

import java.sql.Date;

public class noticeVO {
	private String noticeId;
	private String disposalId;
    private Date noticeDate;
    private String readYn;
    private String foodMaterialName;
    private String foodCategory;
    private int disposalCountAll;
    private Date expireDate;
    private String foodMaterialType;
    
    public noticeVO(){}
    
    public noticeVO(String noticeId, String disposalId, Date noticeDate, String readYn, 
    		String foodMaterialName, String foodCategory, int disposalCountAll, Date expireDate, String foodMaterialType){
    	this.noticeId = noticeId;
    	this.disposalId = disposalId;
    	this.noticeDate = noticeDate;
    	this.readYn = readYn;
    	this.foodMaterialName = foodMaterialName;
    	this.foodCategory = foodCategory;
    	this.disposalCountAll = disposalCountAll;
    	this.expireDate = expireDate;
    	this.foodMaterialType = foodMaterialType;
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

	public String getFoodMaterialName() {
		return foodMaterialName;
	}

	public void setFoodMaterialName(String foodMaterialName) {
		this.foodMaterialName = foodMaterialName;
	}

	public String getFoodCategory() {
		return foodCategory;
	}

	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}

	public int getDisposalCountAll() {
		return disposalCountAll;
	}

	public void setDisposalCountAll(int disposalCountAll) {
		this.disposalCountAll = disposalCountAll;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getFoodMaterialType() {
		return foodMaterialType;
	}

	public void setFoodMaterialType(String foodMaterialType) {
		this.foodMaterialType = foodMaterialType;
	}
	
	
    
}

