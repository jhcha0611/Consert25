package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.PriceDTO;
import jdbc.DBManager;

public class PriceDAO {
    public List<PriceDTO> getPricesByConsert(int c_no) {
        List<PriceDTO> priceList = new ArrayList<>();
        String sql = "SELECT * FROM price WHERE c_no = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c_no);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PriceDTO dto = new PriceDTO(
                        rs.getInt("p_no"),
                        rs.getInt("c_no"),
                        rs.getInt("p_price")
                    );
                    priceList.add(dto);
                }
            }
        } catch(Exception e) { e.printStackTrace(); }
        return priceList;
    }
}
