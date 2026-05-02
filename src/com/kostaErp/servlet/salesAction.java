package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.DAO.menuDAO;

public class salesAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {

		String url = "menuList.jsp";

		String menuId = request.getParameter("menuId");
		String saleCountStr = request.getParameter("saleCount");

		int saleCount = 1;

		try {
			if (saleCountStr != null && !saleCountStr.equals("")) {
				saleCount = Integer.parseInt(saleCountStr);
			}

			HttpSession session = request.getSession(true);
			String bId = (String) session.getAttribute("bId");

			if (bId == null || bId.equals("")) {
				bId = "0000000000";
				session.setAttribute("bId", bId);
			}

			if (menuId == null || menuId.equals("")) {
				request.setAttribute("errorMessage", "판매할 메뉴가 선택되지 않았습니다.");
				return url;
			}

			menuDAO dao = new menuDAO();

			boolean result = dao.updateFoodMaterialAfterSale(menuId, saleCount, bId);

			if (result) {
				request.setAttribute("message", "판매 처리 성공");
			} else {
				request.setAttribute("errorMessage", "재고가 부족하여 판매 처리를 할 수 없습니다.");
			}

			request.setAttribute("selectedMenuId", menuId);

			menuAction action = new menuAction();
			url = action.execute(request);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "판매 처리 실패");
		}

		return url;
	}

}