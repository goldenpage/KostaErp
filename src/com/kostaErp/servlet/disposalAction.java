package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.DAO.disposalDAO;
import com.kostaErp.model.VO.disposalVO;

public class disposalAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("bId") == null) {
            System.out.println("로그: 세션 비정상 (null)"); 
            return "login.jsp"; 
        }
        
        System.out.println("로그: 세션 정상 bId = " + session.getAttribute("bId"));

        String bId = (String) session.getAttribute("bId");
        System.out.println("로그: 현재 접속 bId = " + bId);

        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }

        String category = request.getParameter("category");
        String reason = request.getParameter("reason");
        
        int pageSize = 5;
        int start = (page - 1) * pageSize + 1;
        int end = page * pageSize;

        disposalDAO dao = new disposalDAO();
        
        List<disposalVO> list = dao.getDisposalsFilteredPaging(bId, category, reason, start, end);
        
        int totalCount = dao.getTotalCount(bId, category, reason);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        request.setAttribute("list", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("selectedCategory", category);
        request.setAttribute("selectedReason", reason);
        
        request.setAttribute("reasons", dao.getReasons());

        String isAjax = request.getParameter("ajax");
        if ("true".equals(isAjax)) {
            return "disposalItems.jsp"; 
        }
        
        return "disposalItems.jsp"; 
    }
}