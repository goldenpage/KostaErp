package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.DAO.menuDAO;
import com.kostaErp.model.VO.menuVO;

public class menuAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "/jsp/menuList.jsp";

		String menuId = request.getParameter("menuId");

		try {
			HttpSession session = request.getSession(true);
			String bId = (String) session.getAttribute("bId");

			if (bId == null || bId.equals("")) {
				bId = "0000000000";
				session.setAttribute("bId", bId);
			}

			menuDAO dao = new menuDAO();

			List<menuVO> menuList = dao.getMenuList(bId);

			List<menuVO> detailList = null;

			if ((menuId == null || menuId.equals("")) && menuList != null && menuList.size() > 0) {

				for (menuVO menu : menuList) {
					List<menuVO> tempList = dao.getMenuDetail(menu.getMenuId());

					if (tempList != null && tempList.size() > 0) {
						menuId = menu.getMenuId();
						detailList = tempList;
						break;
					}
				}

				if (menuId == null || menuId.equals("")) {
					menuId = menuList.get(0).getMenuId();
					detailList = dao.getMenuDetail(menuId);
				}

			} else {
				detailList = dao.getMenuDetail(menuId);
			}

			System.out.println("선택된 메뉴ID : " + menuId);
			System.out.println("사용 식자재 개수  : " + (detailList == null ? 0 : detailList.size()));

			request.setAttribute("menuList", menuList);
			request.setAttribute("detailList", detailList);
			request.setAttribute("selectedMenuId", menuId);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "메뉴 조회 실패");
		}

		return url;
	}
}