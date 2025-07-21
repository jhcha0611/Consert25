package controller;

import dao.ConsertDAO;
import dto.ConsertDTO;
import jdbc.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/consert")
public class ConsertServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ConsertDAO consertDAO = new ConsertDAO(DBManager.getConnection());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String c_no = request.getParameter("c_no"); // 상세
        if (c_no != null) {
            // 상세 조회
            int cnoInt = Integer.parseInt(c_no);
            ConsertDTO dto = consertDAO.getConsert(cnoInt);

            // JSP로 데이터 전달
            request.setAttribute("concert", dto);
            request.getRequestDispatcher("/WEB-INF/views/consertDetail.jsp").forward(request, response);
        } else {
            // 전체 목록
            List<ConsertDTO> list = consertDAO.getAllConserts();

            // JSP로 데이터 전달
            request.setAttribute("concertList", list);
            request.getRequestDispatcher("/WEB-INF/views/consertList.jsp").forward(request, response);
        }
    }
}
