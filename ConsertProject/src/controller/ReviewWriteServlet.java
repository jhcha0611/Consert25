package controller;

import dao.ReviewDAO;
import dto.ReviewDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class ReviewWriteServlet extends HttpServlet {
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

        String content = request.getParameter("content");
        int rating = Integer.parseInt(request.getParameter("rating"));
        int concertId = Integer.parseInt(request.getParameter("concertId"));

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/your_db?serverTimezone=Asia/Seoul",
                "your_username", "your_password");

            ReviewDAO reviewDao = new ReviewDAO(conn);

            ReviewDTO review = new ReviewDTO(
                0, // reviewId (AUTO_INCREMENT)
                userId,
                concertId,
                0, // historyId는 insertReview에서 내부적으로 조회됨
                content,
                rating,
                null // reviewDate는 DB의 NOW()로 자동 저장
            );

            boolean result = reviewDao.insertReview(review);
            conn.close();

            if (result) {
                response.sendRedirect("reviewSuccess.jsp");
            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<script>alert('후기 등록 실패: 예매 정보가 없습니다.'); history.back();</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
