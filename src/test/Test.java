package test;

import dao.*;
import dto.*;
import jdbc.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Test {
	
	int consertInt;
	
	public static void main(String[] args) {
		new DBManager();
		
		Connection conn = DBManager.getConnection();
		Scanner sc = new Scanner(System.in);

		try {
			if (conn == null) {
				System.out.println("데이터베이스 연결 실패!");
				return;
			}

			System.out.println("=== 사용자 데이터 초기화 ===");
			initializeUsers(conn); // 사용자 데이터 삽입
			initializeonConsert(conn);// 콘서트 데이터 삽입
			initializeonZone(conn); // 좌석 데이터 삽입
			
			System.out.println("=== 콘서트 예매 프로그램 ===");

			// 1. 공연 정보 출력
			ConsertDAO consertDAO = new ConsertDAO(conn);
			List<ConsertDTO> concerts = consertDAO.getAllConserts();

			if (concerts.isEmpty()) {
				System.out.println("등록된 공연이 없습니다.");
				return;
			}

			System.out.println("공연 목록:");
			for (ConsertDTO concert : concerts) {
				System.out.println(concert.getC_no() + ": " + concert.getC_name() + " - " + concert.getC_location()
						+ " (" + concert.getC_period() + ")");
			}

			System.out.print("공연 번호를 선택하세요: ");
			int concertNo = sc.nextInt();

			// 2. 좌석 정보 출력
			ZoneDAO zoneDAO = new ZoneDAO(conn);
			List<ZoneDTO> zones = zoneDAO.getAllZones();

			if (zones.isEmpty()) {
				System.out.println("해당 공연의 좌석 정보가 없습니다.");
				return;
			}

			System.out.println("좌석 목록:");
			for (ZoneDTO zone : zones) {
				System.out.println(zone.getZ_no() + ": " + zone.getZ_name() + " - 가격: " + zone.getZ_price());
			}

			System.out.print("좌석 번호를 선택하세요: ");
			int zoneNo = sc.nextInt();

			ZoneDTO selectedZone = zones.stream().filter(zone -> zone.getZ_no() == zoneNo).findFirst().orElse(null);

			if (selectedZone == null) {
				System.out.println("잘못된 좌석 번호입니다.");
				return;
			}

			// 3. 결제 및 좌석 예매
			LocalDate today = LocalDate.now();
			System.out.println("=== 결제 정보 ===");
			int totalPrice = selectedZone.getZ_price() + 4000;

			System.out.println("좌석 구역: " + selectedZone.getZ_name());
			System.out.println("구매 좌석 수: " + 1);
			System.out.println("총 결제 금액: " + totalPrice + "원");

			System.out.print("결제를 진행하시겠습니까? (1: 예, 0: 아니오): ");
			int confirm = sc.nextInt();

			if (confirm == 1) {
				// 예매 정보 저장
				HistoryDAO historyDAO = new HistoryDAO(conn);
				HistoryDTO history = new HistoryDTO();
				history.setU_no(1); // 새로운 사용자 번호
				history.setC_no(concertNo);
				history.setH_zone(selectedZone.getZ_name());
				history.setH_buy(1);
				history.setH_date(today);
				history.setH_situ("예매완료");

				boolean success = historyDAO.bookSeat(history);
				if (success) {
					System.out.println("결제가 완료되었습니다! 예매 성공.");
				} else {
					System.out.println("결제가 실패했습니다. 관리자에게 문의하세요.");
				}
			} else {
				System.out.println("결제를 취소하였습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ignore) {
			}
			sc.close();
			System.out.println("프로그램 종료!");
		}
	}

	private static void initializeUsers(Connection conn) {
		try {
			String sql = "INSERT INTO user (u_no, u_id, u_pw, u_email, u_name, u_birth, u_number, u_gender) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, 0);
			pstmt.setString(2, "test_user");
			pstmt.setString(3, "password");
			pstmt.setString(4, "test@example.com");
			pstmt.setString(5, "Test User");
			pstmt.setString(6, "1990-01-01");
			pstmt.setString(7, "010-1234-5678");
			pstmt.setString(8, "M");
			pstmt.executeUpdate();

			System.out.println("사용자 데이터 삽입 완료!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("사용자 데이터 삽입 실패!");
		}
	}

	private static void initializeonConsert(Connection conn) {

		try {

			String insertConcert = "INSERT INTO consert (c_no, c_name, c_location, c_period, c_age) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement ps = conn.prepareStatement(insertConcert);

			ps.setInt(1, 0);
			ps.setString(2, "여름 콘서트");
			ps.setString(3, "서울 예술의 전당");
			ps.setString(4, "2025-08-01 ~ 2025-08-03");
			ps.setInt(5, 12);
			ps.executeUpdate();
			System.out.println("✅ 콘서트 삽입 완료");
		} catch (Exception e) {
			e.addSuppressed(e);
		}
	}
	private static void initializeonZone(Connection conn) {

		try {

			String zone = "INSERT INTO zone (z_name, z_price) VALUES " +
                    "('S1', 185000), " +
                    "('S2', 185000), " +
                    "('S3', 300000), " +
                    "('S4', 110000), " +
                    "('S5', 123456)";

			PreparedStatement ps = conn.prepareStatement(zone);
			ps.executeUpdate(zone);
			System.out.println("✅ 콘서트 삽입 완료");
		} catch (Exception e) {
			e.addSuppressed(e);
		}
	}
}
