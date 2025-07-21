package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import dto.ZoneDTO;

public class ZoneDAO {

    private Connection conn;

    public ZoneDAO(Connection conn) {
        this.conn = conn;
    }

    // 1. Zone 추가
    public int addZone(ZoneDTO zone) {
        int result = 0;
        String sql = "INSERT INTO zone (z_name, z_price) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, zone.getZ_name());
            pstmt.setInt(2, zone.getZ_price());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 2. 모든 Zone 목록 조회
    public List<ZoneDTO> getAllZones() {
        List<ZoneDTO> zones = new ArrayList<>();
        String sql = "SELECT * FROM zone";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ZoneDTO zone = new ZoneDTO(
                    rs.getInt("z_no"),
                    rs.getString("z_name"),
                    rs.getInt("z_price")
                );
                zones.add(zone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zones;
    }

    // 3. Zone 수정
    public int updateZone(ZoneDTO zone) {
        int result = 0;
        String sql = "UPDATE zone SET z_name = ?, z_price = ? WHERE z_no = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, zone.getZ_name());
            pstmt.setInt(2, zone.getZ_price());
            pstmt.setInt(3, zone.getZ_no());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 4. Zone 삭제
    public int deleteZone(int z_no) {
        int result = 0;
        String sql = "DELETE FROM zone WHERE z_no = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, z_no);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 5. Zone 조회 (z_no로)
    public ZoneDTO getZoneById(int z_no) {
        ZoneDTO zone = null;
        String sql = "SELECT * FROM zone WHERE z_no = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, z_no);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                zone = new ZoneDTO(
                    rs.getInt("z_no"),
                    rs.getString("z_name"),
                    rs.getInt("z_price")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zone;
    }
}
