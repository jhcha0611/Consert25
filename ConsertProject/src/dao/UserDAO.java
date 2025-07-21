package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import java.util.*;

import dto.UserDTO;
import jdbc.DBManager;


public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    


    // 로그인
	 public UserDTO login(String id, String password) {
	        UserDTO user = null;
	       
	        String sql = "SELECT u_no, u_id, u_pw, u_name, u_email, u_birth, u_number, u_gender FROM user WHERE u_id = ? AND u_pw = ?";

	        try (
	            Connection conn = DBManager.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql)
	        ) {
	            pstmt.setString(1, id.trim());
	            pstmt.setString(2, password.trim()); 

	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) { 
	                user = new UserDTO();
	                user.setUserNo(rs.getInt("u_no"));
	                user.setUserId(rs.getString("u_id"));
	                user.setUserPw(rs.getString("u_pw")); 
	                user.setUserName(rs.getString("u_name"));
	                user.setUserEmail(rs.getString("u_email"));
	                user.setUserBirth(rs.getDate("u_birth"));
	                user.setUserNumber(rs.getString("u_number"));
	                user.setUserGender(rs.getString("u_gender"));
	            }
	            rs.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return user;
	    }

	    // 다른 메서드들 (회원가입, 아이디 중복 체크 등)도 여기 넣으면 돼
	

    // 회원가입
    public boolean insertUser(UserDTO user) {
        String sql = "INSERT INTO user (u_id, u_pw, u_name, u_email, u_birth, u_number, u_gender) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, user.getUserId());
            // 🚨 비밀번호 해싱 없이 DTO의 평문 비밀번호를 그대로 저장
            pstmt.setString(2, user.getUserPw());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getUserEmail());
            pstmt.setDate(5, user.getUserBirth());
            pstmt.setString(6, user.getUserNumber());
            pstmt.setString(7, user.getUserGender());

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ID로 회원 정보 가져오기 (비밀번호 제외)
    public UserDTO getUserById(String id) {
        UserDTO user = null;
        // 비밀번호는 보안상 가져오지 않음 (여기서는 기존 로직 유지)
        String sql = "SELECT u_no, u_id, u_name, u_email, u_birth, u_number, u_gender FROM user WHERE u_id = ?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setUserNo(rs.getInt("u_no"));
                user.setUserId(rs.getString("u_id"));
                // user.setUserPw(rs.getString("u_pw")); // 비밀번호는 가져오지 않음
                user.setUserName(rs.getString("u_name"));
                user.setUserEmail(rs.getString("u_email"));
                user.setUserBirth(rs.getDate("u_birth"));
                user.setUserNumber(rs.getString("u_number"));
                user.setUserGender(rs.getString("u_gender"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // 회원 정보 수정 (비밀번호 변경은 별도의 메서드로 분리하는 것이 좋음)
    public boolean updateUserProfile(UserDTO user) {
        String sql = "UPDATE user SET u_name=?, u_email=?, u_birth=?, u_number=?, u_gender=? WHERE u_id=?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getUserEmail());
            pstmt.setDate(3, user.getUserBirth());
            pstmt.setString(4, user.getUserNumber());
            pstmt.setString(5, user.getUserGender());
            pstmt.setString(6, user.getUserId()); // WHERE 절 조건

            int rows = pstmt.executeUpdate();
            return (rows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🚨 비밀번호만 업데이트하는 메서드 (평문 비밀번호를 받음)
    public boolean updateUserPassword(String userId, String newPassword) { // 🚨 매개변수 이름 변경
        String sql = "UPDATE user SET u_pw = ? WHERE u_id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword); // 🚨 해싱 없이 평문 비밀번호 저장
            pstmt.setString(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 아이디 찾기
    public String findId(String name, String email) {
        String foundId = null;
        String sql = "SELECT u_id FROM user WHERE u_name = ? AND u_email = ?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                foundId = rs.getString("u_id");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundId;
    }

    // 비밀번호 찾기 (재설정) - 평문 비밀번호로 업데이트
    public boolean resetPassword(String id, String name, String email, String newPassword) { // 🚨 매개변수 이름 변경
        String sql = "UPDATE user SET u_pw = ? WHERE u_id = ? AND u_name = ? AND u_email = ?";
        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, newPassword); // 🚨 해싱 없이 평문 비밀번호 저장
            pstmt.setString(2, id);
            pstmt.setString(3, name);
            pstmt.setString(4, email);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 사용자 번호(u_no)로 사용자 정보를 가져오는 메서드 (세션 갱신용, 비밀번호 제외)
    public UserDTO getUserByNo(int u_no) {
        UserDTO user = null;
        String sql = "SELECT u_no, u_id, u_name, u_email, u_birth, u_number, u_gender FROM user WHERE u_no = ?";
        
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, u_no);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setUserNo(rs.getInt("u_no"));
                user.setUserId(rs.getString("u_id"));
                user.setUserName(rs.getString("u_name"));
                user.setUserEmail(rs.getString("u_email"));
                user.setUserBirth(rs.getDate("u_birth"));
                user.setUserNumber(rs.getString("u_number"));
                user.setUserGender(rs.getString("u_gender"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}