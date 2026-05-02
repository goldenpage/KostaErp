package com.kostaErp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.DAO.disposalDAO;
import com.kostaErp.model.DAO.noticeDAO;
import com.kostaErp.model.VO.noticeVO;

public class disposalNoticeAction implements Action {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String bId = null;
        if (session != null) {
            bId = (String) session.getAttribute("bId");
        }

        noticeDAO ndao = new noticeDAO();
        disposalDAO ddao = new disposalDAO();

        List<String> expiredList = ddao.getExpiredDisposalIds(bId);
       
        for (String disposalId : expiredList) {
            String noticeId = "NT" + System.currentTimeMillis();
            ndao.insertNotice(noticeId, disposalId);
        }

        ArrayList<noticeVO> list = ndao.getNoticeList(bId);
        System.out.println("bId = " + bId);
        System.out.println("notice list size = " + list.size());
        int expiredCount = ndao.getExpiredCount(bId);
        int solidTotal = ndao.getSolidTotal(bId);
        int liquidTotal = ndao.getLiquidTotal(bId);
        int maxOverDay = ndao.getMaxOverDay(bId);

        request.setAttribute("list", list);

        request.setAttribute("expiredCount", expiredCount);
        request.setAttribute("solidTotal", solidTotal);
        request.setAttribute("liquidTotal", liquidTotal);
        request.setAttribute("maxOverDay", maxOverDay);
        
        return "/jsp/notification.jsp";
    }
}