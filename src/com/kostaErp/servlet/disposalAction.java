package com.kostaErp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.kostaErp.model.disposalDAO;
import com.kostaErp.model.disposalVO;

public class disposalAction implements Action {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        disposalDAO dao = new disposalDAO();
        //DB 조회
        List<disposalVO> list = dao.getDisposals();
        //JSP에 데이터 전달
        request.setAttribute("list", list);
        return "disposalItems.jsp";
    }
}