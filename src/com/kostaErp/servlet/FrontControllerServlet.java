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
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String cmd = request.getParameter("cmd");
        System.out.println("cmd : " + cmd);

        Action action = ActionFactory.getAction(cmd);

        String url = action.execute(request);

        if (url == null) {
            url = "login.jsp";
        }

        request.getRequestDispatcher("/view/" + url).forward(request, response);
    }
}