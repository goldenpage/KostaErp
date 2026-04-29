package com.kostaErp.model;

import java.sql.Date;

public class userInfoVO {

	private String bld;
	private String name;
	private int phone;
	private String storeName;
	private String storeType;
	private String storeCategory;
	private String pw;
	private String agreementDate;
	private String marketingDate;

	private String email;
	private Date MarketingDate;
	private Date signDate;
	


	public userInfoVO(){}

	public userInfoVO(String bId, String name, String phone, String email, String storeName, 
			String storeType, String storeCategory, String pw, Date signDate,
			Date agreementDate, String marketingDate){
	
		this.name = name;
		
		this.email = email;
		this.storeName = storeName;
		this.pw = pw;
		this.signDate = signDate;
		
		this.marketingDate = marketingDate;

	}


	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
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
	
	public String getMarketingDate() {
		return marketingDate;
	}
	



	public Date getSignDate() {return signDate;}
	

	







    public void setbld(String bld) {
        this.bld = bld;
    }

    public String name() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }


    public void setstoreName(String storeName) {
        this.storeName = storeName;
    }

 

    public void setstoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }

    public String getstoreCategory() {
        return storeCategory;
    }

    public void setpw(String pw) {
        this.pw = pw;
    }

    public String agreementDate() {
        return agreementDate;
    }

    public void setagreementDate(String agreementDate) {
        this.agreementDate = agreementDate;
    }

 

    public void setmarketingDate(String marketingDate) {
        this.marketingDate = marketingDate;
    }



   
}







