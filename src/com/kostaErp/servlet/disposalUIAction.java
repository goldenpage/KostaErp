package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.DAO.disposalDAO;
import com.kostaErp.model.VO.disposalVO;

public class disposalUIAction implements Action {


    @Override
    public String execute(HttpServletRequest request) throws IOException {


        HttpSession session = request.getSession(false);
        String bId = null;
        if (session != null) {
            bId = (String) session.getAttribute("bId");
        }
        if (bId == null) {
            return "login.jsp"; 
        }
        
        int page = 1;
        int pageSize = 5;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }

        String category = request.getParameter("category");
        String reason = request.getParameter("reason");

        if ("전체".equals(category)) category = null;
        if ("전체".equals(reason)) reason = null;

        int start = (page - 1) * pageSize + 1;
        int end = page * pageSize;

        disposalDAO dao = new disposalDAO();
        
        List<disposalVO> list = dao.getDisposalsFilteredPaging(bId, category, reason, start, end);
        
        int totalCount = dao.getTotalCount(bId, category, reason);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        request.setAttribute("list", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages); // 필수 추가

        return "disposalItems.jsp";
    }
}