package com.kostaErp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class addFoodMaterialAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		System.out.println("addFoodMaterialAction");
		
		request.setAttribute("message", "addfood");
		return "addFoodMaterial.jsp";
	}

}
