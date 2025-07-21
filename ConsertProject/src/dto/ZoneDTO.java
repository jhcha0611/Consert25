package dto;

public class ZoneDTO {
    private int z_no;       // Zone 번호 (PK)
    private int c_no;       // Concert 번호 (FK)
    private String z_name;  // Zone 이름
    private int z_price;    // Zone 가격

    // 기본 생성자
    public ZoneDTO() {
    }

    // 모든 필드를 초기화하는 생성자
    public ZoneDTO(int z_no, String z_name, int z_price) {
        this.z_no = z_no;
        this.z_name = z_name;
        this.z_price = z_price;
    }

    // Getter와 Setter 메소드
    public int getZ_no() {
        return z_no;
    }

    public void setZ_no(int z_no) {
        this.z_no = z_no;
    }

    public int getC_no() {
        return c_no;
    }

    public String getZ_name() {
        return z_name;
    }

    public void setZ_name(String z_name) {
        this.z_name = z_name;
    }

    public int getZ_price() {
        return z_price;
    }

    public void setZ_price(int z_price) {
        this.z_price = z_price;
    }

    // 객체를 문자열로 표현하는 toString 메소드
    @Override
    public String toString() {
        return "ZoneDTO [z_no=" + z_no + ", z_name=" + z_name + ", z_price=" + z_price + "]";
    }
}
