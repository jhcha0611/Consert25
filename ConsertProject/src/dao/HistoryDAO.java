package dao;

import dto.HistoryDTO;
import jdbc.DBManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HistoryDAO {
    private Connection conn;

    public HistoryDAO(Connection conn) {
        this.conn = conn;
    }

    // 예매 데이터 삽입
    public boolean bookSeat(HistoryDTO dto) {
        String sql = "INSERT INTO history (u_no, c_no, h_zone, h_date, h_buy, h_situ, h_reservation) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	Random rand = new Random();
        	String reservationNo = String.format("%08d", rand.nextInt(100_000_000));
            pstmt.setInt(1, dto.getU_no());
            pstmt.setInt(2, dto.getC_no());
            pstmt.setString(3, dto.getH_zone());
            pstmt.setDate(4, java.sql.Date.valueOf(dto.getH_date())); // LocalDate → SQL Date 변환
            pstmt.setInt(5, dto.getH_buy());
            pstmt.setString(6, dto.getH_situ());
			pstmt.setString(7, reservationNo);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 사용자별 예매 기록 조회
    public List<HistoryDTO> getUserHistory(int u_no) {
        List<HistoryDTO> historyList = new ArrayList<>();
        String sql = "SELECT * FROM history WHERE u_no = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, u_no);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                HistoryDTO history = new HistoryDTO();
                history.setH_no(rs.getInt("h_no"));
                history.setU_no(rs.getInt("u_no"));
                history.setC_no(rs.getInt("c_no"));
                history.setH_zone(rs.getString("h_zone"));
                history.setH_buy(rs.getInt("h_buy"));
                history.setH_date(rs.getDate("h_date").toLocalDate()); // SQL Date → LocalDate 변환
                history.setH_situ(rs.getString("h_situ"));
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }

    // 특정 시간과 구역으로 좌석 예약 여부 확인
    public boolean isSeatBooked(int c_no, String h_zone, LocalDate h_date) {
        String sql = "SELECT COUNT(*) FROM history WHERE c_no=? AND h_zone=? AND h_date=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, c_no);
            pstmt.setString(2, h_zone);
            pstmt.setDate(3, java.sql.Date.valueOf(h_date));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 예매 취소
    public boolean cancelHistory(int h_no) {
        String sql = "UPDATE history SET h_situ = '취소' WHERE h_no = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, h_no);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
