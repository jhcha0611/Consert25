package dto;

import java.sql.Date;

public class UserDTO {
    private int u_no;
    private String u_id;
    private String u_pw;
    private String u_email;
    private String u_name;
    private Date u_birth;
    private String u_number;
    private String u_gender;

    // 기본 생성자
    public UserDTO() {}

    // 모든 필드를 포함한 생성자
    public UserDTO(int u_no, String u_id, String u_pw, String u_email,
                   String u_name, Date u_birth, String u_number, String u_gender) {
        this.u_no = u_no;
        this.u_id = u_id;
        this.u_pw = u_pw;
        this.u_email = u_email;
        this.u_name = u_name;
        this.u_birth = u_birth;
        this.u_number = u_number;
        this.u_gender = u_gender;
    }

    // Getter / Setter
    public int getU_no() {
        return u_no;
    }

    public void setU_no(int u_no) {
        this.u_no = u_no;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_pw() {
        return u_pw;
    }

    public void setU_pw(String u_pw) {
        this.u_pw = u_pw;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public Date getU_birth() {
        return u_birth;
    }

    public void setU_birth(Date u_birth) {
        this.u_birth = u_birth;
    }

    public String getU_number() {
        return u_number;
    }

    public void setU_number(String u_number) {
        this.u_number = u_number;
    }

    public String getU_gender() {
        return u_gender;
    }

    public void setU_gender(String u_gender) {
        this.u_gender = u_gender;
    }
}
