# Consert25
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException; // SQLException 임포트 추가

public class Test {

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

            // 3. 테스트 데이터 초기화 전에 외래 키 제약 일시 중지
            setForeignKeyChecks(conn, false);

            // 4. 모든 테스트 데이터 준비 (마이페이지 테스트를 위한 데이터)
            initializeUsers(conn);          // 사용자 데이터
            initializeConcerts(conn);       // 콘서트 데이터
            initializeHistory(conn);        // 예매 내역 데이터
            initializeCashReceipts(conn);   // 현금 영수증 데이터
            initializeInterests(conn);      // 관심 콘서트 데이터
            initializeReviews(conn);        // 나의 후기 데이터

            // 5. 테스트 데이터 준비 완료 후 외래 키 제약 다시 활성화
            setForeignKeyChecks(conn, true);

            // 6. 마이페이지 기능 테스트를 위한 사용자 로그인 시뮬레이션
            // 여기서는 'test_user'가 로그인했다고 가정하고 해당 사용자의 정보를 조회해요.
            int loggedInUserNo = loginUserAndGetNo(conn, "test_user", "password");
            if (loggedInUserNo != -1) {
                System.out.println("🎉 test_user 로그인 성공! 사용자 번호: " + loggedInUserNo);
                System.out.println("\n--- test_user의 마이페이지 ---");

                // 7. 마이페이지 각 항목 조회 및 출력
                System.out.println("--- 최근 예매 내역 ---");
                displayReservationHistory(conn, loggedInUserNo);

                System.out.println("\n--- 현금 영수증 내역 ---");
                displayCashReceipts(conn, loggedInUserNo);

                System.out.println("\n--- 관심 있는 콘서트 목록 ---");
                displayInterestedConcerts(conn, loggedInUserNo);

                System.out.println("\n--- 나의 후기 ---");
                displayMyReviews(conn, loggedInUserNo);

            } else {
                System.out.println("😢 test_user 로그인 실패! 마이페이지를 보여줄 수 없습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    setForeignKeyChecks(conn, true); // 혹시 모를 에러에도 꼭 복구
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 외래 키 제약 설정/해제 유틸리티 메소드
    private static void setForeignKeyChecks(Connection conn, boolean enable) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            String sql = "SET FOREIGN_KEY_CHECKS = " + (enable ? "1" : "0");
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            System.out.println("✅ 외래 키 체크 " + (enable ? "활성화" : "일시 중지"));
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }

    // 사용자 데이터 초기화
    private static void initializeUsers(Connection conn) {
        PreparedStatement pstmt = null;
        try {
            String deleteSql = "TRUNCATE TABLE user";
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.executeUpdate();
            System.out.println("✅ 사용자 테이블 초기화 완료");

            String insertSql = "INSERT INTO user (u_no, u_id, u_pw, u_email, u_name, u_birth, u_number, u_gender) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            // test_user (u_no: 1)
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setInt(1, 1);
            pstmt.setString(2, "test_user");
            pstmt.setString(3, "password");
            pstmt.setString(4, "test@example.com");
            pstmt.setString(5, "테스트 사용자");
            pstmt.setDate(6, Date.valueOf("1990-01-01"));
            pstmt.setString(7, "010-1234-5678");
            pstmt.setString(8, "M");
            pstmt.executeUpdate();
            System.out.println("✅ 사용자 데이터 삽입 완료 (test_user)");

            // newuser123 (u_no: 2) - 마이페이지 테스트용 외 추가 사용자
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setInt(1, 2);
            pstmt.setString(2, "newuser123");
            pstmt.setString(3, "pass1234");
            pstmt.setString(4, "newuser@example.com");
            pstmt.setString(5, "새로운 사용자");
            pstmt.setDate(6, Date.valueOf("1995-05-05"));
            pstmt.setString(7, "010-9876-5432");
            pstmt.setString(8, "F");
            pstmt.executeUpdate();
            System.out.println("✅ 사용자 데이터 삽입 완료 (newuser123)");

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

    // 콘서트 데이터 초기화
    private static void initializeConcerts(Connection conn) {
        PreparedStatement ps = null;
        try {
            String truncateConsert = "TRUNCATE TABLE consert";
            ps = conn.prepareStatement(truncateConsert);
            ps.executeUpdate();
            System.out.println("✅ 콘서트 테이블 초기화 완료");

            String insertConcert = "INSERT INTO consert (c_no, c_name, c_location, c_period, c_age) VALUES (?, ?, ?, ?, ?)";

            // 콘서트 1 (c_no: 101)
            ps = conn.prepareStatement(insertConcert);
            ps.setInt(1, 101);
            ps.setString(2, "여름 밤의 재즈 콘서트");
            ps.setString(3, "서울 예술의 전당");
            ps.setString(4, "2025-08-01 ~ 2025-08-03");
            ps.setInt(5, 12);
            ps.executeUpdate();
            System.out.println("✅ 콘서트 삽입 완료 (c_no:101, 여름 밤의 재즈 콘서트)");

            // 콘서트 2 (c_no: 102)
            ps = conn.prepareStatement(insertConcert);
            ps.setInt(1, 102);
            ps.setString(2, "가을 낭만 클래식");
            ps.setString(3, "세종문화회관");
            ps.setString(4, "2025-09-15 ~ 2025-09-17");
            ps.setInt(5, 7);
            ps.executeUpdate();
            System.out.println("✅ 콘서트 삽입 완료 (c_no:102, 가을 낭만 클래식)");

            // 콘서트 3 (c_no: 103)
            ps = conn.prepareStatement(insertConcert);
            ps.setInt(1, 103);
            ps.setString(2, "겨울 발라드 축제");
            ps.setString(3, "올림픽공원 KSPO 돔");
            ps.setString(4, "2025-12-20 ~ 2025-12-25");
            ps.setInt(5, 15);
            ps.executeUpdate();
            System.out.println("✅ 콘서트 삽입 완료 (c_no:103, 겨울 발라드 축제)");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 콘서트 삽입 실패!");
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 예매 내역 초기화
    private static void initializeHistory(Connection conn) {
        PreparedStatement ps = null;
        try {
            String truncateHistory = "TRUNCATE TABLE history";
            ps = conn.prepareStatement(truncateHistory);
            ps.executeUpdate();
            System.out.println("✅ 예매 내역 테이블 초기화 완료");

            String insertHistory = "INSERT INTO history (u_no, c_no, h_zone, h_buy, h_situ) VALUES (?, ?, ?, ?, ?)";
            
            // test_user (u_no: 1)의 예매 내역 1
            ps = conn.prepareStatement(insertHistory);
            ps.setInt(1, 1); // test_user
            ps.setInt(2, 101); // 여름 밤의 재즈 콘서트
            ps.setString(3, "R석");
            ps.setInt(4, 80000);
            ps.setString(5, "정상");
            ps.executeUpdate();
            System.out.println("✅ 예매 내역 삽입 완료 (test_user, 여름 재즈 R석 정상)");

            // test_user (u_no: 1)의 예매 내역 2 (취소된 내역)
            ps = conn.prepareStatement(insertHistory);
            ps.setInt(1, 1); // test_user
            ps.setInt(2, 102); // 가을 낭만 클래식
            ps.setString(3, "VIP석");
            ps.setInt(4, 120000);
            ps.setString(5, "취소");
            ps.executeUpdate();
            System.out.println("✅ 예매 내역 삽입 완료 (test_user, 가을 클래식 VIP석 취소)");

            // newuser123 (u_no: 2)의 예매 내역
            ps = conn.prepareStatement(insertHistory);
            ps.setInt(1, 2); // newuser123
            ps.setInt(2, 103); // 겨울 발라드 축제
            ps.setString(3, "S석");
            ps.setInt(4, 100000);
            ps.setString(5, "정상");
            ps.executeUpdate();
            System.out.println("✅ 예매 내역 삽입 완료 (newuser123, 겨울 발라드 S석 정상)");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 예매 내역 삽입 실패!");
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 현금 영수증 데이터 초기화
    private static void initializeCashReceipts(Connection conn) {
        PreparedStatement ps = null;
        try {
            String truncateCr = "TRUNCATE TABLE cash_receipts";
            ps = conn.prepareStatement(truncateCr);
            ps.executeUpdate();
            System.out.println("✅ 현금 영수증 테이블 초기화 완료");

            String insertCr = "INSERT INTO cash_receipts (u_no, cr_amount, cr_date, cr_status) VALUES (?, ?, ?, ?)";
            
            // test_user (u_no: 1)의 현금 영수증 1
            ps = conn.prepareStatement(insertCr);
            ps.setInt(1, 1);
            ps.setInt(2, 80000);
            ps.setDate(3, Date.valueOf("2025-07-20"));
            ps.setString(4, "발급 완료");
            ps.executeUpdate();
            System.out.println("✅ 현금 영수증 삽입 완료 (test_user, 80000원)");

            // test_user (u_no: 1)의 현금 영수증 2 (취소된 건)
            ps = conn.prepareStatement(insertCr);
            ps.setInt(1, 1);
            ps.setInt(2, 120000);
            ps.setDate(3, Date.valueOf("2025-09-01"));
            ps.setString(4, "취소 완료");
            ps.executeUpdate();
            System.out.println("✅ 현금 영수증 삽입 완료 (test_user, 120000원 취소)");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 현금 영수증 삽입 실패!");
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 관심 콘서트 데이터 초기화
    private static void initializeInterests(Connection conn) {
        PreparedStatement ps = null;
        try {
            String truncateInterests = "TRUNCATE TABLE user_interests";
            ps = conn.prepareStatement(truncateInterests);
            ps.executeUpdate();
            System.out.println("✅ 관심 콘서트 테이블 초기화 완료");

            String insertInterest = "INSERT INTO user_interests (u_no, c_no) VALUES (?, ?)";
            
            // test_user (u_no: 1)의 관심 콘서트 1
            ps = conn.prepareStatement(insertInterest);
            ps.setInt(1, 1);
            ps.setInt(2, 103); // 겨울 발라드 축제
            ps.executeUpdate();
            System.out.println("✅ 관심 콘서트 삽입 완료 (test_user, 겨울 발라드 축제)");

            // test_user (u_no: 1)의 관심 콘서트 2
            ps = conn.prepareStatement(insertInterest);
            ps.setInt(1, 1);
            ps.setInt(2, 102); // 가을 낭만 클래식
            ps.executeUpdate();
            System.out.println("✅ 관심 콘서트 삽입 완료 (test_user, 가을 낭만 클래식)");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 관심 콘서트 삽입 실패!");
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 나의 후기 데이터 초기화
    private static void initializeReviews(Connection conn) {
        PreparedStatement ps = null;
        try {
            String truncateReviews = "TRUNCATE TABLE reviews";
            ps = conn.prepareStatement(truncateReviews);
            ps.executeUpdate();
            System.out.println("✅ 나의 후기 테이블 초기화 완료");

            String insertReview = "INSERT INTO reviews (u_no, c_no, review_content, review_rating, review_date) VALUES (?, ?, ?, ?, ?)";
            
            // test_user (u_no: 1)의 후기 1
            ps = conn.prepareStatement(insertReview);
            ps.setInt(1, 1);
            ps.setInt(2, 101); // 여름 밤의 재즈 콘서트
            ps.setString(3, "여름 재즈 콘서트 정말 최고였어요! 분위기며 연주며 완벽했습니다!");
            ps.setInt(4, 5);
            ps.setDate(5, Date.valueOf("2025-08-04"));
            ps.executeUpdate();
            System.out.println("✅ 후기 삽입 완료 (test_user, 재즈 콘서트 후기)");

            // test_user (u_no: 1)의 후기 2 (아직 관람 전 콘서트의 기대평)
            ps = conn.prepareStatement(insertReview);
            ps.setInt(1, 1);
            ps.setInt(2, 103); // 겨울 발라드 축제
            ps.setString(3, "너무 기대하고 있는 겨울 발라드 콘서트! 미리 기대평 남깁니다. 겨울에 꼭 가봐야지!");
            ps.setInt(4, 4); // 기대평이니까 4점
            ps.setDate(5, Date.valueOf("2025-11-20"));
            ps.executeUpdate();
            System.out.println("✅ 후기 삽입 완료 (test_user, 겨울 발라드 축제 기대평)");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 나의 후기 삽입 실패!");
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 사용자 ID와 비밀번호로 로그인하여 사용자 번호(u_no) 반환 (로그인 실패 시 -1 반환)
    private static int loginUserAndGetNo(Connection conn, String u_id, String u_pw) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int userNo = -1;
        try {
            String sql = "SELECT u_no FROM user WHERE u_id = ? AND u_pw = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, u_id);
            pstmt.setString(2, u_pw);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                userNo = rs.getInt("u_no");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ 로그인 중 SQL 오류 발생: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userNo;
    }

    // 마이페이지 - 예매 내역 조회
    private static void displayReservationHistory(Connection conn, int u_no) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT c.c_name, h.h_zone, h.h_buy, h.h_situ " +
                         "FROM history h JOIN consert c ON h.c_no = c.c_no " +
                         "WHERE h.u_no = ? ORDER BY h.h_no DESC"; // 최근 예매 내역이 위로 오게
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, u_no);
            rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) { // 결과가 없는 경우
                System.out.println("  예매 내역이 없습니다.");
            } else {
                while (rs.next()) {
                    String concertName = rs.getString("c_name");
                    String zone = rs.getString("h_zone");
                    int price = rs.getInt("h_buy");
                    String status = rs.getString("h_situ");
                    System.out.printf("  - 콘서트: %s, 좌석: %s, 금액: %,d원, 상태: %s\n",
                                      concertName, zone, price, status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ 예매 내역 조회 중 오류 발생!");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 마이페이지 - 현금 영수증 조회
    private static void displayCashReceipts(Connection conn, int u_no) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT cr_amount, cr_date, cr_status " +
                         "FROM cash_receipts WHERE u_no = ? ORDER BY cr_date DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, u_no);
            rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("  발급된 현금 영수증이 없습니다.");
            } else {
                while (rs.next()) {
                    int amount = rs.getInt("cr_amount");
                    Date date = rs.getDate("cr_date");
                    String status = rs.getString("cr_status");
                    System.out.printf("  - 금액: %,d원, 날짜: %s, 상태: %s\n",
                                      amount, date.toString(), status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ 현금 영수증 조회 중 오류 발생!");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 마이페이지 - 관심 콘서트 조회
    private static void displayInterestedConcerts(Connection conn, int u_no) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT c.c_name, c.c_location, c.c_period " +
                         "FROM user_interests ui JOIN consert c ON ui.c_no = c.c_no " +
                         "WHERE ui.u_no = ? ORDER BY c.c_no DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, u_no);
            rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("  관심 등록한 콘서트가 없습니다.");
            } else {
                while (rs.next()) {
                    String concertName = rs.getString("c_name");
                    String location = rs.getString("c_location");
                    String period = rs.getString("c_period");
                    System.out.printf("  - 콘서트: %s, 장소: %s, 기간: %s\n",
                                      concertName, location, period);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ 관심 콘서트 조회 중 오류 발생!");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 마이페이지 - 나의 후기 조회
    private static void displayMyReviews(Connection conn, int u_no) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT c.c_name, r.review_content, r.review_rating, r.review_date " +
                         "FROM reviews r JOIN consert c ON r.c_no = c.c_no " +
                         "WHERE r.u_no = ? ORDER BY r.review_no DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, u_no);
            rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("  작성한 후기가 없습니다.");
            } else {
                while (rs.next()) {
                    String concertName = rs.getString("c_name");
                    String content = rs.getString("review_content");
                    int rating = rs.getInt("review_rating");
                    Date date = rs.getDate("review_date");
                    System.out.printf("  - 콘서트: %s (평점: %d점, 날짜: %s)\n    내용: \"%s\"\n",
                                      concertName, rating, date.toString(), content);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ 나의 후기 조회 중 오류 발생!");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
