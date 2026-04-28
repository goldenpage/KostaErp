package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.kostaErp.model.userDAO;

public class addUserAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "register.jsp"; 

		String name = request.getParameter("name");
		String bId = request.getParameter("bId");
		String pw = request.getParameter("pw"); 

		try {
			userDAO dao = new userDAO();
			if(dao.addUser(request.getParameter("bId"), request.getParameter("pw"),
					request.getParameter("name")))

				url = "login.html";
			else
				request.setAttribute("errorMessage", "회원가입실패");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
}
