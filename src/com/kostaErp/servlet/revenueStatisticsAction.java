package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class revenueStatisticsAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "/jsp/revenueStatistics.jsp";
		HttpSession session = request.getSession();
		String bId = (String) session.getAttribute("loginOK");
		
		Gson gson = new Gson();
		request.setAttribute("rankLabelsJson", "[]");
		request.setAttribute("rankValuesJson", "[]");
		request.setAttribute("monthlyLabelsJson", "[]");
		request.setAttribute("monthlyValuesJson", "[]");
		
		return url;
	}

}
