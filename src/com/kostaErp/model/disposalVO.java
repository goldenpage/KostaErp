package com.kostaErp.model;

import java.util.Date;

public class disposalVO {
	private String disposalId;
	private int disposalCountAll;
	private int disposalPrice;
	private Date disposalDate;
	private String reasonId;
	private String foodMaterialId;
	
	public disposalVO() {}
	
	public disposalVO(String disposalId, int disposalCountAll, int disposalPrice, Date disposalDate, String reasonId, String foodMaterialId){
		this.disposalId = disposalId;
		this.disposalCountAll = disposalCountAll;
		this.disposalPrice = disposalPrice;
		this.disposalDate = disposalDate;
		this.reasonId = reasonId;
		this.foodMaterialId = foodMaterialId;
	}
	
	public String getDisposalId() {
		return disposalId;
	}
	public int getDisposalCountAll() {
		return disposalCountAll;
	}
	public int getDisposalPrice() {
		return disposalPrice;
	}
	public Date getDisposalDate() {
		return disposalDate;
	}
	public String getReasonId() {
		return reasonId;
	}
	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}

	public void setDisposalCountAll(int disposalCountAll) {
		this.disposalCountAll = disposalCountAll;
	}

	public void setDisposalPrice(int disposalPrice) {
		this.disposalPrice = disposalPrice;
	}

	public void setDisposalDate(Date disposalDate) {
		this.disposalDate = disposalDate;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public void setFoodMaterialId(String foodMaterialId) {
		this.foodMaterialId = foodMaterialId;
	}

	public String getFoodMaterialId() {
		return foodMaterialId;
	}
}
