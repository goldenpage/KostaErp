package com.kostaErp.model;

import java.sql.Date;

public class userInfoVO {
	private String bId;
	private String name;
	private String storeName;
	private String email;
	private String phone;
	private String pw;
	private String signDate;
	private String agreementDate;
	private String marketingDate;


	public userInfoVO(){}

	public userInfoVO(String bId, String name, String phone, String email, String storeName, 
			String storeType, String storeCategory, String pw, String signDate,
			String agreementDate, String marketingDate){
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

<<<<<<< HEAD
=======

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
>>>>>>> refs/remotes/origin/master
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
<<<<<<< HEAD

	public void setStoreType(String storeType) {
		this.storeType = storeType;
=======
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
	public String getMarketingDate() {
		return marketingDate;
	}
	



	public String getSignDate() {return signDate;}
	public String getAgreementDate() {return agreementDate;}

	public void setMarketingDate(String marketingDate) {
		this.marketingDate = marketingDate;
>>>>>>> refs/remotes/origin/master
	}

<<<<<<< HEAD
	public void setStoreCategory(String storeCategory) {
		this.storeCategory = storeCategory;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public void setAgreementDate(Date agreementDate) {
		this.agreementDate = agreementDate;
	}    
}
=======




}







>>>>>>> refs/remotes/origin/master
