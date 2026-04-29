package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class disposalUIAction implements Action{
	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		return "disposalItems.jsp";
	}
}
