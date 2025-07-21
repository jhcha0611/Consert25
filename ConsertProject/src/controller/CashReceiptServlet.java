package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.CashReceiptDAO;
import dto.CashReceiptDTO;

public class CashReceiptServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CashReceiptDAO dao = new CashReceiptDAO();
        try {
            // 발행 완료 내역 조회
            List<CashReceiptDTO> receipts = dao.getIssuedReceipts();

            // 조회된 데이터 JSP 전달
            request.setAttribute("receipts", receipts);
            request.getRequestDispatcher("/WEB-INF/views/cashReceipts.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
