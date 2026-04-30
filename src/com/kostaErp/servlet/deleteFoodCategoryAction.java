package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.kostaErp.model.foodMaterialCategoryVO;
import com.kostaErp.model.foodMaterialDAO;

public class deleteFoodCategoryAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		foodMaterialDAO dao = new foodMaterialDAO();
		 
		String foodCategory = request.getParameter("foodCategory");
 
		if (dao.hasFoodMaterialByCategory(foodCategory)) {
			request.setAttribute("errorMessage", "'" + foodCategory + "' 카테고리에 등록된 식자재가 있어 삭제할 수 없습니다.");
		} else {
			int result = dao.deleteFoodCategory(foodCategory);
			if (result > 0) {
				request.setAttribute("successMessage", "'" + foodCategory + "' 카테고리가 삭제되었습니다.");
				System.out.println("삭제성공");
			} else {
				request.setAttribute("errorMessage", "카테고리 삭제에 실패했습니다.");
				System.out.println("삭제실패");
			}
		}
 
		List<foodMaterialCategoryVO> categoryList = dao.getFoodCategoryList();
		request.setAttribute("categoryList", categoryList);
 
		return "addFoodMaterial.jsp";
	}

}
