package servlet;

import dao.ReviewDAO;
import dto.ReviewDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/mypage/review")
public class ReviewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId");
        List<ReviewDTO> list = new ReviewDAO().getByUserId(userId);
        request.setAttribute("reviewList", list);
        request.getRequestDispatcher("/mypage/review.jsp").forward(request, response);
    }
}