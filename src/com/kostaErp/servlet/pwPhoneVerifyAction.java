package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class pwPhoneVerifyAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String phone = request.getParameter("phone");
		String phoneCode = request.getParameter("phoneCode");

		HttpSession session = request.getSession();

		String savedPhone = (String) session.getAttribute("pwPhoneAuthPhone");
		String savedCode = (String) session.getAttribute("pwPhoneAuthCode");

		if (phone != null && phone.equals(savedPhone)
				&& phoneCode != null && phoneCode.equals(savedCode)) {
			session.setAttribute("pwResetVerified", true);
			request.setAttribute("ajaxResponse", "휴대폰 인증이 완료되었습니다.");
			return null;
		}

		session.setAttribute("pwResetVerified", false);
		request.setAttribute("ajaxResponse", "인증번호가 일치하지 않습니다.");
		return null;
	}
}
