package dto;

public class ConsertDTO {
	private int c_no;
	private String c_name;
	private String c_location;
	private String c_period;
	private int c_age;

	// 기본 생성자
	public ConsertDTO() {
	}

	// 모든 필드 생성자
	public ConsertDTO(int c_no, String c_name, String c_location, String c_period, int c_age) {
		this.c_no = c_no;
		this.c_name = c_name;
		this.c_location = c_location;
		this.c_period = c_period;
		this.c_age = c_age;
	}

	// getter / setter
	public int getC_no() {
		return c_no;
	}

	public void setC_no(int c_no) {
		this.c_no = c_no;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public String getC_location() {
		return c_location;
	}

	public void setC_location(String c_location) {
		this.c_location = c_location;
	}

	public String getC_period() {
		return c_period;
	}

	public void setC_period(String c_period) {
		this.c_period = c_period;
	}

	public int getC_age() {
		return c_age;
	}

	public void setC_age(int c_age) {
		this.c_age = c_age;
	}
}
