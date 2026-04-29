package com.kostaErp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class loginAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        String bId = request.getParameter("bId");
        String pw = request.getParameter("pw");

        System.out.println("�α��� �õ� bId : " + bId);
        System.out.println("�α��� �õ� pw : " + pw);

        request.setAttribute("loginMessage", "�α��� ��û Ȯ�� �Ϸ�");

        return "foodMaterials.jsp";
    }
}