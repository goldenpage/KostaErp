package com.kostaErp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.kostaErp.model.foodMaterialDAO;

public class addFoodMaterialAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url="addFoodMaterial.jsp";
		
		try{
			foodMaterialDAO dao = new foodMaterialDAO();
			if(dao.addFoodMaterial(
					request.getParameter("foodMaterialName"),
					request.getParameter("foodCategory_Id"),
					Integer.parseInt(request.getParameter("foodMaterialCount")),
					Integer.parseInt(request.getParameter("foodMaterialCountAll")),
					Integer.parseInt(request.getParameter("foodMaterialPrice")),
					request.getParameter("vender"),
					request.getParameter("foodMaterialType"),
					request.getParameter("incomeDate"),
					request.getParameter("expirationDate"),
					request.getParameter("bId")))
				System.out.println("등록성공");
			else
				System.out.println("등록실패");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return url;
	}

}
