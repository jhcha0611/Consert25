package controller;

import dao.PaymentDAO;
import dto.PaymentDTO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PaymentDAO paymentDAO = new PaymentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 파라미터 받아오기
        int u_no = Integer.parseInt(request.getParameter("u_no"));
        int c_no = Integer.parseInt(request.getParameter("c_no"));
        int h_no = Integer.parseInt(request.getParameter("h_no"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        String card = request.getParameter("card");
        String payDateStr = request.getParameter("pay_date");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        Timestamp pay_date = Timestamp.valueOf(payDateStr);

        // DTO 생성 및 데이터 설정
        PaymentDTO dto = new PaymentDTO();
        dto.setU_no(u_no);
        dto.setC_no(c_no);
        dto.setH_no(h_no);
        dto.setPay_amount(amount);
        dto.setPay_card(card);
        dto.setPay_date(pay_date);
        dto.setPay_name(name);
        dto.setPay_phone(phone);
        dto.setPay_address(address);

        // DAO를 통해 결제 저장
        boolean result = paymentDAO.addPayment(dto);

        // JSP에 결제 결과 전달
        request.setAttribute("result", result ? "success" : "fail");
        request.getRequestDispatcher("/WEB-INF/views/paymentResult.jsp").forward(request, response);
    }
}
