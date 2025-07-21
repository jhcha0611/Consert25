package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import jdbc.DBManager;

public class InterestDAO {

    // 관심 콘서트 등록
    public boolean insertInterest(int userNo, int consertNo) {
        String sql = "INSERT INTO interest (u_no, c_no) VALUES (?, ?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userNo);
            pstmt.setInt(2, consertNo);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 관심 콘서트 삭제
    public boolean deleteInterest(int userNo, int consertNo) {
        String sql = "DELETE FROM interest WHERE u_no = ? AND c_no = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

                 pstmt.setInt(1, userNo);
                 pstmt.setInt(2, consertNo);

                 int result = pstmt.executeUpdate();
                 return result > 0;

             } catch (Exception e) { // SQLException으로 구체화
                 e.printStackTrace();
                 return false;
             }
         }
     }
