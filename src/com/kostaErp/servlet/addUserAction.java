package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.DAO.userDAO;

public class addUserAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		userDAO dao = new userDAO();
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


		HttpSession session = request.getSession();

		Boolean phoneVerified = (Boolean) session.getAttribute("phoneVerified");
		String verifiedPhone = (String) session.getAttribute("verifiedPhone");
		Boolean businessVerified = (Boolean) session.getAttribute("businessVerified");
		String verifiedBId = (String) session.getAttribute("verifiedBId");
		
		if(!pw.equals(pwConfirm)) {
			request.setAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
		    return "register.jsp";
		}
		
		if (phoneVerified == null || !phoneVerified || !phone.equals(verifiedPhone)) {
		    request.setAttribute("errorMessage", "휴대폰 인증을 완료해주세요.");
		    return "register.jsp";
		}
		
		if (businessVerified == null || !businessVerified || !bId.equals(verifiedBId)) {
		    request.setAttribute("errorMessage", "사업자번호 인증을 완료해주세요.");
		    return "register.jsp";
		}
		
		
		int result = dao.register(bId, name, phone, email, storeName, storeType, storeCategory, pw, signDate, agreementDate, marketingDate);
		
		
		
		
		if(result == 1) {
			url="/jsp/login.jsp";
			
		} else {
			request.setAttribute("errorMessage", "다시 기입해주세요");
		}
		
		return url;
	}
}
