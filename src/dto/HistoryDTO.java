package dto;

import java.time.LocalDate;

public class HistoryDTO {
    private int h_no;        // History 번호 (Primary Key)
    private int u_no;        // 사용자 번호 (Foreign Key)
    private int c_no;        // 콘서트 번호 (Foreign Key)
    private String h_zone;   // 좌석 구역
    private int h_buy;       // 구매 수량
    private LocalDate h_date; // 구매 날짜
    private String h_situ;   // 예매 상태
    private String h_reservation; //예약번호

    // Getter와 Setter 메서드

    public int getH_no() {
        return h_no;
    }

    public void setH_no(int h_no) {
        this.h_no = h_no;
    }

    public int getU_no() {
        return u_no;
    }

    public void setU_no(int u_no) {
        this.u_no = u_no;
    }

    public int getC_no() {
        return c_no;
    }

    public void setC_no(int c_no) {
        this.c_no = c_no;
    }

    public String getH_zone() {
        return h_zone;
    }

    public void setH_zone(String h_zone) {
        this.h_zone = h_zone;
    }

    public int getH_buy() {
        return h_buy;
    }

    public void setH_buy(int h_buy) {
        this.h_buy = h_buy;
    }

    public LocalDate getH_date() {
        return h_date;
    }

    public void setH_date(LocalDate h_date) {
        this.h_date = h_date;
    }

    public String getH_situ() {
        return h_situ;
    }

    public void setH_situ(String h_situ) {
        this.h_situ = h_situ;
    }
//    public String getH_reservation() {
//        return h_reservation;
//    }
//    public void setH_reservation(String h_reservation) {
//        this.h_reservation = h_reservation;
//    }
}
