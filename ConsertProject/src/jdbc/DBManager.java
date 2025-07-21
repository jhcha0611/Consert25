package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DBManager {

	static Connection cn;
	static Statement st;

	public DBManager() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cn = DriverManager.getConnection("jdbc:mysql://localhost/?allowLoadLocalInfile=true&serverTimezone=UTC",
					"root", "1234");
			st = cn.createStatement();

			st.executeUpdate("SET GLOBAL local_infile = true");
			st.executeUpdate("DROP DATABASE IF EXISTS `콘서트25`");
			st.executeUpdate("CREATE DATABASE `콘서트25`");
			st.executeUpdate("USE `콘서트25`");

			st.executeUpdate("DROP USER IF EXISTS `user`");
			st.executeUpdate("CREATE USER `user` IDENTIFIED BY '1234'");
			st.executeUpdate("GRANT INSERT, SELECT, DELETE, UPDATE ON `콘서트25`.* TO `user`");

			st.executeUpdate(
					"CREATE TABLE user (" + "u_no INT AUTO_INCREMENT PRIMARY KEY, " + "u_id VARCHAR(50) NOT NULL, "
							+ "u_pw VARCHAR(100) NOT NULL, " + "u_email VARCHAR(100), " + "u_name VARCHAR(50), "
							+ "u_birth DATE, " + "u_number VARCHAR(20), " + "u_gender ENUM('M', 'F')" + ")");

			st.executeUpdate("CREATE TABLE consert (" + "c_no INT AUTO_INCREMENT PRIMARY KEY, "
					+ "c_name VARCHAR(100) NOT NULL, " + "c_location VARCHAR(100), " + "c_period VARCHAR(50), "
					+ "c_age INT" + ")");


			st.executeUpdate("CREATE TABLE price (" + "p_no INT AUTO_INCREMENT PRIMARY KEY, " + "c_no INT, "
					+ "p_price INT, " + "FOREIGN KEY (c_no) REFERENCES consert(c_no) ON DELETE CASCADE" + ")");

			st.executeUpdate("CREATE TABLE time (" + "t_no INT AUTO_INCREMENT PRIMARY KEY, " + "c_no INT, "
					+ "t_date DATETIME, " + "FOREIGN KEY (c_no) REFERENCES consert(c_no) ON DELETE CASCADE" + ")");

			st.executeUpdate("CREATE TABLE history (" + "h_no INT AUTO_INCREMENT PRIMARY KEY, " + "u_no INT, "
					+ "c_no INT, " + "h_zone VARCHAR(50), " + "h_buy INT, "
					+ "h_date DATETIME DEFAULT CURRENT_TIMESTAMP, " + "h_situ VARCHAR(50), "
					+ "h_reservation CHAR(8) NOT NULL," + "FOREIGN KEY (u_no) REFERENCES user(u_no) ON DELETE CASCADE, "
					+ "FOREIGN KEY (c_no) REFERENCES consert(c_no) ON DELETE CASCADE" + ")");

			st.executeUpdate("CREATE TABLE review (" + "r_no INT AUTO_INCREMENT PRIMARY KEY, " + "u_no INT, "
					+ "c_no INT, " + "h_no INT, " + "r_con TEXT, " + "r_rating INT, "
					+ "r_date DATETIME DEFAULT CURRENT_TIMESTAMP, " + "`likes` TINYINT(1) DEFAULT 0, "
					+ "FOREIGN KEY (u_no) REFERENCES user(u_no) ON DELETE CASCADE, "
					+ "FOREIGN KEY (c_no) REFERENCES consert(c_no) ON DELETE CASCADE, "
					+ "FOREIGN KEY (h_no) REFERENCES history(h_no) ON DELETE CASCADE" + ")");

			st.executeUpdate("CREATE TABLE interest (" + "u_no INT, " + "c_no INT, " + "r_no INT, "
					+ "PRIMARY KEY (u_no, c_no), " + "FOREIGN KEY (u_no) REFERENCES user(u_no) ON DELETE CASCADE, "
					+ "FOREIGN KEY (c_no) REFERENCES consert(c_no) ON DELETE CASCADE, "
					+ "FOREIGN KEY (r_no) REFERENCES review(r_no) ON DELETE SET NULL" + ")");

			st.executeUpdate("CREATE TABLE payment (pay_no INT AUTO_INCREMENT PRIMARY KEY," + "u_no INT," + "c_no INT,"
					+ "h_no INT," + "pay_amount INT," + "pay_card VARCHAR(50), "
					+ "pay_date DATETIME DEFAULT CURRENT_TIMESTAMP," + "pay_name VARCHAR(50),"
					+ "pay_phone VARCHAR(20)," + "pay_address TEXT,"
					+ "FOREIGN KEY (u_no) REFERENCES user(u_no) ON DELETE CASCADE,"
					+ "FOREIGN KEY (c_no) REFERENCES consert(c_no) ON DELETE CASCADE,"
					+ "FOREIGN KEY (h_no) REFERENCES history(h_no) ON DELETE CASCADE" + ")");

			st.executeUpdate("CREATE TABLE zone (" + "z_no INT AUTO_INCREMENT PRIMARY KEY, " + "z_name VARCHAR(50), "
					+ "z_price INT" + ")");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection conn = null;

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/콘서트25?serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false";
			String user = "root";
			String password = "1234";
			conn = DriverManager.getConnection(url, user, password);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}
	   public static ResultSet getResultSet(String sql) throws Exception {
		      return st.executeQuery(sql);
		   }

		   public static PreparedStatement getPreparedStatement(String sql) throws Exception {
		      return cn.prepareStatement(sql);
		   }

		   public static void updateQ(String sql) throws Exception {
		      st.executeUpdate(sql);
		   }

	public static void main(String[] args) {
		new DBManager();
	}
}
