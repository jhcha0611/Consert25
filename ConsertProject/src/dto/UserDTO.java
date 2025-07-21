package dto;

import java.sql.Date; 

public class UserDTO {
    private int userNo;
    private String userId;
    private String userPw;
    private String userEmail;
    private String userName;
    private java.sql.Date userBirth; // LocalDate -> Date 로 타입 변경
    private String userNumber;
    private String userGender;

    // 기본 생성자
    public UserDTO() {}

    // 모든 필드를 포함한 생성자
    public UserDTO(int userNo, String userId, String userPw, String userEmail,
                   String userName, Date userBirth, String userNumber, String userGender) { // 타입 변경 반영
        this.userNo = userNo;
        this.userId = userId;
        this.userPw = userPw;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userBirth = userBirth;
        this.userNumber = userNumber;
        this.userGender = userGender;
    }

    // Getter / Setter (필드명 변경 및 타입 변경 반영)
    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public java.sql.Date getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(java.sql.Date userBirth) {
        this.userBirth = userBirth;
    }
    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}