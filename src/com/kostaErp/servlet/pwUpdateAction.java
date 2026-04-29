package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.userDAO;

public class pwUpdateAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String pw = request.getParameter("pw");
		String pwConfirm = request.getParameter("pwConfirm");
		String requestBId = request.getParameter("bId");
		String requestName = request.getParameter("name");
		String requestPhone = request.getParameter("phone");

		if (requestBId != null) requestBId = requestBId.trim();
		if (requestName != null) requestName = requestName.trim();
		if (requestPhone != null) requestPhone = requestPhone.trim();

		HttpSession session = request.getSession();

		Boolean pwResetVerified = (Boolean) session.getAttribute("pwResetVerified");
		String bId = (String) session.getAttribute("pwResetBId");
		String name = (String) session.getAttribute("pwResetName");
		String phone = (String) session.getAttribute("pwResetPhone");

		if (pwResetVerified == null || !pwResetVerified) {
		    request.setAttribute("error", "본인 인증 후 비밀번호를 변경해주세요.");
		    return "pwUdate.jsp";
		}

		if (bId == null || name == null || phone == null) {
		    request.setAttribute("error", "인증 정보가 만료되었습니다. 다시 인증해주세요.");
		    return "pwUdate.jsp";
		}

		if (!bId.equals(requestBId) || !name.equals(requestName) || !phone.equals(requestPhone)) {
		    request.setAttribute("error", "인증한 회원 정보와 입력한 정보가 일치하지 않습니다.");
		    return "pwUdate.jsp";
		}

		if (pw == null || pw.trim().isEmpty() || pwConfirm == null || pwConfirm.trim().isEmpty()) {
		    request.setAttribute("error", "새 비밀번호를 입력해주세요.");
		    return "pwUdate.jsp";
		}

		if (!pw.equals(pwConfirm)) {
		    request.setAttribute("error", "비밀번호가 일치하지 않습니다.");
		    return "pwUdate.jsp";
		}

		userDAO dao = new userDAO();
		int result = dao.setPw(pw, bId, name, phone);

		if (result == 1) {
		    session.removeAttribute("pwResetVerified");
		    session.removeAttribute("pwResetBId");
		    session.removeAttribute("pwResetName");
		    session.removeAttribute("pwResetPhone");
		    session.removeAttribute("pwPhoneAuthCode");
		    session.removeAttribute("pwPhoneAuthPhone");

		    return "login.jsp";
		} else {
		    request.setAttribute("error", "회원 정보를 찾을 수 없습니다.");
		    return "pwUdate.jsp";
		}
	}

}
