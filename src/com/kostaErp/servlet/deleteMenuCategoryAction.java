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

public class deleteMenuCategoryAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String bId = (session != null) ? (String) session.getAttribute("loginOK") : null;
 
		if(bId == null){
			return "login.jsp";
		}
 
		menuDAO mDao = new menuDAO();
		String menuCategory = request.getParameter("menuCategory");
 
		if(mDao.hasMenuByCategory(menuCategory)){
			request.setAttribute("errorMessage", "'" + menuCategory + 
					"'카테고리에 등록된 메뉴가 있어 삭제할 수 없습니다.");
		} 
		else{
			int result = mDao.deleteMenuCategory(menuCategory);
			if(result > 0){
				request.setAttribute("successMessage", "'" + menuCategory + "' 카테고리가 삭제되었습니다.");
				System.out.println("삭제성공");
			} 
			else{
				request.setAttribute("errorMessage", "카테고리 삭제에 실패했습니다.");
				System.out.println("삭제실패");
			}
		}
 
		List<menuCategoryVO> categoryList = mDao.getMenuCategoryList(bId);
		List<foodMaterialVO> foodMaterialList = new foodMaterialDAO().getFoodMaterialListAll(bId);
 
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("foodMaterialList", foodMaterialList);
 
		return "addMenu.jsp";
	}

}
