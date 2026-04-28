package com.kostaErp.model;

public class usedVO {
	private String usedMaterialId;
    private int usedCount;
    private String foodMaterial_Id;
    private String menu_Id;

    public usedVO() {}

    public usedVO(int usedCount, String foodMaterial_Id, String menu_Id) {
        this.usedCount = usedCount;
        this.foodMaterial_Id = foodMaterial_Id;
        this.menu_Id = menu_Id;
    }

    public String getUsedMaterialId(){ return usedMaterialId; }
    public int getUsedCount(){ return usedCount; }
    public String getFoodMaterial_Id(){ return foodMaterial_Id; }
    public String getMenu_Id(){ return menu_Id; }

    public void setUsedMaterialId(String usedMaterialId){
    	this.usedMaterialId = usedMaterialId;
    }
    public void setUsedCount(int usedCount){
    	this.usedCount = usedCount;
    }
    public void setFoodMaterial_Id(String foodMaterial_Id){
    	this.foodMaterial_Id = foodMaterial_Id;
    }
    public void setMenu_Id(String menu_Id){
    	this.menu_Id = menu_Id;
    }
}
