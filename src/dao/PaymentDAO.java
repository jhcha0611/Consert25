package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.DBManager;
import dto.PaymentDTO;

public class PaymentDAO {
	public boolean insertPayment(PaymentDTO dto) {
		String sql = "INSERT INTO payment (u_no, c_no, h_no, pay_amount, pay_card, pay_name, pay_phone, pay_address) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, dto.getU_no());
			pstmt.setInt(2, dto.getC_no());
			pstmt.setInt(3, dto.getH_no());
			pstmt.setInt(4, dto.getPay_amount());
			pstmt.setString(5, dto.getPay_card());
			pstmt.setString(6, dto.getPay_name());
			pstmt.setString(7, dto.getPay_phone());
			pstmt.setString(8, dto.getPay_address());

			int result = pstmt.executeUpdate();
			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 결제정보 저장
	public boolean addPayment(PaymentDTO dto) {
	    String sql = "INSERT INTO payment " +
	        "(u_no, c_no, h_no, pay_amount, pay_card, pay_date, pay_name, pay_phone, pay_address) " +
	        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    try (
	    		Connection conn = DBManager.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	    ) {
	        pstmt.setInt(1, dto.getU_no());
	        pstmt.setInt(2, dto.getC_no());
	        pstmt.setInt(3, dto.getH_no());
	        pstmt.setInt(4, dto.getPay_amount());
	        pstmt.setString(5, dto.getPay_card());
	        pstmt.setTimestamp(6, dto.getPay_date());
	        pstmt.setString(7, dto.getPay_name());
	        pstmt.setString(8, dto.getPay_phone());
	        pstmt.setString(9, dto.getPay_address());

	        int result = pstmt.executeUpdate();
	        return result > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

}
