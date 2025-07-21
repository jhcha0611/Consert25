package servlet;

import dao.InterestDAO;
import dto.InterestDTO;
import dto.UserDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/mypage/interest")
public class InterestServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 세션에서 로그인 사용자 정보 가져오기
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/login.jsp");
            return;
        }

        int userNo = user.getU_no(); // 정수형 사용자 번호

        // 관심 콘서트 목록 가져오기
        List<InterestDTO> list = new InterestDAO().getByUserId(userNo);

        // request에 데이터 전달
        request.setAttribute("interestList", list);

        // JSP로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/mypage/interest.jsp");
        dispatcher.forward(request, response);
    }
}
