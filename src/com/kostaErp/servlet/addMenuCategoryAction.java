package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.DAO.foodMaterialDAO;
import com.kostaErp.model.DAO.menuDAO;
import com.kostaErp.model.VO.foodMaterialVO;
import com.kostaErp.model.VO.menuCategoryVO;

public class addMenuCategoryAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String bId = (session != null) ? (String) session.getAttribute("loginOK") : null;

		if (bId == null) {
			request.setAttribute("ajaxResponse", "fail|로그인이 필요합니다.");
			return null;
		}

		String menuCategory = request.getParameter("menuCategory");
		if (menuCategory == null || menuCategory.trim().isEmpty()) {
			request.setAttribute("ajaxResponse", "fail|카테고리명을 입력해주세요.");
			return null;
		}
		menuCategory = menuCategory.trim();

		menuDAO dao = new menuDAO();
		int result = dao.addMenuCategory(menuCategory, bId);
		String Id = dao.getCategoryId(menuCategory);

		if (result == 1) {
			request.setAttribute("ajaxResponse", "success|" + Id + "|" + menuCategory);
		} else if (result == 0) {
			request.setAttribute("ajaxResponse", "fail|이미 존재하는 카테고리입니다.");
		} else {
			request.setAttribute("ajaxResponse", "fail|카테고리 추가에 실패했습니다.");
		}
		return null;
	}

}
