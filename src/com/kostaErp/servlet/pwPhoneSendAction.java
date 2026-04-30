package com.kostaErp.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.userDAO;

public class pwPhoneSendAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String bId = request.getParameter("bId");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");

		if (bId == null || bId.trim().isEmpty()
				|| name == null || name.trim().isEmpty()
				|| phone == null || phone.trim().isEmpty()) {
			request.setAttribute("ajaxResponse", "아이디, 이름, 휴대폰 번호를 모두 입력해주세요.");
			return null;
		}

		bId = bId.trim();
		name = name.trim();
		phone = phone.trim();

		HttpSession session = request.getSession();
		session.setAttribute("pwResetVerified", false);
		session.removeAttribute("pwPhoneAuthCode");
		session.removeAttribute("pwPhoneAuthPhone");

		userDAO dao = new userDAO();
		boolean exists = dao.checkPwFindUser(bId, name, phone);

		if (!exists) {
			request.setAttribute("ajaxResponse", "일치하는 회원 정보가 없습니다.");
			return null;
		}

		String code = String.format("%06d", new Random().nextInt(1000000));

		session.setAttribute("pwPhoneAuthCode", code);
		session.setAttribute("pwPhoneAuthPhone", phone);
		session.setAttribute("pwResetBId", bId);
		session.setAttribute("pwResetName", name);
		session.setAttribute("pwResetPhone", phone);
		session.setAttribute("pwResetVerified", false);

		System.out.println("비밀번호 찾기 휴대폰 인증번호:" + code);

		request.setAttribute("ajaxResponse", "인증번호가 발송되었습니다." + code);
		return null;
	}
}
