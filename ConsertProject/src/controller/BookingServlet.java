package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HistoryDAO;
import dto.HistoryDTO;
import jdbc.DBManager;

import java.time.LocalDate;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            // 파라미터 수집 및 검증
            int u_no = Integer.parseInt(request.getParameter("u_no"));
            int c_no = Integer.parseInt(request.getParameter("c_no"));
            String h_zone = request.getParameter("h_zone");
            String h_date_str = request.getParameter("h_date");

            // LocalDate로 변환
            LocalDate h_date = LocalDate.parse(h_date_str);

            // DAO 객체 생성
            HistoryDAO dao = new HistoryDAO(DBManager.getConnection());

            // 좌석 중복 체크 (seat_no 제거됨)
            boolean alreadyBooked = dao.isSeatBooked(c_no, h_zone, h_date);

            // 결과 전달
            request.setAttribute("alreadyBooked", alreadyBooked);

            if (!alreadyBooked) {
                // 예매 처리
                HistoryDTO dto = new HistoryDTO();
                dto.setU_no(u_no);
                dto.setC_no(c_no);
                dto.setH_zone(h_zone);
                dto.setH_date(h_date);
                dto.setH_buy(1); // 구매 수량 (기본값 1)
                dto.setH_situ("예매완료");

                boolean result = dao.bookSeat(dto);
                request.setAttribute("result", result ? "success" : "fail");
            } else {
                request.setAttribute("result", "duplicate");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("result", "error");
        }

        // JSP로 포워딩
        request.getRequestDispatcher("/WEB-INF/views/bookingResult.jsp").forward(request, response);
    }
}
