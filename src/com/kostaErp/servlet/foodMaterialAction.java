package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.foodMaterialDAO;
import com.kostaErp.model.foodMaterialVO;

public class foodMaterialAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "foodMaterials.jsp";

		String sortType = request.getParameter("sortType");
		String pageStr = request.getParameter("page");

		if (sortType == null || sortType.equals("")) {
			sortType = "idDesc";
		}

		int page = 1;
		int pageSize = 5;

		try {
			if (pageStr != null && !pageStr.equals("")) {
				page = Integer.parseInt(pageStr);
			}

			HttpSession session = request.getSession(true);
			String bId = (String) session.getAttribute("bId");

			if (bId == null || bId.equals("")) {
				bId = "0000000000";
				session.setAttribute("bId", bId);
			}

			foodMaterialDAO dao = new foodMaterialDAO();

			List<foodMaterialVO> foodList =
					dao.getFoodMaterialList(bId, sortType, page, pageSize);

			int totalCount = dao.getFoodMaterialCount(bId);

			int totalPage = totalCount / pageSize;
			if (totalCount % pageSize != 0) {
				totalPage++;
			}

			request.setAttribute("foodList", foodList);
			request.setAttribute("currentPage", page);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("sortType", sortType);
			

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "식자재 조회 실패");
		}

		return url;
	}

}