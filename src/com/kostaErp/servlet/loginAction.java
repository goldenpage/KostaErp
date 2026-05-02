package com.kostaErp.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kostaErp.model.userDAO;

public class loginAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
    	String url = "login.jsp";
        String bId = request.getParameter("bId");
        String pw = request.getParameter("pw");
        
        userDAO dao = new userDAO();
        Map<String, Object> m = dao.login(bId, pw);
        System.out.println("m : " + m);
        System.out.println("m size : " + m.size());
        if(m.keySet().size() > 0) {
        	HttpSession session = request.getSession(true);
        	session.setAttribute("loginOK", bId);
        	session.setAttribute("info", m);
        	session.setAttribute("bId", bId);

        	url="foodMaterials.jsp";
        }else {
        	request.setAttribute("errorMessage", "다시 로그인 해주세요");
        }

        System.out.println("bId : " + bId);
        System.out.println("pw : " + pw);

      

        return url;
    }
}