package controller;

import dao.UserDAO; // UserDAO 클래스 임포트
import dto.UserDTO; // UserDTO 클래스 임포트

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; // HttpSession 임포트
import java.io.IOException;

@WebServlet("/loginProcess") // 이 서블릿은 /loginProcess URL로 POST 요청을 받을 거야
public class LoginServlet extends HttpServlet {

    // GET 요청이 오면 로그인 폼 페이지로 리다이렉트
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp"); // 로그인 폼 페이지로 이동
    }

    // POST 요청 (로그인 폼 제출) 처리
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8"); // POST 요청 한글 깨짐 방지

        // 1. 입력값 받기
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        // 2. 입력값 유효성 검사 (비어 있는 값 체크)
        if (id == null || id.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            // 오류 메시지와 함께 로그인 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/view/login.jsp?error=empty_fields");
            return;
        }

        // 3. UserDAO를 통해 로그인 시도
        UserDAO userDAO = new UserDAO();
        // UserDAO의 login 메서드 내부에서 비밀번호 해싱을 이용해 비교하는 것을 강력히 권장해!
        UserDTO user = userDAO.login(id, password); 

        // 4. 로그인 결과 처리
        if (user != null) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            HttpSession session = request.getSession(); // 세션이 없으면 새로 생성
            session.setAttribute("user", user); // "user"라는 이름으로 UserDTO 객체를 세션에 저장

            // 메인 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/view/main.jsp");
        } else {
            // 로그인 실패 시 로그인 페이지로 다시 이동 (오류 메시지 포함)
            response.sendRedirect(request.getContextPath() + "/view/login.jsp?error=invalid_credentials");
        }
    }
}