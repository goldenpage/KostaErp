package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.menuDAO;

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
				request.setAttribute("errorMessage", "�Ǹ��� �޴��� ���õ��� �ʾҽ��ϴ�.");
				return url;
			}

			menuDAO dao = new menuDAO();

			boolean result = dao.updateFoodMaterialAfterSale(menuId, saleCount, bId);

			if (result) {
				request.setAttribute("message", "�Ǹ� ó�� ����");
			} else {
				request.setAttribute("errorMessage", "��� �����Ͽ� �Ǹ� ó���� �� �� �����ϴ�.");
			}

			request.setAttribute("selectedMenuId", menuId);

			menuAction action = new menuAction();
			url = action.execute(request);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "�Ǹ� ó�� ����");
		}

		return url;
	}

}