package com.kostaErp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.foodMaterialDAO;

public class deleteFoodMaterialAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "foodMaterials.jsp";

		String[] foodMaterialIds = request.getParameterValues("foodMaterialId");

		try {
			HttpSession session = request.getSession(true);
			String bId = (String) session.getAttribute("bId");

			if (bId == null || bId.equals("")) {
				bId = "0000000000";
				session.setAttribute("bId", bId);
			}

			if (foodMaterialIds == null || foodMaterialIds.length == 0) {
				request.setAttribute("errorMessage", "삭제할 식자재를 선택해주세요.");
				return new foodMaterialAction().execute(request);
			}

			foodMaterialDAO dao = new foodMaterialDAO();

			int deleteCount = 0;

			for (String foodMaterialId : foodMaterialIds) {
				int result = dao.deleteFoodMaterial(foodMaterialId, bId);

				if (result > 0) {
					deleteCount++;
				}
			}

			if (deleteCount > 0) {
				request.setAttribute("message", deleteCount + "개의 식자재가 삭제되었습니다.");
			} else {
				request.setAttribute("errorMessage", "삭제된 식자재가 없습니다.");
			}

			url = new foodMaterialAction().execute(request);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "식자재 삭제 중 오류가 발생했습니다.");
			url = new foodMaterialAction().execute(request);
		}

		return url;
	}
}
