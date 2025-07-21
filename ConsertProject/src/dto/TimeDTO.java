// src/dto/TimeDTO.java
package dto;

import java.sql.Timestamp;

public class TimeDTO {
    private int t_no;
    private int c_no;
    private Timestamp t_date;

    public TimeDTO(int t_no, int c_no, Timestamp t_date) {
        this.t_no = t_no;
        this.c_no = c_no;
        this.t_date = t_date;
    }

    // getter/setter 필수!
    public int getT_no() { return t_no; }
    public void setT_no(int t_no) { this.t_no = t_no; }
    public int getC_no() { return c_no; }
    public void setC_no(int c_no) { this.c_no = c_no; }
    public Timestamp getT_date() { return t_date; }
    public void setT_date(Timestamp t_date) { this.t_date = t_date; }
}
