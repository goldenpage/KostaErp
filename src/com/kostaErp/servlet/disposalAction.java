package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kostaErp.model.disposalDAO;
import com.kostaErp.model.disposalVO;

public class disposalAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws IOException {

	    System.out.println("disposalAction 실행됨");

	    String category = request.getParameter("category");

	    HttpSession session = request.getSession(false);
	    String bId = null;

	    if (session != null) {
	        bId = (String) session.getAttribute("bId");
	    }

	    disposalDAO dao = new disposalDAO();

	    
	    if (category != null) {

	        System.out.println("AJAX 요청, bId = " + bId);

	        List<disposalVO> list;

	        if ("전체".equals(category)) {
	            list = dao.getDisposalsPaging(bId, 1, 100);
	        } else {
	            list = dao.getDisposalsByCategoryAndBId(category, bId);
	        }

	        HttpServletResponse response =
	            (HttpServletResponse) request.getAttribute("response");

	        response.setContentType("application/json; charset=UTF-8");

	        Gson gson = new Gson();
	        response.getWriter().write(gson.toJson(list));

	        return null; 
	    }

	    
	    if (bId == null) {
	        return "login.jsp";
	    }

	    int page = 1;
	    int pageSize = 5;

	    String pageParam = request.getParameter("page");
	    if (pageParam != null) {
	        page = Integer.parseInt(pageParam);
	    }

	    int start = (page - 1) * pageSize + 1;
	    int end = page * pageSize;

	    List<disposalVO> list = dao.getDisposalsPaging(bId, start, end);

	    request.setAttribute("list", list);
	    request.setAttribute("currentPage", page);

	    return "disposalItems.jsp";
	}
}