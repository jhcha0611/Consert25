package dto;

public class PriceDTO {
    private int p_no;
    private int c_no;
    private int p_price;

    public PriceDTO() {}

    public PriceDTO(int p_no, int c_no, int p_price) {
        this.p_no = p_no;
        this.c_no = c_no;
        this.p_price = p_price;
    }

    public int getP_no() {
        return p_no;
    }
    public void setP_no(int p_no) {
        this.p_no = p_no;
    }
    public int getC_no() {
        return c_no;
    }
    public void setC_no(int c_no) {
        this.c_no = c_no;
    }
    public int getP_price() {
        return p_price;
    }
    public void setP_price(int p_price) {
        this.p_price = p_price;
    }
}
