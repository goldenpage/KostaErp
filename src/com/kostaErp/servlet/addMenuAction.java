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

public class addMenuAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "/jsp/addMenu.jsp";
		 
		HttpSession session = request.getSession(false);
		String bId = (session != null) ? (String) session.getAttribute("loginOK") : null;
 
		if (bId == null){
			return "/jsp/login.jsp";
		}
 
		menuDAO mDao = new menuDAO();
		foodMaterialDAO fDao = new foodMaterialDAO();
 
		String[] menuNames = request.getParameterValues("menuName");
		String[] menuCategoryIds = request.getParameterValues("menuCategoryId");
		String[] menuPrices = request.getParameterValues("menuPrice");
		String[] menuIngredientCounts = request.getParameterValues("menuIngredientCount");
		String[] foodMaterialIds = request.getParameterValues("foodMaterial_Id");
		String[] usedCounts = request.getParameterValues("usedCount");
 
		if(menuNames != null && menuNames.length > 0){
			for(int i = 0; i < menuNames.length; i++){
				if(mDao.hasMenuCheck(menuNames[i])){
					request.setAttribute("errorMessage", "'" + menuNames[i] + "' 메뉴가 이미 존재합니다. 등록을 취소합니다.");
					List<menuCategoryVO> categoryList = mDao.getMenuCategoryList(bId);
					List<foodMaterialVO> foodMaterialList = fDao.getFoodMaterialListAll(bId);
					request.setAttribute("categoryList", categoryList);
					request.setAttribute("foodMaterialList", foodMaterialList);
					return url;
				}
			}

			int ingredientOffset = 0;

			for(int i = 0; i < menuNames.length; i++){
				String menuId = mDao.addMenu(
					menuNames[i],
					Integer.parseInt(menuPrices[i]),
					menuCategoryIds[i]
				);

				if(menuId != null){
					int ingredientCount = Integer.parseInt(menuIngredientCounts[i]);
					for (int j = 0; j < ingredientCount; j++){
						mDao.addUsedMaterial(
							Integer.parseInt(usedCounts[ingredientOffset + j]),
							foodMaterialIds[ingredientOffset + j],
							menuId
						);
					}
					ingredientOffset += ingredientCount;
				}
			}
		}
 
		List<menuCategoryVO> categoryList = mDao.getMenuCategoryList(bId);
		List<foodMaterialVO> foodMaterialList = fDao.getFoodMaterialListAll(bId);
 
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("foodMaterialList", foodMaterialList);
 
		return url;
	}
}