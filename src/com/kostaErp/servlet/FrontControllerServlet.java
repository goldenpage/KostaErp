package com.kostaErp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/controller")
public class FrontControllerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String cmd = request.getParameter("cmd");
        System.out.println("cmd : " + cmd);
        Action action = ActionFactory.getAction(cmd);
        request.setAttribute("response", response);
        String url = action.execute(request);

        if (url == null) {
            return;
        }

        boolean isAjax = "true".equals(request.getParameter("ajax"));
        
        if (url.equals("login.jsp") && isAjax) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        request.getRequestDispatcher("/view/" + url).forward(request, response);
    }
}