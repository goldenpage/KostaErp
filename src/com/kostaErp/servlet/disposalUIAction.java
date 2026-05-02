package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.disposalDAO;
import com.kostaErp.model.disposalVO;

public class disposalUIAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession(false);

        String bId = null;
        if (session != null) {
            bId = (String) session.getAttribute("bId");
        }

        // 🔥 로그인 체크
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

        disposalDAO dao = new disposalDAO();
        List<disposalVO> list = dao.getDisposalsPaging(bId, start, end);

        request.setAttribute("list", list);
        request.setAttribute("currentPage", page);

        return "disposalItems.jsp";
    }
}