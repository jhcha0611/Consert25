// LogoutServlet.java (예시)
package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout") // 이 서블릿은 /logout URL로 접근할 수 있게 해줘
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); // 현재 세션이 없으면 새로 만들지 않음
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        
        response.sendRedirect(request.getContextPath() + "/view/login.jsp"); // 로그인 페이지로 리다이렉트
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}