package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.DAO.userDAO;

public class IdCheckAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String bId = request.getParameter("bId");

		userDAO dao = new userDAO();

		try {
		    if (!dao.getBid(bId)) {
		        request.setAttribute("ajaxResponse", "이미 가입된 사업자번호입니다.");
		        return null;
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    request.setAttribute("ajaxResponse", "DB 연결 오류입니다.");
		    return null;
		}

		if (bId != null && bId.matches("\\d{10}")) {
			HttpSession session = request.getSession();
			session.setAttribute("businessVerified", true);
			session.setAttribute("verifiedBId", bId);
			request.setAttribute("ajaxResponse", "사업자번호 확인이 완료되었습니다.");
			return null;
		} else {
			request.setAttribute("ajaxResponse", "사업자번호는 숫자 10자리여야 합니다.");
			return null;
		}

		
	}

}
