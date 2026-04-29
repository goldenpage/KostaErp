package com.kostaErp.model;


import java.sql.Date;


public class userInfoVO {
    private String bId;
    private String name;
    private String phone;
    private String email;
    private String storeName;
    private String storeType;
    private String storeCategory;
    private String pw;
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
    	this.storeType = storeType;
    	this.storeCategory = storeCategory;
    	this.pw = pw;
    	this.signDate = signDate;
    	this.agreementDate = agreementDate;
    	this.marketingDate = marketingDate;
    	
    }
    
	public String getbId() {return bId;}
	public String getName() {return name;}
	public String getPhone() {return phone;}
	public String getEmail() {return email;}
	public String getStoreName() {return storeName;}
	public String getStoreType() {return storeType;}
	public String getStoreCategory() {return storeCategory;}
	public String getPw() {return pw;}
	public Date getSignDate() {return signDate;}
	public Date getAgreementDate() {return agreementDate;}
	public Date getMarketingDate() {return marketingDate;}

	public void setbId(String bId) {
		this.bId = bId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setMarketingDate(Date marketingDate) {
		this.marketingDate = marketingDate;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

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