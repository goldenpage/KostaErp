package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.kostaErp.model.userDAO;

public class phoneCheckAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		boolean flag = false;
		
		String phone = request.getParameter("phone");
		
		userDAO dao = new userDAO();
		
		flag = dao.getPhoneCheck(phone);
		
		if(flag) {
			System.out.println("인증");
			request.setAttribute("sucess", "인증에 성공했습니다.");
			
		} else {
			System.out.println("NO 인증");
			request.setAttribute("errorMessage", "다시 인증해주세요");
		}
		
		return "인증됐습니다.";
		
	}

}
