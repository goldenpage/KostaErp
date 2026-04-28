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
   public void setMarketingDate(Date marketingDate) {
      MarketingDate = marketingDate;
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

    
    
}