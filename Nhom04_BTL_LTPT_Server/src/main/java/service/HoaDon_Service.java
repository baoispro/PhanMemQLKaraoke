package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.HoaDon_DAO;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.NhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;

public class HoaDon_Service extends UnicastRemoteObject implements HoaDon_DAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9118170583429901979L;
	private EntityManager entityManager;

	public HoaDon_Service(EntityManager entityManager) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

	public ArrayList<HoaDon> getALLDanhSachHoaDon() throws RemoteException {
		ArrayList<HoaDon> dshd = new ArrayList<HoaDon>();
		try {
			String jpql = "SELECT hd FROM HoaDon hd";
			TypedQuery<HoaDon> query = entityManager.createQuery(jpql, HoaDon.class);
			List<HoaDon> resultList = query.getResultList();
			dshd.addAll(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	public HoaDon getHoaDonTheoMa(String maHD) throws RemoteException {
		HoaDon hd = null;
		try {
			String jpql = "SELECT hd FROM HoaDon hd WHERE hd.maHoaDon = :maHD";
			TypedQuery<HoaDon> query = entityManager.createQuery(jpql, HoaDon.class);
			query.setParameter("maHD", maHD);
			hd = query.getSingleResult();
		} catch (NoResultException e) {
			// Không tìm thấy hóa đơn với mã tương ứng
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hd;
	}

	public ArrayList<HoaDon> getHoaDonTheoTenKH(String tenKH) throws RemoteException {
		List<HoaDon> dshd = new ArrayList<HoaDon>();
		try {
			String jpql = "SELECT hd FROM HoaDon hd WHERE hd.tenKH LIKE :tenKH";
			TypedQuery<HoaDon> query = entityManager.createQuery(jpql, HoaDon.class);
			query.setParameter("tenKH", "%" + tenKH + "%");
			dshd = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ArrayList<HoaDon>) dshd;
	}

	public ArrayList<HoaDon> getHoaDonTheoTenNV(String tenNV) throws RemoteException {
		ArrayList<HoaDon> dshd = new ArrayList<HoaDon>();
		try {
			String jpql = "SELECT hd FROM HoaDon hd WHERE hd.tenNV LIKE :tenNV";
			TypedQuery<HoaDon> query = entityManager.createQuery(jpql, HoaDon.class);
			query.setParameter("tenNV", "%" + tenNV + "%");
			dshd.addAll(query.getResultList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	public ArrayList<HoaDon> getHoaDonTheoNgayLapHD(Date ngayLap) throws RemoteException {
		ArrayList<HoaDon> dshd = new ArrayList<HoaDon>();
		try {
			String jpql = "SELECT hd FROM HoaDon hd WHERE hd.ngayLapHD = :ngayLap";
			TypedQuery<HoaDon> query = entityManager.createQuery(jpql, HoaDon.class);
			query.setParameter("ngayLap", ngayLap);
			dshd.addAll(query.getResultList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dshd;
	}

	public boolean create(HoaDon hd) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Thực hiện gọi stored procedure để phát sinh ID
			StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("phatSinhIDHD");
			storedProcedure.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
			storedProcedure.execute();
			String maHD = (String) storedProcedure.getOutputParameterValue(1);

			hd.setMaHoaDon(maHD);
			entityManager.persist(hd);

			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public String taoHoaDonMoi(HoaDon hd) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		String maHD = null;
		try {
			transaction.begin();

			// Thực hiện gọi stored procedure để phát sinh ID
			StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("phatSinhIDHD");
			storedProcedure.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
			storedProcedure.execute();
			maHD = (String) storedProcedure.getOutputParameterValue(1);

			hd.setMaHoaDon(maHD);
			entityManager.persist(hd);

			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			maHD = null;
		}
		return maHD;
	}

//	public ArrayList<HoaDon> getALLDanhSachHoaDon() {
//		ArrayList<HoaDon> dshd = new ArrayList<HoaDon>();
//		try {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			String sql = "Select * from hoaDon";
//			Statement statement = con.createStatement();
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery(sql);
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maHD = rs.getString(1);
//				NhanVien nv = new NhanVien(rs.getString(2));
//				String tenKH = rs.getString(3);
//				String tenNV = rs.getString(4);
//				KhachHang kh = new KhachHang(rs.getString(5));
//				Date ngayLapHoaDon = rs.getDate(6);
//				LocalTime thoiGianSuDung;
//				if (rs.getTime(7) == null)
//					thoiGianSuDung = null;
//				else
//					thoiGianSuDung = rs.getTime(7).toLocalTime();
//				KhuyenMai km = new KhuyenMai(rs.getString(8));
//				HoaDon hd = new HoaDon(maHD, nv, tenNV, tenKH, kh, ngayLapHoaDon, thoiGianSuDung, km);
//				dshd.add(hd);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dshd;
//	}
//
//	public HoaDon getHoaDonTheoMa(String maHD) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		HoaDon hd = null;
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from hoaDon where maHD=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maHD);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String ma = rs.getString(1);
//				NhanVien nv = new NhanVien(rs.getString(2));
//				String tenKH = rs.getString(3);
//				String tenNV = rs.getString(4);
//				KhachHang kh = new KhachHang(rs.getString(5));
//				Date ngayLapHoaDon = rs.getDate(6);
//				LocalTime thoiGianSuDung = rs.getTime(7).toLocalTime();
//				KhuyenMai km = new KhuyenMai(rs.getString(8));
//
//				hd = new HoaDon(maHD, nv, tenNV, tenKH, kh, ngayLapHoaDon, thoiGianSuDung, km);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return hd;
//	}
//
//	public ArrayList<HoaDon> getHoaDonTheoTenKH(String tenKH) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<HoaDon> dshd = new ArrayList<HoaDon>();
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from hoaDon where tenKH like '%'+?+'%' ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, tenKH);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String ma = rs.getString(1);
//				NhanVien nv = new NhanVien(rs.getString(2));
//				String tenkhach = rs.getString(3);
//				String tenNV = rs.getString(4);
//				KhachHang kh = new KhachHang(rs.getString(5));
//				Date ngayLapHoaDon = rs.getDate(6);
//				LocalTime thoiGianSuDung;
//				if (rs.getTime(7) == null)
//					thoiGianSuDung = null;
//				else
//					thoiGianSuDung = rs.getTime(7).toLocalTime();
//				KhuyenMai km = new KhuyenMai(rs.getString(8));
//
//				HoaDon hd = new HoaDon(ma, nv, tenNV, tenkhach, kh, ngayLapHoaDon, thoiGianSuDung, km);
//				dshd.add(hd);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dshd;
//	}
//
//	public ArrayList<HoaDon> getHoaDonTheoTenNV(String tenNV) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<HoaDon> dshd = new ArrayList<HoaDon>();
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from hoaDon where tenNV like '%'+?+'%' ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, tenNV);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String ma = rs.getString(1);
//				NhanVien nv = new NhanVien(rs.getString(2));
//				String tenkhach = rs.getString(3);
//				String tenNhanVien = rs.getString(4);
//				KhachHang kh = new KhachHang(rs.getString(5));
//				Date ngayLapHoaDon = rs.getDate(6);
//				LocalTime thoiGianSuDung;
//				if (rs.getTime(7) == null)
//					thoiGianSuDung = null;
//				else
//					thoiGianSuDung = rs.getTime(7).toLocalTime();
//				KhuyenMai km = new KhuyenMai(rs.getString(8));
//				HoaDon hd = new HoaDon(ma, nv, tenNhanVien, tenkhach, kh, ngayLapHoaDon, thoiGianSuDung, km);
//				dshd.add(hd);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dshd;
//	}
//
//	public ArrayList<HoaDon> getHoaDonTheoNgayLapHD(Date ngayLap) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<HoaDon> dshd = new ArrayList<HoaDon>();
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from hoaDon where ngayLapHD= ?";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngayLap.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String ma = rs.getString(1);
//				NhanVien nv = new NhanVien(rs.getString(2));
//				String tenkhach = rs.getString(3);
//				String tenNhanVien = rs.getString(4);
//				KhachHang kh = new KhachHang(rs.getString(5));
//				Date ngayLapHoaDon = rs.getDate(6);
//				LocalTime thoiGianSuDung;
//				if (rs.getTime(7) == null)
//					thoiGianSuDung = null;
//				else
//					thoiGianSuDung = rs.getTime(7).toLocalTime();
//				KhuyenMai km = new KhuyenMai(rs.getString(8));
//				HoaDon hd = new HoaDon(ma, nv, tenNhanVien, tenkhach, kh, ngayLapHoaDon, thoiGianSuDung, km);
//				dshd.add(hd);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dshd;
//	}
//
//	public boolean create(HoaDon hd) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			// stored procedures tự động phát sinh
//			CallableStatement myCall = con.prepareCall("{call phatSinhIDHD(?)}");
//			myCall.registerOutParameter(1, Types.VARCHAR);
//			myCall.execute();
//			String maHD = myCall.getString(1);
//
//			stmt = con.prepareStatement("insert into " + "HoaDon values(?,?,?,?,?,?,?,?)");
//			stmt.setString(1, maHD);
//			stmt.setString(2, hd.getNv().getMaNV());
//			stmt.setString(3, hd.getTenKH());
//			stmt.setString(4, hd.getTenNV());
//			stmt.setString(5, hd.getKh().getMaKH());
//			stmt.setDate(6, new java.sql.Date(hd.getNgayLapHD().getTime()));
//			stmt.setTime(7, Time.valueOf(hd.getThoiGianTraPhong()));
//			if (hd.getKm() == null)
//				stmt.setNull(8, Types.NVARCHAR);
//			else
//				stmt.setString(8, hd.getKm().getMaKM());
//
//			n = stmt.executeUpdate();
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			try {
//				stmt.close();
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//		return n > 0;
//	}
//
//	public String taoHoaDonMoi(HoaDon hd) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		String maHD = null;
//		try {
//			// stored procedures tự động phát sinh
//			CallableStatement myCall = con.prepareCall("{call phatSinhIDHD(?)}");
//			myCall.registerOutParameter(1, Types.VARCHAR);
//			myCall.execute();
//			maHD = myCall.getString(1);
//
//			stmt = con.prepareStatement("insert into " + "HoaDon values(?,?,?,?,?,?,?,?)");
//			stmt.setString(1, maHD);
//			stmt.setString(2, hd.getNv().getMaNV());
//			stmt.setString(3, hd.getTenKH());
//			stmt.setString(4, hd.getTenNV());
//			stmt.setString(5, hd.getKh().getMaKH());
//			stmt.setDate(6, new java.sql.Date(hd.getNgayLapHD().getTime()));
//			stmt.setTime(7, Time.valueOf(hd.getThoiGianTraPhong()));
//			if (hd.getKm() == null)
//				stmt.setNull(8, Types.NVARCHAR);
//			else
//				stmt.setString(8, hd.getKm().getMaKM());
//
//			int n = stmt.executeUpdate();
//			if (n <= 0) {
//				maHD = null; // Nếu không thành công, trả về null
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			maHD = null;
//		} finally {
//			try {
//				stmt.close();
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//		return maHD;
//	}
//
//	// Khôi thêm
//		public String[] getAllThangLapHD() {
//			String[] list = new String[12 * getAllNamLapHD().length];
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			PreparedStatement statement = null;
//
//			try {
//				String sql = "SELECT DISTINCT \r\n"
//						+ "CONCAT(MONTH(NgayLapHD), '/', YEAR(NgayLapHD)) AS ThangNam , MONTH(NgayLapHD), YEAR(NgayLapHD)\r\n"
//						+ "FROM HoaDon \r\n" + "ORDER BY YEAR(NgayLapHD) ASC, MONTH(NgayLapHD) ASC";
//				statement = con.prepareStatement(sql);
//				// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//				ResultSet rs = statement.executeQuery();
//				// Duyệt kết quả trả về
//				int i = 0;
//				while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//					list[i] = rs.getString("ThangNam");
//					i++;
//				}
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			return list;
//		}
//
//		public int[] getAllNamLapHD() {
//			int[] list = new int[5]; // Khoảng 5 năm
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			PreparedStatement statement = null;
//
//			try {
//				String sql = "SELECT DISTINCT\r\n" + "    YEAR(NgayLapHD) AS Thang\r\n" + "FROM\r\n" + "    HoaDon\r\n"
//						+ "ORDER BY\r\n" + "    Thang;";
//				statement = con.prepareStatement(sql);
//				// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//				ResultSet rs = statement.executeQuery();
//				// Duyệt kết quả trả về
//				int i = 0;
//				while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//					list[i] = rs.getInt(1);
//					i++;
//				}
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			return list;
//		}
//
//		public class HoaDonThongKe {
//			private HoaDon hoaDon;
//			private double tongTienPhong;
//			private double tongTienDV;
//
//			public double getTongTienPhong() {
//				return tongTienPhong;
//			}
//
//			public double getTongTienDV() {
//				return tongTienDV;
//			}
//
//			public HoaDon getHoaDon() {
//				return hoaDon;
//			}
//
//			public HoaDonThongKe(HoaDon hoaDon, double tongTienPhong, double tongTienDV) {
//				super();
//				this.hoaDon = hoaDon;
//				this.tongTienPhong = tongTienPhong;
//				this.tongTienDV = tongTienDV;
//			}
//		}
//
//		public ArrayList<HoaDonThongKe> thongKeDoanhThuTheoNgay(Date ngay) {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			ArrayList<HoaDonThongKe> dsHDTK = new ArrayList<HoaDonThongKe>();
//			PreparedStatement statement = null;
//			ResultSet rs;
//
//			try {
//				String sql = "SELECT\r\n" + "    HD.MaHD,\r\n" + "    HD.MaNV,\r\n" + "    HD.TenKH,\r\n"
//						+ "    HD.TenNV,\r\n" + "    HD.MaKH,\r\n" + "    HD.NgayLapHD,\r\n"
//						+ "    HD.Thoigiantraphong,\r\n" + "	HD.MaKM,\r\n"
//						+ "    SUM((COALESCE(CTDV.SoLuong * CTDV.DonGia, 0) - COALESCE(CTDV.SoLuong * CTDV.DonGia * KM.GiaTriKM, 0)) * COALESCE(KM.GiaTriKM, 1)) AS TongTienDV,\r\n"
//						+ "    SUM((COALESCE(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia, 0) - COALESCE(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia * KM.GiaTriKM, 0)) * COALESCE(KM.GiaTriKM, 1)) AS TongTienSDPhong\r\n"
//						+ "FROM\r\n" + "    HoaDon HD\r\n" + "LEFT JOIN\r\n"
//						+ "    KhuyenMai KM ON KM.MaKM = HD.MaKM AND HD.MaKM IS NOT NULL\r\n" + "LEFT JOIN\r\n"
//						+ "    ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD\r\n" + "LEFT JOIN\r\n"
//						+ "    ChiTietSuDungDichVuHoaDon CTDV ON HD.MaHD = CTDV.MaHD\r\n" + "WHERE\r\n"
//						+ "    NgayLapHD = ?\r\n" + "GROUP BY\r\n" + "    HD.MaHD,\r\n" + "    HD.MaNV,\r\n"
//						+ "    HD.TenKH,\r\n" + "    HD.TenNV,\r\n" + "    HD.MaKH,\r\n" + "    HD.NgayLapHD,\r\n"
//						+ "    HD.Thoigiantraphong,\r\n" + "    HD.MaKM,\r\n" + "    KM.GiaTriKM;\r\n" + "";
//				statement = con.prepareStatement(sql);
//				statement.setDate(1, new java.sql.Date(ngay.getTime()));
//				// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//				rs = statement.executeQuery();
//				// Duyệt kết quả trả về
//				while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//					String maHD = rs.getString(1);
//					NhanVien nv = new NhanVien(rs.getString(2));
//					String tenKH = rs.getString(3);
//					String tenNV = rs.getString(4);
//					KhachHang kh = new KhachHang(rs.getString(5));
//					Date ngayLapHD = rs.getDate(6);
//					LocalTime thoiGianTraPhong = rs.getTime(7).toLocalTime();
//					KhuyenMai km = new KhuyenMai(rs.getString(8));
//					double tongTienDV = rs.getDouble(9);
//					double tongTienPhong = rs.getDouble(10);
//
//					HoaDon hoaDon = new HoaDon(maHD, nv, tenNV, tenKH, kh, ngayLapHD, thoiGianTraPhong, km);
//					HoaDonThongKe hdtk = new HoaDonThongKe(hoaDon, tongTienDV, tongTienPhong);
//					dsHDTK.add(hdtk);
//				}
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			} finally {
//				// Đóng connection, statement và resultSet ở đây
//			}
//			return dsHDTK;
//		}
//
//		public ArrayList<HoaDonThongKe> thongKeDoanhThuTheoThang(int thang, int nam) {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			ArrayList<HoaDonThongKe> dsHDTK = new ArrayList<HoaDonThongKe>();
//			PreparedStatement statement = null;
//			ResultSet rs;
//
//			try {
//				String sql = "SELECT\r\n" + "    HD.MaHD,\r\n" + "    HD.MaNV,\r\n" + "    HD.TenKH,\r\n"
//						+ "    HD.TenNV,\r\n" + "	HD.MaKH,\r\n" + "    HD.NgayLapHD,\r\n" + "    HD.Thoigiantraphong,\r\n"
//						+ "	HD.MaKM,\r\n"
//						+ "    SUM((COALESCE(CTDV.SoLuong * CTDV.DonGia, 0) - COALESCE(CTDV.SoLuong * CTDV.DonGia * KM.GiaTriKM, 0)) * COALESCE(KM.GiaTriKM, 1)) AS TongTienDV,\r\n"
//						+ "    SUM((COALESCE(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia, 0) - COALESCE(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia * KM.GiaTriKM, 0)) * COALESCE(KM.GiaTriKM, 1)) AS TongTienSDPhong\r\n"
//						+ "FROM\r\n" + "    HoaDon HD\r\n" + "LEFT JOIN\r\n"
//						+ "    KhuyenMai KM ON KM.MaKM = HD.MaKM AND HD.MaKM IS NOT NULL\r\n" + "LEFT JOIN\r\n"
//						+ "    ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD\r\n" + "LEFT JOIN\r\n"
//						+ "    ChiTietSuDungDichVuHoaDon CTDV ON HD.MaHD = CTDV.MaHD\r\n" + "WHERE\r\n"
//						+ "    MONTH(NgayLapHD) = ? AND YEAR(NgayLapHD) = ?\r\n" + "GROUP BY\r\n" + "    HD.MaHD,\r\n"
//						+ "    HD.MaNV,\r\n" + "    HD.TenKH,\r\n" + "    HD.TenNV,\r\n" + "    HD.MaKH,\r\n"
//						+ "    HD.NgayLapHD,\r\n" + "    HD.Thoigiantraphong,\r\n" + "    HD.MaKM,\r\n"
//						+ "    KM.GiaTriKM;";
//				statement = con.prepareStatement(sql);
//				statement.setInt(1, thang);
//				statement.setInt(2, nam);
//				// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//				rs = statement.executeQuery();
//				// Duyệt kết quả trả về
//				while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//					String maHD = rs.getString(1);
//					NhanVien nv = new NhanVien(rs.getString(2));
//					String tenKH = rs.getString(3);
//					String tenNV = rs.getString(4);
//					KhachHang kh = new KhachHang(rs.getString(5));
//					Date ngayLapHD = rs.getDate(6);
//					LocalTime thoiGianTraPhong = rs.getTime(7).toLocalTime();
//					KhuyenMai km = new KhuyenMai(rs.getString(8));
//					double tongTienDV = rs.getDouble(9);
//					double tongTienPhong = rs.getDouble(10);
//
//					HoaDon hoaDon = new HoaDon(maHD, nv, tenNV, tenKH, kh, ngayLapHD, thoiGianTraPhong, km);
//					HoaDonThongKe hdtk = new HoaDonThongKe(hoaDon, tongTienDV, tongTienPhong);
//					dsHDTK.add(hdtk);
//				}
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			} finally {
//				// Đóng connection, statement và resultSet ở đây
//			}
//			return dsHDTK;
//		}
//
//		public ArrayList<HoaDonThongKe> thongKeDoanhThuTheoNam(int nam) {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			ArrayList<HoaDonThongKe> dsHDTK = new ArrayList<HoaDonThongKe>();
//			PreparedStatement statement = null;
//			ResultSet rs;
//
//			try {
//				String sql = "SELECT\r\n"
//						+ "    HD.MaHD,\r\n"
//						+ "    HD.MaNV,\r\n"
//						+ "    HD.TenKH,\r\n"
//						+ "    HD.TenNV,\r\n"
//						+ "	HD.MaKH,\r\n"
//						+ "    HD.NgayLapHD,\r\n"
//						+ "    HD.Thoigiantraphong,\r\n"
//						+ "	HD.MaKM,\r\n"
//						+ "    SUM((COALESCE(CTDV.SoLuong * CTDV.DonGia, 0) - COALESCE(CTDV.SoLuong * CTDV.DonGia * KM.GiaTriKM, 0)) * COALESCE(KM.GiaTriKM, 1)) AS TongTienDV,\r\n"
//						+ "    SUM((COALESCE(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia, 0) - COALESCE(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia * KM.GiaTriKM, 0)) * COALESCE(KM.GiaTriKM, 1)) AS TongTienSDPhong\r\n"
//						+ "FROM\r\n"
//						+ "    HoaDon HD\r\n"
//						+ "LEFT JOIN\r\n"
//						+ "    KhuyenMai KM ON KM.MaKM = HD.MaKM AND HD.MaKM IS NOT NULL\r\n"
//						+ "LEFT JOIN\r\n"
//						+ "    ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD\r\n"
//						+ "LEFT JOIN\r\n"
//						+ "    ChiTietSuDungDichVuHoaDon CTDV ON HD.MaHD = CTDV.MaHD\r\n"
//						+ "WHERE\r\n"
//						+ "   YEAR(NgayLapHD) = ?\r\n"
//						+ "GROUP BY\r\n"
//						+ "    HD.MaHD,\r\n"
//						+ "    HD.MaNV,\r\n"
//						+ "    HD.TenKH,\r\n"
//						+ "    HD.TenNV,\r\n"
//						+ "    HD.MaKH,\r\n"
//						+ "    HD.NgayLapHD,\r\n"
//						+ "    HD.Thoigiantraphong,\r\n"
//						+ "    HD.MaKM,\r\n"
//						+ "    KM.GiaTriKM;";
//				statement = con.prepareStatement(sql);
//				statement.setInt(1, nam);
//				// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//				rs = statement.executeQuery();
//				// Duyệt kết quả trả về
//				while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//					String maHD = rs.getString(1);
//					NhanVien nv = new NhanVien(rs.getString(2));
//					String tenKH = rs.getString(3);
//					String tenNV = rs.getString(4);
//					KhachHang kh = new KhachHang(rs.getString(5));
//					Date ngayLapHD = rs.getDate(6);
//					LocalTime thoiGianTraPhong = rs.getTime(7).toLocalTime();
//					KhuyenMai km = new KhuyenMai(rs.getString(8));
//					double tongTienDV = rs.getDouble(9);
//					double tongTienPhong = rs.getDouble(10);
//
//					HoaDon hoaDon = new HoaDon(maHD, nv, tenNV, tenKH, kh, ngayLapHD, thoiGianTraPhong, km);
//					HoaDonThongKe hdtk = new HoaDonThongKe(hoaDon, tongTienDV, tongTienPhong);
//					dsHDTK.add(hdtk);
//				}
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			} finally {
//				// Đóng connection, statement và resultSet ở đây
//			}
//			return dsHDTK;
//		}
//
//		public Double[] thongKeBieuDoLineTongDoanhThu(int namTK) {
//			Double[] thongKeList = new Double[12];
//			for (int i = 0; i < thongKeList.length; i++) {
//				thongKeList[i] = (double) 0;
//			}
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			PreparedStatement statement = null;
//			ResultSet rs;
//			try {
//				String sql = "SELECT \r\n" + "	MONTH(NgayLapHD) as Thang, YEAR(NgayLapHD) as Nam,\r\n"
//						+ "    SUM(CTDV.SoLuong * CTDV.DonGia) AS TongTienDV,\r\n"
//						+ "    SUM(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia) AS TongTienSDPhong\r\n"
//						+ "FROM \r\n" + "    HoaDon HD\r\n" + "LEFT JOIN \r\n"
//						+ "    ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD\r\n" + "LEFT JOIN \r\n"
//						+ "    ChiTietSuDungDichVuHoaDon CTDV ON HD.MaHD = CTDV.MaHD\r\n" + "WHERE\r\n"
//						+ "   YEAR(NgayLapHD) = ?\r\n" + "Group by\r\n" + "	MONTH(NgayLapHD), YEAR(NgayLapHD)\r\n"
//						+ "Order by\r\n" + "	YEAR(NgayLapHD) DESC, MONTH(NgayLapHD) DESC";
//				statement = con.prepareStatement(sql);
//				statement.setInt(1, namTK);
//				// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//				rs = statement.executeQuery();
//				// Duyệt kết quả trả về
//				while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//					thongKeList[rs.getInt(1) - 1] = rs.getDouble(3) + rs.getDouble(4);
//				}
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			return thongKeList;
//		}
//
//		public int[] thongKeBieuDoLineTongHoaDon(int namTK) {
//			int[] thongKeList = new int[12];
//			for (int i = 0; i < thongKeList.length; i++) {
//				thongKeList[i] = 0;
//			}
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			PreparedStatement statement = null;
//			ResultSet rs;
//			try {
//				String sql = "SELECT \r\n" + "    MONTH(NgayLapHD) as Thang, \r\n" + "    YEAR(NgayLapHD) as Nam,\r\n"
//						+ "    SUM(CTDV.SoLuong * CTDV.DonGia) AS TongTienDV,\r\n"
//						+ "    SUM(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia) AS TongTienSDPhong,\r\n"
//						+ "	COUNT(HD.MaHD) as SoHoaDon\r\n" + "FROM \r\n" + "    HoaDon HD\r\n" + "LEFT JOIN \r\n"
//						+ "    ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD\r\n" + "LEFT JOIN \r\n"
//						+ "    ChiTietSuDungDichVuHoaDon CTDV ON HD.MaHD = CTDV.MaHD\r\n" + "WHERE\r\n"
//						+ "    YEAR(NgayLapHD) = ?\r\n" + "GROUP BY\r\n" + "    MONTH(NgayLapHD), YEAR(NgayLapHD)\r\n"
//						+ "ORDER BY\r\n" + "    YEAR(NgayLapHD) DESC, MONTH(NgayLapHD) DESC;";
//				statement = con.prepareStatement(sql);
//				statement.setInt(1, namTK);
//				// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//				rs = statement.executeQuery();
//				// Duyệt kết quả trả về
//				while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//					thongKeList[rs.getInt(1) - 1] = rs.getInt(5);
//				}
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			return thongKeList;
//		}
//
//		public Double[] thongKeBieuDoLineDoanhThuCaoNhatMoiThang(int namTK) {
//			Double[] thongKeList = new Double[12];
//			for (int i = 0; i < thongKeList.length; i++) {
//				thongKeList[i] = (double) 0;
//			}
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			PreparedStatement statement = null;
//			ResultSet rs;
//			try {
//				String sql = "WITH RankedRevenue AS (\r\n" + "    SELECT \r\n" + "        HD.MaHD,\r\n"
//						+ "        MONTH(HD.NgayLapHD) as Thang, \r\n" + "        YEAR(HD.NgayLapHD) as Nam,\r\n"
//						+ "        COALESCE(SUM(CTDV.SoLuong * CTDV.DonGia), 0) AS TongTienDV,\r\n"
//						+ "        COALESCE(SUM(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia), 0) AS TongTienSDPhong,\r\n"
//						+ "        COALESCE(SUM(CTDV.SoLuong * CTDV.DonGia), 0) + COALESCE(SUM(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia), 0) AS TongDoanhThu\r\n"
//						+ "    FROM \r\n" + "        HoaDon HD\r\n" + "    LEFT JOIN \r\n"
//						+ "        ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD\r\n" + "    LEFT JOIN \r\n"
//						+ "        ChiTietSuDungDichVuHoaDon CTDV ON HD.MaHD = CTDV.MaHD\r\n" + "    WHERE\r\n"
//						+ "        YEAR(HD.NgayLapHD) = ? -- @Nam là biến bạn truyền vào\r\n" + "    GROUP BY\r\n"
//						+ "        HD.MaHD, MONTH(HD.NgayLapHD), YEAR(HD.NgayLapHD)\r\n" + ")\r\n" + "SELECT \r\n"
//						+ "    Thang, \r\n" + "    Nam,\r\n" + "    MAX(TongDoanhThu) AS DoanhThuCaoNhatTrongThang\r\n"
//						+ "FROM \r\n" + "    RankedRevenue\r\n" + "GROUP BY\r\n" + "    Thang, Nam;";
//				statement = con.prepareStatement(sql);
//				statement.setInt(1, namTK);
//				// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//				rs = statement.executeQuery();
//				// Duyệt kết quả trả về
//				while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//					thongKeList[rs.getInt(1) - 1] = rs.getDouble(3);
//				}
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			return thongKeList;
//		}
//
//		public Double[] thongKeBieuDoLineDoanhThuThapNhatMoiThang(int namTK) {
//			Double[] thongKeList = new Double[12];
//			for (int i = 0; i < thongKeList.length; i++) {
//				thongKeList[i] = (double) 0;
//			}
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			PreparedStatement statement = null;
//			ResultSet rs;
//			try {
//				String sql = "WITH RankedRevenue AS (\r\n" + "    SELECT \r\n" + "        HD.MaHD,\r\n"
//						+ "        MONTH(HD.NgayLapHD) as Thang, \r\n" + "        YEAR(HD.NgayLapHD) as Nam,\r\n"
//						+ "        COALESCE(SUM(CTDV.SoLuong * CTDV.DonGia), 0) AS TongTienDV,\r\n"
//						+ "        COALESCE(SUM(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia), 0) AS TongTienSDPhong,\r\n"
//						+ "        COALESCE(SUM(CTDV.SoLuong * CTDV.DonGia), 0) + COALESCE(SUM(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia), 0) AS TongDoanhThu,\r\n"
//						+ "        ROW_NUMBER() OVER (PARTITION BY MONTH(HD.NgayLapHD), YEAR(HD.NgayLapHD) ORDER BY (COALESCE(SUM(CTDV.SoLuong * CTDV.DonGia), 0) + COALESCE(SUM(DATEDIFF(SECOND, '00:00:00', CTHD.ThoiGianSuDungPhong) / 3600.0 * CTHD.DonGia), 0)) ASC) as RowNum\r\n"
//						+ "    FROM \r\n" + "        HoaDon HD\r\n" + "    LEFT JOIN \r\n"
//						+ "        ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD\r\n" + "    LEFT JOIN \r\n"
//						+ "        ChiTietSuDungDichVuHoaDon CTDV ON HD.MaHD = CTDV.MaHD\r\n" + "    WHERE\r\n"
//						+ "        YEAR(HD.NgayLapHD) = ? -- @Nam là biến bạn truyền vào\r\n" + "    GROUP BY\r\n"
//						+ "        HD.MaHD, MONTH(HD.NgayLapHD), YEAR(HD.NgayLapHD)\r\n" + ")\r\n" + "SELECT \r\n"
//						+ "    Thang, \r\n" + "    Nam,\r\n" + "    MIN(TongDoanhThu) AS DoanhThuThapNhatTrongThang\r\n"
//						+ "FROM \r\n" + "    RankedRevenue\r\n" + "WHERE \r\n" + "    RowNum = 1\r\n" + "GROUP BY\r\n"
//						+ "    Thang, Nam;";
//				statement = con.prepareStatement(sql);
//				statement.setInt(1, namTK);
//				// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//				rs = statement.executeQuery();
//				// Duyệt kết quả trả về
//				while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//					thongKeList[rs.getInt(1) - 1] = rs.getDouble(3);
//				}
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			return thongKeList;
//		}
}
