package com.kostaErp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class foodMaterialAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        System.out.println("식자재 처리 Action 실행");

        request.setAttribute("message", "식자재 요청 처리 확인");

        return "foodMaterials.jsp";
    }
}