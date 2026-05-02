package com.kostaErp.model.VO;

import java.sql.Date;

public class revenueVO {
	private String saleId;
	private int saleMenuCount;
	private String menuId;
	private String revenueId;
	private String menuName;
	private int menuPrice;
	private Date revenueDate;
	private int saleTotalPrice;
	private int ranking;
    private int totalSaleCount;
    private int totalSalesAmount;

	public String getSaleId() {
		return saleId;
	}
	public int getSaleMenuCount() {
		return saleMenuCount;
	}
	public String getMenuId() {
		return menuId;
	}
	public String getRevenueId() {
		return revenueId;
	}
	public String getMenuName() {
		return menuName;
	}
	public int getMenuPrice() {
		return menuPrice;
	}
	public Date getRevenueDate() {
		return revenueDate;
	}
	public int getSaleTotalPrice() {
		return saleTotalPrice;
	}
	public int getRanking() {
		return ranking;
	}
	public int getTotalSaleCount() {
		return totalSaleCount;
	}
	public int getTotalSalesAmount() {
		return totalSalesAmount;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	public void setSaleMenuCount(int saleMenuCount) {
		this.saleMenuCount = saleMenuCount;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public void setRevenueId(String revenueId) {
		this.revenueId = revenueId;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public void setMenuPrice(int menuPrice) {
		this.menuPrice = menuPrice;
	}
	public void setRevenueDate(Date revenueDate) {
		this.revenueDate = revenueDate;
	}
	public void setSaleTotalPrice(int saleTotalPrice) {
		this.saleTotalPrice = saleTotalPrice;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public void setTotalSaleCount(int totalSaleCount) {
		this.totalSaleCount = totalSaleCount;
	}
	public void setTotalSalesAmount(int totalSalesAmount) {
		this.totalSalesAmount = totalSalesAmount;
	}
	

}
