@WebServlet("/mypage/cash-receipt")
public class CashReceiptServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("/login.jsp");
            return;
        }

        CashReceiptDAO dao = new CashReceiptDAO();
        List<CashReceiptDTO> list = dao.getReceiptList(userId);

        request.setAttribute("receiptList", list);
        request.getRequestDispatcher("/mypage/cashReceipt.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reservationNo = request.getParameter("reservationNo");

        CashReceiptDAO dao = new CashReceiptDAO();
        dao.applyCashReceipt(reservationNo);

        response.sendRedirect("/mypage/cash-receipt");
    }
}
