package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.foodMaterialDAO;
import com.kostaErp.model.foodMaterialVO;
import com.kostaErp.model.menuCategoryVO;
import com.kostaErp.model.menuDAO;

public class addMenuCategoryAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String bId = (session != null) ? (String) session.getAttribute("loginOK") : null;
 
		if (bId == null) {
			return "login.jsp";
		}
 
		menuDAO mDao = new menuDAO();
 
		int result = mDao.addMenuCategory(request.getParameter("menuCategory"), bId);
		System.out.println(result > 0 ? "등록성공" : "등록실패");
 
		List<menuCategoryVO> categoryList = mDao.getMenuCategoryList(bId);
		List<foodMaterialVO> foodMaterialList = new foodMaterialDAO().getFoodMaterialListAll(bId);
 
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("foodMaterialList", foodMaterialList);
 
		return "addMenu.jsp";
	}

}
