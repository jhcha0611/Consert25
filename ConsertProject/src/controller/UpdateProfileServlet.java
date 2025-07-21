package controller;

import dao.UserDAO;
import dto.UserDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import org.mindrot.jbcrypt.BCrypt; // 비밀번호 해싱을 위해 BCrypt 임포트 (pom.xml에 jbcrypt 의존성 추가 필수)

@WebServlet("/updateProfile") // 이 서블릿은 /updateProfile URL로 접근할 수 있게 해줘
public class UpdateProfileServlet extends HttpServlet {

    // GET 요청: 회원 정보 수정 폼 페이지 보여주기
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); // 현재 세션이 없으면 새로 만들지 않음
        if (session == null || session.getAttribute("user") == null) {
            // 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/view/login.jsp?error=not_logged_in");
            return;
        }

        // 세션에서 현재 로그인된 사용자 정보 가져오기
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        
        // 사용자 정보를 request 속성에 저장하여 JSP로 전달
        request.setAttribute("user", currentUser);

        // 회원 정보 수정 폼 JSP로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/editProfile.jsp");
        dispatcher.forward(request, response);
    }

    // POST 요청: 수정된 회원 정보 처리
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8"); // 한글 깨짐 방지

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp?error=not_logged_in");
            return;
        }

        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        int userNo = currentUser.getU_no(); // 세션에서 사용자 번호(u_no) 가져오기

        // 1. 수정 폼에서 입력된 값 받기
        String email = request.getParameter("u_email");
        String name = request.getParameter("u_name");
        String birthStr = request.getParameter("u_birth");
        String number = request.getParameter("u_number");
        String gender = request.getParameter("u_gender");

        // 비밀번호 변경 관련 필드 (비밀번호 변경 시에만 사용)
        String newPassword = request.getParameter("new_password");
        String newPasswordConfirm = request.getParameter("new_password_confirm");

        // 2. 유효성 검사 (필수 항목 누락 체크 - 비밀번호 제외)
        if (email == null || email.trim().isEmpty() || name == null || name.trim().isEmpty() ||
            birthStr == null || birthStr.trim().isEmpty() || number == null || number.trim().isEmpty() ||
            gender == null || gender.trim().isEmpty()) {
            
            response.sendRedirect(request.getContextPath() + "/view/editProfile.jsp?error=missing_info");
            return;
        }

        // 3. 생년월일 String을 Date 타입으로 변환
        Date birth = null;
        try {
            birth = Date.valueOf(birthStr);
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/view/editProfile.jsp?error=invalid_birth_format");
            return;
        }

        // 4. UserDTO 객체에 수정된 정보 설정 (비밀번호 제외)
        UserDTO updatedUser = new UserDTO();
        updatedUser.setU_no(userNo); // 어떤 사용자를 수정할지 식별하는 번호
        updatedUser.setUserId(currentUser.getUserId()); // ID는 보통 변경 불가하므로 기존 값 사용
        // updatedUser.setUserPw(currentUser.getUserPw()); // 비밀번호는 여기서 업데이트하지 않음 (따로 처리)
        updatedUser.setUserEmail(email);
        updatedUser.setUserName(name);
        updatedUser.setUserBirth(birth);
        updatedUser.setUserNumber(number);
        updatedUser.setUserGender(gender);

        UserDAO userDAO = new UserDAO();
        boolean updateSuccess = false;

        try {
            // 5. 일반 회원 정보 업데이트
            updateSuccess = userDAO.updateUserProfile(updatedUser);

            // 6. 비밀번호 변경 요청이 있을 경우 추가 처리
            if (newPassword != null && !newPassword.trim().isEmpty()) { // 새 비밀번호 입력란이 비어있지 않다면
                if (!newPassword.equals(newPasswordConfirm)) {
                    response.sendRedirect(request.getContextPath() + "/view/editProfile.jsp?error=password_mismatch");
                    return;
                }
                // 새 비밀번호를 해싱하여 업데이트
                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                boolean passwordUpdateSuccess = userDAO.updateUserPassword(userNo, hashedPassword);

                if (!passwordUpdateSuccess) {
                    // 비밀번호 업데이트 실패 시 처리
                    response.sendRedirect(request.getContextPath() + "/view/editProfile.jsp?error=password_update_failed");
                    return;
                }
            }
            
            // 7. 업데이트 성공 시 세션 정보 갱신
            if (updateSuccess) {
                // DB에서 최신 사용자 정보를 다시 가져와 세션에 저장 (비밀번호는 저장 안 함)
                UserDTO refreshedUser = userDAO.getUserByNo(userNo); // UserDAO에 getUserByNo 메서드 필요
                if (refreshedUser != null) {
                    session.setAttribute("user", refreshedUser); // 세션 정보 갱신
                    response.sendRedirect(request.getContextPath() + "/view/main.jsp?success=profile_updated"); // 성공 메시지와 함께 메인으로
                } else {
                    response.sendRedirect(request.getContextPath() + "/view/editProfile.jsp?error=update_failed");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/view/editProfile.jsp?error=update_failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/view/editProfile.jsp?error=server_error");
        }
    }
}