package com.kostaErp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class menuAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        System.out.println("메뉴 처리 Action 실행");

        request.setAttribute("message", "메뉴 요청 처리 확인");

        return "menuList.jsp";
    }
}