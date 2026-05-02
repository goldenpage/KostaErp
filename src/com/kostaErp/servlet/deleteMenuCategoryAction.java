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

public class deleteMenuCategoryAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String bId = (session != null) ? (String) session.getAttribute("loginOK") : null;

		if (bId == null) {
			request.setAttribute("ajaxResponse", "fail|로그인이 필요합니다.");
			return null;
		}

		menuDAO mDao = new menuDAO();
		String menuCategory = request.getParameter("menuCategory");

		if (mDao.hasMenuByCategory(menuCategory)) {
			request.setAttribute("ajaxResponse", "fail|'" + menuCategory + "' 카테고리에 등록된 메뉴가 있어 삭제할 수 없습니다.");
		} else {
			int result = mDao.deleteMenuCategory(menuCategory);
			if (result > 0) {
				request.setAttribute("ajaxResponse", "success|'" + menuCategory + "' 카테고리가 삭제되었습니다.");
			} else {
				request.setAttribute("ajaxResponse", "fail|카테고리 삭제에 실패했습니다.");
			}
		}
		return null;
	}

}
