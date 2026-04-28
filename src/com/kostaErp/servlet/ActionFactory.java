package com.kostaErp.servlet;

public class ActionFactory {
	private ActionFactory(){}
		
		public static Action getAction(String cmd) {
			Action a=null;
			if(cmd ==null) 
				cmd="";
			switch(cmd){
				case "idCheck":
					a=new IdCheckAction();
					break;
				case "loginAction":
					a=new loginAction();
					break;
				case "loginUIAction":
					a=new loginUIAction();
					break;
				case "addUserAction":
					a=new addUserAction();
					break;
				case "addUserUIAction":
					a=new addUserUIAction();
					break;
				case "foodMaterialAction":
					a=new foodMaterialAction();
					break;
				case "foodMaterialUIAction":
					a=new foodMaterialUIAction();
					break;
				case "menuAction":
					a=new menuAction();
					break;
				case "menuUIAction":
					a=new menuUIAction();
					break;
				case "disposalAction":
					a=new disposalAction();
					break;
				case "salesAction":
					a=new salesAction();
					break;
				case "revenueAction":
					a=new revenueAction();
					break;
				case "revenueUIAction":
					a=new revenueUIAction();
					break;
				default:
			}
			return a;	
		}
		
	}
