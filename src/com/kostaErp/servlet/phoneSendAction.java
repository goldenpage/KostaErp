package com.kostaErp.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.DAO.userDAO;

public class phoneSendAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String phone = request.getParameter("phone");
		
		userDAO dao = new userDAO();
		
		boolean check = dao.getPhoneCheck(phone);
		System.out.println("phone=[" + phone + "]");
		System.out.println("phoneCheck=" + check);
		
		
		try {
		    if (!check) {
		        request.setAttribute("ajaxResponse", "이미 가입된 휴대폰 번호입니다.");
		        return null;
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    request.setAttribute("ajaxResponse", "DB 연결 오류입니다.");
		    return null;
		}
		
		String code = String.format("%06d", new Random().nextInt(1000000));
		
		HttpSession session = request.getSession();
		session.setAttribute("phoneAuthCode", code);
		session.setAttribute("phoneAuthPhone", phone);
		session.setAttribute("phoneVerified", false);
		
		System.out.println("휴대폰 인증번호:" + code);
		
		request.setAttribute("ajaxResponse", "인증번호가 발송되었습니다." + code);
		return null;
		
		
	}

}
