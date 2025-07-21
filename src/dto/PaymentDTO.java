package dto;

import java.sql.Timestamp;

public class PaymentDTO {
    private int pay_no;
    private int u_no;
    private int c_no;
    private int h_no;
    private int pay_amount;
    private String pay_card;
    private Timestamp pay_date;
    private String pay_name;
    private String pay_phone;
    private String pay_address;

    public int getPay_no() {
        return pay_no;
    }

    public void setPay_no(int pay_no) {
        this.pay_no = pay_no;
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

    public int getH_no() {
        return h_no;
    }

    public void setH_no(int h_no) {
        this.h_no = h_no;
    }

    public int getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(int pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getPay_card() {
        return pay_card;
    }

    public void setPay_card(String pay_card) {
        this.pay_card = pay_card;
    }

    public Timestamp getPay_date() {
        return pay_date;
    }

    public void setPay_date(Timestamp pay_date) {
        this.pay_date = pay_date;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getPay_phone() {
        return pay_phone;
    }

    public void setPay_phone(String pay_phone) {
        this.pay_phone = pay_phone;
    }

    public String getPay_address() {
        return pay_address;
    }

    public void setPay_address(String pay_address) {
        this.pay_address = pay_address;
    }
}
