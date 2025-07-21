package controller;

import dao.ReviewDAO;
import dto.ReviewDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class ReviewListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 연결
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/your_db?serverTimezone=Asia/Seoul",
                "your_username", "your_password");

            ReviewDAO reviewDao = new ReviewDAO(conn);

            // concertId를 쿼리 파라미터로 받음
            String concertIdParam = request.getParameter("concertId");
            int concertId = concertIdParam != null ? Integer.parseInt(concertIdParam) : -1;

            // concertId가 유효하지 않으면 에러 처리
            if (concertId == -1) {
                response.sendRedirect("error.jsp");
                return;
            }

            List<ReviewDTO> reviewList = reviewDao.getReviewsByConcert(concertId);

            conn.close();

            request.setAttribute("reviews", reviewList);
            request.setAttribute("concertId", concertId);

            RequestDispatcher dispatcher = request.getRequestDispatcher("reviewList.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
