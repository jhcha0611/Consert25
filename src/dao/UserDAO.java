package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.UserDTO;
import jdbc.DBManager;

public class UserDAO {

    // 로그인
    public UserDTO login(String id, String password) {
        UserDTO user = null;
        String sql = "SELECT * FROM user WHERE u_id = ? AND u_pw = ?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, id.trim());
            pstmt.setString(2, password.trim());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new UserDTO();
                user.setU_no(rs.getInt("u_no"));
                user.setU_id(rs.getString("u_id"));
                user.setU_pw(rs.getString("u_pw"));
                user.setU_name(rs.getString("u_name"));
                user.setU_email(rs.getString("u_email"));
                user.setU_birth(rs.getDate("u_birth"));
                user.setU_number(rs.getString("u_number"));
                user.setU_gender(rs.getString("u_gender"));
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // 회원가입
    public boolean insertUser(UserDTO user) {
        String sql = "INSERT INTO user (u_id, u_pw, u_name, u_email, u_birth, u_number, u_gender) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, user.getU_id());
            pstmt.setString(2, user.getU_pw());
            pstmt.setString(3, user.getU_name());
            pstmt.setString(4, user.getU_email());
            pstmt.setDate(5, user.getU_birth());
            pstmt.setString(6, user.getU_number());
            pstmt.setString(7, user.getU_gender());

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ID로 회원 정보 가져오기
    public UserDTO getUserById(String id) {
        UserDTO user = null;
        String sql = "SELECT * FROM user WHERE u_id = ?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setU_no(rs.getInt("u_no"));
                user.setU_id(rs.getString("u_id"));
                user.setU_pw(rs.getString("u_pw"));
                user.setU_name(rs.getString("u_name"));
                user.setU_email(rs.getString("u_email"));
                user.setU_birth(rs.getDate("u_birth"));
                user.setU_number(rs.getString("u_number"));
                user.setU_gender(rs.getString("u_gender"));
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // 회원 정보 수정
    public boolean updateUser(UserDTO user) {
        String sql = "UPDATE user SET u_pw=?, u_name=?, u_email=?, u_birth=?, u_number=?, u_gender=? WHERE u_id=?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, user.getU_pw());
            pstmt.setString(2, user.getU_name());
            pstmt.setString(3, user.getU_email());
            pstmt.setDate(4, user.getU_birth());
            pstmt.setString(5, user.getU_number());
            pstmt.setString(6, user.getU_gender());
            pstmt.setString(7, user.getU_id());

            int rows = pstmt.executeUpdate();
            return (rows > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 회원 탈퇴
    public boolean deleteUser(String id) {
        String sql = "DELETE FROM user WHERE u_id = ?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, id);
            int rows = pstmt.executeUpdate();
            return (rows > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
