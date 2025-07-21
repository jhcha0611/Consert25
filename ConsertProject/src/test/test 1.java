package test;

import dao.ReviewDAO;
import dto.ReviewDTO;
import jdbc.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        try {
            // DB 연결
            Connection conn = DBManager.getConnection();
            
            initializeUsers(conn);
            initializeonConsert(conn);
            initializeonHistory(conn);
            
            
            ReviewDAO reviewDao = new ReviewDAO(conn);

            // ✅ 1. 테스트용 후기 등록
            ReviewDTO review = new ReviewDTO(
                    0,           // reviewId (자동 증가)
                    1,           // userId (예매 내역이 있는 사용자 번호)
                    1,           // concertId (예매 내역이 있는 콘서트 번호)
                    0,           // historyId (DAO 내부에서 조회됨)
                    "정말 멋진 공연이었어요!", // content
                    5,           // rating
                    null         // reviewDate (DB NOW())
            );

            boolean insertResult = reviewDao.insertReview(review);
            System.out.println("후기 등록 성공 여부: " + insertResult);

            // ✅ 2. 특정 콘서트의 후기 목록 조회
            int concertIdToQuery = 1;
            List<ReviewDTO> reviews = reviewDao.getReviewsByConcert(concertIdToQuery);
            System.out.println("후기 개수: " + reviews.size());
            for (ReviewDTO r : reviews) {
                System.out.println("[" + r.getReviewId() + "] " +
                        "User#" + r.getUserId() + " 평점: " + r.getRating() +
                        " - " + r.getContent() + " (" + r.getReviewDate() + ")");
            }

            // ✅ 3. 좋아요 증가 테스트
            if (!reviews.isEmpty()) {
                int reviewId = reviews.get(0).getReviewId();
                boolean liked = reviewDao.incrementLike(reviewId);
                System.out.println("좋아요 증가 성공 여부 (reviewId=" + reviewId + "): " + liked);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void initializeUsers(Connection conn) {
        try {
            String sql = "INSERT INTO user (u_no, u_id, u_pw, u_email, u_name, u_birth, u_number, u_gender) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, 0);
            pstmt.setString(2, "test_user");
            pstmt.setString(3, "password");
            pstmt.setString(4, "test@example.com");
            pstmt.setString(5, "Test User");
            pstmt.setString(6, "1990-01-01");
            pstmt.setString(7, "010-1234-5678");
            pstmt.setString(8, "M");
            pstmt.executeUpdate();

            System.out.println("사용자 데이터 삽입 완료!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("사용자 데이터 삽입 실패!");
        }
    }
    
    private static void initializeonConsert(Connection conn) {
    	
    	try {
    		
    		String insertConcert = "INSERT INTO consert (c_name, c_location, c_period, c_age) VALUES (?, ?, ?, ?)";
    		
    		PreparedStatement ps = conn.prepareStatement(insertConcert);
    		
    		ps.setInt(1, 0);
    		ps.setString(2, "여름 콘서트");
    		ps.setString(3, "서울 예술의 전당");
    		ps.setString(4, "2025-08-01 ~ 2025-08-03");
    		ps.setInt(5, 12);
    		ps.executeUpdate();
    		System.out.println("✅ 콘서트 삽입 완료");
		} catch (Exception e) {
			e.addSuppressed(e);
		}
    }
    
    private static void initializeonHistory(Connection conn) {
    	
    	try {
    		String insertHistory = "INSERT INTO history (u_no, c_no, h_zone, h_buy, h_situ) VALUES (?, ?, ?, ?, ?)";
    		PreparedStatement ps = conn.prepareStatement(insertHistory);
    				ps.setInt(1, 0); // u_no
    		ps.setInt(2, 1); // c_no
    		ps.setString(3, "R석");
    		ps.setInt(4, 80000);
    		ps.setString(5, "정상");
    		ps.executeUpdate();
    		System.out.println("✅ 예매 내역 삽입 완료");
			
		} catch (Exception e) {
			e.addSuppressed(e);
		}
    
    }
}
