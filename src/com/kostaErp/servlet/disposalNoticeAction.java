package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class disposalNoticeAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		System.out.println("disposalNoticeAction 실행됨");
		return "notification.jsp";
	}
}
