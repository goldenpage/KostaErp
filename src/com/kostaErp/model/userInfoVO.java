package com.kostaErp.model;

import java.sql.Date;

public class userInfoVO {
	private String bId;
	private String name;
	private String storeName;
	private String email;
	private String phone;
	private String pw;
	private Date MarketingDate;
	private Date signDate;
	private Date agreementDate;
	private Date marketingDate;


	public userInfoVO(){}

	public userInfoVO(String bId, String name, String phone, String email, String storeName, 
			String storeType, String storeCategory, String pw, Date signDate,
			Date agreementDate, Date marketingDate){
		this.bId = bId;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.storeName = storeName;
		this.pw = pw;
		this.signDate = signDate;
		this.agreementDate = agreementDate;
		this.marketingDate = marketingDate;

	}


	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getMarketingDate() {
		return MarketingDate;
	}


	public Date getSignDate() {return signDate;}
	public Date getAgreementDate() {return agreementDate;}

	public void setMarketingDate(Date marketingDate) {
		this.marketingDate = marketingDate;
	}





}







