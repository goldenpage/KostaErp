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

            case "foodMaterialUIAction":
                a = new foodMaterialUIAction();
                break;

            case "foodMaterialAction":
                a = new foodMaterialAction();
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

            case "salesAction":
                a = new salesAction();
                break;

            case "revenueUIAction":
                a = new revenueUIAction();
                break;

            case "revenueAction":
                a = new revenueAction();
                break;

            case "idCheck":
                a = new IdCheckAction();
                break;

            default:
                a = new loginUIAction();
        }

        return a;
    }
}