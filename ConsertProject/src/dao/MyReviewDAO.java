package dao;

import dto.MyReviewDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyReviewDAO {
    private Connection conn;  // DB 연결 객체

    public MyReviewDAO(Connection conn) {
        this.conn = conn;
    }

    // 특정 사용자의 특정 콘서트를 위한 historyId 조회
    public int getHistoryIdByUserAndConsert(int userId, int consertId) {
        String sql = "SELECT h_no FROM history WHERE u_no = ? AND c_no = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, consertId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("h_no");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 예매 내역이 없으면 -1 반환
    }

    // 후기 저장
    public boolean insertReview(MyReviewDTO review) {
        int historyId = getHistoryIdByUserAndConsert(review.getUserId(), review.getConsertId());

        // historyId가 유효하지 않으면 후기 등록 불가
        if (historyId == -1) {
            System.out.println("예매 내역이 없는 사용자입니다.");
            return false;
        }

        String sql = "INSERT INTO review (u_no, c_no, h_no, r_con, r_rating) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, review.getUserId());
            pstmt.setInt(2, review.getConsertId());
            pstmt.setInt(3, historyId); // historyId 연결
            pstmt.setString(4, review.getContent());
            pstmt.setInt(5, review.getRating());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 특정 콘서트의 후기 목록 조회
    public List<MyReviewDTO> getReviewsByConcert(int concertId) {
        List<MyReviewDTO> reviews = new ArrayList<>();
        String sql = "SELECT * FROM review WHERE c_no = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, concertId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reviews.add(new MyReviewDTO(
                        rs.getInt("r_no"),
                        rs.getInt("u_no"),
                        rs.getInt("c_no"),
                        rs.getInt("h_no"),
                        rs.getString("r_con"),
                        rs.getInt("r_rating"),
                        rs.getString("r_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
