package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.kostaErp.model.DAO.foodMaterialDAO;
import com.kostaErp.model.VO.foodMaterialCategoryVO;

public class addFoodMaterialUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		
		foodMaterialDAO dao = new foodMaterialDAO();
		List<foodMaterialCategoryVO> categoryList = dao.getFoodCategoryList();
		request.setAttribute("categoryList", categoryList);
		
		return "/jsp/addFoodMaterial.jsp";
	}

}
