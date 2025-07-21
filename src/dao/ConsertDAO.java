package dao;

import java.sql.*;
import java.util.*;
import dto.ConsertDTO;
import jdbc.DBManager;

public class ConsertDAO {
	
    private Connection conn;
	
	public ConsertDAO(Connection conn) {
	    this.conn = conn;
	}

	// 콘서트 전체 리스트 불러오기
	public List<ConsertDTO> getAllConserts() {
		List<ConsertDTO> list = new ArrayList<>();
		String sql = "SELECT * FROM consert";
		try (Connection conn = DBManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				ConsertDTO dto = new ConsertDTO(rs.getInt("c_no"), rs.getString("c_name"), rs.getString("c_location"),
						rs.getString("c_period"), rs.getInt("c_age"));
				list.add(dto);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ConsertDTO getConsert(int c_no) {
		ConsertDTO dto = null;
		String sql = "SELECT * FROM consert WHERE c_no = ?";
		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, c_no);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					dto = new ConsertDTO(rs.getInt("c_no"), rs.getString("c_name"), rs.getString("c_location"),
							rs.getString("c_period"), rs.getInt("c_age"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	// 콘서트 등록
	public int insertConsert(ConsertDTO dto) {
		String sql = "INSERT INTO consert(c_name, c_location, c_period, c_age) VALUES (?, ?, ?, ?)";
		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, dto.getC_name());
			ps.setString(2, dto.getC_location());
			ps.setString(3, dto.getC_period());
			ps.setInt(4, dto.getC_age());
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
