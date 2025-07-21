package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dto.CashReceiptDTO;
import jdbc.DBManager;

public class CashReceiptDAO {
	private Connection conn;

	public CashReceiptDAO() {
		conn = DBManager.getConnection();
	}

	// 발행 완료 내역 가져오기
	public List<CashReceiptDTO> getIssuedReceipts() throws SQLException {
		List<CashReceiptDTO> receipts = new ArrayList<>();
		String sql = "SELECT h.h_date AS reservation_date, h.h_no AS reservation_number, c.c_name AS product_name, p.pay_amount AS issued_amount, p.pay_amount * 0.1 AS fee_amount FROM history h JOIN payment p ON h.h_no = p.h_no JOIN consert c ON h.c_no = c.c_no WHERE p.receipt_status = '발행완료';";

		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				receipts.add(new CashReceiptDTO(rs.getTimestamp("reservation_date"), rs.getInt("reservation_number"),
						rs.getString("product_name"), rs.getDouble("issued_amount"), rs.getDouble("fee_amount")));
			}
		}
		return receipts;
	}
}
