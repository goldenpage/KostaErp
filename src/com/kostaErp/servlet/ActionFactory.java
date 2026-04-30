package com.kostaErp.servlet;

public class ActionFactory {
    private ActionFactory(){}

    public static Action getAction(String cmd) {
        Action a = null;

        if (cmd == null) {
            cmd = "";
        }

        switch(cmd) {
            case "loginUIAction":
                a = new loginUIAction();
                break;

            case "loginAction":
                a = new loginAction();
                break;

            case "addUserUIAction":
                a = new addUserUIAction();
                break;

            case "addUserAction":
                a = new addUserAction();
                break;
            
            case "addFoodCategoryAction":
            	a = new addFoodCategoryAction();
            	break;
            	
            case "deleteFoodCategoryAction":
            	a = new deleteFoodCategoryAction();
            	break;
            
            case "addFoodMaterialUIAction":
            	a = new addFoodMaterialUIAction();
            	break;
            	
            case "addFoodMaterialAction":
            	a = new addFoodMaterialAction();
            	break;
            	
            case "foodMaterialUIAction":
                a = new foodMaterialUIAction();
                break;

            case "foodMaterialAction":
                a = new foodMaterialAction();
                break;
            
            case "addMenuCategoryAction":
            	a = new addMenuCategoryAction();
            	break;
            	
            case "deleteMenuCategoryAction":
            	a = new deleteMenuCategoryAction();
            	break;
            	
            case "addMenuUIAction":
            	a = new addMenuAction();
            	break;
            	
            case "addMenuAction":
            	a = new addMenuAction();
            	break;
            	
            case "menuUIAction":
                a = new menuUIAction();
                break;

            case "menuAction":
                a = new menuAction();
                break;

            case "disposalAction":
                a = new disposalAction();
                break;
            case "disposalUIAction":
            	a = new disposalUIAction();
            	break;

            case "disposalStatisticUIAction":
            	a = new disposalStatisticsUIAction();
            	break;
            	
            case "salesAction":
                a = new salesAction();
                break;

            case "revenueUIAction":
                a = new revenueUIAction();
                break;

            case "revenueAction":
                a = new revenueAction();
                break;

            case "idCheckAction":
                a = new IdCheckAction();
                break;

            case "phoneSendAction":
                a = new phoneSendAction();
                break;

            case "phoneVerifyAction":
                a = new phoneVerifyAction();
                break;

            case "pwFindUIAction":
                a = new pwFindUIAction();
                break;

            case "pwUpdateAction":
                a = new pwUpdateAction();
                break;

            case "pwPhoneSendAction":
                a = new pwPhoneSendAction();
                break;

            case "pwPhoneVerifyAction":
                a = new pwPhoneVerifyAction();
                break;

            default:
                a = new loginUIAction();
        }

        return a;
    }
}
