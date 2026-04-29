package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.kostaErp.model.foodMaterialCategoryVO;
import com.kostaErp.model.foodMaterialDAO;

public class addFoodCategoryAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {

		foodMaterialDAO dao = new foodMaterialDAO();
		 
		try {
			if (dao.addFoodCategory(request.getParameter("foodCategory")))
				System.out.println("등록성공");
			else
				System.out.println("등록실패");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("정보 못가져옴");
		}
 
		List<foodMaterialCategoryVO> categoryList = dao.getFoodCategoryList();
		request.setAttribute("categoryList", categoryList);
 
		return "addFoodMaterial.jsp";
	}

}
