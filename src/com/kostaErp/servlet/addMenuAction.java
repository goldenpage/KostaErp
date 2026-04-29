package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class addMenuAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		System.out.println("addMenuAction");
		
		request.setAttribute("message", "addmenu");
		
		return "addMenu.jsp";
	}

}
