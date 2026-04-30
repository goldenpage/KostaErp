package com.kostaErp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.foodMaterialCategoryVO;
import com.kostaErp.model.foodMaterialDAO;
import com.kostaErp.model.foodMaterialVO;

public class addFoodMaterialAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "addFoodMaterial.jsp";
		 
		HttpSession session = request.getSession(false);
		String bId = (session != null) ? (String) session.getAttribute("loginOK") : null;
 
		if (bId == null) {
			return "login.jsp";
		}
 
		foodMaterialDAO dao = new foodMaterialDAO();
 
		String[] foodMaterialNames = request.getParameterValues("foodMaterialName");
 
		if (foodMaterialNames != null && foodMaterialNames.length > 0) {
			String[] foodCategory_Ids      = request.getParameterValues("foodCategory_Id");
			String[] foodMaterialCounts    = request.getParameterValues("foodMaterialCount");
			String[] foodMaterialCountAlls = request.getParameterValues("foodMaterialCountAll");
			String[] foodMaterialPrices    = request.getParameterValues("foodMaterialPrice");
			String[] foodMaterialTypes     = request.getParameterValues("foodMaterialType");
			String[] venders               = request.getParameterValues("vender");
			String[] incomeDates           = request.getParameterValues("incomeDate");
			String[] expirationDates       = request.getParameterValues("expirationDate");
 
			List<foodMaterialVO> list = new ArrayList<>();
 
			for (int i = 0; i < foodMaterialNames.length; i++) {
				foodMaterialVO vo = new foodMaterialVO();
				vo.setFoodMaterialName(foodMaterialNames[i]);
				vo.setFoodCategory(foodCategory_Ids[i]);
				vo.setFoodMaterialCount(Integer.parseInt(foodMaterialCounts[i]));
				vo.setFoodMaterialCountAll(Integer.parseInt(foodMaterialCountAlls[i]));
				vo.setFoodMaterialPrice(Integer.parseInt(foodMaterialPrices[i]));
				vo.setFoodMaterialType(foodMaterialTypes[i]);
				vo.setVender(venders[i]);
				vo.setIncomeDate(java.sql.Date.valueOf(incomeDates[i]));
				vo.setExpirationDate(java.sql.Date.valueOf(expirationDates[i]));
				list.add(vo);
			}
 
			int successCount = dao.addFoodMaterial(list, bId);
			System.out.println("등록 완료: " + successCount + "/" + foodMaterialNames.length);
		}
 
		List<foodMaterialCategoryVO> categoryList = dao.getFoodCategoryList();
		request.setAttribute("categoryList", categoryList);
 
		return url;
	}
}
