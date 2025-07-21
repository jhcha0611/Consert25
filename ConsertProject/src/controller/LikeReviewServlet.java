package controller;

import dao.ReviewDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class LikeReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<script>alert('로그인이 필요합니다.'); location.href='login.jsp';</script>");
            return;
        }

        String reviewIdParam = request.getParameter("reviewId");
        if (reviewIdParam == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        int reviewId = Integer.parseInt(reviewIdParam);

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/your_db?serverTimezone=Asia/Seoul",
                "your_username", "your_password");

            ReviewDAO reviewDao = new ReviewDAO(conn);
            boolean success = reviewDao.incrementLike(reviewId);

            conn.close();

            if (success) {
                response.sendRedirect("reviews"); // 좋아요 누른 후 목록으로 이동
            } else {
                response.sendRedirect("error.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
