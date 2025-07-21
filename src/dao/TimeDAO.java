package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.TimeDTO;
import jdbc.DBManager;

public class TimeDAO {
    public List<TimeDTO> getTimesByConsert(int c_no) {
        List<TimeDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM time WHERE c_no = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c_no);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TimeDTO dto = new TimeDTO(
                        rs.getInt("t_no"),
                        rs.getInt("c_no"),
                        rs.getTimestamp("t_date")
                    );
                    list.add(dto);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}

