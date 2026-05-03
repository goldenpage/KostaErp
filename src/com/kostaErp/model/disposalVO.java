package com.kostaErp.model;

import java.sql.Date;

public class disposalVO {
	private String disposalId;
	private int disposalCountAll;
	private int disposalPrice;
	private String disposalDate;
	private String foodMaterialId;
	private String reasonId;
	private String foodMaterialName;
    private int disposalCount;
    private int totalDisposalPrice;
    private String reason;
    private double reasonRatio;
    private Date disposalDay;
    private int solidCount;
    private int liquidCount;
    private String foodMaterialType;
    private double disposalRate;

    public disposalVO(String disposalId, int disposalCountAll, int disposalPrice, 
    		String disposalDate, String foodMaterialId, String reasonId, 
    		String foodMaterialName, int disposalCount, int totalDisposalPrice,
    		String reason, double reasonRatio, Date disposalDay,
    		int solidCount, int liquidCount, String foodMaterialType,
    		double disposalRate){
		this.disposalId = disposalId;
		this.disposalCountAll = disposalCountAll;
		this.disposalPrice = disposalPrice;
		this.disposalDate = disposalDate;
		this.foodMaterialId = foodMaterialId;
		this.reasonId = reasonId;
		this.foodMaterialName = foodMaterialName;
		this.disposalCount = disposalCount;
		this.totalDisposalPrice = totalDisposalPrice;
		this.reason = reason;
		this.reasonRatio = reasonRatio;
		this.disposalDay = disposalDay;
		this.solidCount = solidCount;
		this.liquidCount = liquidCount;
		this.foodMaterialType = foodMaterialType;
		this.disposalRate = disposalRate;
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
	public String getFoodMaterialId() {
		return foodMaterialId;
	}
	public String getReasonId() {
		return reasonId;
	}
	public String getFoodMaterialName() {
		return foodMaterialName;
	}
	public void setFoodMaterialName(String foodMaterialName) {
		this.foodMaterialName = foodMaterialName;
	}
	public int getDisposalCount() {
		return disposalCount;
	}
	public void setDisposalCount(int disposalCount) {
		this.disposalCount = disposalCount;
	}
	public int getTotalDisposalPrice() {
		return totalDisposalPrice;
	}
	public void setTotalDisposalPrice(int totalDisposalPrice) {
		this.totalDisposalPrice = totalDisposalPrice;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public double getReasonRatio() {
		return reasonRatio;
	}
	public void setReasonRatio(double reasonRatio) {
		this.reasonRatio = reasonRatio;
	}
	public Date getDisposalDay() {
		return disposalDay;
	}
	public void setDisposalDay(Date disposalDay) {
		this.disposalDay = disposalDay;
	}
	public int getSolidCount() {
		return solidCount;
	}
	public void setSolidCount(int solidCount) {
		this.solidCount = solidCount;
	}
	public int getLiquidCount() {
		return liquidCount;
	}
	public void setLiquidCount(int liquidCount) {
		this.liquidCount = liquidCount;
	}
	public double getDisposalRate() {
		return disposalRate;
	}
	public void setDisposalRate(double disposalRate) {
		this.disposalRate = disposalRate;
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
	public void setDisposalDate(String disposalDate) {
		this.disposalDate = disposalDate;
	}
	public void setFoodMaterialId(String foodMaterialId) {
		this.foodMaterialId = foodMaterialId;
	}
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	public String getFoodMaterialType() {
		return foodMaterialType;
	}
	public void setFoodMaterialType(String foodMaterialType) {
		this.foodMaterialType = foodMaterialType;
	}
}
