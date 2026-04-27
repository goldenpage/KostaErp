package com.kostaErp.model;

public class disposalVO {
	private String disposalId;
	private int disposalCountAll;
	private int disposalPrice;
	private String disposalDate;
	private String reasonId;
	private String foodMaterialId;
	
	public disposalVO(String disposalId, int disposalCountAll, int disposalPrice, String disposalDate, String reasonId, String foodMaterialId){
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
	public String getDisposalDate() {
		return disposalDate;
	}
	public String getReasonId() {
		return reasonId;
	}
	public String getFoodMaterialId() {
		return foodMaterialId;
	}
}
