package com.kostaErp.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; // 추가 가능성 대비
import javax.servlet.http.HttpSession;

import com.kostaErp.model.DAO.userDAO;

public class loginAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        String url = "login.jsp";
        String bId = request.getParameter("bId");
        String pw = request.getParameter("pw");
        
        userDAO dao = new userDAO();
        Map<String, Object> m = dao.login(bId, pw);
        
        System.out.println("--- 로그인 시도 ---");
        System.out.println("입력 bId : " + bId);
        System.out.println("DB 조회 결과(m) : " + m);
        
        if(m != null && m.size() > 0) {
            HttpSession session = request.getSession(true);
            
            session.setAttribute("loginOK", bId);
            session.setAttribute("info", m);
            session.setAttribute("bId", bId);

            System.out.println("로그: 세션 저장 완료 - bId : " + session.getAttribute("bId"));
            
            url = "foodMaterials.jsp"; 
        } else {
            request.setAttribute("errorMessage", "아이디 또는 비밀번호가 틀렸습니다. 다시 로그인 해주세요.");
            System.out.println("로그: 로그인 실패");
        }

        return url;
    }
}