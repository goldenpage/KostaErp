package com.kostaErp.model;

public class saleVO {
	private String saleId;
	private String saleMenuCount;
	private String menuId;
	private String revenueId;
	
	public saleVO(){}
	
	public saleVO(String saleMenuCount, String menuId, String revenueId){
		this.saleMenuCount = saleMenuCount;
		this.menuId = menuId;
		this.revenueId = revenueId; 
	}
	
	public String getSaleId(){ return saleId; }
	public String getSalMenuCount(){ return saleId; }
	public String getmenuId(){ return saleId; }
	public String getrevenueId(){ return saleId; }
}