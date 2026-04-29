package com.kostaErp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class loginAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        String bId = request.getParameter("bId");
        String pw = request.getParameter("pw");

        System.out.println("로그인 시도 bId : " + bId);
        System.out.println("로그인 시도 pw : " + pw);

        request.setAttribute("loginMessage", "로그인 요청 확인 완료");

        return "foodMaterials.jsp";
    }
}