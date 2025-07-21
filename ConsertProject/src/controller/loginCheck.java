package servlet;

import dao.UserDAO;
import dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        UserDTO user = userDAO.login(id, password);

        if (user != null) {
            // 로그인 성공
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userName", user.getUserName());

            response.sendRedirect("mypage.jsp"); // 로그인 후 마이페이지 등으로 이동
        } else {
            // 로그인 실패
            request.setAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
