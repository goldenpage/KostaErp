package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.foodMaterialDAO;
import com.kostaErp.model.foodMaterialVO;

public class searchFoodMaterialAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String bId = (session != null) ? (String) session.getAttribute("loginOK") : null;
 
		if (bId == null) {
			request.setAttribute("ajaxResponse", "[]");
			return null;
		}
 
		String keyword = request.getParameter("keyword");
		if (keyword == null || keyword.trim().isEmpty()) {
			request.setAttribute("ajaxResponse", "[]");
			return null;
		}
 
		foodMaterialDAO dao = new foodMaterialDAO();
		List<foodMaterialVO> list = dao.getFoodMaterialByName(keyword.trim(), bId);
 
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < list.size(); i++) {
			foodMaterialVO vo = list.get(i);
			if (i > 0) sb.append(",");
			sb.append("{")
			  .append("\"foodMaterialName\":\"").append(vo.getFoodMaterialName()).append("\",")
			  .append("\"foodCategory\":\"").append(vo.getFoodCategory()).append("\",")
			  .append("\"vender\":\"").append(vo.getVender()).append("\",")
			  .append("\"foodMaterialType\":\"").append(vo.getFoodMaterialType()).append("\"")
			  .append("}");
		}
		sb.append("]");
 
		request.setAttribute("ajaxResponse", sb.toString());
		return null;
	}

}
