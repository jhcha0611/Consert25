package controller;

import dao.UserDAO;
import dto.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

// 🚨 BCrypt 라이브러리 임포트 제거
// import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/registerProcess")
public class RegisterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. 입력값 받기
        String id = request.getParameter("u_id");
        String password = request.getParameter("u_password");
        // 🚨 비밀번호 확인 필드 제거
        // String confirmPassword = request.getParameter("u_password_confirm");
        String email = request.getParameter("u_email");
        String name = request.getParameter("u_name");
        String birthStr = request.getParameter("u_birth");
        String number = request.getParameter("u_number");
        String gender = request.getParameter("u_gender");

        // 2. 입력값 유효성 검사 (필수 항목 누락 체크)
        // 🚨 confirmPassword 관련 체크 제거
        if (id == null || id.trim().isEmpty() || password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty() || name == null || name.trim().isEmpty() ||
            birthStr == null || birthStr.trim().isEmpty() || number == null || number.trim().isEmpty() ||
            gender == null || gender.trim().isEmpty()) {
            
            response.sendRedirect("register.jsp?error=missing_info");
            return;
        }

        // 🚨 비밀번호와 비밀번호 확인 일치 여부 검사 로직 제거
        // if (!password.equals(confirmPassword)) {
        //     response.sendRedirect("register.jsp?error=password_mismatch");
        //     return;
        // }

        // 3. 생년월일 String을 Date 타입으로 변환
        Date birth = null;
        try {
            birth = Date.valueOf(birthStr);
        } catch (IllegalArgumentException e) {
            response.sendRedirect("register.jsp?error=invalid_birth_format");
            return;
        }

        // 4. UserDTO 객체 생성 및 데이터 설정
        UserDTO user = new UserDTO();
        user.setUserId(id);

        // 🚨 비밀번호 해싱 제거! (평문 비밀번호 그대로 저장) 🚨
        // String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        // user.setUserPw(hashedPassword);
        user.setUserPw(password); // ⚠️ 보안 경고: 비밀번호를 해싱하지 않고 그대로 저장

        user.setUserEmail(email);
        user.setUserName(name);
        user.setUserBirth(birth);
        user.setUserNumber(number);
        user.setUserGender(gender);

        // 5. UserDAO를 통해 DB에 사용자 정보 저장
        UserDAO dao = new UserDAO();
        boolean result = dao.insertUser(user);

        // 6. 결과 처리 및 리다이렉트
        if (result) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp?success=registered");
        } else {
            response.sendRedirect(request.getContextPath() + "/register.jsp?error=id_duplicate");
        }
    }
}