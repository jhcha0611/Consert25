package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // ResultSet 임포트 추가
import java.sql.Date;

public class Test2 {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 1. 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. DB 연결
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/콘서트25?serverTimezone=UTC",
                "root",
                "1234"
            );

            // 3. 초기화 메소드들 실행 (테스트용 데이터 준비)
            initializeUsers(conn);          // test_user 추가 (u_no 1)
            initializeonConsert(conn);
            initializeHistory(conn);

            // 4. 회원가입 테스트 (새 사용자 등록)
            registerUser(conn, 2, "newuser123", "pass1234", "newuser@example.com", "New User", Date.valueOf("1995-05-05"), "010-9876-5432", "F");

            // 5. 로그인 기능 테스트
            System.out.println("\n--- 로그인 테스트 시작 ---");
            boolean loginSuccess = loginUser(conn, "test_user", "password"); // 기존 사용자 로그인
            if (loginSuccess) {
                System.out.println("🎉 test_user 로그인 성공!");
            } else {
                System.out.println("😢 test_user 로그인 실패!");
            }

            loginSuccess = loginUser(conn, "newuser123", "pass1234"); // 새로 가입한 사용자 로그인
            if (loginSuccess) {
                System.out.println("🎉 newuser123 로그인 성공!");
            } else {
                System.out.println("😢 newuser123 로그인 실패!");
            }

            loginSuccess = loginUser(conn, "wrong_id", "wrong_pw"); // 실패하는 로그인 시도
            if (loginSuccess) {
                System.out.println("🎉 잘못된 사용자 로그인 성공? (오류)");
            } else {
                System.out.println("😢 잘못된 사용자 로그인 실패! (정상)");
            }
            System.out.println("--- 로그인 테스트 종료 ---");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void initializeUsers(Connection conn) {
        PreparedStatement pstmt = null;
        try {
            String setFkChecksOff = "SET FOREIGN_KEY_CHECKS = 0";
            pstmt = conn.prepareStatement(setFkChecksOff);
            pstmt.executeUpdate();
            System.out.println("✅ 외래 키 체크 일시 중지");

            String deleteSql = "TRUNCATE TABLE user";
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.executeUpdate();
            System.out.println("✅ 사용자 테이블 초기화 완료");

            String setFkChecksOn = "SET FOREIGN_KEY_CHECKS = 1";
            pstmt = conn.prepareStatement(setFkChecksOn);
            pstmt.executeUpdate();
            System.out.println("✅ 외래 키 체크 다시 활성화");

            String insertSql = "INSERT INTO user (u_no, u_id, u_pw, u_email, u_name, u_birth, u_number, u_gender) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(insertSql);
            pstmt.setInt(1, 1);
            pstmt.setString(2, "test_user");
            pstmt.setString(3, "password");
            pstmt.setString(4, "test@example.com");
            pstmt.setString(5, "Test User");
            pstmt.setDate(6, Date.valueOf("1990-01-01"));
            pstmt.setString(7, "010-1234-5678");
            pstmt.setString(8, "M");

            pstmt.executeUpdate();
            System.out.println("✅ 사용자 데이터 삽입 완료");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 사용자 데이터 삽입 실패!");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void initializeonConsert(Connection conn) {
        try {
            String insertConcert = "INSERT INTO consert (c_name, c_location, c_period, c_age) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertConcert);

            ps.setString(1, "여름 콘서트");
            ps.setString(2, "서울 예술의 전당");
            ps.setString(3, "2025-08-01 ~ 2025-08-03");
            ps.setInt(4, 12);
            ps.executeUpdate();
            System.out.println("✅ 콘서트 삽입 완료");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 콘서트 삽입 실패!");
        }
    }

    private static void initializeHistory(Connection conn) {
        try {
            String insertHistory = "INSERT INTO history (u_no, c_no, h_zone, h_buy, h_situ) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertHistory);

            ps.setInt(1, 1);
            ps.setInt(2, 1);
            ps.setString(3, "R석");
            ps.setInt(4, 80000);
            ps.setString(5, "정상");

            ps.executeUpdate();
            System.out.println("✅ 예매 내역 삽입 완료");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 예매 내역 삽입 실패!");
        }
    }

    private static void registerUser(Connection conn, int u_no, String u_id, String u_pw, String u_email, String u_name, Date u_birth, String u_number, String u_gender) {
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO user (u_no, u_id, u_pw, u_email, u_name, u_birth, u_number, u_gender) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, u_no);
            pstmt.setString(2, u_id);
            pstmt.setString(3, u_pw);
            pstmt.setString(4, u_email);
            pstmt.setString(5, u_name);
            pstmt.setDate(6, u_birth);
            pstmt.setString(7, u_number);
            pstmt.setString(8, u_gender);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("✅ 회원가입 성공: " + u_id);
            } else {
                System.out.println("❌ 회원가입 실패: " + u_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 회원가입 중 예외 발생: " + u_id);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ⭐ 로그인 메소드 추가 ⭐
    private static boolean loginUser(Connection conn, String userId, String password) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean loginSuccess = false;
        try {
            String sql = "SELECT u_id FROM user WHERE u_id = ? AND u_pw = ?"; // ID와 PW가 일치하는지 확인
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery(); // 쿼리 실행 결과 받기

            if (rs.next()) { // 결과 집합에 다음 레코드가 있으면 (일치하는 사용자가 있으면)
                loginSuccess = true;
            } else {
                loginSuccess = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 로그인 중 예외 발생: " + userId);
        } finally {
            try {
                if (rs != null) rs.close(); // ResultSet 닫기
                if (pstmt != null) pstmt.close(); // PreparedStatement 닫기
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loginSuccess;
    }
}