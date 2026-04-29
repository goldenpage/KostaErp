package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.kostaErp.model.userDAO;

public class addUserAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String today = java.time.LocalDate.now().toString();
		
		String url = "register.jsp"; 
		String bId = request.getParameter("bId");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String storeName = request.getParameter("storeName");
		String storeType = request.getParameter("storeType");
		String storeCategory = request.getParameter("storeCategory");
		String pw = request.getParameter("pw");
		String pwConfirm = request.getParameter("pwConfirm");
		String signDate = request.getParameter("signAgree") != null ? today: null;
		String agreementDate = request.getParameter("agreementAgree") != null ? today:null;
		String marketingDate = request.getParameter("marketingAgree") != null ? today: null;
		
		userDAO dao = new userDAO();
		int result = dao.register(bId, name, phone, email, storeName, storeType, storeCategory, pw, signDate, agreementDate, marketingDate);
		
		if(!pw.equals(pwConfirm)) {
			request.setAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
		    return "register.jsp";
		}
		
		
		if(result == 1) {
			url="login.jsp";
			
		} else {
			request.setAttribute("errorMessage", "다시 기입해주세요");
		}
		
		return url;
	}
}
